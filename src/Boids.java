import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Boids {
    //Question : should we use another class for the rules functions or we just
    //write them in this class
    private List<Agent> ListAgents;
    private List<Function<Integer,Integer>> RulesFunctions = new ArrayList<>();

    public Boids(Agent... agents){
        ListAgents = new ArrayList<>(List.of(agents));
    }

    public void AddAgent(Agent agent){ListAgents.add(agent);}

    public List<Agent> getListAgents() {
        return ListAgents;
    }


}





