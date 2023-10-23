import java.awt.Color;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.*;
import javax.swing.*;

class SnakeMenu extends JPanel implements ActionListener {
    
    private JButton b1 = new JButton("Multi Player");
    private JButton b2 = new JButton("Single Player");
    private JButton b3 = new JButton("Quit Game");

    public GameStatus status = GameStatus.MENU;

    public SnakeMenu() {
        add(b1);
        add(b2);
        add(b3);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        setBackground(Color.BLUE);
        add(new Label("Menu"));
    }

    public void keybindings() {
        
    }
    

    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == b1) {
            goToMulti();
            status = GameStatus.MULTI_PLAYER;
            System.out.println(status);
        }
        if (e.getSource() == b2) {
            goToSingle();
            status = GameStatus.SINGLE_PLAYER;
            System.out.println(status);
        }

        if (e.getSource() == b3) {
            System.exit(0);
        }

    }

    public void goToMulti() {
        Main.tabs.show(Main.panel, "Multi Player");
    }

    public void goToSingle() {
        Main.tabs.show(Main.panel, "Single Player");
    }
}
