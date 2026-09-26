import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;

public class Metro implements Runnable {
    int x, y, dx, dy;
    int size = 40;
    int width, height;
    boolean alive = true;
    Image myImage; 
    Image bombImg;
    boolean exploding = false;
    double exploEndTime;
    Random random = new Random();


    public Metro(int width, int height, Image img) {
        this.width = width;
        this.height = height;
        this.myImage = img;
        this.bombImg = new ImageIcon("images/bomb.gif").getImage();
        spawn(width, height);
    }

    public void spawn(int width, int height) {
        this.x = random.nextInt(Math.max(1, width - size));
        this.y = random.nextInt(Math.max(1, height - size));

        this.dx = randomSpeed();
        this.dy = randomSpeed();
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
        while (alive || exploding) {
            if(alive){
                move(width, height);
            }
            if(exploding && System.currentTimeMillis() > exploEndTime){
                exploding = false;
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void draw(Graphics g) {
       if(exploding && bombImg != null){
        g.drawImage(bombImg, x, y, size, size, null);
       }else if(alive && myImage != null){
        g.drawImage(myImage, x, y, size,size, null);
       }
    }

    public boolean isAlive(){
        return alive;
    }

    public void explode(){
        if(!alive) return;
        alive = false;
        exploding = true;
        exploEndTime = System.currentTimeMillis() + 1000;
    }
    public boolean isExploding() {
    return exploding;
    }   

    private int randomSpeed(){
        int speed = 2 + random.nextInt(5);
        return random.nextBoolean() ? speed : -speed;
    }
}
