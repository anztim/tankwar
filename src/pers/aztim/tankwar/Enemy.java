package pers.aztim.tankwar;

public class Enemy extends Tank implements Runnable {
    public Enemy(int x, int y) {
        super(x, y, Identity.ENEMY,1);
        this.setDirection(Direction.DOWN);
    }

    @Override
    public void run() {
        while (this.isAlive()) {
            for (int i = 0; i < 30; ++i) {
                //移动
                try {
                    this.stepForward();
                } catch (OutOfMap e) {
                    //碰到边界就掉头
                    this.setDirection(this.getDirection().opposite());
                }
                //休眠
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //随机改变方向
            this.setDirection(Direction.random());
        }
    }
}
