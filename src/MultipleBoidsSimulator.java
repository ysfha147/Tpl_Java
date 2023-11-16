import gui.GUISimulator;
import gui.Oval;
import gui.Rectangle;
import gui.Simulable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MultipleBoidsSimulator extends Event implements Simulable {

    private List<Boids> multipleBoids;

    private EventManager eventManager = new EventManager();

    private final GUISimulator gui;

    public MultipleBoidsSimulator(int height, int width, long date,ArrayList<Boids> multipleBoids) {
        super(date);
        this.gui = new GUISimulator(height, width, Color.gray);
        gui.setSimulable(this);
        this.multipleBoids = multipleBoids;
        eventManager.addEvent(this);
        draw();

    }


    public MultipleBoidsSimulator(int height, int width, long date, Boids... multipleBoids) {
        super(date);
        this.gui = new GUISimulator(height, width, Color.gray);
        gui.setSimulable(this);
        this.multipleBoids = new ArrayList<>(List.of(multipleBoids));
        eventManager.addEvent(this);
        draw();
    }

    public GUISimulator getGui() {
        return gui;
    }


    @Override
    public void execute() {
        for (Boids boids : multipleBoids) {
            boids.update();
            this.setDate(getDate()+boids.getStep());
            eventManager.addEvent(this);
            draw();
        }
    }

    @Override
    public void next() {
        if (!eventManager.isFinished()) {
            eventManager.next();
        }
    }

    @Override
    public void restart() {
        for (Boids boids : multipleBoids) {
            boids.setBoidsInit();
        }
        eventManager.setCurrentDate(0);
        draw();
    }

    void draw() {
        gui.reset();
        for (Boids boids : multipleBoids) {
            for (Agent agent : boids.getlistAgents()){
                BoidsSimulator.draw_agent(boids, agent, gui);
            }
        }
    }


}