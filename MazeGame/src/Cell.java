// Created By: Rupal Shah

import java.awt.*;
import javax.swing.*;

public class Cell extends JPanel {

    private int row, column;  // to store number of rows and columns

    // stores edges of each cell
    // true if there is an edge, false if not
    // top, right, bottom, left
    boolean edges[] = {true, true, true, true};


    /** CONSTRUCTOR **/
    public Cell(){
        super();
        super.setBackground(Color.BLACK);  // sets cell color to black
        super.setBorder(BorderFactory.createLineBorder(Color.WHITE));  // sets cell borders to white
    }


    /** SETS ROWS AND COLUMNS TO GIVEN DIMENSIONS **/
    public void setDimensions(int r, int c) {
        row = r;
        column = c;
    }

    /** RETURNS ROW AND COLUMN NUMBER OF CELL **/
    public int getCellRow(){ return row; }
    public int getCellColumn(){ return column; }


    /** SETS EDGES (respective array indices) TO GIVEN BOOLEAN **/
    public void setTopEdge(boolean choice1){
        edges[0] = choice1;
    }
    public void setRightEdge(boolean choice2){
        edges[1] = choice2;
    }
    public void setBottomEdge(boolean choice3){
        edges[2] = choice3;
    }
    public void setLeftEdge(boolean choice4){
        edges[3] = choice4;
    }


    /** RETURNS EDGES (respective array indices) TO GIVEN BOOLEAN **/
    public boolean getTopEdge(){ return edges[0]; }
    public boolean getRightEdge(){ return edges[1]; }
    public boolean getBottomEdge(){ return edges[2]; }
    public boolean getLeftEdge(){ return edges[3]; }


    /** CREATES CERTAIN BORDERS ACCORDING TO EDGE ARRAY **/
    public void drawBorders(Cell current){

        // translating true/false to 1/0
        int edgeCopy[] = {1, 1, 1, 1}; // all borders set to existent

        // if edges[] index = false, set to 0 (remove border) - createMatteBorder only takes in 1's and 0's
        for(int i = 0; i < 4; i++){

            if(current.edges[i] == false){
                edgeCopy[i] = 0;
            }

        }

        // 1 = border, 0 = no border
        current.setBorder(BorderFactory.createMatteBorder(edgeCopy[0], edgeCopy[3],edgeCopy[2], edgeCopy[1], Color.WHITE)); // top, LEFT, bottom, RIGHT
    }

}
