import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GuiExample {

    private static JFrame frame;
    private static JPanel panel;
    private static JLabel label;
    private static JButton button2;
    private static Timer timer;

   public static void main(String[] args) {
       frame = new JFrame();

       panel = new JPanel() {
           @Override
           protected void paintComponent(Graphics g) {
               super.paintComponent(g);
               Image image = new ImageIcon("resources/apple.png").getImage();
               g.drawImage(image, 150, 150, null);
           }
       };

       frame.add(panel);

       JButton button = new JButton("moj buton");
       button.addActionListener(new MojButtonListner());
       panel.add(button);

       button2 = new JButton("radek");
       button2.addActionListener(new StopButtonListener());
       panel.add(button2);

       label = new JLabel("polezztekstem");
       panel.add(label);

       timer = new Timer(100, new RadekButtonListener());
       timer.start();



       frame.setSize(300, 300);
       frame.setVisible(true);
       frame.setLocationRelativeTo(null);

   }

   private static class MojButtonListner implements ActionListener {

       @Override
       public void actionPerformed(ActionEvent e) {
            panel.add(new JLabel("nacisnieto guzik"));
            panel.revalidate();
       }
   }

   private static class RadekButtonListener implements ActionListener {

       @Override
       public void actionPerformed(ActionEvent e) {
           button2.setLocation(randomInt(270), randomInt(270));
       }
   }

   private static class StopButtonListener implements ActionListener {

       @Override
       public void actionPerformed(ActionEvent e) {
           timer.stop();
       }
   }

    static int randomInt(int maxInt) {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(maxInt) + 1;

        return randomInt;
    }

}
