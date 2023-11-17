import gui.GUISimulator;
import gui.GraphicalElement;
import gui.Oval;
import gui.Rectangle;
import gui.Simulable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Boids {
    //Question : should we use another class for the rules functions or we just
    //write them in this class
    private final List<Agent> listAgents;
    private final List<Agent> listAgentsInit;

    private Vector2d centerOfMass;
    private Vector2d Place = new Vector2d(0,0);
    private Vector2d Wind = new Vector2d(0,0);
    private final Point MinPoint = new Point(0,0);private Point MaxPoint = new Point(1000,1000);

    private final int distance;

    private final int step;

    private final Color color;

    private boolean isPredatory = false;


    public Boids(int distance,int step,Color color, Agent... agents) {
        this.color = color;
        this.step = step;
        listAgents = new ArrayList<>(List.of(agents));
        listAgentsInit = new ArrayList<>();
        for(Agent agent : agents) {
            listAgentsInit.add(new Agent(agent.getX(),agent.getY(),agent.getVelocity().getX(),agent.getVelocity().getY(),agent.getLimitVelocity()));
        }
        centerOfMass = new Vector2d();
        for (Agent agent : this.listAgents){
            centerOfMass.add(agent);
        }
        centerOfMass.multiply((double) 1 /listAgents.size());
        this.distance = distance;
    }

    public Boids(int distance,int step,Color color, ArrayList<Agent> agents) {
        this.color = color;
        this.step = step;
        listAgents = agents;
        listAgentsInit = new ArrayList<>();
        for(Agent agent : agents) {
            listAgentsInit.add(new Agent(agent.getX(),agent.getY(),agent.getVelocity().getX(),agent.getVelocity().getY(),agent.getLimitVelocity()));
        }
        centerOfMass = new Vector2d();
        for (Agent agent : this.listAgents){
            centerOfMass.add(agent);
        }
        centerOfMass.multiply((double) 1 /listAgents.size());
        this.distance = distance;
    }

    public boolean isPredatory() {
        return isPredatory;
    }

    public int getDistance() {
        return distance;
    }

    public Vector2d getPlace() {
        return Place;
    }

    public Vector2d getWind() {
        return Wind;
    }

    public Point getMaxPoint() {
        return MaxPoint;
    }

    public Point getMinPoint() {
        return MinPoint;
    }

    public Vector2d getCenterOfMass() {
        return centerOfMass;
    }

    public void setPredatory(boolean predatory) {
        isPredatory = predatory;
    }

    public int getStep() {
        return step;
    }

    public Color getColor() {
        return color;
    }

    public void setMinPoint(Point minPoint) {
        this.MaxPoint = minPoint;
    }
    public void setMaxPoint(Point maxPoint) {
        this.MaxPoint = maxPoint;
    }

    public void setCenterOfMass() {
        centerOfMass.setX(0);
        centerOfMass.setY(0);
        for (Agent agent : listAgents) {
            centerOfMass.add(agent);
        }
        centerOfMass.multiply((double) 1 /listAgents.size());
    }

    public void setBoidsInit(){
        centerOfMass.setX(0);
        centerOfMass.setY(0);
        for (int i=0; i<listAgentsInit.size();i++) {
            Agent agent = listAgentsInit.get(i);
            listAgents.set(i, new Agent(agent.getX(), agent.getY(), agent.getVelocity().getX(), agent.getVelocity().getY(), agent.getLimitVelocity()));
            centerOfMass.add(agent);
        }
        centerOfMass.multiply((double) 1 /listAgents.size());
    }

    public void setWind(Vector2d wind) {
        Wind = wind;
    }

    public void setPlace(Vector2d place) {
        Place = place;
    }

    public void AddAgent(Agent agent){
        int n = this.listAgents.size();
        centerOfMass.setX((centerOfMass.getX()*n + agent.getX())/(n+1));
        centerOfMass.setY((centerOfMass.getX()*n + agent.getY())/(n+1));

        listAgents.add(agent);

    }

    /**
     * moves the position of the agent a 1% towards the centre
     * @param a: agent to move
     * @return velocity vector to add
     */
    public Vector2d MoveTocenterOfMass(Agent a){
        Vector2d Pcj = new Vector2d(0,0);
        for (Agent agent : this.listAgents){
            if (!a.equals(agent)){
                Pcj.add(agent);
            }
        }

        Pcj.multiply((double)1/this.listAgents.size());

        return (new Vector2d((Pcj.getX() - a.getX())/100, (Pcj.getY() - a.getY())/100));
    }

    /**
     * this function tries to keep a small distance (100)
     * away from other agents to make sure they don't collide into each other.
     * @param a
     * @return
     */
    public Vector2d KeepSmallDistance(Agent a, int distance){
        Vector2d c = new Vector2d();
        for (Agent agent : this.listAgents){
            if (!a.equals(agent)){
                if (a.getDistance(agent) < distance){
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
        Vector2d Pvj = new Vector2d(0,0);
        for (Agent agent : this.listAgents){
            if (!a.equals(agent)){
                Pvj.add(agent.getVelocity());
            }
        }
        Pvj.multiply((double) 1/listAgents.size());

        return new Vector2d((Pvj.getX() - a.getVelocity().getX())/8, (Pvj.getY() - a.getVelocity().getY())/8);
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
    public static Vector2d TendToPlace(Agent a, Vector2d Place){
        return new Vector2d((Place.getX() - a.getX())/100, (Place.getY() - a.getY())/100);
    }

    public void enforceBoundaries(Agent a, Point minBound, Point maxBound) {
        Vector2d v = new Vector2d(0,0);
        // Ensure x-coordinate is within bounds
        if (a.getX() - 15 < minBound.getX()) {
            a.setX(minBound.getX() + 15);
            v.setX(3*Math.abs(a.getVelocity().getX()));  // Adjust velocity
        } else if (a.getX() + 15> maxBound.getX()) {
            a.setX(maxBound.getX() - 15);
            v.setX(-3*Math.abs(a.getVelocity().getX())); // Adjust velocity
        }

        // Ensure y-coordinate is within bounds
        if (a.getY() - 15< minBound.getY()) {
            a.setY(minBound.getY() + 15);
            v.setY(3*Math.abs(a.getVelocity().getY()));  // Adjust velocity
        } else if (a.getY() + 15> maxBound.getY()) {
            a.setY(maxBound.getY() - 15);
            v.setY(-3*Math.abs(a.getVelocity().getY())); // Adjust velocity
        }
        a.updateAgent(v);
    }

    public void update(){
        Vector2d V1; Vector2d V2; Vector2d V3; Vector2d V4; Vector2d V5;
        int m1 = 1; double m2 = 1; int m3 = 1; int m4 = 1; int m5 = 1;


        for (Agent a : this.listAgents){
            V1 = MoveTocenterOfMass(a);
            V2 = KeepSmallDistance(a, distance);
            V3 = MatchVelocity(a);
            V4 = TendToPlace(a, Place);
            //if we want to tend away from a place make m4 negative
            V5 = StrongWind(a, Wind);
            a.updateAgent(V1.getMultiply(m1),
                    V2.getMultiply(m2), V3.getMultiply(m3), V4.getMultiply(m4), V5.getMultiply(m5));
            enforceBoundaries(a, MinPoint, MaxPoint);
        }
        setCenterOfMass();
    }




    public List<Agent> getlistAgents() {
        return listAgents;
    }


}


class BoidsSimulator extends Event implements Simulable {

    private final Boids boids;
    private final GUISimulator gui;

    private final EventManager eventManager = new EventManager();

    public BoidsSimulator(int height, int width, Boids boids,long date){
        super(date);
        this.gui = new GUISimulator(height, width, Color.gray);
        gui.setSimulable(this);
        this.boids = boids;
        boids.setMaxPoint(new Point(width, height));
        eventManager.addEvent(this);
        draw();
    }
    public BoidsSimulator(int height, int width, Boids boids,long date, GUISimulator gui){
        super(date);
        this.gui = gui;
        this.boids = boids;
        boids.setMaxPoint(new Point(width, height));
        eventManager.addEvent(this);
    }
    public GUISimulator getGui() {
        return gui;
    }

    public Boids getBoids() {
        return boids;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    @Override
    public void next() {
        if (!eventManager.isFinished()) {
            eventManager.next();
        }
    }

    @Override
    public void restart() {
        boids.setBoidsInit();
        eventManager.setCurrentDate(-1);
        boids.setCenterOfMass();
        super.setDate(0);
        draw();
    }

    public void draw(){
        gui.reset();
        for (Agent agent : this.boids.getlistAgents()){
            draw_agent(boids, agent, gui);
        }
    }

    static void draw_agent(Boids boids, Agent agent, GUISimulator gui) {
        gui.addGraphicalElement(new Oval((int)agent.getX(), (int)agent.getY()  , Color.WHITE, boids.getColor(), 30));
        Vector2d vect = new Vector2d((int)agent.getX(),(int)agent.getY()+10);
        Vector2d velocityVector = agent.getVelocity();
        velocityVector.normalize();
        double angle = Math.atan2(velocityVector.getX(), velocityVector.getY());
        vect.rotate(angle, agent);
        gui.addGraphicalElement(new Rectangle((int)vect.getX(),(int)vect.getY() , Color.BLACK, Color.WHITE, 5));
    }

    @Override
    public void execute() {
        boids.update();
        this.setDate(getDate()+boids.getStep());
        eventManager.addEvent(this);
        draw();
    }
}

