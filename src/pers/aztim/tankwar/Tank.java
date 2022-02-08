package pers.aztim.tankwar;

public class Tank extends MovableElement {
    public static final int TANK_SIZE_X = 60;
    public static final int TANK_SIZE_Y = 60;

    private final Identity identity;
    private int bulletSpeed = 15;

    public Tank(int x, int y, Identity identity) {
        super(x, y, TANK_SIZE_X, TANK_SIZE_Y, Direction.UP, 3);
        this.identity = identity;
    }

    @Override
    public void step(Direction d) {
        try {
            super.step(d);
        } catch (OutOfMap ignore) {
        }
    }

    public Bullet fire() {
        int genX = getX();
        int genY = getY();
        Direction genD = getDirection();
        switch (genD) {
            case UP:
                genX += (TANK_SIZE_X - Bullet.BULLET_SIZE_X) / 2;
                genY -= Bullet.BULLET_SIZE_Y;
                break;
            case DOWN:
                genX += (TANK_SIZE_X - Bullet.BULLET_SIZE_X) / 2;
                genY += TANK_SIZE_Y;
                break;
            case RIGHT:
                genX += TANK_SIZE_X;
                genY += (TANK_SIZE_Y - Bullet.BULLET_SIZE_Y) / 2;
                break;
            case LEFT:
                genX -= Bullet.BULLET_SIZE_X;
                genY += (TANK_SIZE_Y - Bullet.BULLET_SIZE_Y) / 2;
                break;
        }
        return new Bullet(genX, genY, genD, bulletSpeed, identity);

    }

    public Identity getIdentity() {
        return identity;
    }

    public void setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }
}




