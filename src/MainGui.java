import java.awt.EventQueue;
import javax.swing.JFrame;

public class MainGui extends JFrame {

    public MainGui() {
        add(new BoardGui());
        setResizable(false);
        pack();
        setTitle("Snejk");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame ex = new MainGui();
            ex.setVisible(true);
        });
    }
}