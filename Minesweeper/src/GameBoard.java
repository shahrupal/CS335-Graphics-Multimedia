import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoard {

    private GameButton buttonsMatrix [][];

    private int numRows, numCols;
    private int numBombs;

    public GameBoard(int row, int col, ActionListener AL){

        //makes data accessible
        numRows = row;
        numCols = col;

        //creates empty matrix of row x col size -- expecting GameButton object
        buttonsMatrix = new GameButton[row][col];

        //fills matrix with buttons -- each with action listener
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                GameButton b = new GameButton();
                b.addActionListener(AL);
                buttonsMatrix[i][j] = b;
            }
        }

    }


    //add each button in the matrix to panel
    public void fillBoard(JPanel board){
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                board.add(buttonsMatrix[i][j]);
            }
        }
    }


    //randomly sets bombs across board
    //5x5 grid contains 5 bombs, 8x8 contains 15, 15x15 contains 30
    public void setBombs(int rows, int columns){

        if(rows == 5){ numBombs = 5; }
        else if(rows == 8){ numBombs = 15; }
        else if(rows == 15){ numBombs = 30; }

        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++) {
                buttonsMatrix[i][j].setSurroundingBombs(0);  //initialize bomb id's to zero
            }
        }

        //set numBombs amount of random bombs throughout the matrix (id of -1 will indicate this)
        for(int k = 0; k < numBombs; k++){
            /**set random number for row and random number for col**/
            /**check if id is already -1**/
        }

    }

}
