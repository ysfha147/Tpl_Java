import java.awt.*;
import java.util.MissingFormatArgumentException;

public class TestMultipleBoidsSimulator {
    public static void main(String[] args){
        // Create a group of pink boids with predatory behavior
        Boids pinkBoids = new Boids(10,2, Color.pink, new Agent(300,300,10,10,10),
                new Agent(310,310,10,10,10),
                new Agent(320,330,0,0,10),
                new Agent(330,350,100,10,10));
        pinkBoids.setPredatory(true);
        pinkBoids.setPlace(new Vector2d(250,250));
        // Create a group of blue boids
        Boids blueBoids = new Boids(10,1, Color.blue, new Agent(200,123,0,0,20),
                new Agent(10,125,30,10,20),
                new Agent(20,30,0,0,20),
                new Agent(30,50,10,100,20));
        blueBoids.setPlace(new Vector2d(250,250));

        // Create a simulator for multiple groups of boids
        MultipleBoidsSimulator multipleBoids = new MultipleBoidsSimulator(1000, 1000, 0,100
                , pinkBoids, blueBoids
        );
        // Set the simulator as the simulable object for the GUI
        multipleBoids.getGui().setSimulable(multipleBoids);

    }
}
