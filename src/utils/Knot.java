package utils;

import java.util.Objects;

public class Knot {

    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Knot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveRight() {
        this.x = x+1;
    }

    public void moveLeft() {
        this.x = x-1;
    }

    public void moveUp() {
        this.y = y+1;
    }

    public void moveDown() {
        this.y = y-1;
    }

    @Override
    public String toString() {
        return String.format("[%s,%s]", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Knot knot = (Knot) o;
        return x == knot.x && y == knot.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
