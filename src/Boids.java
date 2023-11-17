import gui.GUISimulator;
import gui.GraphicalElement;
import gui.Oval;
import gui.Rectangle;
import gui.Simulable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
// Represents a collection of boids with behaviors.
public class Boids {
    private final List<Agent> listAgents; // List of current agents.
    private final List<Agent> listAgentsInit; // List of initial agents for reset.

    private final Vector2d centerOfMass; // Center of mass of all agents.
    private Vector2d Place = new Vector2d(0,0); // Target position.
    private Vector2d Wind = new Vector2d(0,0); // Wind vector.
    private final Point MinPoint = new Point(0,0); // Minimum bounds for agents.
    private Point MaxPoint = new Point(1000,1000);  // Maximum bounds for agents.

    private final int distance; // Distance threshold for behaviors.

    private final long step; // Time step for simulation.

    private final Color color; // Color of the boids.

    private boolean isPredatory = false; // Indicates if boids are predatory.

    // Constructor with initial configuration of agents.
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
    // Constructor with initial configuration of agents as an ArrayList.
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
    // Getters and setters for various properties.

    // Method to check if boids are predatory.
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

    public long getStep() {
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
    // Recalculates the center of mass based on the current agent positions.
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

    // Moves the position of the agent 1% towards the center of mass.
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

    // Tries to keep a small distance (distance) away from other agents to avoid collisions.
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

    // Tries to match velocity with nearby boids.
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
    // Applies a strong wind to all agents.
    public Vector2d StrongWind(Agent a, Vector2d wind){
        return wind;
        //we could have any value for the wind vector that we apply to all boids
    }

    // Moves the agent 1% of the way towards a specific Place.
    public static Vector2d TendToPlace(Agent a, Vector2d Place){
        return new Vector2d((Place.getX() - a.getX())/100, (Place.getY() - a.getY())/100);
    }
    // Enforces boundaries to keep agents within a specified region.
    public void enforceBoundaries(Agent a, Point minBound, Point maxBound) {
        Vector2d v = new Vector2d(0,0);

        if (a.getX() - 15 < minBound.getX()) {
            a.setX(minBound.getX() + 15);
            v.setX(3*Math.abs(a.getVelocity().getX()));
        } else if (a.getX() + 15> maxBound.getX()) {
            a.setX(maxBound.getX() - 15);
            v.setX(-3*Math.abs(a.getVelocity().getX()));
        }


        if (a.getY() - 15< minBound.getY()) {
            a.setY(minBound.getY() + 15);
            v.setY(3*Math.abs(a.getVelocity().getY()));
        } else if (a.getY() + 15> maxBound.getY()) {
            a.setY(maxBound.getY() - 15);
            v.setY(-3*Math.abs(a.getVelocity().getY()));
        }
        a.updateAgent(v);
    }
    /**
     * Updates the state of all agents based on various behavioral rules.
     */
    public void update(){
        Vector2d V1; Vector2d V2; Vector2d V3; Vector2d V4; Vector2d V5;
        double m1 = 0.5; double m2 = 1.5; double m3 = 1; double m4 = 1; double m5 = 1;

        // Iterate through all agents in the list.
        for (Agent a : this.listAgents){
            // Calculate forces based on different rules.
            V1 = MoveTocenterOfMass(a);
            V2 = KeepSmallDistance(a, distance);
            V3 = MatchVelocity(a);
            V4 = TendToPlace(a, Place);
            //if we want to tend away from a place make m4 negative
            V5 = StrongWind(a, Wind);
            // Update the agent's state using the calculated forces and apply boundary constraints.
            a.updateAgent(V1.getMultiply(m1),
                    V2.getMultiply(m2), V3.getMultiply(m3), V4.getMultiply(m4), V5.getMultiply(m5));
            enforceBoundaries(a, MinPoint, MaxPoint);
        }
        // Recalculate the center of mass based on the updated agent positions.
        setCenterOfMass();
    }



    /**
     * Getter method to retrieve the list of agents.
     * @return The list of agents.
     */
    public List<Agent> getlistAgents() {
        return listAgents;
    }


}

/**
 * Represents a simulator for a Boids system, responsible for managing the simulation events,
 * updating the Boids state, and drawing the agents on a GUI.
 */
class BoidsSimulator extends Event implements Simulable {
    // Boids system to simulate
    private final Boids boids;
    // GUI simulator for visualization
    private final GUISimulator gui;
    // Dimensions of the simulation environment
    private int height; private int width;
    // Event manager to handle simulation events
    private final EventManager eventManager = new EventManager();
    /**
     * Constructor for BoidsSimulator with specified height, width, Boids system, and initial date.
     *
     * @param height The height of the simulation environment.
     * @param width The width of the simulation environment.
     * @param boids The Boids system to simulate.
     * @param date The initial date of the simulation.
     */
    public BoidsSimulator(int height, int width, Boids boids,long date){
        super(date);
        this.height = height; this.width = width;
        this.gui = new GUISimulator(height, width, Color.gray);
        gui.setSimulable(this);
        this.boids = boids;
        boids.setMaxPoint(new Point(width, height));
        eventManager.addEvent(this);
        draw();
    }
    /**
     * Constructor for BoidsSimulator with specified height, width, Boids system, initial date, and existing GUI.
     *
     * @param height The height of the simulation environment.
     * @param width The width of the simulation environment.
     * @param boids The Boids system to simulate.
     * @param date The initial date of the simulation.
     * @param gui An existing GUISimulator for visualization.
     */
    public BoidsSimulator(int height, int width, Boids boids,long date, GUISimulator gui){
        super(date);
        this.gui = gui;
        this.boids = boids;
        boids.setMaxPoint(new Point(width, height));
        eventManager.addEvent(this);
    }
    /**
     * Retrieves the GUI simulator associated with the BoidsSimulator.
     *
     * @return The GUISimulator.
     */
    public GUISimulator getGui() {
        return gui;
    }
    /**
     * Retrieves the Boids system associated with the simulator.
     *
     * @return The Boids system.
     */

