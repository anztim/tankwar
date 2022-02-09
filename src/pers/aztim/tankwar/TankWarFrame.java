package pers.aztim.tankwar;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankWarFrame extends JFrame {
    public static void main(String[] args) {
        new TankWarFrame();
    }

    private final int FRAME_WIDTH = 1300;
    private final int FRAME_HEIGHT = 800;
    private final GamePanel gp;


    public TankWarFrame() {
        gp = new GamePanel();
        this.add(gp);
        this.addKeyListener(gp);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.instant().store();
                super.windowClosing(e);
            }
        });

        new Thread(gp).start();
    }
}
