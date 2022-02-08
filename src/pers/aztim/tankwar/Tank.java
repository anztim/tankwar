package pers.aztim.tankwar;

import java.util.Vector;

public class Tank extends MovableElement {
    public static final int TANK_SIZE_X = 60;
    public static final int TANK_SIZE_Y = 60;

    private final Identity identity;
    private final int bulletCapacity;
    private final Vector<Bullet> bullets;
    private int bulletSpeed = 15;

    public Tank(int x, int y, Identity identity, int bulletCapacity) {
        super(x, y, TANK_SIZE_X, TANK_SIZE_Y, Direction.UP, 3);
        this.identity = identity;
        this.bulletCapacity = bulletCapacity;
        bullets = new Vector<>();
    }

    @Override
    public void step(Direction d) {
        try {
            super.step(d);
        } catch (OutOfMap ignore) {
        }
    }

    public Bullet fire() {
        if (bullets.size() >= bulletCapacity) return null;
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
        Bullet bullet = new Bullet(genX, genY, genD, bulletSpeed, identity, this);
        bullets.add(bullet);
        return bullet;

    }

    public void removeBullet(Bullet bullet) {
        this.bullets.remove(bullet);
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }
}




