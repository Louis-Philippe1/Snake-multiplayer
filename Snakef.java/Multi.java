import java.awt.RenderingHints.Key;
import java.awt.event.*;
import javax.swing.*;
import java.util.TimerTask;

import java.util.*;
import javax.imageio.ImageIO;
import java.util.Timer;
import java.awt.*;
import java.awt.image.*;
import java.io.*;


class Multi extends JPanel implements ActionListener {

    private JButton b1 = new JButton();
    private JButton b2 = new JButton("Pause");
    private Timer timer;
    private GameStatus multi;
    private Snakes snake;
    

    public Multi() {
        
        add(b1);
        b1.addActionListener(this);
        add(b2);
        b2.addActionListener(this);
        setBackground(Color.CYAN);
        setUpKeyBinding();
        setDoubleBuffered(true);
        multi = GameStatus.NOT_STARTED;
        snake = new Snakes(60, 60);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        render(g);

        Toolkit.getDefaultToolkit().sync();
    }


    private void setStatus(GameStatus newGameStatus) {
        switch (newGameStatus) {
            case MULTI_PLAYER:
                break;
            case PAUSED: 
                break;
            case NOT_STARTED:
                break;
        }
        multi = newGameStatus;
    }

    private void setUpKeyBinding() {
                 

     this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Ready");
            this.getActionMap().put("Ready",  new AbstractAction() {
                public void actionPerformed(ActionEvent e) {        
                    System.out.println("Go!");
                    setStatus(GameStatus.MULTI_PLAYER);
                }
            });       
        

        

            

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

            this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "left");
            this.getActionMap().put("left",  new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("left");
                }
            });
            this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "right");
            this.getActionMap().put("right",  new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("right");
                }
            });
            this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "up");
            this.getActionMap().put("up",  new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("up");
                }
            });

            this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "down");
        
            this.getActionMap().put("down",  new AbstractAction() {
            
                public void actionPerformed(ActionEvent e) {
                    System.out.println("down");
                }
            });
        }

    
    public void drawCenteredString(Graphics g, String text, int y) { 
        
        int x = 300;
        g.drawString(text, x, y);
    }

    private void render(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(Color.BLACK);
        if (multi == GameStatus.PAUSED) {
            drawCenteredString(g2d, "Paused", 200);
        }
        if (multi == GameStatus.NOT_STARTED) {
            drawCenteredString(g2d, "SNAKE", 200);
            drawCenteredString(g2d, "GAME", 300);
            drawCenteredString(g2d, "Press Enter to begin", 330);
            
            return;
        }
          
    }

    private void update() {
        snake.move();
    }

    private void togglePause() { 
        setStatus(multi == GameStatus.PAUSED ? GameStatus.MULTI_PLAYER : GameStatus.PAUSED);
    }


    

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == b1) {
            goTo();
        }
        if (e.getSource() == b2) {
            
            if (multi == GameStatus.MULTI_PLAYER) {
                setStatus(GameStatus.PAUSED);
                System.out.println(multi);
            }
            else if (multi == GameStatus.PAUSED) {
                setStatus(GameStatus.MULTI_PLAYER);
                System.out.println(multi);
            }
            else if (multi == GameStatus.NOT_STARTED) {
                setStatus(GameStatus.MULTI_PLAYER);
                System.out.println(multi);
            }
        }
    }

    
    public void goTo() {
        Main.tabs.show(Main.panel, "Main Menu");
    }

    private class GameLoop extends java.util.TimerTask {
        public void run() {
            update();
            repaint();
        }
    }
}
