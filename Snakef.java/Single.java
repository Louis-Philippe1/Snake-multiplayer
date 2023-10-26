import java.awt.event.*;
import javax.swing.*;
import java.awt.RenderingHints.Key;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.util.Timer;
import java.awt.*;
import java.awt.image.*;
import java.io.*;


class Single extends JPanel implements ActionListener {
    
    private JButton b1 = new JButton("Back to Main Menu");
    private JButton b2 = new JButton("Pause");
    private JButton b3 = new JButton("reset");
    private int playerPoints = 0;
    private int computerPoints = 0;
    private Timer timer;
    private Position apple;
    private BufferedImage image;
    private boolean loadedApple = true;
    private GameStatus single;
    private Snakes snake;

    public Single() {

        single = GameStatus.PAUSED;
        add(b1);
        b1.addActionListener(this);
        setBackground(Color.ORANGE);
        setUpKeyBinding();
        
    }

    private void setUpKeyBinding() {
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Ready");
        this.getActionMap().put("Ready",  new AbstractAction() {
                public void actionPerformed(ActionEvent e) {        
                    System.out.println("Go!");
                    setStatus(GameStatus.SINGLE_PLAYER);
                }
            });
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
        this.getActionMap().put("left",  new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                snake.turn(Direction.LEFT);
            }
        });
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
        this.getActionMap().put("right",  new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                snake.turn(Direction.RIGHT);
            }
        });
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
        this.getActionMap().put("up",  new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                snake.turn(Direction.UP);
            }
        });
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
        this.getActionMap().put("down",  new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                snake.turn(Direction.DOWN);
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
        Toolkit.getDefaultToolkit().sync();
    }

    public void drawCenteredString(Graphics g, String text, int y) { 
        int x = 300;
        g.drawString(text, x, y);
    }

    private void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        
        if (single == GameStatus.NOT_STARTED) {
            drawCenteredString(g2d, "SNAKE", 200);
            drawCenteredString(g2d, "PLAYER 1 IS BLUE, PLAYER 2 IS RED", 250);
            drawCenteredString(g2d, "GAME", 300);
            drawCenteredString(g2d, "Press Enter to begin", 330);
            g2d.setColor(Color.BLACK);
        } 

        if (single == GameStatus.PAUSED) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("Paused", 200, 100);
            g2d.setColor(Color.BLACK);
        }
        
        if (single == GameStatus.TIE) {
            drawCenteredString(g2d, "TIE", 250);
            drawCenteredString(g2d, "Game over", 300);
            g2d.setColor(Color.BLACK);
        }
        if (single == GameStatus.HUMAN_WINS) {
            drawCenteredString(g2d, "Blue wins", 300);
            g2d.setColor(Color.BLACK);
        }
        if (single == GameStatus.COMPUTER_WINS) {
            drawCenteredString(g2d, "Red wins", 300);
            g2d.setColor(Color.BLACK);
        }

        if (apple != null) {
            if (loadedApple) {
                g2d.drawImage(image, apple.getX(), apple.getY(), 60, 60, null);
            }
            else {
                g2d.setColor(Color.BLACK);
                g2d.fillOval(apple.getX(), apple.getY(), 10, 10);
                g2d.setBackground(Color.BLACK);
            }
        }
        
        Position p = snake.getHead();
        
        g2d.setColor(new Color(33, 70, 199));
        g2d.fillRect(p.getX(), p.getY(), 10, 10);

        for (int i = 0, size = snake.getTail().size(); i < size; i++) {
            Position t = snake.getTail().get(i);
            g2d.fillRect(t.getX(), t.getY(), 10, 10);
        }
        borders();
        checkForWinner();
    }

    private void borders() {
        Position head1 = snake.getHead();
        
        boolean hitBoundary = head1.getX() <= 10 || head1.getX() >= 590 
            || head1.getY() <= 10 || head1.getY() >= 590;

        
        if (hitBoundary) {
            setStatus(GameStatus.PLAYER_2_WINS);
            System.out.println(single);
        }
        
    }

    private void checkForWinner() {
        Position head1 = snake.getHead();
        
        
    }

    public void spawnApple() {
        apple = new Position((new Random()).nextInt(580), (new Random()).nextInt(580));
    }

    private void setStatus(GameStatus newGameStatus) {
        switch (newGameStatus) {
            case SINGLE_PLAYER:
                timer = new Timer();
                timer.schedule(new GameLoop(), 0, 50);
                break;
            case PAUSED:
                timer.cancel();
                break;
            case NOT_STARTED:
                timer.cancel();
                break;
            case COMPUTER_WINS:
                timer.cancel();
                break;
            case HUMAN_WINS:
                timer.cancel();
                break;
            case TIE:
                timer.cancel();
                break;
        }
        single = newGameStatus;
    }

    private void setPause() {
        setStatus(single == GameStatus.PAUSED ? GameStatus.SINGLE_PLAYER : GameStatus.PAUSED);
    }

    private void reset() {
        setStatus(GameStatus.NOT_STARTED);
        snake = new Snakes(120, 240);
        repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            reset();
            goTo();
        }
        if (e.getSource() == b2) {
            setPause();
        }
        if (e.getSource() == b3) {
            reset();
        }
    }

    public void goTo() {
        Main.tabs.show(Main.panel, "Main Menu");
    }

    private void update() {
        snake.move();

        if (apple != null && snake.getHead().intersects(apple, 20)) {
            snake.addTail();
            computerPoints++;
            apple = null;
        }
        if (apple == null) {
            spawnApple();
        }
    }


private class GameLoop extends java.util.TimerTask {
        public void run() {
            update();
            repaint();
        }
    }
}
