package pers.aztim.tankwar;

public class Player extends Tank {
    public Player(int x, int y) {
        super(x, y,Identity.ALLY,5);
    }

    @Override
    public void step(Direction d) {
        try {
            super.step(d);
        } catch (OutOfMap ignore) {
        }
    }
}
