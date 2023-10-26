import java.util.ArrayList;
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

class Snakes {
    private Direction direction;
    private Position head1;
    private ArrayList<Position> tail;

    public Snakes(int x, int y) {
        this.head1 = new Position(x, y);
        this.direction = Direction.RIGHT;
        this.tail = new ArrayList<Position>();
        this.tail.add(new Position(0, 0));
        this.tail.add(new Position(0, 0));
        this.tail.add(new Position(0, 0));
    }
    
    public void move() {
        ArrayList<Position> newTail = new ArrayList<Position>();
        for (int i = 0, size = tail.size(); i < size; i++) {
            Position previous = i == 0 ? head1 : tail.get(i - 1);
            newTail.add(new Position(previous.getX(), previous.getY()));
        }
        this.tail = newTail;
        this.head1.move(this.direction, 10);
    }
    public void turn(Direction d) {       
        if (d.isX() && direction.isY() || d.isY() && direction.isX()) {
            direction = d; 
        }       
    }
    public ArrayList<Position> getTail() {
        return this.tail;
    }

    public void addTail() {
        this.tail.add(new Position(-10, -10));
    }
    
    public Position getHead() {
        return this.head1;
    }

}
