import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Schelling extends Cells{
    // Maximum number of neighbors that a cell can tolerate having of a different family
    int maxNeighbors;
    // Set to track vacant habitations
    Set<Cell> vacantHabitation;
    // Constructor
    public Schelling(int height, int width, int maxNeighbors) {
        super(height, width);
        this.maxNeighbors = maxNeighbors;
        // Initialize the set of vacant habitations
        vacantHabitation = new HashSet<>();
        // Populate the set with all cells initially
        for(int row = 0; row<height; row++){
            for(int column = 0; column<width; column++){
                vacantHabitation.add(super.getCells_matrix()[row][column]);
            }
        }
    }
    // Getter method for maxNeighbors
    public int getMaxNeighbors(){
        return maxNeighbors;
    }
    // Method to add habitation with a specific family status at a given location
    public void addHabitationInit(int row, int column, int family_status){
        super.getCells_matrix()[row][column].setStatus(family_status);
        super.getCells_matrix_init()[row][column].setStatus(family_status);
        if(family_status != 0){
            // If the family status is not 0, remove the cell from the vacantHabitation set
            vacantHabitation.remove(super.getCells_matrix()[row][column]);
        }

    }
    // Method to add vacant habitation at a given location
    public void addVacantHabitationInit(int row, int column){
        super.getCells_matrix()[row][column].setStatus(0);
        super.getCells_matrix_init()[row][column].setStatus(0);
        // Add the cell to the vacantHabitation set
        vacantHabitation.add(super.getCells_matrix()[row][column]);
    }

    // method to add habitation with a specific family status at a given location
    public void addHabitation(int row, int column, int family_status){
        super.getCells_matrix()[row][column].setStatus(family_status);
        if(family_status != 0){
            // If the family status is not 0, remove the cell from the vacantHabitation set
            vacantHabitation.remove(super.getCells_matrix()[row][column]);
        }

    }
    // Private method to add vacant habitation at a given location (used during update)
    public void addVacantHabitation(int row, int column){
        super.getCells_matrix()[row][column].setStatus(0);
        vacantHabitation.add(super.getCells_matrix()[row][column]);
    }
    // Override countNeighbors method to consider only neighbors with different family status
    @Override
    public int countNeighbors(int row, int column) {
        int counter = 0;
        for(int i=-1; i<2; i++) {
            for (int j=-1; j<2; j++) {
                if (i!=0 || j!=0) {
                    int new_row = (row+i+super.getWidth())%super.getWidth();
                    int new_column = (column+j+super.getHeight())%super.getHeight();
                    // Check if the neighbor has a different family status
                    if(super.getCells_matrix()[new_row][new_column].getStatus() != 0) {
                        if (super.getCells_matrix()[new_row][new_column].getStatus() != super.getCells_matrix()[row][column].getStatus()) {
                            counter++;
                        }
                    }
                }
            }
        }
        return counter;
    }
    // Override update method to implement Schelling's segregation model
    @Override
    public void update() {
        // List to track habitations that will become vacant
        ArrayList<Cell> habitationsThatWillBecomeVacant = new ArrayList<>();
        // Iterate over each cell in the grid
        for (int i=0;i<super.getHeight();i++) {
            for (int j=0; j<super.getWidth();j++) {
                // Check if the cell is not vacant
                if(super.getCells_matrix()[i][j].getStatus() != 0) {
                    // Check if the cell has more neighbors of a different family than the tolerance threshold
                    if (countNeighbors(i, j) > maxNeighbors) {
                        habitationsThatWillBecomeVacant.add(super.getCells_matrix()[i][j]);
                    }
                }

            }
        }


        // Iterator to traverse the vacantHabitation set
        Iterator<Cell> iterator = vacantHabitation.iterator();
        // Make a copy of habitationsThatWillBecomeVacant to avoid concurrent modification issues
        ArrayList<Cell> habitationsThatWillBecomeVacantCopy = new ArrayList<>(habitationsThatWillBecomeVacant) ;
        // Iterate over vacant habitations and assign new families from the list
        while (iterator.hasNext()) {
            Cell habitation = iterator.next();
            // Check if there are habitations to be assigned
            if (!habitationsThatWillBecomeVacant.isEmpty()) {
                Cell newHabitation = habitationsThatWillBecomeVacant.remove(0);
                // Swap the family status between the old and new habitations
                habitation.setStatus(newHabitation.getStatus());
                newHabitation.setStatus(0);
                // Remove the old habitation from the vacant set
                iterator.remove();
                continue;
            }
            break;
        }
        // If there are habitations left to be assigned, throw an exception
        if(!habitationsThatWillBecomeVacant.isEmpty()) throw new ArrayStoreException("not enough vacant places");
        // Add the remaining habitations back to the vacant set
        vacantHabitation.addAll(habitationsThatWillBecomeVacantCopy);


    }
}


