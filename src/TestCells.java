import gui.GUISimulator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestCells {
    public static void main(String[] args) {
        Cells cells = new Cells(50, 50 );
        Cell[][] matrix_init = cells.getCells_matrix_init();
        Random random = new Random();

        for (int i= 0;i<500;i++) {
            matrix_init[random.nextInt(50)][random.nextInt(50)].setStatus(1);
        }

        CellsSimulator cellsSimulator =new CellsSimulator(cells);
        cellsSimulator.getGui().setSimulable(cellsSimulator);

    }

}
