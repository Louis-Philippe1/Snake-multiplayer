public class Position {

    private int y;
    private int x;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Position(Position p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    public void move(Direction d, int value) {
        switch(d) {
            case UP: 
                this.y -= value;
                break;
            case DOWN: 
                this.y += value;
                break;
            case RIGHT: 
                this.x += value;
                break;
            case LEFT: 
                this.x -= value;
                break;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position setX(int x) {
        this.x = x;
        return this;
    }

    public Position setY(int y) {
        this.y = y;
        return this;
    }

    public boolean equals(Position p) {
        return this.x == p.getX() && this.y == p.getY();
    }

    public String toString() { 
        return "(" + x + ", " + y + ")";
    }

    public boolean intersects(Position p) {
        return intersects(p, 10);
    }
    
    public boolean intersects(Position p, int tolerance) {
        int diffX = Math.abs(x - p.getX());
        int diffY = Math.abs(y - p.getY());
        return this.equals(p) || (diffX <= tolerance && diffY <= tolerance);
    }


}
 