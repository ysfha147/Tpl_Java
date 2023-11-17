import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

import java.awt.*;
import java.util.ArrayList;

public class ImmigrationGame extends Cells{

    private final int maxStatus;
    // Constructor for the ImmigrationGame class
    public ImmigrationGame(int height, int width,int maxStatus, Point... StateZero) {
        // Call the constructor of the superclass (Cells) with the provided parameters
        super(height, width, StateZero);
        this.maxStatus = maxStatus;
    }
    // Getter method for the maximum status value
    public int getMaxStatus() {
        return maxStatus;
    }
    // Override the update method to implement the rules specific to the ImmigrationGame
    @Override
    public void update() {
        // List to store cells that will change their status
        ArrayList<Cell> changedCells = new ArrayList<Cell>();
        // Iterate over each cell in the grid
        for (int i = 0; i < super.getHeight(); i++) {
            for (int j = 0; j < super.getWidth(); j++) {
                // Check if a cell has more than 2 neighbors with the same status
                if (super.countNeighbors(i, j, maxStatus) > 2) {
                    // Add the cell to the list of changed cells
                    changedCells.add(super.getCells_matrix()[i][j]);
                }

            }
        }
        // Update the status of the cells in the list
        for (Cell changedCell : changedCells) {
            // Increment the status and take modulo maxStatus to ensure it stays within bounds
            changedCell.setStatus((changedCell.getStatus()+1)%maxStatus);
        }
    }
    // Method to add a cell with a specified status to the grid
    public void addCell(int row, int column, int status) {
        // Check if the provided status is within bounds
        if (status>=maxStatus) throw new IllegalArgumentException("status must be less than maxStatus");
        // Set the status of the cell in both the current and initial grids
        super.getCells_matrix()[row][column].setStatus(status);
        super.getCells_matrix_init()[row][column].setStatus(status);
    }

}


class ImmigrationGameSimulator extends  Event implements Simulable {
    // The grid of cells for the ImmigrationGame
    private final ImmigrationGame cells;
    // Graphical user interface for simulation visualization
    private final GUISimulator gui;
    // Event manager to manage simulation events
    private final EventManager eventManager = new EventManager();
    // Constructor for the ImmigrationGameSimulator class
    public ImmigrationGameSimulator(ImmigrationGame cells, int date) {
        super(date);
        this.gui = new GUISimulator(cells.getHeight()*10,cells.getWidth()*10,Color.white);
        gui.setSimulable(this);
        this.cells = cells;
        eventManager.addEvent(this);
        draw(); // Initial drawing of the grid
    }
    // Getter method for the graphical user interface
    public GUISimulator getGui() {
        return gui;
    }
    // Method to advance to the next simulation step
    @Override
    public void next() {
        if (!eventManager.isFinished()) {
            eventManager.next();
        }
    }
    // Method to restart the simulation
    @Override
    public void restart() {
        cells.setCells_matrix_init();
        eventManager.setCurrentDate(-1);
        super.setDate(0);
        draw(); // Redraw the grid after restart
    }
    // Method to change the hue of a color based on a degree change
    public static Color changeHue(float degreeChange) {
        float red = (1-degreeChange)*255;
        float green = (1-degreeChange)*255;
        float blue = (1-degreeChange)*255;
        return new Color((int) red, (int) green, (int) blue);
    }
    // Method to draw the current state of the grid
    public void draw() {
        gui.reset(); // Clear previous graphical elements
        // Iterate over each cell in the grid
        for(int i = 0; i<cells.getHeight(); i++) {
            for (int j=0; j<cells.getWidth();j++) {
                // Calculate the color based on the status of the cell
                float prev_color = (float) cells.getCells_matrix()[i][j].getStatus() /(cells.getMaxStatus()-1);
                Color color =  changeHue(prev_color);
                // Add a rectangle to the GUI representing the cell
                gui.addGraphicalElement(new Rectangle(i*10,j*10,Color.gray,color,10));
            }
        }
    }
    // Method to execute the simulation step
    @Override
    public void execute() {
        cells.update(); // Update the state of the cells
        this.setDate(getDate()+1); // Increment the simulation date
        eventManager.addEvent(this); // Add the next event to the event manager
        draw(); // Draw the updated grid
    }
}
