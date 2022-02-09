package pers.aztim.tankwar;

import java.util.Vector;

public class Tank extends MovableElement {
    public static final int TANK_SIZE_X = 60;
    public static final int TANK_SIZE_Y = 60;
    private static final Vector<Tank> allTanks = new Vector<>();;
    private static final TankMonitor TANK_MONITOR = TankMonitor.getTankMonitor();

    private static class TankMonitor implements Runnable {
        private static final TankMonitor tankMonitor = new TankMonitor();

        private TankMonitor() {
            new Thread(this).start();
        }

        public void add(Tank tank) {
            allTanks.add(tank);
        }

        public static TankMonitor getTankMonitor() {
            return tankMonitor;
        }

        @Override
        public void run() {
            while (true) {
                // 两两检查是否碰撞，如果碰撞就恢复到上一步的状态
                int len = allTanks.size();
                Tank t1;
                Tank t2;
                for (int i = 0; i < len; ++i) {
                    t1 = allTanks.get(i);
                    for (int j = i + 1; j < len; ++j) {
                        t2 = allTanks.get(j);
                        if (MovableElement.isTouched(t1, t2)) {
                            t1.recover();
                            t2.recover();
                        }
                    }
                }
                // 清理已经消亡的坦克
                allTanks.removeIf(tank -> !tank.isAlive());
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private final Identity identity;
    private final int bulletCapacity;
    private final Vector<Bullet> bullets;
    private int bulletSpeed = 15;
    private int prevX;
    private int prevY;

    public Tank(int x, int y, Identity identity, int bulletCapacity) {
        super(x, y, TANK_SIZE_X, TANK_SIZE_Y, Direction.UP, 3);
        this.identity = identity;
        this.bulletCapacity = bulletCapacity;
        bullets = new Vector<>();
        this.prevX = x;
        this.prevY = y;
        TANK_MONITOR.add(this);
    }

    @Override
    public void stepForward() throws OutOfMap {
        this.prevX = getX();
        this.prevY = getY();
        super.stepForward();
    }

    private void recover() {
        this.setX(prevX);
        this.setY(prevY);
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




