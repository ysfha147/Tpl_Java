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
    public int getMaxNeighbors(){
        return maxNeighbors;
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
                    if(super.getCells_matrix()[new_row][new_column].getStatus() != 0) {
                        if (super.getCells_matrix()[new_row][new_column].getStatus() != super.getCells_matrix()[row][column].getStatus()) {
                            counter++;
                        }
                    }
                }
            }
        }
        return counter;
    }

    @Override
    public void update() {
        ArrayList<Cell> habitationsThatWillBecomeVacant = new ArrayList<>();
        for (int i=0;i<super.getHeight();i++) {
            for (int j=0; j<super.getWidth();j++) {
                if(super.getCells_matrix()[i][j].getStatus() != 0) {
                    if (countNeighbors(i, j) > maxNeighbors) {
                        habitationsThatWillBecomeVacant.add(super.getCells_matrix()[i][j]);
                    }
                }

            }
        }



        Iterator<Cell> iterator = vacantHabitation.iterator();
        ArrayList<Cell> habitationsThatWillBecomeVacantCopy = new ArrayList<>(habitationsThatWillBecomeVacant) ;
        while (iterator.hasNext()) {
            Cell habitation = iterator.next();

            if (!habitationsThatWillBecomeVacant.isEmpty()) {
                Cell newHabitation = habitationsThatWillBecomeVacant.remove(0);
                habitation.setStatus(newHabitation.getStatus());
                newHabitation.setStatus(0);

                iterator.remove();  // Use iterator's remove method
                continue;
            }
            break;
        }

        if(!habitationsThatWillBecomeVacant.isEmpty()) throw new ArrayStoreException("not enough vacant places");

        vacantHabitation.addAll(habitationsThatWillBecomeVacantCopy);


    }
}


class SchellingSimulator extends Event implements Simulable {

    private final Schelling habitations;
    private final GUISimulator gui;

    private final EventManager eventManager = new EventManager();



    public SchellingSimulator(Schelling habitations, int date){
        super(date);
        this.gui = new GUISimulator(habitations.getHeight()*10, habitations.getWidth()*10, Color.WHITE);
        gui.setSimulable(this);
        this.habitations = habitations;
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
        habitations.setCells_matrix_init();
        draw();
    }

    Color generateColor(int value) {


        float hue = (float) ((value*69)%359)/359;
        float saturation = value == 0 ? 0.0f : 1.0f;
        float brightness = 1;




        // Create and return the Color object
        return Color.getHSBColor(hue, saturation, brightness);
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


    @Override
    public void execute() {
        habitations.update();
        this.setDate(getDate()+1);
        eventManager.addEvent(this);
        draw();
    }
}