    public Boids getBoids() {
        return boids;
    }
    /**
     * Retrieves the EventManager associated with the simulator.
     *
     * @return The EventManager.
     */
    public EventManager getEventManager() {
        return eventManager;
    }
    /**
     * Retrieves the height of the simulation environment.
     *
     * @return The height.
     */
    public int getHeight() {
        return height;
    }
    /**
     * Retrieves the width of the simulation environment.
     *
     * @return The width.
     */
    public int getWidth(){
        return width;
    }
    /**
     * Advances the simulation to the next event.
     */
    @Override
    public void next() {
        if (!eventManager.isFinished()) {
            eventManager.next();
        }
    }
    /**
     * Restarts the simulation by resetting the Boids system, event manager, and date.
     */
    @Override
    public void restart() {
        boids.setBoidsInit();
        eventManager.setCurrentDate(-1);
        boids.setCenterOfMass();
        super.setDate(0);
        draw();
    }
    /**
     * Draws the agents on the GUI using the specified Boids system and agent list.
     */
    public void draw(){
        gui.reset();
        for (Agent agent : this.boids.getlistAgents()){
            draw_agent(boids, agent, gui);
        }
    }
    /**
     * Draws a single agent on the GUI, including its main body and velocity vector.
     */
    static void draw_agent(Boids boids, Agent agent, GUISimulator gui) {
        int r;
        r = boids.isPredatory() ? 40 : 20;
        // Draw the main body of the agent
        gui.addGraphicalElement(new Oval((int)agent.getX(), (int)agent.getY()  , Color.WHITE, boids.getColor(), r));
        // Draw the rest of the agent
        Vector2d vect = new Vector2d((int)agent.getX(),(int)agent.getY()+10);
        Vector2d velocityVector = agent.getVelocity();
        velocityVector.normalize();
        double angle = Math.atan2(velocityVector.getX(), velocityVector.getY());
        vect.rotate(angle, agent);
        gui.addGraphicalElement(new Oval((int)vect.getX(),(int)vect.getY() , Color.BLACK, Color.WHITE, (int)(r/4)));
    }
    /**
     * Executes the simulation by updating the Boids system, advancing the date, and adding a new event.
     */
    @Override
    public void execute() {
        boids.update();
        this.setDate(getDate()+boids.getStep());
        eventManager.addEvent(this);
        draw();
    }
}

