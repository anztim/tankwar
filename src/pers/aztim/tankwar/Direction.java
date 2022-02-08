package pers.aztim.tankwar;

public enum Direction {
    UP, RIGHT, DOWN, LEFT;

    private static Direction get(int i) {
        return values()[i];
    }

    public static Direction random() {
        return get((int) (Math.random() * 4));
    }

    public Direction opposite() {
        return get((this.ordinal() + 2) % 4);
    }
}
