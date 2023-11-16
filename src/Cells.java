import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cells {
    /**/
    private Cell[][] cells_matrix;
    private int height; private int width;
    private Cell[][] cells_matrix_init;


    public Cells(int height, int width, Point... StateZero){
        try{
            this.width = width;
            this.height = height;
            cells_matrix = new Cell[height][width];
            cells_matrix_init = new Cell[height][width];
            for (int i = 0;i<height;i++) {
                for(int j=0;j<width;j++) {
                    cells_matrix[i][j] = new Cell(0);
                    cells_matrix_init[i][j] = new Cell(0);
                }
            }
            for (Point pt : StateZero){
                cells_matrix[(int)pt.getX()][(int)pt.getY()].setStatus(1);;
                cells_matrix_init[(int)pt.getX()][(int)pt.getY()].setStatus(1);
            }
        }catch (IllegalAccessError e){
            System.out.println("Caught an IllegalArgumentException: on of the point has exceeded the given range");
        }
    }
    public void Cells(int height, int width, List<Point> StateZero){
        try{
            this.width = width;
            this.height = height;
            cells_matrix = new Cell[height][width];
            cells_matrix_init = new Cell[height][width];
            for (int i = 0;i<height;i++) {
                for(int j=0;j<width;j++) {
                    cells_matrix[i][j] = new Cell(0);
                    cells_matrix_init[i][j] = new Cell(0);
                }
            }
            for (Point pt : StateZero){
                cells_matrix[(int)pt.getX()-1][(int)pt.getY()-1].setStatus(1);
            }
        }catch (IllegalAccessError e){
            System.out.println("Caught an IllegalArgumentException: on of the point has exceeded the given range");
        }
    }


    public Cell[][] getCells_matrix() {
        return cells_matrix;
    }

    public Cell[][] getCells_matrix_init() {
        return cells_matrix_init;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }


    int countNeighbors(int row, int column, int maxStatus) {
        int counter = 0;
        for(int i=-1; i<2; i++) {
            for (int j=-1; j<2; j++) {
                if (i!=0 || j!=0) {
                    int new_row = (row+i+width)%width;
                    int new_column = (column+j+height)%height;
                    if (cells_matrix[new_row][new_column].getStatus() == (cells_matrix[row][column].getStatus() +1)%maxStatus) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    int countNeighbors(int row, int colum) {
        return countNeighbors(row,colum,2);
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

    public void setCells_matrix_init() {
        for (int i=0;i<height;i++) {
            for (int j=0;j<width; j++) {
                cells_matrix[i][j].setStatus(cells_matrix_init[i][j].getStatus());
            }
        }
    }

    public void update() {
        ArrayList<Cell> aliveCells = new ArrayList<Cell>();
        ArrayList<Cell> deadCells = new ArrayList<Cell>();
        for (int i=0;i<height;i++) {
            for (int j=0; j<width;j++) {
                if (cells_matrix[i][j].getStatus() == 0) {
                    if (countNeighbors(i, j) == 3) {
                        aliveCells.add(cells_matrix[i][j]);
                    }
                }
                else if (countNeighbors(i, j) != 6 && countNeighbors(i, j) != 5) {
                    deadCells.add(cells_matrix[i][j]);
                }

            }
        }
        for (Cell aliveCell : aliveCells) {
            aliveCell.setStatus(1);
        }
        for (Cell deadCell : deadCells) {
            deadCell.setStatus(0);
        }
    }
}







class CellsSimulator extends Event implements Simulable {

    private final Cells cells;
    private final GUISimulator gui;

    private final EventManager eventManager = new EventManager();



    public CellsSimulator(Cells cells, int date){
        super(date);
        this.gui = new GUISimulator(cells.getHeight()*10, cells.getWidth()*10, Color.WHITE);
        gui.setSimulable(this);
        this.cells = cells;
        eventManager.addEvent(this);
        draw();
    }

    public GUISimulator getGui() {
        return gui;
    }

    @Override
    public void next() {
        if (!eventManager.isFinished()) {
            eventManager.next();
        }
    }

    @Override
    public void restart() {
        cells.setCells_matrix_init();
        draw();
    }

    private void draw(){
        gui.reset();
        for(int i = 0; i<cells.getHeight(); i++) {
            for (int j=0; j<cells.getWidth();j++) {
                Color color =  cells.getCells_matrix()[i][j].getStatus() == 1 ? Color.black : Color.white;
                gui.addGraphicalElement(new Rectangle(i*10,j*10,Color.gray,color,10));
            }
        }
    }


    @Override
    public void execute() {
        cells.update();
        this.setDate(getDate()+1);
        eventManager.addEvent(this);
        draw();
    }
}