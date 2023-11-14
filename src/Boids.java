import gui.GUISimulator;
import gui.Simulable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Boids {
    //Question : should we use another class for the rules functions or we just
    //write them in this class
    private List<Agent> ListAgents;
    private List<Agent> ListAgentsInit;

    private Vector2d CenterOfMass;
    private Vector2d Place = new Vector2d(0,0);
    private Vector2d Wind = new Vector2d(0,0);



    public Boids(Agent... agents) {
        ListAgents = new ArrayList<>(List.of(agents));
        ListAgentsInit = new ArrayList<>(List.of(agents));

        for (Agent agent : this.ListAgents){
            CenterOfMass.add(agent);
        }
        CenterOfMass.multiply(ListAgents.size());
    }

    public void setBoidsInit(){
        this.ListAgents = new ArrayList<>(this.ListAgentsInit);
    }

    public void setWind(Vector2d wind) {
        Wind = wind;
    }

    public void setPlace(Vector2d place) {
        Place = place;
    }

    public void AddAgent(Agent agent){
        int n = this.ListAgents.size();
        CenterOfMass.setX((CenterOfMass.getX()*n + agent.getX())/(n+1));
        CenterOfMass.setY((CenterOfMass.getX()*n + agent.getY())/(n+1));

        ListAgents.add(agent);

    }

    /**
     * moves the position of the agent a 1% towards the centre
     * @param a: agent to move
     * @return velocity vector to add
     */
    public Vector2d MoveToCenterOfMass(Agent a){
        Vector2d Pcj = new Vector2d(0,0);
        for (Agent agent : this.ListAgents){
            if (!a.equals(agent)){
                Pcj.add(agent);
            }
        }

        Pcj.multiply((double)1/this.ListAgents.size());

        return (new Vector2d((Pcj.getX() - a.getX())/100, (Pcj.getY() - a.getY())/100));
    }

    /**
     * this function tries to keep a small distance (100)
     * away from other agents to make sure they don't collide into each other.
     * @param a
     * @return
     */
    public Vector2d KeepSmallDistance(Agent a){
        Vector2d c = new Vector2d();
        for (Agent agent : this.ListAgents){
            if (!a.equals(agent)){
                if (a.getDistance(agent) < 100){
                    c.add(new Vector2d(a.getX() - agent.getX(), a.getY()-agent.getY()));
                }
            }
        }
        return c;
    }

    /**
     * this function make boids try to match velocity with near boids.
     * @param a
     * @return
     */
    public Vector2d MatchVelocity(Agent a){
        Vector2d Pvj = new Vector2d();
        for (Agent agent : this.ListAgents){
            if (!a.equals(agent)){
                Pvj.add(agent.getVelocity());
            }
        }
        Pvj.multiply((double) 1/ListAgents.size());

        return (new Vector2d((Pvj.getX() - a.getVelocity().getX())/8, (Pvj.getY() - a.getVelocity().getY())/8));
    }

    public Vector2d StrongWind(Agent a, Vector2d wind){
        return wind;
        //we could have any value for the wind vector that we apply to all boids
    }

    /**
     * this rule moves the agent 1% of the way towards Place
     * @param a
     * @param Place
     * @return
     */
    public Vector2d TendToPlace(Agent a, Vector2d Place){
        return new Vector2d((Place.getX() - a.getX())/100, (Place.getY() - a.getY())/100);
    }


    public void update(){
        Vector2d V1; Vector2d V2; Vector2d V3; Vector2d V4; Vector2d V5;
        int m1 = 1; int m2 = 1; int m3 = 1; int m4 = 1; int m5 = 1;

        for (Agent a : this.ListAgents){
            V1 = MoveToCenterOfMass(a);
            V2 = KeepSmallDistance(a);
            V3 = MatchVelocity(a);
            V4 = TendToPlace(a, Place);
            //if we want to tend away from a place make m4 negative
            V5 = StrongWind(a, Wind);
            a.updateAgent(V1.getMultiply(m1),
                    V2.getMultiply(m2), V3.getMultiply(m3), V4.getMultiply(m4), V5.getMultiply(m5));
        }
    }




    public List<Agent> getListAgents() {
        return ListAgents;
    }


}


class BoidsSimulator implements Simulable {

    private Boids boids;
    private GUISimulator gui;

    public BoidsSimulator(int height, int width, Boids boids){
        this.gui = new GUISimulator(height, width, Color.gray);
        gui.setSimulable(this);
        this.boids = boids;
        draw();
    }

    public GUISimulator getGui() {
        return gui;
    }

    @Override
    public void next() {
        boids.update();
        draw();
    }

    @Override
    public void restart() {
        boids.setBoidsInit();
        draw();
    }

    public void draw(){
        gui.reset();

    }
}

