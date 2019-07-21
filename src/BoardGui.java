import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BoardGui extends JPanel implements ActionListener {

    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 140;


    private Timer timer;
    private Image ball;
    private Image apple;

    private Game game;

    private boolean isPause;

    public BoardGui() {
        List<Point> snake = new ArrayList<>();
        snake.add(new Point(4, 2));
        snake.add(new Point(3, 2));
        snake.add(new Point(3, 1));
        snake.add(new Point(2, 1));
        snake.add(new Point(1, 1));
        game = new Game(snake, Direction.UP, B_WIDTH / DOT_SIZE - 1, B_HEIGHT / DOT_SIZE - 1);

        isPause = false;
        initBoard();

    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        ImageIcon iid = new ImageIcon("resources/dot.png");
        ball = iid.getImage();

        ImageIcon iid2 = new ImageIcon("resources/apple.png");
        apple = iid2.getImage();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Point point : game.getSnake()) {
            g.drawImage(ball, DOT_SIZE * point.getX(), DOT_SIZE * point.getY(), this);
        }
        if (game.getCherry() != null) {
            g.drawImage(apple, DOT_SIZE * game.getCherry().getX(), DOT_SIZE * game.getCherry().getY(), this);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isPause) {
            game.tick();
            if (game.isGameOver())
                isPause = true;
        }
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            System.out.println(key);
            switch(key) {
                case 37:
                    game.changeDirection(Direction.LEFT);
                    break;
                case 38:
                    game.changeDirection(Direction.UP);
                    break;
                case 39:
                    game.changeDirection(Direction.RIGHT);
                    break;
                case 40:
                    game.changeDirection(Direction.DOWN);
                    break;
                case 32:
                    isPause = !isPause;
                    break;
            }

        }
    }
}