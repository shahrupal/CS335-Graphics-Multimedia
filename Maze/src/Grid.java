import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Grid extends JPanel{

    private int numRows, numCols;
    private Cell cellMatrix[][];
    private boolean visited[][];

    public Grid(int rows, int columns){

        numRows = rows;
        numCols = columns;

        cellMatrix = new Cell[numRows][numCols];
        visited = new boolean[numRows][numCols];

        //create a 2D array of cells
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                Cell c = new Cell(numRows, numCols);
                cellMatrix[i][j] = c;
            }
        }

    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                g.drawLine(cellMatrix[i][j].getTopLeftX(i), cellMatrix[i][j].getTopLeftY(j), cellMatrix[i][j].getTopRightX(i), cellMatrix[i][j].getTopRightY(j)); //draws top line of cell
                g.drawLine(cellMatrix[i][j].getTopRightX(i), cellMatrix[i][j].getTopRightY(j), cellMatrix[i][j].getBottomRightX(i), cellMatrix[i][j].getBottomRightY(j)); //draws right line of cell
                g.drawLine(cellMatrix[i][j].getBottomRightX(i), cellMatrix[i][j].getBottomRightY(j), cellMatrix[i][j].getBottomLeftX(i), cellMatrix[i][j].getBottomLeftY(j)); //draws bottom line of cell
                g.drawLine(cellMatrix[i][j].getBottomLeftX(i), cellMatrix[i][j].getBottomLeftY(j), cellMatrix[i][j].getTopLeftX(i), cellMatrix[i][j].getTopLeftY(j)); //draws left line
            }
        }

    }

}
