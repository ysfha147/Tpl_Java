import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

import java.awt.*;
import java.util.ArrayList;

public class ImmigrationGame extends Cells{

    private final int maxStatus;
    public ImmigrationGame(int height, int width,int maxStatus, Point... StateZero) {
        super(height, width, StateZero);
        this.maxStatus = maxStatus;
    }

    public int getMaxStatus() {
        return maxStatus;
    }

    @Override
    public void update() {
        ArrayList<Cell> changedCells = new ArrayList<Cell>();
        for (int i = 0; i < super.getHeight(); i++) {
            for (int j = 0; j < super.getWidth(); j++) {
                if (super.countNeighbors(i, j, maxStatus) > 2) {
                    changedCells.add(super.getCells_matrix()[i][j]);
                }

            }
        }
        for (Cell changedCell : changedCells) {
            changedCell.setStatus((changedCell.getStatus()+1)%maxStatus);
        }
    }

    public void addCell(int row, int column, int status) {
        if (status>=maxStatus) throw new IllegalArgumentException("status must be less than maxStatus");
        super.getCells_matrix()[row][column].setStatus(status);
        super.getCells_matrix_init()[row][column].setStatus(status);
    }

}


class ImmigrationGameSimulator extends  Event implements Simulable {

    private final ImmigrationGame cells;
    private final GUISimulator gui;

    private final EventManager eventManager = new EventManager();

    public ImmigrationGameSimulator(ImmigrationGame cells, int date) {
        super(date);
        this.gui = new GUISimulator(cells.getHeight()*10,cells.getWidth()*10,Color.white);
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

    public static Color changeHue(float degreeChange) {
        float red = (1-degreeChange)*255;
        float green = (1-degreeChange)*255;
        float blue = (1-degreeChange)*255;
        return new Color((int) red, (int) green, (int) blue);
    }

    public void draw() {
        gui.reset();
        for(int i = 0; i<cells.getHeight(); i++) {
            for (int j=0; j<cells.getWidth();j++) {
                float prev_color = (float) cells.getCells_matrix()[i][j].getStatus() /(cells.getMaxStatus()-1);
                Color color =  changeHue(prev_color);
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
