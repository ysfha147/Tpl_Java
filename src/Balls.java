import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;

import java.awt.*;
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
        for(int i = 0; i < balls_cords.size(); i++ ){
            balls_cords.set(i, (Point) balls_cords_init.get(i).clone());
        }

    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (Point p : balls_cords) {
            s.append("x cord: ").append(p.getX()).append(" y cord: ").append(p.getY()).append("\n");
        }
        return s.toString();
    }

    public List<Point> getBalls_cords() {
        return balls_cords;
    }
}

class BallsSimulator implements Simulable {

    private Balls balls;
    private GUISimulator gui;
    private Color ballsColor;

    /* The list of the coordinates the current balls
     */

    public BallsSimulator(){
        balls = new Balls();
    }

    public BallsSimulator(Balls balls, GUISimulator gui, Color ballsColor){
        this.gui = gui;
        gui.setSimulable(this);
        this.ballsColor = ballsColor;
        this.balls = balls;
        draw();
    }


    @Override
    public void next() {
        balls.translate(10, 10);
        System.out.println(balls.toString());
        draw();

    }

    @Override
    public void restart() {
        balls.reInit();
        System.out.println(balls.toString());
        draw();
    }

    private void draw(){
        gui.reset();

        for(Point ball : this.balls.getBalls_cords()){
            gui.addGraphicalElement(new Oval(ball.x, ball.y, ballsColor, ballsColor, 10));
        }
    }
}

