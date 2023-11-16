import java.awt.*;

public class TestBoids {
    public static void main(String[] args){
//        Boids boids = new Boids(new Agent(300,300,10,10),
//                new Agent(310,310,10,10),
//                new Agent(320,330,0,0),
//                new Agent(330,350,100,10));
        Boids boids = new Boids(100,1, Color.pink, new Agent(300,300,10,10,10),
                new Agent(310,310,10,10,10),
                new Agent(320,330,0,0,10),
                new Agent(330,350,100,10,10));
        boids.setPlace(new Vector2d(250,250));
        BoidsSimulator boidsSimulator = new BoidsSimulator(500,500,boids,0);

        boidsSimulator.getGui().setSimulable(boidsSimulator);
    }

}
