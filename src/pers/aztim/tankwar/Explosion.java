package pers.aztim.tankwar;


public class Explosion extends Element implements Runnable{
    public static final int EXPLOSION_SIZE_X = 60;
    public static final int EXPLOSION_SIZE_Y = 60;

    private int lifeTime = 8;

    public Explosion(int x, int y) {
        super(x, y, EXPLOSION_SIZE_X, EXPLOSION_SIZE_Y, Direction.UP);
    }

    public void decrease() {
        if(lifeTime >= 0){
            lifeTime--;
        }else{
            this.kill();
        }
    }

    public int getLifeTime() {
        return lifeTime;
    }

    @Override
    public void run() {
        while(this.isAlive()){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.decrease();
        }
    }
}
