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

    public Cells(int height, int width, Point... StateZero){
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

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Boolean[] line: cells_matrix){
            for (Boolean cell: line){
                s.append(cell).append(" ");
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
    public CellsSimulator(Cells cells, GUISimulator gui, Color cellsColor){
        this.gui = gui;
        gui.setSimulable(this);
        this.cellsColor = cellsColor;
        this.cells = cells;
        draw();
    }

    /**
     *
     * @param i
     * @param j
     * @return an iterator over the indexes of the neighbors of the element in of
     * the index (i,j) of the matrix of size n*m
     */
    private Iterator<Point> NeighborIterator(int i, int j){
        List<Point> neighbors = new ArrayList<>();
        int n = cells.getHeight(); int m = cells.getWidth();
        if (i == 1){
            if (j == 1){
                neighbors.add(new Point(i,j+1));//right
                neighbors.add(new Point(i+1, j+1));//right down
                neighbors.add(new Point(n,j+1));//right up
                neighbors.add(new Point(i,m)); //left
                neighbors.add(new Point(i+1, m));//left down
                neighbors.add(new Point(n, m));//left up
                neighbors.add(new Point(i+1, j));//down
                neighbors.add(new Point(n,j));//up
            }
        }

        return neighbors.iterator();
    }


    /**
     * returns true if the cell in the row i and column j is can be set Alive
     * in the next step and false if not
     * @param i
     * @param j
     * @param Cells_matrix
     * @return
     */
    private boolean estSetAlive(int i, int j, Boolean[][] Cells_matrix){
        return true;
    }

    @Override
    public void next() {

    }

    @Override
    public void restart() {

    }

    private void draw(){

    }


}