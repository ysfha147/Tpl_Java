import java.awt.*;

public class TestMultipleBoidsSimulator2 {
    public static void main(String[] args) {
        // Create a group of pink predatory boids
        Boids pinkBoids = new Boids(10, 2, Color.pink, new Agent(300, 300, 10, 10, 10),
                new Agent(310, 310, 10, 10, 10));
        pinkBoids.setPredatory(true);
        pinkBoids.setPlace(new Vector2d(110, 110));
        // Create a group of red predatory boids
        Boids redBoids = new Boids(10, 3, Color.red, new Agent(100, 100, 10, 10, 10),
                new Agent(110, 110, 10, 10, 10));
        redBoids.setPredatory(true);
        redBoids.setPlace(new Vector2d(250, 250));

        // Create a group of blue boids (non-predatory)
        Boids blueBoids = new Boids(10, 1, Color.blue, new Agent(200, 123, 0, 0, 20),
                new Agent(10, 125, 30, 10, 20),
                new Agent(20, 30, 0, 0, 20),
                new Agent(130, 150, 10, 100, 20),
                new Agent(80, 70, 10, 100, 20),
                new Agent(10, 20, 10, 100, 20));
        blueBoids.setPlace(new Vector2d(250, 250));

        // Create a simulator for multiple groups of boids
        MultipleBoidsSimulator multipleBoids = new MultipleBoidsSimulator(1000, 1000, 0, 100
                , pinkBoids, blueBoids,redBoids
        );
        // Set the simulator as the simulable object for the GUI
        multipleBoids.getGui().setSimulable(multipleBoids);
    }
}
