public class Cell {
    private int status; //for the first part of the project, we will use 1 for alive, 0 for died

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public Cell(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "" + status;
    }
}
