package pers.aztim.tankwar;

public abstract class MovableElement extends Element {
    private static final int MAP_EDGE_X = 1000;
    private static final int MAP_EDGE_Y = 750;
    private static final int[] NAVIGATION = {-1, 0, 1, 0, -1};
    private int speed;


    public static class OutOfMap extends Exception {
        MovableElement element;
        Direction direction;

        public OutOfMap(MovableElement element, Direction direction) {
            this.element = element;
            this.direction = direction;
        }
    }

    public static boolean isTouched(MovableElement e1, MovableElement e2) {
        return e1.getX() > e2.getX() - e1.getSizeX() &&
                e1.getX() < e2.getX() + e2.getSizeX() &&
                e1.getY() > e2.getY() - e1.getSizeY() &&
                e1.getY() < e2.getY() + e2.getSizeY();
    }

    public MovableElement(int x, int y, int sizeX, int sizeY, Direction direction, int speed) {
        super(x, y, sizeX, sizeY, direction);
        this.speed = speed;
    }

    public void step(Direction d) throws OutOfMap {
        setDirection(d);
        stepForward();
    }

    public void stepForward() throws OutOfMap {
        int nav = getDirection().ordinal();
        this.setX(this.getX() + NAVIGATION[nav + 1] * speed);
        this.setY(this.getY() + NAVIGATION[nav] * speed);
        edgeCheck();
    }


    private void edgeCheck() throws OutOfMap {
        int x = getX();
        int y = getY();
        int maxX = getMaxX();
        int maxY = getMaxY();
        if (x <= 0) {
            this.setX(0);
            throw new OutOfMap(this,Direction.LEFT);
        }
        if (x >= maxX) {
            this.setX(maxX);
            throw new OutOfMap(this,Direction.RIGHT);
        }
        if (y <= 0) {
            this.setY(0);
            throw new OutOfMap(this,Direction.UP);
        }
        if (y >= maxY) {
            this.setY(maxY);
            throw new OutOfMap(this,Direction.DOWN);
        }
    }

    public int getMaxX() {
        return MAP_EDGE_X - getSizeX();
    }

    public int getMaxY() {
        return MAP_EDGE_Y - getSizeY();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}


