import java.util.Random;

public class TestSchelling {
    public static void main(String[] args) {
        // Create a Schelling object representing the grid of habitations with a size of 50x50 and tolerance of 1
        Schelling habitations = new Schelling(50, 50, 1);
        // Create a Random object for generating random positions and family statuses
        Random random = new Random();
        // Initialize specific habitations with family statuses
        habitations.addHabitationInit(1,1,1);
        habitations.addHabitationInit(3,1,1);

        habitations.addHabitationInit(2,2,2);
        habitations.addHabitationInit(4,2,2);

        habitations.addHabitationInit(1,2,1);
        habitations.addHabitationInit(3,2,1);
        habitations.addHabitationInit(3,3,1);

        // Randomly initialize additional habitations with family statuses for testing
        for (int i= 0;i<1000;i++) {
            habitations.addHabitationInit(random.nextInt(50), random.nextInt(50), random.nextInt(5));
        }
        // Create a SchellingSimulator object with the initialized grid of habitations and a starting date of 0
        SchellingSimulator schellingSimulator =new SchellingSimulator(habitations,0);
        // Set the SchellingSimulator as the simulable object for the graphical user interface
        schellingSimulator.getGui().setSimulable(schellingSimulator);

    }

}
