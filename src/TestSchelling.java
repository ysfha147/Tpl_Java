import java.util.Random;

public class TestSchelling {
    public static void main(String[] args) {
        Schelling habitations = new Schelling(50, 50, 4);
        Random random = new Random();
        for (int i= 0;i<10000;i++) {
            habitations.addHabitationInit(random.nextInt(50), random.nextInt(50), random.nextInt(4));
        }
        SchellingSimulator schellingSimulator =new SchellingSimulator(habitations);
        schellingSimulator.getGui().setSimulable(schellingSimulator);

    }

}
