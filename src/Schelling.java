import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Schelling extends Cells{
    int maxNeighbors;
    Set<Cell> vacantHabitation;
    public Schelling(int height, int width, int maxNeighbors) {
        super(height, width);
        this.maxNeighbors = maxNeighbors;
        vacantHabitation = new HashSet<>();
        for(int row = 0; row<height; row++){
            for(int column = 0; column<width; column++){
                vacantHabitation.add(super.getCells_matrix()[row][column]);
            }
        }
    }

    public void addHabitationInit(int row, int column, int family_status){
        super.getCells_matrix()[row][column].setStatus(family_status);
        super.getCells_matrix_init()[row][column].setStatus(family_status);
        if(family_status != 0){
            vacantHabitation.remove(super.getCells_matrix()[row][column]);
        }

    }
    public void addVacantHabitationInit(int row, int column){
        super.getCells_matrix()[row][column].setStatus(0);
        super.getCells_matrix_init()[row][column].setStatus(0);
        vacantHabitation.add(super.getCells_matrix()[row][column]);
    }

    private void addHabitation(int row, int column, int family_status){
        super.getCells_matrix()[row][column].setStatus(family_status);
        if(family_status != 0){
            vacantHabitation.remove(super.getCells_matrix()[row][column]);
        }

    }

    private void addVacantHabitation(int row, int column){
        super.getCells_matrix()[row][column].setStatus(0);
        vacantHabitation.add(super.getCells_matrix()[row][column]);
    }

    @Override
    public int countNeighbors(int row, int column) {
        int counter = 0;
        for(int i=-1; i<2; i++) {
            for (int j=-1; j<2; j++) {
                if (i!=0 || j!=0) {
                    int new_row = (row+i+super.getWidth())%super.getWidth();
                    int new_column = (column+j+super.getHeight())%super.getHeight();
                    if (super.getCells_matrix()[new_row][new_column].getStatus() != super.getCells_matrix()[row][column].getStatus()) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    @Override
    public void update() {
        ArrayList<Cell> habitationsThatWillBecomeVacant = new ArrayList<>();
        Iterator<Cell> iterator = vacantHabitation.iterator();
        for (int i=0;i<super.getHeight();i++) {
            for (int j=0; j<super.getWidth();j++) {
                if(super.getCells_matrix()[i][j].getStatus() == 0) {
                    if (countNeighbors(i, j) > maxNeighbors) {
                        habitationsThatWillBecomeVacant.add(super.getCells_matrix()[i][j]);
                    }
                }

            }
        }

        Cell currentHabitation = new Cell(0);
        //TODO
        if(iterator.hasNext()){
            currentHabitation = iterator.next();
        } else throw new ArrayStoreException("Not enough space");

        for(int i= 0; i < habitationsThatWillBecomeVacant.size()-1; i++){
            if(iterator.hasNext()){
                Cell currentCellCopy = currentHabitation;
                currentHabitation.setStatus(habitation.getStatus());
                currentHabitation = iterator.next();
                vacantHabitation.remove(currentCellCopy);
                habitation.setStatus(0);

            }


        }

    }
}


class SchellingSimulator implements Simulable {

    private Schelling habitations;
    private GUISimulator gui;



    public SchellingSimulator(Schelling habitations){
        this.gui = new GUISimulator(habitations.getHeight()*10, habitations.getWidth()*10, Color.WHITE);
        gui.setSimulable(this);
        this.habitations = habitations;
        draw();
    }

    public GUISimulator getGui() {
        return gui;
    }

    @Override
    public void next() {
        habitations.update();
        draw();
    }

    @Override
    public void restart() {
        habitations.setCells_matrix_init();
        draw();
    }

    static Color generateColor(int value) {
        value = Math.abs(value);

        // Generate RGB components based on the value
        int red = (value * 37) % 256;
        int green = (value * 56) % 256;
        int blue = (value * 63) % 256;

        // Create and return the Color object
        return new Color(red, green, blue);
    }



    private void draw(){
        gui.reset();
        for(int i = 0; i<habitations.getHeight(); i++) {
            for (int j=0; j<habitations.getWidth();j++) {
                Color color = generateColor(habitations.getCells_matrix()[i][j].getStatus());
                gui.addGraphicalElement(new Rectangle(i*10,j*10,Color.gray,color,10));
            }
        }
    }


}