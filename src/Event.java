import java.util.ArrayList;
import java.util.PriorityQueue;
/**
 * Abstract class representing an event in the simulation.
 * Events are associated with a specific date and can be compared based on their dates.
 */
public abstract class Event implements Comparable<Event> {
    private long date;
    /**
     * Constructor for the Event class.
     *
     * @param date The date at which the event is scheduled to occur.
     */
    public Event(long date) {
        this.date = date;
    }
    /**
     * Setter method for updating the date of the event.
     *
     * @param date The new date for the event.
     */
    public void setDate(long date) {
        this.date = date;
    }
    /**
     * Getter method to retrieve the date of the event.
     *
     * @return The date of the event.
     */
    public long getDate() {
        return date;
    }
    /**
     * Abstract method that must be implemented by subclasses to define the action to be taken when the event occurs.
     */
    public abstract void execute();
    /**
     * Implementation of the compareTo method from the Comparable interface.
     * Allows events to be compared based on their dates for sorting purposes.
     *
     * @param otherEvent Another Event object to compare with.
     * @return A negative integer, zero, or a positive integer as this event is earlier, at the same time, or later than the specified event.
     */
    @Override
    public int compareTo(Event otherEvent) {
        return (int) (date - otherEvent.date);
    }
}
/**
 * Manages a collection of events and their execution based on their scheduled dates.
 */
class EventManager {
    private long currentDate = -1; // The current date of the simulation
    // Priority queue to store events in chronological order
    private PriorityQueue<Event> listOfEvents = new PriorityQueue<Event>();
    /**
     * Getter method to retrieve the priority queue of events.
     *
     * @return The priority queue of events.
     */
    public PriorityQueue<Event> getListOfEvents() {
        return listOfEvents;
    }
    /**
     * Adds an event to the priority queue.
     *
     * @param event The event to be added.
     */
    public void addEvent(Event event) {
        listOfEvents.add(event);
    }
    /**
     * Setter method for updating the current date of the simulation.
     *
     * @param currentDate The new current date of the simulation.
     */
    public void setCurrentDate(long currentDate) {
        this.currentDate = currentDate;
    }
    /**
     * Getter method to retrieve the current date of the simulation.
     *
     * @return The current date of the simulation.
     */
    public long getCurrentDate() {
        return currentDate;
    }
    /**
     * Advances the simulation to the next time step, executing events that are scheduled for the current date.
     */
    public void next() {
        assert listOfEvents.peek() != null;
        currentDate++; // Increment the current date to move to the next time step
        // Check if there are events scheduled for the current date
        if (listOfEvents.peek().getDate() <= currentDate) {
            Event event = listOfEvents.poll(); // Retrieve and remove the event with the earliest date
            assert event != null;
            event.execute(); // Execute the event
        }
    }
    /**
     * Checks if there are no more events pending in the simulation.
     *
     * @return True if there are no more events pending, false otherwise.
     */
    public boolean isFinished() {
        return  listOfEvents.peek() == null;
    }
    /**
     * Restarts the simulation by creating a new empty priority queue for events.
     */
    public void restart() {
        listOfEvents = new PriorityQueue<>();
        currentDate = -1;
    }
}
