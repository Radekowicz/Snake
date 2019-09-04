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
import java.awt.Font;


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

    List<Integer> scoreList = new ArrayList<>();

    public BoardGui() {
        List<Point> snake = new ArrayList<>();
        snake.add(new Point(4, 2));
        snake.add(new Point(3, 2));
        snake.add(new Point(3, 1));
        snake.add(new Point(2, 1));
        snake.add(new Point(1, 1));
        game = new Game(snake, Direction.UP, B_WIDTH / DOT_SIZE - 1, B_HEIGHT / DOT_SIZE - 1);

        scoreList.add(0, 0);
//        scoreList.add(1, 43);
//        scoreList.add(2, 563);


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

        timer = new Timer(DELAY, this);
        timer.start();


    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(isMenu) {
//            g.drawImage(startPanel, 150, 200, null);
            g.setColor(Color.GREEN);
            g.setFont(new Font("Calibri", Font.PLAIN, 42));
            g.drawString("TO START", 190   , 280);
            g.setFont(new Font("Calibri", Font.PLAIN, 42));
            g.drawString("Press ENTER", 172, 330);
//            isPause = false;
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

            if(game.isGameOver()) {
//                g.drawImage(gameOver, 150, 200, null);
                g.setColor(Color.RED);
                g.setFont(new Font("Calibri", Font.BOLD, 60));
                g.drawString("GAME OVER", 120   , 300);
                g.setFont(new Font("Calibri", Font.PLAIN, 22));
                g.drawString("Press SPACE to continue", 178, 350);

//                scoreList.add(game.getShit(), game.getCherryCounter());

                for(int i=0; i<scoreList.size(); i++) {
                    g.drawString(  "Score "+ game.getShit() + ". " + scoreList.get(i).toString(), 40, 40+(i*30));

                }

            }
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
                    if(game.isGameOver()) {
                        scoreList.add(game.getShit(),game.getCherryCounter());
                        game.restart();
                    }
                    break;
                case 10:
                    if(!game.isGameOver()) {
                        isMenu = !isMenu;
                    }
                    break;
            }

        }
    }

//    @Override
//    public String toString() {
//        String string = "";
//        for(int i=0; i<scoreList.size(); i++) {
//            string += scoreList
//        }
//    }
}