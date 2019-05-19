import java.awt.*;
import javax.swing.*;

public class Przyciski extends JPanel
{
    JButton []przycisk=new JButton[8];
    JTextField []pola = new JTextField[6];
    JLabel []napis=new JLabel[7] ;
    GridBagConstraints c = new GridBagConstraints();
    
    public Przyciski()
    {
        super();
        setLayout(new GridBagLayout());
        przycisk[0]=new JButton("Wyczyœæ");
        przycisk[1]=new JButton("Oblicz punkty Beziera");
        przycisk[2]=new JButton("Rysuj ³aman¹");
        napis[0]=new JLabel("Podaj dok³adnoœæ krzywej beziera");
        pola[0]=new JTextField();
        przycisk[3]=new JButton("Rysuj krzyw¹ Beziera");
        
        przycisk[4]=new JButton("Odœwierz");
        napis[1]=new JLabel("Macierze:");
        napis[2]=new JLabel("Podaj x:");
        pola[1]=new JTextField();
        napis[3]=new JLabel("Podaj y:");
        pola[2]=new JTextField();
        przycisk[5]=new JButton("Przesuñ");
        
        napis[4]=new JLabel("Podaj o ile stopni obróciæ");
        pola[3]=new JTextField();
        przycisk[6]=new JButton("Obróæ");
        
        napis[5]=new JLabel("Podaj x:");
        pola[4]=new JTextField();
        napis[6]=new JLabel("Podaj y:");
        pola[5]=new JTextField();
        przycisk[7]=new JButton("Skaluj");
        
        
        c.fill = GridBagConstraints.HORIZONTAL;
        //c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        add(przycisk[0],c);
        
        c.gridx = 0;
        c.gridy = 1;
        add(przycisk[1],c);
        
        c.gridx = 0;
        c.gridy = 2;
        add(przycisk[2],c);
        
        c.gridx = 0;
        c.gridy = 3;
        add(napis[0],c);
        
        c.gridx = 0;
        c.gridy = 4;
        add(pola[0],c);
        
        c.gridx = 0;
        c.gridy = 5;
        add(przycisk[3],c);
        
        c.gridx = 0;
        c.gridy = 6;
        add(przycisk[4],c);
        
        c.gridx = 0;
        c.gridy = 7;
        add(napis[1],c);
        
        c.gridx = 0;
        c.gridy = 8;
        add(napis[2],c);
        
        c.gridx = 0;
        c.gridy = 9;
        add(pola[1],c);
        
        c.gridx = 0;
        c.gridy = 10;
        add(napis[3],c);
        
        c.gridx = 0;
        c.gridy = 11;
        add(pola[2],c);
        
        c.gridx = 0;
        c.gridy = 12;
        add(przycisk[5],c);
        
        c.gridx = 0;
        c.gridy = 13;
        add(napis[4],c);
        
        c.gridx = 0;
        c.gridy = 14;
        add(pola[3],c);
        
        c.gridx = 0;
        c.gridy = 15;
        add(przycisk[6],c);
        
        c.gridx = 0;
        c.gridy = 16;
        add(napis[5],c);
        
        c.gridx = 0;
        c.gridy = 17;
        add(pola[4],c);
        
        c.gridx = 0;
        c.gridy = 18;
        add(napis[6],c);
        
        c.gridx = 0;
        c.gridy = 19;
        add(pola[5],c);
        
        c.gridx = 0;
        c.gridy = 20;
        add(przycisk[7],c);
    }

}
