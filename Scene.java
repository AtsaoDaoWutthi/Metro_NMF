
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Scene extends JPanel implements Runnable {

    int width = 1080, height = 1080;

    Image[] metroPics = new Image[10];

    int metroCount;
    Metro[] metros;
    Thread repaintThread;

    public Scene(int metroCount) {
        this.metroCount = metroCount;
        this.metros = new Metro[metroCount];

        for (int i = 0; i < 10; i++) {
            metroPics[i] = new ImageIcon(
                "images/" + (i + 1) + ".png"
            ).getImage();
        }

        Random rand = new Random();

        for (int i = 0; i < metroCount; i++) {
            Image randomPic = metroPics[rand.nextInt(10)];

            metros[i] = new Metro(
                width,
                height,
                randomPic
            );

            Thread t = new Thread(metros[i]);
            t.start();
        }

        repaintThread = new Thread(this);
        repaintThread.start();
    }

    @Override
    public void run() {
        while (true) {
            checkCollisions();
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

    private void checkCollisions() {
    for (int i = 0; i < metroCount; i++) {
        Metro first = metros[i];

        if (first == null || !first.alive) {
            continue;
        }

        for (int j = i + 1; j < metroCount; j++) {
            Metro second = metros[j];

            if (second == null || !second.alive) {
                continue;
            }

            boolean collision = first.x < second.x + second.size 
            && first.x + first.size > second.x 
            && first.y < second.y + second.size 
            && first.y + first.size > second.y;

            if (collision) {
                second.alive = false;
                break;
            }
        }
    }
}
}
