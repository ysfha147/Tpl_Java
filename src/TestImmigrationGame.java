import java.util.Random;

public class TestImmigrationGame {
    public static void main(String[] args) {
        ImmigrationGame cells = new ImmigrationGame(50, 50, 4);
        Random random = new Random();
        cells.addCell(0,0,2);
        for (int i= 0;i<100;i++) {
            cells.addCell(random.nextInt(50), random.nextInt(50), random.nextInt(4));
        }
        ImmigrationGameSimulator immigrationGameSimulator =new ImmigrationGameSimulator(cells);
        immigrationGameSimulator.getGui().setSimulable(immigrationGameSimulator);

    }

}
