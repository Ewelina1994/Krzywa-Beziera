import javax.swing.*;
public class Menu extends JMenuBar
{
    JMenu plik = new JMenu("Plik");
    JMenuItem otworzPlik = new JMenuItem("Otwórz plik");
    JMenuItem zapiszPlik = new JMenuItem("Zapisz plik");
    JMenuItem  zakoncz = new JMenuItem("Zakoñcz");    
    public Menu()
    {
        plik.add(otworzPlik);
        plik.add(zapiszPlik);        
       
        plik.add(new JSeparator());        
        plik.add(zakoncz);     
        add(plik);                  
    }
}
