import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Grid {

    private int numRows, numCols;
    private Cell cellMatrix[][];

    public Grid(int rows, int columns){

        numRows = rows;
        numCols = columns;

        cellMatrix = new Cell[numRows][numCols];

        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                Cell c = new Cell();
                cellMatrix[i][j] = c;
            }
        }

    }

    public void fillGrid(JPanel panel){
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                panel.add(cellMatrix[i][j]);
            }
        }
    }

    public void setNumRows(int r){
        numRows = r;
    }

    public void setNumCols(int c){
        numCols = c;
    }

}
