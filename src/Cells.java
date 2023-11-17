import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cells {
    // Matrix of Cell objects representing the grid
    private Cell[][] cells_matrix;
    // Initial matrix for resetting purposes
    private Cell[][] cells_matrix_init;
    // Height and width of the grid
    private int height; private int width;


    // Constructor to initialize the grid with a specified height, width, and initial state defined by Point objects
    public Cells(int height, int width, Point... StateZero){
        try{
            this.width = width;
            this.height = height;
            cells_matrix = new Cell[height][width];
            cells_matrix_init = new Cell[height][width];
            // Initialize each cell in the grid
            for (int i = 0;i<height;i++) {
                for(int j=0;j<width;j++) {
                    cells_matrix[i][j] = new Cell(0);
                    cells_matrix_init[i][j] = new Cell(0);
                }
            }
            // Set the initial state of the specified cells to alive (status = 1)
            for (Point pt : StateZero){
                cells_matrix[(int)pt.getX()][(int)pt.getY()].setStatus(1);;
                cells_matrix_init[(int)pt.getX()][(int)pt.getY()].setStatus(1);
            }
        }catch (IllegalAccessError e){
            System.out.println("Caught an IllegalArgumentException: on of the point has exceeded the given range");
        }
    }

    // Another constructor to initialize the grid with a specified height, width, and initial state defined by a List of Point objects
    public void Cells(int height, int width, List<Point> StateZero){
        try{
            this.width = width;
            this.height = height;
            cells_matrix = new Cell[height][width];
            cells_matrix_init = new Cell[height][width];
            // Initialize each cell in the grid
            for (int i = 0;i<height;i++) {
                for(int j=0;j<width;j++) {
                    cells_matrix[i][j] = new Cell(0);
                    cells_matrix_init[i][j] = new Cell(0);
                }
            }
            // Set the initial state of the specified cells to alive (status = 1)
            for (Point pt : StateZero){
                cells_matrix[(int)pt.getX()-1][(int)pt.getY()-1].setStatus(1);
            }
        }catch (IllegalAccessError e){
            System.out.println("Caught an IllegalArgumentException: on of the point has exceeded the given range");
        }
    }

    // Getter methods
    public Cell[][] getCells_matrix() {
        return cells_matrix;
    }

    public Cell[][] getCells_matrix_init() {
        return cells_matrix_init;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    // Method to count the number of neighbors that have statusNeighbors = (status + 1)%maxStatus.
    int countNeighbors(int row, int column, int maxStatus) {
        int counter = 0;
        for(int i=-1; i<2; i++) {
            for (int j=-1; j<2; j++) {
                if (i!=0 || j!=0) {
                    int new_row = (row+i+width)%width;
                    int new_column = (column+j+height)%height;
                    if (cells_matrix[new_row][new_column].getStatus() == (cells_matrix[row][column].getStatus() +1)%maxStatus) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }
    // Overloaded method to count the number of alive or dead neighbors of a cell, assuming a binary status value
    // if a cell is alive, it counts the number of dead neighbors
    // if a cell is dead, it counts the number of alive neighbors
    int countNeighbors(int row, int colum) {
        return countNeighbors(row,colum,2);
    }

    // String representation of the grid for debugging and printing
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                s.append(cells_matrix[i][j]).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
    // Method to set the grid to its initial state
    public void setCells_matrix_init() {
        for (int i=0;i<height;i++) {
            for (int j=0;j<width; j++) {
                cells_matrix[i][j].setStatus(cells_matrix_init[i][j].getStatus());
            }
        }
    }
    // Method to update the grid based on the rules of the Game of Life
    public void update() {
        ArrayList<Cell> aliveCells = new ArrayList<Cell>();
        ArrayList<Cell> deadCells = new ArrayList<Cell>();
        for (int i=0;i<height;i++) {
            for (int j=0; j<width;j++) {
                if (cells_matrix[i][j].getStatus() == 0) {
                    if (countNeighbors(i, j) == 3) {
                        aliveCells.add(cells_matrix[i][j]);
                    }
                }
                else if (countNeighbors(i, j) != 6 && countNeighbors(i, j) != 5) {
                    deadCells.add(cells_matrix[i][j]);
                }

            }
        }
        for (Cell aliveCell : aliveCells) {
            aliveCell.setStatus(1);
        }
        for (Cell deadCell : deadCells) {
            deadCell.setStatus(0);
        }
    }
}







class CellsSimulator extends Event implements Simulable {
    // The grid of cells to simulate
    private final Cells cells;
    // The graphical user interface simulator
    private final GUISimulator gui;
    // Event manager to handle simulation events
    private final EventManager eventManager = new EventManager();


    // Constructor to initialize the simulator with a given grid of cells and a starting date
    public CellsSimulator(Cells cells, int date){
        // Call the constructor of the superclass (Event) with the given date
        super(date);
        // Initialize the graphical user interface with dimensions based on the grid size
        this.gui = new GUISimulator(cells.getHeight()*10, cells.getWidth()*10, Color.WHITE);
        // Set the simulator as the simulable object for the GUI
        gui.setSimulable(this);
        // Set the grid of cells for the simulator
        this.cells = cells;
        // Add the simulator as an event to the event manager
        eventManager.addEvent(this);
        // Draw the initial state of the cells
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
    // Method to restart the simulation by resetting the cells and event manager
    @Override
    public void restart() {
        // Reset the initial state of the cells
        cells.setCells_matrix_init();
        // Set the current date of the event manager to -1
        eventManager.setCurrentDate(0);
        super.setDate(0);
        // Redraw the initial state of the cells
        draw();
    }
    // Method to draw the current state of the cells on the GUI
    private void draw(){
        // Reset the GUI to remove previous graphical elements
        gui.reset();
        for(int i = 0; i<cells.getHeight(); i++) {
            for (int j=0; j<cells.getWidth();j++) {
                // Determine the color based on the status of the cell (alive or dead)
                Color color =  cells.getCells_matrix()[i][j].getStatus() == 1 ? Color.black : Color.white;
                // Add a graphical element (rectangle) to represent the cell on the GUI
                gui.addGraphicalElement(new Rectangle(i*10,j*10,Color.gray,color,10));
            }
        }
    }

    // Method to execute the simulation event by updating the cells and scheduling the next event
    @Override
    public void execute() {
        // Update the state of the cells based on the rules of the simulation
        cells.update();
        // Increment the date of the event
        this.setDate(getDate()+1);
        // Re-add the simulator as an event to the event manager for the next iteration
        eventManager.addEvent(this);
        // Draw the updated state of the cells on the GUI
        draw();
    }
}