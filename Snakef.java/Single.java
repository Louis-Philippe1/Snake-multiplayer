import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;


class Single extends JPanel implements ActionListener {
    
    private JButton b1 = new JButton();
    GameStatus ready;

    public Single() {

        ready = GameStatus.PAUSED;
        add(b1);
        b1.addActionListener(this);
        setBackground(Color.ORANGE);
        setUpKeyBinding();
        
    }

    private void setUpKeyBinding() {
        
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
        this.getActionMap().put("left",  new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("left");
            }
        });
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
        this.getActionMap().put("right",  new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("right");
            }
        });
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
        this.getActionMap().put("up",  new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("up");
            }
        });
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
        this.getActionMap().put("down",  new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("down");
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            goTo();
            System.out.println(ready);
        }
    }

    public void goTo() {
        Main.tabs.show(Main.panel, "Main Menu");
    }
}
