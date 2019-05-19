import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.geom.Ellipse2D;

public class PanelGraficzny extends JPanel 
{
    //obiekt do przechowywania grafiki
    BufferedImage plotno;
    
    ArrayList<Punkt> lista_punktow = new ArrayList<Punkt>();
    ArrayList<Punkt> lista_przeksztalcona = new ArrayList<Punkt>();
    ArrayList<Punkt> lista_bezier = new ArrayList<Punkt>();

    public PanelGraficzny(int szer, int wys) 
    {
        super();   
        ustawRozmiar(new Dimension(szer,wys));

        wyczysc();
    }    

    public void ustawRozmiar(Dimension r)
    {
        //przygotowanie p³ótna
        plotno = new BufferedImage((int)r.getWidth(), (int)r.getHeight(), BufferedImage.TYPE_INT_RGB);
        setPreferredSize(r);     
    }    

    public void wyczysc()
    {
        //wyrysowanie bia³ego t³a
        Graphics2D g = (Graphics2D) plotno.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, plotno.getWidth(), plotno.getHeight());
        repaint();
        lista_punktow.clear();
        lista_przeksztalcona.clear();

    }

    public void zapiszPlikGraficzny(String sciezka)
    {
        File plikGraficzny = new File(sciezka); 
        try {
            if(plotno != null)
            {
                if(!ImageIO.write(plotno, sciezka.substring(sciezka.lastIndexOf('.') + 1), new File(sciezka)))
                {
                    JOptionPane.showMessageDialog(null,"Nie uda³o sie zapisaæ pliku w " + sciezka);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Brak obrazu do zapisania");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Nie uda³o sie zapisaæ pliku w " + sciezka);
        } 
    }

    public void zaznaczMiejsce(int x, int y)
    {

        Graphics2D g = (Graphics2D) plotno.getGraphics();

        Punkt tmp = new Punkt((double)x,(double)y);

        lista_punktow.add(tmp);
        lista_przeksztalcona.add(tmp);

        g.setColor(Color.BLACK);
        Ellipse2D circle = new Ellipse2D.Double(x-3, y-3, 6, 6);
        g.draw(circle);
        g.fill(circle);
        repaint();

    }     

    void rysujLamana()
    {
        Graphics2D g = (Graphics2D) plotno.getGraphics();
        g.setColor(Color.red);

        int i=0;
        while(i<lista_przeksztalcona.size()-1)
        {
            g.fillOval((int)lista_przeksztalcona.get(i).dajX(), (int)lista_przeksztalcona.get(i).dajY(), 1, 1);
            g.drawLine((int)lista_przeksztalcona.get(i).dajX(),(int)lista_przeksztalcona.get(i).dajY(), 
                (int)lista_przeksztalcona.get(i+1).dajX(),(int)lista_przeksztalcona.get(i+1).dajY());
            i++;
            repaint();
        }
    }

    void rysujLamana(ArrayList<Punkt> lista_2)
    {
        Graphics2D g = (Graphics2D) plotno.getGraphics();
        g.setColor(Color.red);

        int i=0;
        while(i<lista_2.size()-1)
        {
            g.fillOval((int)lista_2.get(i).dajX(), (int)lista_2.get(i).dajY(), 1, 1);
            g.drawLine((int)lista_2.get(i).dajX(),(int)lista_2.get(i).dajY(), 
                (int)lista_2.get(i+1).dajX(),(int)lista_2.get(i+1).dajY());
            i++;
            repaint();
        }
    }

    void rysujKrzywaBeziera()
    {
        Graphics2D g = (Graphics2D) plotno.getGraphics();
        g.setColor(Color.blue);

        int i=0;
        while(i<lista_bezier.size()-1)
        {
            g.fillOval((int)lista_bezier.get(i).dajX(), (int)lista_bezier.get(i).dajY(), 1, 1);
            g.drawLine((int)lista_bezier.get(i).dajX(),(int)lista_bezier.get(i).dajY(), 
                (int)lista_bezier.get(i+1).dajX(),(int)lista_bezier.get(i+1).dajY());
            i++;
            repaint();
        }
        /*for(int j=0; j< lista_bezier.size()-1; j++)
        {
        System.out.print("tabBezier["+j+"]= "+lista_bezier.get(j+1).dajX()+", "+lista_bezier.get(j+1).dajY()+"\n");
        }*/
    }

    void krzywaBeziera(double t)
    {
        double wynik_x = 0;
        double wynik_y = 0;
        int n = lista_przeksztalcona.size()-1;

        //lista_2.clear();
        lista_bezier.clear();
        lista_bezier.add(lista_przeksztalcona.get(0));

        for(double x = t; x < 1.0; x += t)
        {
            for(int i = 0; i <= n; i++)
            {
                wynik_x += (lista_przeksztalcona.get(i).dajX() * Bezier(n,i,x));
                wynik_y += (lista_przeksztalcona.get(i).dajY() * Bezier(n,i,x));
            }
            Punkt tmp = new Punkt(wynik_x, wynik_y);

            lista_bezier.add(tmp);

            wynik_x = wynik_y = 0;
        }
        lista_bezier.add(lista_przeksztalcona.get(n));
        //panel.bezier(lista_bezier); rysuj krzywwa beziera
    }

    public double Bezier(int n, int i, double t)
    {
        double x = (Silnia(n)/(Silnia(i)*Silnia(n-i)))*Math.pow(t,i)*Math.pow(1-t,n-i);
        return x;
    }

    public double Silnia(int i) 
    {
        if (i == 0) 
            return 1;
        else 
            return i * Silnia(i - 1);
    }    

    void przesun(double x, double y)
    {
        double macierzWynikowa[][]= macierzJednostkowa();
        double [][] macierzObrotu = {{1, 0, 0},{0, 1, 0},{x, y, 1}};

        macierzWynikowa = wymnorzTablice(macierzObrotu, macierzWynikowa);
        lista_przeksztalcona=wymnorzPunktzMacierza(macierzWynikowa, lista_przeksztalcona);
        lista_bezier=wymnorzPunktzMacierza(macierzWynikowa, lista_bezier);

    }

    public void obracanie(double x)
    {
        double macierzWynikowa[][]= macierzJednostkowa();
        double macierzObrotu[][]=new double[3][3];

        for(int i=0; i<macierzObrotu.length; i++)
        {
            for(int j=0; j<macierzObrotu.length; j++)
            {
                if(i==j)
                {
                    macierzObrotu[i][j] = 1;
                }
                else
                {
                    macierzObrotu[i][j] = 0;
                }
                macierzObrotu[0][0] = Math.cos(Math.toRadians(x));
                macierzObrotu[0][1] = Math.sin(Math.toRadians(x));
                macierzObrotu[1][0] = Math.sin(Math.toRadians(x))*(-1);
                macierzObrotu[1][1] = Math.cos(Math.toRadians(x));

            }

        }

        macierzWynikowa = wymnorzTablice(macierzObrotu, macierzWynikowa);
        lista_przeksztalcona=wymnorzPunktzMacierza(macierzWynikowa, lista_przeksztalcona);
        lista_bezier=wymnorzPunktzMacierza(macierzWynikowa, lista_bezier);

    }

    public void skaluj(double x,double y)
    {
        double macierzWynikowa[][]= macierzJednostkowa();
        double [][] macierzObrotu = {{x, 0, 0},{0, y, 0},{0, 0, 1}};

        macierzWynikowa = wymnorzTablice(macierzObrotu, macierzWynikowa);
        lista_przeksztalcona=wymnorzPunktzMacierza(macierzWynikowa, lista_przeksztalcona);
        lista_bezier=wymnorzPunktzMacierza(macierzWynikowa, lista_bezier);

    }

    double[][] wymnorzTablice(double maObrotu[][], double maWynikowa[][])
    {
        double multiply[][]=new double [3][3];
        double sum=0;
        for (int c= 0; c < 3; c++)
        {
            for (int d= 0; d < 3; d++)
            {  
                for (int k= 0; k < 3; k++)
                {
                    sum = sum + maObrotu[c][k]*maWynikowa[k][d];
                }

                multiply[c][d] = sum;
                sum = 0;
            }
        }
        return multiply;
    }

    ArrayList<Punkt> wymnorzPunktzMacierza(double macierz[][], ArrayList<Punkt>lista)
    {
        ArrayList<Punkt> lista_po_wymnozeniu = new ArrayList<Punkt>();
        double punkt[]=new double[3];
        for(int i=0; i<lista.size(); i++)
        {
            punkt[0]= lista.get(i).dajX();
            punkt[1]= lista.get(i).dajY();
            punkt[2]= 1;

            double tmp_x = punkt[0]*macierz[0][0]+ punkt[1]*macierz[1][0]+ punkt[2]*macierz[2][0];
            double tmp_y = punkt[0]*macierz[0][1]+ punkt[1]*macierz[1][1]+ punkt[2]*macierz[2][1];
            double tmp_2   = punkt[0]*macierz[0][2]+ punkt[1]*macierz[1][2]+ punkt[2]*macierz[2][2];

            Punkt nowy = new Punkt(tmp_x,tmp_y);
            lista_po_wymnozeniu.add(nowy);

            //panel.zaznacz_2((int)tmp_x-3, (int)tmp_y-3, 6);

            tmp_x = tmp_y = tmp_2 = 0;
        }
        return lista_po_wymnozeniu;
    }

    double[][] macierzJednostkowa()
    {
        double macierzWynikowa[][]= new double[3][3];
        for(int i=0; i<macierzWynikowa.length; i++)
        {
            for(int j=0; j<macierzWynikowa.length; j++)
            {
                if(i==j)
                {
                    macierzWynikowa[i][j] = 1;
                }
                else
                {
                    macierzWynikowa[i][j] = 0;
                }

            }

        }
        return macierzWynikowa;
    }
    //przes³oniêta metoda paintComponent z klasy JPanel do rysowania
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //wyrysowanie naszego p³ótna na panelu 
        g2d.drawImage(plotno, 0, 0, this);
    }     
}