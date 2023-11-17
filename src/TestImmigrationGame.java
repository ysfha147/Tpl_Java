import java.util.Random;

public class TestImmigrationGame {
    public static void main(String[] args) {
        // Create an instance of ImmigrationGame with a grid size of 50x50 and 4 possible statuses
        ImmigrationGame cells = new ImmigrationGame(50, 50, 4);
        Random random = new Random();
        // Add an initial cell with status 2 at position (0, 0)
        cells.addCell(0,0,2);
        // Add 2500 random cells with random statuses to the grid
        for (int i= 0;i<2500;i++) {
            cells.addCell(random.nextInt(50), random.nextInt(50), random.nextInt(4));
        }
        // Create an instance of ImmigrationGameSimulator with the ImmigrationGame instance and start the simulation
        ImmigrationGameSimulator immigrationGameSimulator =new ImmigrationGameSimulator(cells,0);
        immigrationGameSimulator.getGui().setSimulable(immigrationGameSimulator);

    }

}
