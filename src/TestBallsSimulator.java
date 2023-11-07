import gui.GUISimulator ;

import java.awt.*;

public class TestBallsSimulator {
    public static void main ( String [] args ) {
        GUISimulator gui = new GUISimulator(500 , 500 , Color.BLACK ) ;

        Balls my_balls = new Balls();
        my_balls.addBalls(new Point(3,4));
        my_balls.addBalls(new Point(30,94));
        my_balls.addBalls(new Point(3,40));
        my_balls.addBalls(new Point(39,48));
        my_balls.addBalls(new Point(3,46));

        gui.setSimulable ( new BallsSimulator(my_balls, gui, Color.BLUE)) ;
        }
}