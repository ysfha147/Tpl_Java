import gui.Simulable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Cells {
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

    @Override
    public void next() {

    }

    @Override
    public void restart() {

    }


}