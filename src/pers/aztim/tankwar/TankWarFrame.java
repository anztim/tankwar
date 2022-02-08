package pers.aztim.tankwar;

import javax.swing.*;

public class TankWarFrame extends JFrame {
    public static void main(String[] args) {
        new TankWarFrame();
    }

    private final int FRAME_WIDTH = 1000;
    private final int FRAME_HEIGHT = 750;
    private final GamePanel gp;


    public TankWarFrame() {
        gp = new GamePanel();
        this.add(gp);
        this.addKeyListener(gp);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);


        new Thread(gp).start();
    }
}
