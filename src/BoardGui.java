import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class BoardGui extends JPanel implements ActionListener {

    private final int B_WIDTH = 600;
    private final int B_HEIGHT = 600;
    private final int DOT_SIZE = 20;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 120;


    private Timer timer;
    private Image ball;
    private Image apple;
    private Image startPanel;
    private Image gameOver;

    private JLabel score;

    private Game game;

    private boolean isPause;
    private boolean isMenu;



    public BoardGui() {
        List<Point> snake = new ArrayList<>();
        snake.add(new Point(4, 2));
        snake.add(new Point(3, 2));
        snake.add(new Point(3, 1));
        snake.add(new Point(2, 1));
        snake.add(new Point(1, 1));
        game = new Game(snake, Direction.UP, B_WIDTH / DOT_SIZE - 1, B_HEIGHT / DOT_SIZE - 1);

        isPause = false;
        isMenu = true;
        initBoard();



    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        ImageIcon iid = new ImageIcon("resources/square.png");
        ball = iid.getImage();

        ImageIcon iid2 = new ImageIcon("resources/cherry.png");
        apple = iid2.getImage();

        ImageIcon iid3 = new ImageIcon("resources/ent.png");
        startPanel = iid3.getImage();

        ImageIcon iid4 = new ImageIcon("resources/over.png");
        gameOver = iid4.getImage();

        score = new JLabel("Score: " + game.getCherryCounter());
        this.add(score);
        score.setForeground(Color.white);

//        JPanel p = new JPanel();
//        this.add(p);
//        p.setForeground(Color.blue);


        timer = new Timer(DELAY, this);
        timer.start();


    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(isMenu) {
            g.drawImage(startPanel, 150, 200, null);
            isPause = false;
        }

        else if(game.isGameOver()) {
            isPause = true;

            while (isPause != false) {
                g.drawImage(gameOver, 150, 200, null);
            }
            // po wciśnięciu spacji powinno wyjść z tej pętli, a nie wychdzi
            game.restart();
            List<Point> snake = new ArrayList<>();
            snake.add(new Point(20, 20));
            snake.add(new Point(20, 21));
            snake.add(new Point(20, 22));
            snake.add(new Point(20, 23));
            snake.add(new Point(20, 24));
            game = new Game(snake, Direction.UP, B_WIDTH / DOT_SIZE - 1, B_HEIGHT / DOT_SIZE - 1);
        }

        else {

            for (Point point : game.getSnake()) {
                g.drawImage(ball, DOT_SIZE * point.getX(), DOT_SIZE * point.getY(), this);
            }
            if (game.getCherry() != null) {
                g.drawImage(apple, DOT_SIZE * game.getCherry().getX(), DOT_SIZE * game.getCherry().getY(), this);
            }

            timer.setDelay(game.getDelay());

            score.setText("Score: " + game.getCherryCounter());

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
                case 10:
                    isMenu = !isMenu;
                    break;
            }

        }
    }
}