import java.awt.*;

public class TestBoids {
    public static void main(String[] args){
        // Create a Boids system with initial agent configurations
        Boids boids = new Boids(100,1, Color.pink, new Agent(300,300,10,10,10),
                new Agent(310,310,10,10,10),
                new Agent(320,330,0,0,10),
                new Agent(330,350,100,10,10));
        // Set a specific place for the Boids to tend towards
        boids.setPlace(new Vector2d(250,250));
        // Create a BoidsSimulator with specified dimensions, Boids system, and initial date
        BoidsSimulator boidsSimulator = new BoidsSimulator(500,500,boids,0);
        // Set the BoidsSimulator as the simulable for the GUI
        boidsSimulator.getGui().setSimulable(boidsSimulator);
    }

}
