import java.awt.*;
import java.util.MissingFormatArgumentException;

public class TestMultipleBoidsSimulator {
    public static void main(String[] args){
        Boids pinkBoids = new Boids(10,2, Color.pink, new Agent(300,300,10,10,10),
                new Agent(310,310,10,10,10),
                new Agent(320,330,0,0,10),
                new Agent(330,350,100,10,10));
        pinkBoids.setPlace(new Vector2d(1000,1000));
        pinkBoids.setPredatory(true);
        Boids blueBoids = new Boids(10,1, Color.blue, new Agent(200,123,0,0,20),
                new Agent(10,125,30,10,20),
                new Agent(20,30,0,0,20),
                new Agent(30,50,10,100,20));
        blueBoids.setPlace(new Vector2d(1000,1000));

        MultipleBoids multipleBoids = new MultipleBoids(100, pinkBoids, blueBoids);
        MultipleBoidsSimulator multipleBoidsSimulator = new MultipleBoidsSimulator(2000, 2000, 0,multipleBoids);

        multipleBoidsSimulator.getGui().setSimulable(multipleBoidsSimulator);

    }
}
