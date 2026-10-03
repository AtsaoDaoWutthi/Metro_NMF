
import javax.swing.JFrame;

public class Main extends JFrame {

    // สร้างหน้าต่างเกม ตั้งค่าขนาด และเอาหน้าจออุกกาบาตมาแสดง
    public Main() {
        setTitle("Metro BOOM!!");
        setSize(1080, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        add(new Scene());
        setVisible(true);
    }

    // จุดเริ่มต้นของโปรแกรม สั่งให้เปิดหน้าต่างเกม
    public static void main(String[] args) {
        new Main();
    }
}
