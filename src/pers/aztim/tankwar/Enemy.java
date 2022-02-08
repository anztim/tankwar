package pers.aztim.tankwar;

public class Enemy extends Tank {
    public Enemy(int x, int y) {
        super(x, y,Identity.ENEMY);
        this.setDirection(Direction.DOWN);
    }
}
