
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Main extends JFrame {

    public Main() {
        setTitle("Metro BOOM!!");
        setSize(1080, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        int metroCount = askMetroCount();

        Scene scene = new Scene(metroCount);
        add(scene);
        setVisible(true);
    }

    private int askMetroCount() {
        while (true) {
            String input = JOptionPane.showInputDialog(
                    this, "กรอกจำนวนอุกกาบาต", "10"
            );

            if (input == null) {
                return 10;
            }

            try {
                int count = Integer.parseInt(input.trim());

                if (count > 0) {
                    return count;
                }

            } catch (NumberFormatException e) {

            }

            JOptionPane.showMessageDialog(
                    this, "กรุณากรอกจำนวนเต็มที่มากกว่า 0"
            );
        }
    }

    public static void main(String[] args) {
        UIManager.put(
                "OptionPane.messageFont",
                new Font("Tahoma", Font.PLAIN, 16)
        );

        UIManager.put(
                "OptionPane.buttonFont",
                new Font("Tahoma", Font.PLAIN, 14)
        );

        new Main();
    }
}
