
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

    // สร้างอุกกาบาตหนึ่งลูก พร้อมกำหนดรูปและขอบเขตการเคลื่อนที่
    public Metro(int width, int height, Image img) {
        this.width = width;
        this.height = height;
        this.myImage = img;
        this.bombImg = new ImageIcon("images/bomb.gif").getImage();
        spawn(width, height);
    }

    // สุ่มตำแหน่งเริ่มต้นและความเร็วของอุกกาบาต
    public void spawn(int width, int height) {
        this.x = random.nextInt(Math.max(1, width - size));
        this.y = random.nextInt(Math.max(1, height - size));

        this.dx = randomSpeed();
        this.dy = randomSpeed();
    }

    // ขยับอุกกาบาต และทำให้เด้งกลับเมื่อชนขอบหน้าจอ
    public void move(int width, int height) {

        // ถ้าลูกบอลไม่ทำงาน ไม่ต้องขยับ
        if (!alive) {
            return;
        }

        // เปลี่ยนตำแหน่งตามความเร็ว
        x += dx;
        y += dy;

        // ชนขอบซ้าย
        if (x <= 0) {
            x = 0;                    // กันไม่ให้เลยขอบ
            dx = Math.abs(dx);        // เปลี่ยนทิศไปทางขวา
        }

        // ชนขอบขวา
        if (x >= width - size) {
            x = width - size;         // กันไม่ให้เลยขอบ
            dx = -Math.abs(dx);       // เปลี่ยนทิศไปทางซ้าย
        }

        // ชนขอบบน
        if (y <= 50) {
            y = 50;                    // กันไม่ให้เลยขอบ
            dy = Math.abs(dy);        // เปลี่ยนทิศลง
        }

        // ชนขอบล่าง
        if (y >= (height - 5) - size) {
            y = (height - 5) - size;        // กันไม่ให้เลยขอบ
            dy = -Math.abs(dy);       // เปลี่ยนทิศขึ้น
        }
    }

    // ทำงานเบื้องหลัง คอยขยับอุกกาบาตและจัดการช่วงเวลาที่กำลังระเบิด
    @Override
    public void run() {
        while (alive || exploding) {
            if (alive) {
                move(width, height);
            }
            if (exploding && System.currentTimeMillis() > exploEndTime) {
                exploding = false;
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    // วาดรูปอุกกาบาต หรือรูปเอฟเฟกต์ระเบิดลงบนหน้าจอ
    public void draw(Graphics g) {
        if (exploding && bombImg != null) {
            g.drawImage(bombImg, x, y, size, size, null);
        } else if (alive && myImage != null) {
            g.drawImage(myImage, x, y, size, size, null);
        }
    }

    // เช็กว่าอุกกาบาตลูกนี้ยังเคลื่อนที่อยู่หรือไม่
    public boolean isAlive() {
        return alive;
    }

    // เปลี่ยนสถานะอุกกาบาตให้หยุดเคลื่อนที่และแสดงภาพระเบิด
    public void explode() {
        if (!alive) {
            return;
        }
        alive = false;
        exploding = true;
        exploEndTime = System.currentTimeMillis() + 1000;
    }

    // เช็กว่าตอนนี้อุกกาบาตกำลังแสดงภาพระเบิดอยู่หรือไม่
    public boolean isExploding() {
        return exploding;
    }

    // สุ่มความเร็วและทิศทางการเคลื่อนที่ของอุกกาบาต
    private int randomSpeed() {
        int speed = 2 + random.nextInt(5);
        return random.nextBoolean() ? speed : -speed;
    }
}
