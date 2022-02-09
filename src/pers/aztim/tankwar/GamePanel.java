package pers.aztim.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.Vector;

public class GamePanel extends JPanel implements KeyListener, Runnable {

    private final static Image[] explosionImages = new Image[3];

    static {
        explosionImages[0] = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        explosionImages[1] = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        explosionImages[2] = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
    }

    private final Vector<Player> players;
    private final Vector<Enemy> enemies;
    private final Vector<Explosion> explosions;
    private final Vector<Bullet> allyBullets;
    private final Vector<Bullet> enemyBullets;


    private final int enemyNum = 6;

    public GamePanel() {
        players = new Vector<>();
        players.add(new Player(470, 550));

        enemies = new Vector<>();
        for (int i = 0; i < enemyNum; i++) {
            enemies.add(new Enemy(50 + i * 100, 20));
        }
        for (Enemy enemy : enemies) {
            new Thread(enemy).start();
        }

        explosions = new Vector<>();
        allyBullets = new Vector<>();
        enemyBullets = new Vector<>();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//background
        Iterator<?> iterator;
        iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Tank tank = (Tank) iterator.next();
            if (!tank.isAlive()) iterator.remove();
            else drawTank(tank, g);
        }
        iterator = players.iterator();
        while (iterator.hasNext()) {
            Tank tank = (Tank) iterator.next();
            if (!tank.isAlive()) iterator.remove();
            else drawTank(tank, g);
        }
        iterator = enemyBullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = (Bullet) iterator.next();
            if (!bullet.isAlive()) iterator.remove();
            else drawBullet(bullet, g);
        }
        iterator = allyBullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = (Bullet) iterator.next();
            if (!bullet.isAlive()) iterator.remove();
            else drawBullet(bullet, g);
        }
        showInfo(g);
    }

    private void drawTank(Tank tank, Graphics g) {
        Color color = null;
        //选择车体颜色
        switch (tank.getIdentity()) {
            case ALLY:
                color = Color.GRAY;
                break;
            case ENEMY:
                color = Color.ORANGE;
                break;
        }

        drawTank0(tank.getX(),tank.getY(),tank.getDirection(),color,Color.WHITE,g);
    }

    private void drawTank0(int x,int y,Direction direction,Color tankColor, Color weaponColor,Graphics g){
        //绘制车体
        g.setColor(tankColor);
        switch (direction) {
            case UP:
            case DOWN:
                g.fill3DRect(x, y, 15, 60, true);
                g.fill3DRect(x + 45, y, 15, 60, true);
                g.fillRect(x + 15, y + 10, 30, 40);
                break;
            case LEFT:
            case RIGHT:
                g.fill3DRect(x, y, 60, 15, true);
                g.fill3DRect(x, y + 45, 60, 15, true);
                g.fillRect(x + 10, y + 15, 40, 30);
                break;
        }

        g.setColor(weaponColor);
        //绘制圆形炮台
        g.fillOval(x + 20, y + 20, 20, 20);
        //绘制炮管
        switch (direction) {
            case UP:
                g.fillRect(x + 28, y, 4, 30);
                break;
            case RIGHT:
                g.fillRect(x + 30, y + 28, 30, 4);
                break;
            case DOWN:
                g.fillRect(x + 28, y + 30, 4, 30);
                break;
            case LEFT:
                g.fillRect(x, y + 28, 30, 4);
        }
    }

    private void drawBullet(Bullet bullet, Graphics g) {
        switch (bullet.getIdentity()) {
            case ALLY:
                g.setColor(Color.CYAN);
                break;
            case ENEMY:
                g.setColor(Color.RED);
                break;
        }
        g.fill3DRect(bullet.getX(), bullet.getY(), Bullet.BULLET_SIZE_X, Bullet.BULLET_SIZE_Y, true);
    }

    private void drawExplosion(Explosion explosion, Graphics g) {
        g.drawImage(explosionImages[explosion.getLifeTime() / 3],
                explosion.getX(), explosion.getY(),
                Explosion.EXPLOSION_SIZE_X, Explosion.EXPLOSION_SIZE_Y, this);
    }

    public void showInfo(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("宋体", Font.BOLD, 25));
        g.drawString("累计击毁地方坦克", 1020, 30);
        g.drawString(""+Recorder.instant().getRecord(),1100,100);
        drawTank0(1020,60,Direction.UP,Color.ORANGE,Color.CYAN,g);
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                players.get(0).step(Direction.UP);
                break;
            case KeyEvent.VK_A:
                players.get(0).step(Direction.LEFT);
                break;
            case KeyEvent.VK_S:
                players.get(0).step(Direction.DOWN);
                break;
            case KeyEvent.VK_D:
                players.get(0).step(Direction.RIGHT);
                break;
            case KeyEvent.VK_J:
                Bullet bullet = players.get(0).fire();
                if (bullet != null) {
                    allyBullets.add(bullet);
                    new Thread(bullet).start();
                }
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while (true) {
            //判断是否有子弹命中目标
            this.checkDamage(allyBullets, enemies);
            this.checkDamage(enemyBullets, players);
            //尝试让敌方坦克发射一枚子弹
            this.enemiesAutoFire();
            this.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkDamage(Vector<? extends Bullet> bullets, Vector<? extends Tank> targets) {
        for (Bullet bullet : bullets) {
            for (Tank target : targets) {
                //如果有子弹命中
                if (target.isAlive() && MovableElement.isTouched(bullet, target)) {
                    //创建爆炸效果
                    Explosion explosion = new Explosion(bullet.getCenterX() - Explosion.EXPLOSION_SIZE_X / 2
                            , bullet.getCenterY() - Explosion.EXPLOSION_SIZE_Y);
                    explosions.add(explosion);
                    new Thread(explosion).start();
                    //消除子弹和命中目标
                    bullet.kill();
                    target.kill();
                }
            }
        }
    }

    private void enemiesAutoFire() {
        for (Tank enemy : enemies) {
            Bullet bullet = enemy.fire();
            if (bullet != null) {
                enemyBullets.add(bullet);
                new Thread(bullet).start();
            }
        }
    }
}
