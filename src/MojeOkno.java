import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MojeOkno extends JFrame implements ActionListener, MouseListener, MouseMotionListener
{
    PanelGraficzny panel;
    Menu menu = new Menu();
    Przyciski przyciski;
    
    String sciezkaPliku; 
    public MojeOkno() 
    {
        //wywolanie konstruktora klasy nadrzednej (JFrame)
        super("Krzywe Beziera");
        //ustawienie standardowej akcji po naci�ni�ciu przycisku zamkniecia
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        przyciski = new Przyciski();
        panel = new PanelGraficzny(1000, 600);
        GridBagConstraints c = new GridBagConstraints();
        //rozmieszczenie elementow - menadzer rozkladu
        //FlowLayout ustawia elementy jeden za drugim
        //w tym przypadku dodatkowo wysrodkowane na ekranie, z odst�pem w pionie i poziomie
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));  
        setJMenuBar(menu);
        add(panel);
        przyciski = new Przyciski();
        add(przyciski);
        //nas�uch zdarze�
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);          
        //dopasowanie rozmiaru okna do zawarto�ci
        pack(); 
        /*c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        add(przyciski,c);

        c.gridx = 1;
        c.gridy = 0;
        add(panel,c);*/
        //wysrodkowanie okna na ekranie
        setLocationRelativeTo(null);          
        //wyswietlenie naszej ramki
        nasluchZdarzen();        
        dopasujZawartosc();
        setVisible(true);
    }

    private void nasluchZdarzen()
    {
        menu.otworzPlik.addActionListener(this);
        menu.zapiszPlik.addActionListener(this); 
        menu.zakoncz.addActionListener(this);  

        for(int i=0; i<przyciski.przycisk.length; i++)
        {  
            przyciski.przycisk[i].addActionListener(this);
        } 
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String label = e.getActionCommand();
        
        if(label.equals("Zapisz plik"))
        {
            zapiszPlik();
        } 
        else if(label.equals("Zako�cz"))
        {
            System.exit(0);
        }
        else if(label.equals("Wyczy��"))
        {
            panel.wyczysc();
        }
        else if(label.equals("Rysuj �aman�"))
        {
            panel.rysujLamana();
        }
          else if(label.equals("Rysuj krzyw� Beziera"))
        {
            if(panel.lista_bezier.isEmpty())
            JOptionPane.showMessageDialog(null, "Kliknij najpierw oblicz punkty Beziera");
            else
            panel.rysujKrzywaBeziera();
        }
        else if(label.equals("Oblicz punkty Beziera"))
        {
            double dokladnosc;
            if(przyciski.pola[0].getText().equals(""))
            dokladnosc=0.01;
            else
            dokladnosc=Double.parseDouble(przyciski.pola[0].getText());
            panel.krzywaBeziera(dokladnosc);
            JOptionPane.showMessageDialog(null, "Punkty zosta�y obliczone kliknij rysuj krzyw�");
        }
        else if(label.equals("Od�wie�"))
        {
            panel.rysujKrzywaBeziera();
            panel.rysujLamana();
        }
        else if(label.equals("Przesu�"))
        {
            double x, y;
            if(przyciski.pola[1].getText().equals(""))
            x=0.0;
            else 
            x=Double.parseDouble(przyciski.pola[1].getText());
            if(przyciski.pola[2].getText().equals(""))
            y=0.0;
            else 
            y=Double.parseDouble(przyciski.pola[2].getText());
            panel.przesun(x,y);
            JOptionPane.showMessageDialog(null, "Punkty zosta�y przesuni�te kliknij kt�r� krzyw� rysowa�, Beziera, czy �aman�");
        }
        else if(label.equals("Obr��"))
        {
            double k;
            if(przyciski.pola[3].getText().equals(""))
            k=0.0;
            else 
            k=Double.parseDouble(przyciski.pola[3].getText());
            panel.obracanie(k);
            JOptionPane.showMessageDialog(null, "Punkty zosta�y obr�cone kliknij kt�r� krzyw� rysowa�, Beziera, czy �aman�");
        }
        
        else if(label.equals("Skaluj"))
        {
            double x, y;
            if(przyciski.pola[4].getText().equals(""))
            x=0.0;
            else 
            x=Double.parseDouble(przyciski.pola[4].getText());
            if(przyciski.pola[5].getText().equals(""))
            y=0.0;
            else 
            y=Double.parseDouble(przyciski.pola[5].getText());
            panel.skaluj(x, y);
            JOptionPane.showMessageDialog(null, "Punkty zosta�y przeskolowane kliknij kt�r� krzyw� rysowa�, Beziera, czy �aman�");
        }
        
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        //panel.wyczysc();
    }

    //klik myszk� na prawy panel
    @Override
    public void mousePressed(MouseEvent e) {
        //klikni�cie na prawy panel powoduje zaznaczenie tej pozycji
       panel.zaznaczMiejsce(e.getX(),e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
            //System.out.println("("+e.getX() + "," + e.getY()+")");  
    }    
    
    private void zapiszPlik()
    {
        JFileChooser zapisz;
        if(sciezkaPliku != null)
            zapisz = new JFileChooser(sciezkaPliku);    
        else
            zapisz = new JFileChooser();
        FileNameExtensionFilter filtr = new FileNameExtensionFilter("BMP & PNG Images", "bmp", "png");
        zapisz.setFileFilter(filtr);
        int wynik = zapisz.showSaveDialog(this);      
        if (wynik == JFileChooser.APPROVE_OPTION)   
        {
            sciezkaPliku= zapisz.getSelectedFile().getPath();    
            panel.zapiszPlikGraficzny(sciezkaPliku);
        }        
    }

    private void dopasujZawartosc()
    {
        pack();   
        setLocationRelativeTo(null);           
    }

}