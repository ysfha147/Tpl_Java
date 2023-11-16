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
    // Method to add a ball at a specified point to the list
    public void addBalls(Point p) {
        balls_cords.add(new Point(p)); // Adds a new Point object to balls_cords list
        balls_cords_init.add(new Point(p)); // Adds a new Point object to balls_cords_init list
    }
    // Method to translate all the balls' coordinates by dx and dy
    public void translate(int dx, int dy) {
        for (Point p : balls_cords) {
            p.translate(dx, dy); // Translates each Point object in balls_cords
        }
    }

    /**
     * Reset all the balls' coordinates to their initial coordinates
     */
    public void reInit() {
        for(int i = 0; i < balls_cords.size(); i++ ){
            balls_cords.set(i, (Point) balls_cords_init.get(i).clone());
        }

    }
    // Overridden toString() method to display coordinates of all balls
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (Point p : balls_cords) {
            s.append("x cord: ").append(p.getX()).append(" y cord: ").append(p.getY()).append("\n");
            // Constructs a string displaying x and y coordinates of each ball
        }
        return s.toString();
    }
    // Getter method to access the list of balls' coordinates
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

    // Default constructor initializing balls
    public BallsSimulator(){
        balls = new Balls();
    }
    // Constructor initializing BallsSimulator with given parameters
    public BallsSimulator(Balls balls, GUISimulator gui, Color ballsColor){
        this.gui = gui;
        gui.setSimulable(this);
        this.ballsColor = ballsColor;
        this.balls = balls;
        draw();
    }

    // Moves the balls by a fixed amount in each direction and updates the display
    @Override
    public void next() {
        balls.translate(10, 10); // Translates the balls by 10 in x and y directions
        System.out.println(balls.toString());  // Prints the current ball positions (it's optional for the simulation )
        draw(); // Calls the draw method to display balls initially

    }
    // Restores the initial positions of the balls and updates the display
    @Override
    public void restart() {
        balls.reInit(); // Resets the balls' positions to their initial values
        System.out.println(balls.toString()); // Prints the initial ball positions (it's optional for the simulation )
        draw(); // Redraws the initial ball positions
    }

    // Draws the balls on the GUI
    private void draw(){
        gui.reset(); // Clears the GUI canvas
        // Adds graphical elements representing balls to the GUI canvas
        for(Point ball : this.balls.getBalls_cords()){
            gui.addGraphicalElement(new Oval(ball.x, ball.y, ballsColor, ballsColor, 10));
            // Adds an oval shape representing each ball at its coordinates
        }
    }
}

