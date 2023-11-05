import java.awt.*;

public class TestBalls {
    public static void main(String[] args){
        Balls my_balls = new Balls();
        my_balls.addBalls(new Point(3,4));
        my_balls.addBalls(new Point(30,94));
        my_balls.addBalls(new Point(3,40));
        my_balls.addBalls(new Point(39,48));
        my_balls.addBalls(new Point(3,46));

        System.out.println("Initial coordinates: ");
        System.out.println(my_balls.toString());

        my_balls.translate(10, 10);
        System.out.println("coordinates After translation of dx = 10, dy = 10: ");
        System.out.println(my_balls.toString());

        System.out.println("Going back to initial coordinates: ");
        my_balls.reInit();
        System.out.println(my_balls.toString());
    }
}
