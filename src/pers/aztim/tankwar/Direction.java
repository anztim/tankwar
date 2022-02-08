package pers.aztim.tankwar;

public enum Direction {
    UP(0), RIGHT(1), DOWN(2), LEFT(3);

    private static final Direction[] directions = {UP,RIGHT,DOWN,LEFT};

    private final int value;

    Direction(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public Direction get(int i){
        return directions[i];
    }
}
