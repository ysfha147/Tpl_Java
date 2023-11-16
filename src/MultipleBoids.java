import gui.GUISimulator;
import gui.Oval;
import gui.Rectangle;
import gui.Simulable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MultipleBoids {
    private ArrayList<Boids> listBoids;
    private int distancePredatory;

    MultipleBoids(int distancePredatory, ArrayList<Boids> listBoids) {
        this.listBoids = listBoids;
        this.distancePredatory = distancePredatory;
    }

    MultipleBoids(int distancePredatory, Boids... listBoids) {
        this.distancePredatory = distancePredatory;
        this.listBoids = new ArrayList<Boids>();
        for (Boids boids : listBoids) {
            this.listBoids.add(boids);
        }
    }

    public ArrayList<Boids> getListBoids() {
        return listBoids;
    }

    public void keepDistance() {
        for (Boids boids : listBoids ) {
            if (!boids.isPredatory()) {
                for (Boids predatoryBoids : listBoids) {
                    if(predatoryBoids.isPredatory()) {
                        for (Agent agent : boids.getlistAgents()) {
                            Vector2d power = predatoryBoids.KeepSmallDistance(agent, distancePredatory);
                            agent.updateAgent(power.getMultiply(10));
                        }
                    }
                }
            }
        }
    }

    public void attack() {
        for (Boids boids : listBoids) {
            if (boids.isPredatory()) {
                for (Boids preyBoids : listBoids) {
                    if (!preyBoids.isPredatory()) {
                        for (Agent agent : boids.getlistAgents()) {
                            Vector2d power = Boids.TendToPlace(agent,preyBoids.getCenterOfMass());
                            agent.updateAgent(power.getMultiply(10));
                        }
                    }
                }
            }
        }
    }

    public void update() {
        for (Boids boids : listBoids) {
            Vector2d V1; Vector2d V2; Vector2d V3; Vector2d V4; Vector2d V5;
            double m1 = 1; double m2 = 1; double m3 = 1; double m4 = 1; double m5 = 1;


            for (Agent a : boids.getlistAgents()){
                V1 = boids.MoveTocenterOfMass(a);
                V2 = boids.KeepSmallDistance(a, boids.getDistance());
                V3 = boids.MatchVelocity(a);
                V4 = boids.TendToPlace(a, boids.getPlace());
                //if we want to tend away from a place make m4 negative
                V5 = boids.StrongWind(a, boids.getWind());
                a.updateAgent(V1.getMultiply(m1),
                        V2.getMultiply(m2), V3.getMultiply(m3), V4.getMultiply(m4), V5.getMultiply(m5));
                boids.enforceBoundaries(a, boids.getMinPoint(), boids.getMaxPoint());
            }
            boids.setCenterOfMass();
        }
        keepDistance();
        attack();
    }
}
class MultipleBoidsSimulator extends Event implements Simulable {

    private MultipleBoids multipleBoids;

    private EventManager eventManager = new EventManager();

    private final GUISimulator gui;

    public MultipleBoidsSimulator(int height, int width, long date,MultipleBoids multipleBoids) {
        super(date);
        this.gui = new GUISimulator(height, width, Color.gray);
        gui.setSimulable(this);
        this.multipleBoids = multipleBoids;
        //eventManager.addEvent(this);
        for (Boids boids : multipleBoids.getListBoids()) {
            eventManager.addEvent(new BoidsSimulator(height,width,boids,date, gui));
        }
        draw();

    }

    public GUISimulator getGui() {
        return gui;
    }


    @Override
    public void execute() {
        multipleBoids.update();
        for (Boids boids : multipleBoids.getListBoids()) {
            this.setDate(getDate()+boids.getStep());
            eventManager.addEvent(this);
        }
        draw();
    }

    @Override
    public void next() {
        if (!eventManager.isFinished()) {
            eventManager.next();
        }
    }

    @Override
    public void restart() {
        for (Boids boids : multipleBoids.getListBoids()) {
            boids.setBoidsInit();
        }
        eventManager.setCurrentDate(0);
        draw();
    }

    void draw() {
        gui.reset();
        for (Boids boids : multipleBoids.getListBoids()) {
            for (Agent agent : boids.getlistAgents()){
                BoidsSimulator.draw_agent(boids, agent, gui);
            }
        }
    }


}
