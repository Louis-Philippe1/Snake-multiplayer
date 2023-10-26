import java.awt.RenderingHints.Key;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;
import javax.imageio.ImageIO;
import java.util.Timer;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

class Multi extends JPanel implements ActionListener {

    private JButton b1 = new JButton("Back to Main Menu");
    private JButton b2 = new JButton("Pause");
    private JButton b3 = new JButton("reset");
    private int player1Points = 0;
    private int player2Points = 0;
    private Timer timer;
    private Position apple;
    private BufferedImage image;
    private boolean loadedApple = true;
    private GameStatus multi;
    private Snakes snake;
    private Snakes snake1;
    
    public Multi() {
        
        try {
            image = ImageIO.read(new File("apple.png"));
        } catch (IOException e) {
            loadedApple = false;
        }
        add(b1);
        b1.addActionListener(this);
        add(b2);
        b2.addActionListener(this);
        add(b3);
        b3.addActionListener(this);
        setBackground(Color.CYAN);
        setUpKeyBinding();
        setDoubleBuffered(true);
        multi = GameStatus.NOT_STARTED;
        snake = new Snakes(120, 240);
        snake1 = new Snakes(120, 360); //Player 2
        repaint();
        setVisible(true);
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
                timer = new Timer();
                timer.schedule(new GameLoop(), 0, 50);
                break;
            case PAUSED:
                timer.cancel();
                break;
            case NOT_STARTED:
                timer.cancel();
                break;
            case PLAYER_1_WINS:
                timer.cancel();
                break;
            case PLAYER_2_WINS:
                timer.cancel();
                break;
            case TIE:
                timer.cancel();
                break;
        }
        multi = newGameStatus;
    }

    private void setPause() {
        setStatus(multi == GameStatus.PAUSED ? GameStatus.MULTI_PLAYER : GameStatus.PAUSED);
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
                 KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left1");
        this.getActionMap().put("left1",  new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    snake.turn(Direction.LEFT);
                }
            });
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                 KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right1");
        this.getActionMap().put("right1",  new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    snake.turn(Direction.RIGHT);
                }
            });
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up1");
        this.getActionMap().put("up1",  new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    snake.turn(Direction.UP);
                }
            });
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down1");
        this.getActionMap().put("down1",  new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    snake.turn(Direction.DOWN);
                }
            });
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "left2");
        this.getActionMap().put("left2",  new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    snake1.turn(Direction.LEFT);
                }
            });
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "right2");
        this.getActionMap().put("right2",  new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    snake1.turn(Direction.RIGHT);
                }
            });
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "up2");
        this.getActionMap().put("up2",  new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    snake1.turn(Direction.UP);
                }
            });
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "down2");
        
        this.getActionMap().put("down2",  new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    snake1.turn(Direction.DOWN);
                }
            });
    }
    
    //private void 

    public void drawCenteredString(Graphics g, String text, int y) { 
        int x = 300;
        g.drawString(text, x, y);
    }

    private void reset() {
        setStatus(GameStatus.NOT_STARTED);
        snake = new Snakes(120, 240);
        snake1 = new Snakes(120, 120);
        repaint();
    }

    private void borders() {
        Position head1 = snake.getHead();
        Position head2 = snake1.getHead();
        
        boolean hitBoundary = head1.getX() <= 10 || head1.getX() >= 590 
            || head1.getY() <= 10 || head1.getY() >= 590;

        boolean hitBoundary2 = head2.getX() <= 10 || head2.getX() >= 590 
            || head2.getY() <= 10 || head2.getY() >= 590;
        
        if (hitBoundary) {
            setStatus(GameStatus.PLAYER_2_WINS);
            System.out.println(multi);
        }
        if (hitBoundary2) {
            setStatus(GameStatus.PLAYER_1_WINS);
            System.out.println(multi);
        }
        if (hitBoundary && hitBoundary2) {
            setStatus(GameStatus.TIE);
            System.out.println(multi);
        }
    }

    private void checkForWinner() {
        Position head1 = snake.getHead();
        Position head2 = snake1.getHead();
        
        for (int i = 0, size = snake1.getTail().size(); i < size; i++) {
            Position t = snake1.getTail().get(i);
            if (head1.intersects(t)) {
                setStatus(GameStatus.PLAYER_2_WINS);
                System.out.println(multi);
            }
            if (head1.intersects(t) && t == head2) {
                setStatus(GameStatus.TIE);
                System.out.println(multi);
            }
        }
        
        for (int i = 0, size = snake.getTail().size(); i < size; i++) {
            Position t = snake.getTail().get(i);
            if (head2.intersects(t)) {
                setStatus(GameStatus.PLAYER_1_WINS);
                System.out.println(multi);
            }
            if (head2.intersects(t) && t == head1) {
                setStatus(GameStatus.TIE);
                System.out.println(multi);
            }
        }
    }

    private void pointsMech() {}
    
    private void render(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        
        if (multi == GameStatus.NOT_STARTED) {
            drawCenteredString(g2d, "SNAKE", 200);
            drawCenteredString(g2d, "PLAYER 1 IS BLUE, PLAYER 2 IS RED", 250);
            drawCenteredString(g2d, "GAME", 300);
            drawCenteredString(g2d, "Press Enter to begin", 330);
            g2d.setColor(Color.BLACK);
        } 

        if (multi == GameStatus.PAUSED) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("Paused", 200, 100);
            g2d.setColor(Color.BLACK);
        }
        
        if (multi == GameStatus.TIE) {
            drawCenteredString(g2d, "TIE", 250);
            drawCenteredString(g2d, "Game over", 300);
            g2d.setColor(Color.BLACK);
        }
        if (multi == GameStatus.PLAYER_1_WINS) {
            drawCenteredString(g2d, "Blue wins", 300);
            g2d.setColor(Color.BLACK);
        }
        if (multi == GameStatus.PLAYER_2_WINS) {
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
        Position p1 = snake1.getHead();
        
        g2d.setColor(new Color(33, 70, 199));
        g2d.fillRect(p.getX(), p.getY(), 10, 10);

        for (int i = 0, size = snake.getTail().size(); i < size; i++) {
            Position t = snake.getTail().get(i);
            g2d.fillRect(t.getX(), t.getY(), 10, 10);
        }
        g2d.setColor(Color.RED);
        g2d.fillRect(p1.getX(), p1.getY(), 10, 10);

        for (int k = 0, size = snake1.getTail().size(); k < size; k++) {
            Position t = snake1.getTail().get(k);
            g2d.fillRect(t.getX(), t.getY(), 10, 10);
        }
        borders();
        checkForWinner();
    }

    private void update() {
        snake.move();
        snake1.move();

        if (apple != null && snake.getHead().intersects(apple, 20)) {
            snake.addTail();
            player1Points++;
            apple = null;
        }
        if (apple != null && snake1.getHead().intersects(apple, 20)) {
            snake1.addTail();
            player2Points++;
            apple = null;
        }
        if (apple == null) {
            spawnApple();
        }
    }

    public void spawnApple() {
        apple = new Position((new Random()).nextInt(580), (new Random()).nextInt(580));
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

    private class GameLoop extends java.util.TimerTask {
        public void run() {
            update();
            repaint();
        }
    }
}
