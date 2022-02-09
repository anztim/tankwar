package pers.aztim.tankwar;

import java.io.Serializable;

public abstract class Element  implements Serializable {
    private final int sizeX;
    private final int sizeY;
    private int x;
    private int y;
    private Direction direction;
    private boolean alive = true;

    public Element(int x, int y, int sizeX, int sizeY, Direction direction) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void kill(){
        alive = false;
    }

    public int getCenterX() {
        return x + sizeX / 2;
    }

    public int getCenterY() {
        return y + sizeY / 2;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public int getSizeX() {
        if (direction == Direction.LEFT
                || direction == Direction.RIGHT) {
            return sizeY;
        }
        return sizeX;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public int getSizeY() {
        if (direction == Direction.LEFT
                || direction == Direction.RIGHT) {
            return sizeX;
        }
        return sizeY;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isAlive() {
        return alive;
    }
}
