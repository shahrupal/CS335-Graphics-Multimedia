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

        setBombs(row, col);
        setNumbers(row, col);

       for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                System.out.print(buttonsMatrix[i][j].getSurroundingBombs() + " ");
            }
            System.out.println();
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

        //set total number of bombs throughout board
        if(rows == 5){ numBombs = 5; }
        else if(rows == 8){ numBombs = 15; }
        else if(rows == 15){ numBombs = 30; }

        //initialize all positions of matrix to have 0 surrounding bombs
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++) {
                buttonsMatrix[i][j].setSurroundingBombs(0);
            }
        }

        //set numBombs amount of random bombs throughout the matrix (id of -1 will indicate this)
        for(int k = 0; k < numBombs; k++){

            //set random number for row and random number for col -- used to randomize position of bomb
            int randomRow = (int) (Math.random() * rows);
            int randomColumn = (int) (Math.random() * columns);

            //check if the position is not already a bomb, make it a bomb
            if(buttonsMatrix[randomRow][randomColumn].getSurroundingBombs() != -1){
                buttonsMatrix[randomRow][randomColumn].setSurroundingBombs(-1);
            }

        }
    }


    public void setNumbers(int rows, int columns){

        //iterate through all positions in matrix
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                if(buttonsMatrix[i][j].getSurroundingBombs() == -1){  //if there is a bomb in the position
                    //look at surround positions
                    for(int m = i - 1; m < i + 2; m++){
                        for(int n = j - 1; n < j + 2; n++){
                            if(isValidPosition(m) && isValidPosition(n)){  //makes sure position is valid (accounts for corners and first/last rows/columns)
                                int prevNum = buttonsMatrix[m][n].getSurroundingBombs();  //gets previous number of surrounding bombs
                                buttonsMatrix[m][n].setSurroundingBombs(prevNum + 1);  //notifies (adds) that another bomb is touching position
                            }
                        }
                    }
                    buttonsMatrix[i][j].setSurroundingBombs(-1);  //resets bomb position to -1
                }
            }
        }

    }



    //check to see if matrix position is valid
    public boolean isValidPosition(int currNum) {
        if((currNum > -1) && (currNum < numRows) && (currNum < numCols)){
            return true;
        }
        else{
            return false;
        }
    }



    /**somewhere set images based on "getSurroundingBombs()"*/
}
