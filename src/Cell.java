public class Cell {
    private int status; // Represents the status of the cell.
                        //alive or dead for the game of life, 1 is used for alive, and 0 for dead.

    // Setter method to update the status of the cell.
    public void setStatus(int status) {
        this.status = status;
    }
    // Getter method to retrieve the current status of the cell.
    public int getStatus() {
        return status;
    }
    // Constructor to initialize the cell with a given status.
    public Cell(int status) {
        this.status = status;
    }
    // Overrides the toString method to provide a string representation of the cell's status.
    @Override
    public String toString() {
        return "" + status;
    }
}
