import gui.Simulable;

import java.awt.Point;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Balls extends Point {
    private List<Point> balls_cords;
    private List<Point> balls_cords_init;

    public Balls(){
        balls_cords = new ArrayList<Point>();
        balls_cords_init = new ArrayList<Point>();
    }
    public void addBalls(Point p) {
        balls_cords.add(new Point(p));
        balls_cords_init.add(new Point(p));
    }

    public void translate(int dx, int dy) {
        for (Point p : balls_cords) {
            p.translate(dx, dy);
        }
    }

    /**
     * Set all the Balls coordinates to the initial coordinates
     */
    public void reInit() {
        balls_cords.clear();
        balls_cords.addAll(balls_cords_init);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (Point p : balls_cords) {
            s.append("x cord: ").append(p.getX()).append(" y cord: ").append(p.getY()).append("\n");
        }
        return s.toString();
    }

}

class BallsSimulator implements Simulable {

    private Balls balls;

    /* The list of the coordinates the current balls
     */
    private List<Point> CurrentPoint;

    private Iterator<Point> PointIterator;

    public BallsSimulator(){
        balls = new Balls();
    }

    @Override
    public void next() {

    }

    @Override
    public void restart() {

    }

    private void planCoordinates(){

    }
}

