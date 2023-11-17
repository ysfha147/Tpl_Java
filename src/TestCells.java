import gui.GUISimulator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestCells {
    // Because in this test we were adding random cells to matrix_init and not matrix, you have to press restart in the beginning of the simulation
    public static void main(String[] args) {
        // Create a grid of cells with dimensions 50x50
        Cells cells = new Cells(50, 50 );
        // Get the initial matrix of cells
        Cell[][] matrix_init = cells.getCells_matrix_init();
        // Create a random number generator
        Random random = new Random();
        // Set random cells in the initial matrix to alive (status = 1)
        for (int i= 0;i<500;i++) {
            matrix_init[random.nextInt(50)][random.nextInt(50)].setStatus(1);
        }
        // Create a CellsSimulator with the grid of cells and a starting date of 2
        CellsSimulator cellsSimulator =new CellsSimulator(cells,2);
        // Set the simulator as the simulable object for the graphical user interface
        cellsSimulator.getGui().setSimulable(cellsSimulator);

    }

}
