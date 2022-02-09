package pers.aztim.tankwar;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Scanner;
import java.util.Vector;

public class TankWarFrame extends JFrame {
    public static void main(String[] args) {
        new TankWarFrame();
    }

    private final int FRAME_WIDTH = 1300;
    private final int FRAME_HEIGHT = 800;
    private GamePanel gp;


    public TankWarFrame() {
        this.launcher();
        this.add(gp);
        this.addKeyListener(gp);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.instant().store();
                savaGame();
                super.windowClosing(e);
            }
        });

        new Thread(gp).start();
    }

    private void savaGame() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("lastGame.dat"));
            if (gp.isGameOver()) {
                out.writeObject(false);
            } else {
                out.writeObject(true);
                out.writeObject(Tank.getAllTanks().size());
                for (Tank tank : Tank.getAllTanks()) {
                    out.writeObject(tank);
                }
                out.writeObject(Bullet.getAllBullets().size());
                for (Bullet bullet : Bullet.getAllBullets()) {
                    out.writeObject(bullet);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void launcher() {
        ObjectInputStream in;
        boolean isValid = false;
        if (new File("lastGame.dat").exists()) {
            try {
                in = new ObjectInputStream(new FileInputStream("lastGame.dat"));
                isValid = (Boolean) in.readObject();
                if (isValid) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("1-继续游戏 其他-新游戏\n请选择:");
                    String key = scanner.next();
                    if (key.equals("1")) {
                        Vector<Tank> tanks = new Vector<>();
                        Vector<Bullet> bullets = new Vector<>();
                        int tanksSize = (int) in.readObject();
                        for (int i = 0; i < tanksSize; ++i) {
                            tanks.add((Tank) in.readObject());
                        }
                        int bulletsSize = (int) in.readObject();
                        for (int i = 0; i < bulletsSize; ++i){
                            bullets.add((Bullet) in.readObject());
                        }
                        gp = new GamePanel(true);
                        gp.recover(tanks, bullets);
                    } else {
                        isValid = false;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        if (!isValid) gp = new GamePanel(false);
    }
}
