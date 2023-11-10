import gui.GUISimulator;
import gui.Simulable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cells {
    /**/
    private Boolean[][] cells_matrix;
    private int height; private int width;
    private Boolean[][] cells_matrix_init;

    public Cells(int height, int width, Point... StateZero){
        try{
            this.width = width;
            this.height = height;
            cells_matrix = new Boolean[height][width];
            cells_matrix_init = new Boolean[height][width];
            for (Point pt : StateZero){
                cells_matrix[(int)pt.getX()-1][(int)pt.getY()-1] = Boolean.TRUE;
            }
        }catch (IllegalAccessError e){
            System.out.println("Caught an IllegalArgumentException: on of the point has exceeded the given range");
        }
    }
    public void Cells(int height, int width, List<Point> StateZero){
        try{
            this.width = width;
            this.height = height;
            cells_matrix = new Boolean[height][width];
            for (Point pt : StateZero){
                cells_matrix[(int)pt.getX()-1][(int)pt.getY()-1] = Boolean.TRUE;
            }
        }catch (IllegalAccessError e){
            System.out.println("Caught an IllegalArgumentException: on of the point has exceeded the given range");
        }
    }

    public Boolean[][] getCells_matrix() {
        return cells_matrix;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    private Iterator<Point> NeighborIterator(int i, int j){
        List<Point> neighbors = new ArrayList<>();
        int n = height; int m = width;
        if (i == 0){
            if (j == 0){
                neighbors.add(new Point(i,j+1));//right
                neighbors.add(new Point(i+1, j+1));//right down
                neighbors.add(new Point(n-1,j+1));//right up
                neighbors.add(new Point(i,m-1)); //left
                neighbors.add(new Point(i+1, m-1));//left down
                neighbors.add(new Point(n-1, m-1));//left up
                neighbors.add(new Point(i+1, j));//down
                neighbors.add(new Point(n-1,j));//up
            }
            else if (j == m-1){
                neighbors.add(new Point(i,0));//right
                neighbors.add(new Point(i+1, 0));//right down
                neighbors.add(new Point(n-1,0));//right up
                neighbors.add(new Point(i,j-1)); //left
                neighbors.add(new Point(i+1, j-1));//left down
                neighbors.add(new Point(n-1, m-2));//left up
                neighbors.add(new Point(i+1, j));//down
                neighbors.add(new Point(n-1,j));//up
            }
            else{
                neighbors.add(new Point(i,j+1));//right
                neighbors.add(new Point(i+1, j+1));//right down
                neighbors.add(new Point(n-1,j+1));//right up
                neighbors.add(new Point(i,j-1)); //left
                neighbors.add(new Point(i+1, j-1));//left down
                neighbors.add(new Point(n-1, j-1));//left up
                neighbors.add(new Point(i+1, j));//down
                neighbors.add(new Point(n-1,j));//up
            }

        }
        else if (i == n-1) {
            if (j == 0){
                neighbors.add(new Point(i,j+1));//right
                neighbors.add(new Point(0, j+1));//right down
                neighbors.add(new Point(i-1,j+1));//right up
                neighbors.add(new Point(i,m-1)); //left
                neighbors.add(new Point(0, m-1));//left down
                neighbors.add(new Point(i-1, m-1));//left up
                neighbors.add(new Point(0, j));//down
                neighbors.add(new Point(i-1,j));//up
            }
            else if (j == m-1){
                neighbors.add(new Point(i,0));//right
                neighbors.add(new Point(0, 0));//right down
                neighbors.add(new Point(n-1,0));//right up
                neighbors.add(new Point(i,j-1)); //left
                neighbors.add(new Point(0, j-1));//left down
                neighbors.add(new Point(i-1, j-1));//left up
                neighbors.add(new Point(0, j));//down
                neighbors.add(new Point(i-1,j));//up
            }
            else{
                neighbors.add(new Point(i,j+1));//right
                neighbors.add(new Point(0, j+1));//right down
                neighbors.add(new Point(i-1,j+1));//right up
                neighbors.add(new Point(i,j-1)); //left
                neighbors.add(new Point(0, j-1));//left down
                neighbors.add(new Point(i-1, j-1));//left up
                neighbors.add(new Point(0, j));//down
                neighbors.add(new Point(i-1,j));//up
            }
        }
        else{
            if (j == 0){
                neighbors.add(new Point(i,j+1));//right
                neighbors.add(new Point(i+1, j+1));//right down
                neighbors.add(new Point(i-1,j+1));//right up
                neighbors.add(new Point(i,m-1)); //left
                neighbors.add(new Point(i+1, m-1));//left down
                neighbors.add(new Point(i-1, m-1));//left up
                neighbors.add(new Point(i+1, j));//down
                neighbors.add(new Point(i-1,j));//up
            }
            else if (j == m-1){
                neighbors.add(new Point(i,0));//right
                neighbors.add(new Point(i+1, 0));//right down
                neighbors.add(new Point(i-1,0));//right up
                neighbors.add(new Point(i,j-1)); //left
                neighbors.add(new Point(i+1, j-1));//left down
                neighbors.add(new Point(i-1, j-1));//left up
                neighbors.add(new Point(i+1, j));//down
                neighbors.add(new Point(i-1,j));//up
            }
            else{
                neighbors.add(new Point(i,j+1));//right
                neighbors.add(new Point(i+1, j+1));//right down
                neighbors.add(new Point(i-1,j+1));//right up
                neighbors.add(new Point(i,j-1)); //left
                neighbors.add(new Point(i+1, j-1));//left down
                neighbors.add(new Point(i-1, j-1));//left up
                neighbors.add(new Point(i+1, j));//down
                neighbors.add(new Point(i-1,j));//up
            }
        }

        return neighbors.iterator();
    }

    /**
     * returns true if the cell in the row i and column j is can be set Alive
     * in the next step and false if not
     * @param i
     * @param j
     * @return
     */
    public boolean isSetAlive(int i, int j) {
        int NeighborsAlive = 0;

        Iterator<Point> Neighbors = NeighborIterator(i, j);
        while (Neighbors.hasNext()) {
            Point neighbor = Neighbors.next();
            NeighborsAlive = cells_matrix[neighbor.x][neighbor.y] ? NeighborsAlive + 1 : NeighborsAlive;
        }
        return NeighborsAlive >= 3;
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                s.append(cells_matrix[i][j]).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}







class CellsSimulator implements Simulable{

    private Cells cells;
    private GUISimulator gui;
    private Color cellsColor;
    private Boolean[][] cells_matrix;



    public CellsSimulator(Cells cells, GUISimulator gui, Color cellsColor){
        this.gui = gui;
        gui.setSimulable(this);
        this.cellsColor = cellsColor;
        this.cells = cells;
        this.cells_matrix = cells.getCells_matrix();
        draw();
    }

    @Override
    public void next() {
        for (int i = 0; i < cells.getHeight(); i++){
            for (int j = 0; j < cells.getWidth(); j++){
                cells_matrix[i][j] = cells.isSetAlive(i,j) ? true : null;
            }
        }
    }

    @Override
    public void restart() {

    }

    private void draw(){

    }


}