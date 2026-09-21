import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.util.Random;
import java.awt.Color;

public class Scene extends JPanel implements Runnable {
    int width = 1080, height = 1080;

    Image[] metroPics = new Image[10];

    int metroCount = 10;
    Metro[] metros = new Metro[metroCount];
    Thread repaintThread;

    public Scene() {
        for (int i = 0; i < 10; i++) {
            metroPics[i] = new ImageIcon("images/" + (i + 1) + ".png").getImage();
        }

        Random rand = new Random();
        for (int i = 0; i < metroCount; i++) {
            Image randomPic = metroPics[rand.nextInt(10)]; // สุ่มดึงรูป 1 รูปจาก Array

            metros[i] = new Metro(width, height, randomPic); // ส่งรูปเข้าไปเก็บในอุกกาบาต
            Thread t = new Thread(metros[i]);
            t.start();
        }

        repaintThread = new Thread(this);
        repaintThread.start();
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        for (int i = 0; i < metroCount; i++) {
            if (metros[i] != null) {
                metros[i].draw(g);
            }
        }
    }
}