import java.util.ArrayList;
import java.util.PriorityQueue;

public abstract class Event implements Comparable<Event> {
    private long date;

    public void setDate(long date) {
        this.date = date;
    }

    public Event(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }
    public abstract void execute();
    @Override
    public int compareTo(Event e) {
        return (int) (date - e.date);
    }
}

class EventManager {
    private long currentDate = -1;

    private PriorityQueue<Event> listOfEvents = new PriorityQueue<Event>();


    public void addEvent(Event event) {
        listOfEvents.add(event);
    }

    public void next() {
        assert listOfEvents.peek() != null;
        currentDate++;
        if (listOfEvents.peek().getDate() <= currentDate) {
            Event event = listOfEvents.poll();
            assert event != null;
            event.execute();
        }
    }

    public boolean isFinished() {
        return  listOfEvents.peek() == null;
    }

    public void restart() {
        listOfEvents = new PriorityQueue<>();
    }
}
