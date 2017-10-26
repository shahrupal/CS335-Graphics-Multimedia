import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Cell{

    private int numRows, numCols;
    private int cellWidth, cellHeight;

    public Cell(int rows, int columns){
        numRows = rows;
        numCols = columns;

        cellHeight = 560 / numRows;
        cellWidth = 380 / numCols;
    }

    public int getTopLeftX(int i){
        return (cellWidth * i);
    }

    public int getTopLeftY(int j){
        return (cellHeight * j);
    }

    public int getTopRightX(int i){
        return (cellWidth * (i + 1));
    }

    public int getTopRightY(int j){
        return (cellHeight * j);
    }

    public int getBottomRightX(int i){
        return (cellWidth * (i + 1));
    }

    public int getBottomRightY(int j){
        return (cellHeight * (j + 1));
    }

    public int getBottomLeftX(int i){
        return (cellWidth * i);
    }

    public int getBottomLeftY(int j){
        return (cellHeight * (j + 1));
    }


}