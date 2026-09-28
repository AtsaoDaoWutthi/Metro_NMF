import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Scene extends JPanel implements Runnable {
    int width = 1080, height = 700;

    Image[] metroPics = new Image[10];

    int metroCount;
    Metro[] metros;
    Thread repaintThread;
    

    // เตรียมหน้าจอ ช่องกรอกจำนวน และปุ่มเริ่มเกม
    public Scene() {
        setLayout(new BorderLayout());
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("Input Metro:");
        JTextField field = new JTextField(8);
        JButton button = new JButton("Start");
        controls.add(label);
        controls.add(field);
        controls.add(button);
        add(controls, BorderLayout.NORTH);
        button.addActionListener(e -> startGame(field.getText()));
        field.addActionListener(e -> startGame(field.getText()));
    }

    // รับจำนวนจากช่องกรอก ตรวจสอบข้อมูล แล้วสร้างอุกกาบาตตามจำนวนที่กรอก
    private void startGame(String input) {
        try {
            metroCount = Integer.parseInt(input);
            if (metroCount <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please enter an integer greater than 0.");
            return;
        }

        metros = new Metro[metroCount];
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

        if (repaintThread == null) {
            repaintThread = new Thread(this);
            repaintThread.start();
        }
    }

    // ทำงานวนซ้ำเพื่อวาดหน้าจอใหม่และตรวจสอบอุกกาบาตชนกันตลอดเวลา
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

    // วาดพื้นหลัง อุกกาบาต และแสดงจำนวนอุกกาบาตที่ยังเหลืออยู่
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (metros == null) {
            g.setColor(Color.WHITE);
            g.drawString("Enter the number of meteorites before starting the game.", 15, 80);
            return;
        }

        int remaining = 0;
        for (int i = 0; i < metroCount; i++) {
            if (metros[i] != null && (metros[i].isAlive() || metros[i].isExploding())) {
                metros[i].draw(g);
            }
            if (metros[i] != null && metros[i].isAlive()) {
                remaining++;
            }
        }

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(18f));
        g.drawString("Metro Count: " + remaining + " / " + metroCount, 15, 80);
    }

    // ตรวจดูว่าอุกกาบาตแต่ละลูกชนกันหรือไม่ ถ้าชนกันจะสั่งให้ระเบิด
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
