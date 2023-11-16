import java.util.Random;

public class TestSchelling {
    public static void main(String[] args) {
        Schelling habitations = new Schelling(50, 50, 1);
        Random random = new Random();

        habitations.addHabitationInit(1,1,1);
        habitations.addHabitationInit(3,1,1);

        habitations.addHabitationInit(2,2,2);
        habitations.addHabitationInit(4,2,2);

        habitations.addHabitationInit(1,2,1);
        habitations.addHabitationInit(3,2,1);
        habitations.addHabitationInit(3,3,1);


        for (int i= 0;i<1000;i++) {
            habitations.addHabitationInit(random.nextInt(50), random.nextInt(50), random.nextInt(5));
        }

        SchellingSimulator schellingSimulator =new SchellingSimulator(habitations,0);
        schellingSimulator.getGui().setSimulable(schellingSimulator);

    }

}
