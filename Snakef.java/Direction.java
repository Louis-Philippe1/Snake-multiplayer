enum Direction {
    UP, DOWN, LEFT, RIGHT;
    boolean isX() {
        return this == RIGHT || this == LEFT; 
    }
    
    boolean isY() {
        return this == UP || this == DOWN; 
    }
}