package pers.aztim.tankwar;

public class Bullet extends MovableElement implements Runnable {
    public static final int BULLET_SIZE_X = 4;
    public static final int BULLET_SIZE_Y = 4;

    private final Identity identity;
    private final Tank host;

    public Bullet(int x, int y, Direction direction, int speed,Identity identity, Tank host) {
        super(x, y, BULLET_SIZE_X, BULLET_SIZE_Y, direction, speed);
        this.identity = identity;
        this.host = host;
    }

    @Override
    public void kill() {
        super.kill();
        if(host != null && host.isAlive()) host.removeBullet(this);
    }

    @Override
    public void run() {
        while (this.isAlive()) {
            try {
                this.stepForward();
            } catch (OutOfMap e) {
                this.kill();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Identity getIdentity() {
        return identity;
    }
}
