
import javax.swing.JFrame;

public class Main extends  JFrame{
    public Main(){
        setTitle("Metro BOOM!!");
        setSize(1080,1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Scene scene = new Scene();
        add(scene);
        setVisible(true);
    }
    public static void main(String[] args) {
        new Main();
    }
}