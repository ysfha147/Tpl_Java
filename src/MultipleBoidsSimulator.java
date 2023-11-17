import gui.*;
import gui.Rectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MultipleBoidsSimulator implements Simulable {

    private List<BoidsSimulator> multipleBoids = new ArrayList<BoidsSimulator>();

    private final GUISimulator gui;

    int distancePredatory;
    // Constructor for initializing the simulator with multiple groups of boids
    public MultipleBoidsSimulator(int height, int width, long date,int distancePredatory, ArrayList<Boids> multipleBoids) {
        this.distancePredatory = distancePredatory;
        this.gui = new GUISimulator(height, width, Color.gray);
        gui.setSimulable(this);
        // Create and initialize BoidsSimulator for each group of boids
        for (Boids boids : multipleBoids) {
            BoidsSimulator boidsSimulator = new BoidsSimulator(height,width,boids,date,gui);
            this.multipleBoids.add(boidsSimulator);
            boidsSimulator.draw();
        }
        draw();

    }

    // Constructor for initializing the simulator with multiple groups of boids (varargs version)
    public MultipleBoidsSimulator(int height, int width, long date, int distancePredatory, Boids... multipleBoids) {
        this.distancePredatory = distancePredatory;
        this.gui = new GUISimulator(height, width, Color.gray);
        gui.setSimulable(this);
        // Create and initialize BoidsSimulator for each group of boids
        for (Boids boids : multipleBoids) {
            BoidsSimulator boidsSimulator = new BoidsSimulator(height,width,boids,date,gui);
            this.multipleBoids.add(boidsSimulator);
            boidsSimulator.draw();
        }
        draw();
    }
    // Get the GUI instance
    public GUISimulator getGui() {
        return gui;
    }

    // Method to advance the simulation to the next step
    @Override
    public void next() {
        for (BoidsSimulator boidsSimulator : multipleBoids ) {
            // Apply inter-boids interaction rules
            PowerInterBoids.power(boidsSimulator,multipleBoids,distancePredatory);
            assert boidsSimulator.getEventManager().getListOfEvents().peek() != null;
            boidsSimulator.getEventManager().setCurrentDate(boidsSimulator.getEventManager().getCurrentDate()+1);
            // Execute events in the order of their scheduled dates
            if (boidsSimulator.getEventManager().getListOfEvents().peek().getDate() <= boidsSimulator.getEventManager().getCurrentDate()) {
                BoidsSimulator event = (BoidsSimulator) boidsSimulator.getEventManager().getListOfEvents().poll();
                assert event != null;
                // Update the boids and schedule the next event
                event.getBoids().update();
                event.setDate(event.getDate()+event.getBoids().getStep());
                event.getEventManager().addEvent(event);
            }
        }
        draw();
    }
    // Method to restart the simulation
    @Override
    public void restart() {
        for (BoidsSimulator boidsSimulator : multipleBoids) {
            // Reset each group of boids
            boidsSimulator.getBoids().setBoidsInit();
            boidsSimulator.getEventManager().setCurrentDate(-1);
            boidsSimulator.getBoids().setCenterOfMass();
            boidsSimulator.setDate(0);
        }
        draw();
    }
    // Method to draw the current state of the simulation
    public void draw() {
        gui.reset();
        // Draw each group of boids
        for (BoidsSimulator boidsSimulator : multipleBoids ) {
            for (Agent agent : boidsSimulator.getBoids().getlistAgents()){
                BoidsSimulator.draw_agent(boidsSimulator.getBoids(), agent, gui);
            }
        }
    }

}
// Class to handle interactions between different groups of boids
class PowerInterBoids {
    public static void power(BoidsSimulator boidsSimulator,List<BoidsSimulator> multipleBoids, int distancePredatory) {
        if (boidsSimulator.getDate() <= boidsSimulator.getEventManager().getCurrentDate()+1) {
            if (boidsSimulator.getBoids().isPredatory()) {
                // If the group is predatory, apply rules for interaction with non-predatory groups
                for (BoidsSimulator preyBoids : multipleBoids) {
                    if (!preyBoids.getBoids().isPredatory()) {
                        // If the group is not predatory, apply rules for interaction with predatory groups
                        for (Agent agent : boidsSimulator.getBoids().getlistAgents()) {
                            Vector2d power = Boids.TendToPlace(agent, preyBoids.getBoids().getCenterOfMass());
                            agent.updateAgent(power.getMultiply(10));
                        }
                    }
                }
            }
            else {
                for (BoidsSimulator predatoryBoids : multipleBoids) {
                    if(predatoryBoids.getBoids().isPredatory()) {
                        for (Agent agent : boidsSimulator.getBoids().getlistAgents()) {
                            Vector2d power = predatoryBoids.getBoids().KeepSmallDistance(agent, distancePredatory);
                            agent.updateAgent(power.getMultiply(10));
                        }
                    }
                }
            }
        }
    }
}