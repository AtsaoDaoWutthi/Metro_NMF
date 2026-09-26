import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Scene extends JPanel implements Runnable {
    int width = 1080, height = 700;

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
            checkCollisions();
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
            if (metros[i] != null && (metros[i].isAlive() || metros[i].isExploding())) {
                metros[i].draw(g);
            }
        }
    }

    private void checkCollisions(){
        for (int i = 0; i < metroCount; i++) {
            if(!metros[i].isAlive()){
                continue;
            }

            for (int j = i + 1; j < metroCount; j++) {
                if(!metros[j].isAlive()){
                    continue;
                }

                Rectangle first = new Rectangle(metros[i].x, metros[i].y, metros[i].size, metros[i].size);
                Rectangle second = new Rectangle(metros[j].x, metros[j].y, metros[j].size, metros[j].size);
                if(first.intersects(second)){
                    System.out.println("ชนกัน: " + i + " กับ " + j);
                    metros[i].explode();
                    break;
                }
            }
        }
    }
}
