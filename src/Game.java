import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Game {
    private Point cherry;
    private List<Point> snake;
    private Direction direction;
    private int max_X;
    private int max_Y;

    

    public Game(List<Point> snake, Direction direction, int max_X, int max_Y) {
        this.snake = snake;
        this.direction = direction;
        this.max_X = max_X;
        this.max_Y = max_Y;

        this.cherry = new Point(randomInt(max_X), randomInt(max_Y));
    }

    public void tick() {
        snake.add(new Point(0, 0));

        for (int i = snake.size() - 1; i > 0; i--) {
            snake.set(i, snake.get(i - 1));
        }


        Point oldPoint = snake.get(0);
        switch(direction) {
            case DOWN:
                snake.set(0, new Point(oldPoint.getX(), oldPoint.getY() + 1));
                break;

            case UP:
                snake.set(0, new Point(oldPoint.getX(), oldPoint.getY() - 1));
                break;

            case RIGHT:
                snake.set(0, new Point(oldPoint.getX() + 1, oldPoint.getY()));
                break;

            case LEFT:
                snake.set(0, new Point(oldPoint.getX() - 1, oldPoint.getY()));
                break;
        }

        Point head = snake.get(0);
        if (head.getX() == max_X + 1) head.setX(0);
        if (head.getY() == max_Y + 1) head.setY(0);
        if (head.getX() < 0) head.setX(max_X);
        if (head.getY() < 0) head.setY(max_Y);

        if (Objects.equals(cherry, head))
            cherry = null;

        if(!noCherry())
            snake.remove(snake.size()-1);

        if (noCherry())
            cherry = new Point(randomInt(max_X), randomInt(max_Y));
    }

    public boolean noCherry() {
        return cherry == null;
    }

    public void changeDirection(Direction newDirection) {
        boolean isIllegal = (newDirection == Direction.UP && direction == Direction.DOWN)
                || (newDirection == Direction.DOWN && direction == Direction.UP)
                || (newDirection == Direction.LEFT && direction == Direction.RIGHT)
                || (newDirection == Direction.RIGHT && direction == Direction.LEFT) ;
        if (!isIllegal) {
            direction = newDirection;
        } else {
            //do nothing
        }
    }

    @Override
    public String toString() {
        String string = "";
        for (Point point : snake) {
            string += point.toString();
        }
        return string + "\n direction: " + direction;
    }

    public Point getCherry() {
        return cherry;
    }

    public List<Point> getSnake() {
        return snake;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getMax_X() {
        return max_X;
    }

    public int getMax_Y() {
        return max_Y;
    }

    static int randomInt(int maxInt) {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(maxInt) + 1;

        return randomInt;
    }

    public boolean isGameOver() {
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).equals( snake.get(i)))
                return true;
        }
        return false;
    }

}