class SchellingSimulator extends Event implements Simulable {
    // The Schelling object representing the grid of habitations
    private final Schelling habitations;
    // The graphical user interface simulator
    private final GUISimulator gui;
    // Event manager to handle simulation events
    private final EventManager eventManager = new EventManager();


    // Constructor to initialize the SchellingSimulator with a given grid of habitations and a starting date
    public SchellingSimulator(Schelling habitations, int date){
        // Call the constructor of the superclass (Event) with the given date
        super(date);
        // Initialize the graphical user interface with dimensions based on the grid size
        this.gui = new GUISimulator(habitations.getHeight()*10, habitations.getWidth()*10, Color.WHITE);
        // Set the simulator as the simulable object for the GUI
        gui.setSimulable(this);
        // Set the grid of habitations for the simulator
        this.habitations = habitations;
        // Add the simulator as an event to the event manager
        eventManager.addEvent(this);
        // Draw the initial state of the habitations
        draw();
    }
    // Getter method for the graphical user interface simulator
    public GUISimulator getGui() {
        return gui;
    }
    // Method to progress to the next event in the simulation
    @Override
    public void next() {
        if (!eventManager.isFinished()) {
            eventManager.next();
        }
    }
    // Method to restart the simulation by resetting the habitations and event manager
    @Override
    public void restart() {
        // Reset the initial state of the habitations
        habitations.setCells_matrix_init();
        // Set the current date of the event manager to -1
        eventManager.setCurrentDate(-1);
        super.setDate(0);
        // Redraw the initial state of the habitations
        draw();
    }
    // Method to generate a color based on a numerical value
    Color generateColor(int value) {


        float hue = (float) ((value*69)%359)/359;
        float saturation = value == 0 ? 0.0f : 1.0f;
        float brightness = 1;




        // Create and return the Color object
        return Color.getHSBColor(hue, saturation, brightness);
    }


    // Private method to draw the current state of the habitations on the GUI
    private void draw(){
        // Reset the GUI to remove previous graphical elements
        gui.reset();
        // Iterate over each habitation and draw it on the GUI
        for(int i = 0; i<habitations.getHeight(); i++) {
            for (int j=0; j<habitations.getWidth();j++) {
                // Determine the color based on the status of the habitation
                Color color = generateColor(habitations.getCells_matrix()[i][j].getStatus());
                // Add a graphical element (rectangle) to represent the habitation on the GUI
                gui.addGraphicalElement(new Rectangle(i*10,j*10,Color.gray,color,10));
            }
        }
    }

    // Method to execute the simulation event by updating the habitations and scheduling the next event
    @Override
    public void execute() {
        // Update the state of the habitations based on the rules of the simulation
        habitations.update();
        // Increment the date of the event
        this.setDate(getDate()+1);
        // Add the simulator as an event to the event manager for the next iteration
        eventManager.addEvent(this);
        // Draw the updated state of the habitations on the GUI
        draw();
    }
}