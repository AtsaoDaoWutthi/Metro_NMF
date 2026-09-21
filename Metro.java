import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

public class Metro implements Runnable {
    int x, y, dx, dy;
    int size = 40;
    int width, height;
    boolean alive = true;
    Image myImage; 
    Random random = new Random();


    public Metro(int width, int height, Image img) {
        this.myImage = img;
        spawn(width, height);
    }

    public void spawn(int width, int height) {
        this.x = random.nextInt(Math.max(1, width - size));
        this.y = random.nextInt(Math.max(1, height - size));

        this.dx = random.nextBoolean() ? 4 : -4;
        this.dy = random.nextBoolean() ? 4 : -4;
    }

    public void move(int width, int height) {
        if (!alive) return;

        x += dx;
        y += dy;

        if (x <= 0){
            dx =  Math.abs(dx);
        }
        if (x >= width - size){
            dx = -Math.abs(dx);
        }
        if (y <= 0){
            dy =  Math.abs(dy);
        }              
        if (y >= height - size){
            dy = -Math.abs(dy);
        }
    }

    @Override
    public void run() {
        while (alive) {
            move(1080,1080);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void draw(Graphics g) {
        if (alive && myImage != null) {
            g.drawImage(myImage, x, y, size, size, null);
        }
    }
}