import gui.GUISimulator;
import gui.Oval;
import gui.Rectangle;
import gui.Simulable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MultipleBoidsSimulator implements Simulable {

    private List<BoidsSimulator> multipleBoids = new ArrayList<BoidsSimulator>();

    private final GUISimulator gui;

    int distancePredatory;

    public MultipleBoidsSimulator(int height, int width, long date,int distancePredatory, ArrayList<Boids> multipleBoids) {
        this.distancePredatory = distancePredatory;
        this.gui = new GUISimulator(height, width, Color.gray);
        gui.setSimulable(this);
        for (Boids boids : multipleBoids) {
            BoidsSimulator boidsSimulator = new BoidsSimulator(height,width,boids,date,gui);
            this.multipleBoids.add(boidsSimulator);
            boidsSimulator.draw();
        }
        draw();

    }


    public MultipleBoidsSimulator(int height, int width, long date, int distancePredatory, Boids... multipleBoids) {
        this.distancePredatory = distancePredatory;
        this.gui = new GUISimulator(height, width, Color.gray);
        gui.setSimulable(this);
        for (Boids boids : multipleBoids) {
            BoidsSimulator boidsSimulator = new BoidsSimulator(height,width,boids,date,gui);
            this.multipleBoids.add(boidsSimulator);
            boidsSimulator.draw();
        }
        draw();
    }

    public GUISimulator getGui() {
        return gui;
    }


    @Override
    public void next() {
        for (BoidsSimulator boidsSimulator : multipleBoids ) {
            PowerInterBoids.power(boidsSimulator,multipleBoids,distancePredatory);
            assert boidsSimulator.getEventManager().getListOfEvents().peek() != null;
            boidsSimulator.getEventManager().setCurrentDate(boidsSimulator.getEventManager().getCurrentDate()+1);
            if (boidsSimulator.getEventManager().getListOfEvents().peek().getDate() <= boidsSimulator.getEventManager().getCurrentDate()) {
                BoidsSimulator event = (BoidsSimulator) boidsSimulator.getEventManager().getListOfEvents().poll();
                assert event != null;
                event.getBoids().update();
                event.setDate(event.getDate()+event.getBoids().getStep());
                event.getEventManager().addEvent(event);
            }
        }
        draw();
    }

    @Override
    public void restart() {
        for (BoidsSimulator boidsSimulator : multipleBoids) {
            boidsSimulator.getBoids().setBoidsInit();
            boidsSimulator.getEventManager().setCurrentDate(-1);
            boidsSimulator.getBoids().setCenterOfMass();
        }
        draw();
    }

    public void draw() {
        gui.reset();
        for (BoidsSimulator boidsSimulator : multipleBoids ) {
            for (Agent agent : boidsSimulator.getBoids().getlistAgents()){
                boidsSimulator.draw_agent(boidsSimulator.getBoids(), agent, gui);
            }
        }
    }

}

class PowerInterBoids {
    public static void power(BoidsSimulator boidsSimulator,List<BoidsSimulator> multipleBoids, int distancePredatory) {
        if (boidsSimulator.getDate() <= boidsSimulator.getEventManager().getCurrentDate()+1) {
            if (boidsSimulator.getBoids().isPredatory()) {
                for (BoidsSimulator preyBoids : multipleBoids) {
                    if (!preyBoids.getBoids().isPredatory()) {
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