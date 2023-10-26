import java.awt.CardLayout;
import javax.swing.*;

class Main {

    public static JFrame frame = new JFrame("Snake");
    public static JPanel panel = new JPanel();

    public static CardLayout tabs = new CardLayout();

    
    public static SnakeMenu p3 = new SnakeMenu();
    public static Multi p2 = new Multi();
    public static Single p1 = new Single();

    public static void main(String[] args) {
        
        panel.setLayout(tabs);
        panel.add(p3, "Main Menu");
        panel.add(p2, "Multi Player");
        panel.add(p1, "Single Player");
        
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(600, 600);
        p2.goTo();

        return;
    }

}
