import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoard {

    private GameButton buttonsMatrix[][];
    private ClassLoader loader = getClass().getClassLoader();

    private int numRows, numCols, numBombs;

    private boolean isCleared[][];  //stores which position have been cleared (criteria: all buttons around this one have been shown)
    private boolean isVisited[][];  //stores which positions have been visited (criteria: this position has been shown)

    private int clearedCounter;

    public GameBoard(int row, int col, int bom, ActionListener AL) {

        //makes data accessible
        numRows = row;
        numCols = col;
        numBombs = bom;

        clearedCounter = 0;

        //reset boolean matrics
        isCleared = new boolean[numRows][numCols];
        isVisited = new boolean[numRows][numCols];

        //creates empty matrix of row x col size -- expecting GameButton object
        buttonsMatrix = new GameButton[row][col];

        //fills matrix with buttons -- each with action listener
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                GameButton b = new GameButton();
                b.addActionListener(AL);
                buttonsMatrix[i][j] = b;
            }
        }

        setBombs(row, col, bom);
        setNumbers(row, col);
        setImages(AL);

    }


    //add each button in the matrix to panel
    public void fillBoard(JPanel board) {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                board.add(buttonsMatrix[i][j]);
            }
        }
    }


    //randomly sets bombs across board
    public void setBombs(int rows, int columns, int bombs) {

        //initialize all positions of matrix to have 0 surrounding bombs
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                buttonsMatrix[i][j].setSurroundingBombs(0);
            }
        }

        //set numBombs amount of random bombs throughout the matrix (id of -1 will indicate this)
        int bombCount = 0;
        while (bombCount < bombs) {

            //set random number for row and random number for col -- used to randomize position of bomb
            int randomRow = (int) (Math.random() * rows);
            int randomColumn = (int) (Math.random() * columns);

            //check if the position is not already a bomb, make it a bomb
            if (buttonsMatrix[randomRow][randomColumn].getSurroundingBombs() != -1) {
                buttonsMatrix[randomRow][randomColumn].setSurroundingBombs(-1);
                bombCount = bombCount + 1;
            }

        }
    }

    //sets number of surrounding bombs to each button
    public void setNumbers(int rows, int columns) {

        //iterate through all positions in matrix
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                if (buttonsMatrix[i][j].getSurroundingBombs() == -1) {  //if there is a bomb in the position

                    //look at surrounding positions
                    for (int m = i - 1; m < i + 2; m++) {
                        for (int n = j - 1; n < j + 2; n++) {

                            buttonsMatrix[i][j].setSurroundingBombs(-1);  //resets bomb position to -1

                            //makes sure position is valid (accounts for corners and first/last rows/columns)
                            if (isValidPosition(m) && isValidPosition(n)) {
                                int prevNum = buttonsMatrix[m][n].getSurroundingBombs();  //gets previous number of surrounding bombs

                                if (prevNum != -1) {  //if surrounding position is also a bomb - leave it at -1
                                    buttonsMatrix[m][n].setSurroundingBombs(prevNum + 1);  //notifies (adds) that another bomb is touching position

                                }

                            }
                        }
                    }

                }
            }
        }

    }


    //check to see if matrix position is valid
    public boolean isValidPosition(int currNum) {
        if ((currNum > -1) && (currNum < numRows) && (currNum < numCols)) {
            return true;
        } else {
            return false;
        }
    }


    //set back images based on number of surrounding bombs to current position
    public void setImages(ActionListener AL) {

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {

                //set image according to number of surrounding bombs
                int imageIndex = buttonsMatrix[i][j].getSurroundingBombs();
                String imgPath = "images/" + imageIndex + "back.jpg";
                ImageIcon img = new ImageIcon(loader.getResource(imgPath));

                //resize images to fit buttons
                Image storeImg = img.getImage();
                Image resizeImg = storeImg.getScaledInstance(25, 20, Image.SCALE_SMOOTH);
                img = new ImageIcon(resizeImg);

                //create a new button -- identical to current one but set with front image
                GameButton newButton = new GameButton(img);
                newButton.setSurroundingBombs(imageIndex);

                //add action listener to each button
                newButton.addActionListener(AL);

                //replace old button with new (same but with image)
                buttonsMatrix[i][j] = newButton;
            }
        }
    }


    //show images of all bombs on the board
    public void showAllBombs() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (buttonsMatrix[i][j].getSurroundingBombs() == -1) {  //if a bomb
                    buttonsMatrix[i][j].showBack();  //show to user
                }
            }
        }
    }


    //if position has no surrounding bombs (already given that the num of surrounding neighbors is 0)
    //show neighbors until no 0 surrounding neighbors left
    public void clearNeighbors(GameButton current) {

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {

                if (buttonsMatrix[i][j] == current) {  //find position of current button

                    isCleared[i][j] = true;  //state that the position has been cleared (meaning it's surrounding positions have been shown)
                    isVisited[i][j] = true; //state that the position has been visited

                    //look at surrounding positions
                    for (int m = i - 1; m < i + 2; m++) {
                        for (int n = j - 1; n < j + 2; n++) {

                            //makes sure position is valid (accounts for corners and first/last rows/columns)
                            if (isValidPosition(m) && isValidPosition(n)) {

                                buttonsMatrix[m][n].showBack();  //show back of all surrounding positions
                                isVisited[m][n] = true;  //state all surrounding positions have been visited

                                if(buttonsMatrix[m][n].getSurroundingBombs() == 0){  //if surrounding bomb also has 0 surrounding bombs
                                   if(isCleared[m][n] == false){  //if it hasn't been cleared yet
                                       current = buttonsMatrix[m][n];  //make that the new current button
                                       isCleared[m][n] = true;  //state that it's been cleared now
                                       clearNeighbors(current); //clear it by recursively calling this function
                                   }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    //state current button has been visited
    public void visited(GameButton current){
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++) {
                if(buttonsMatrix[i][j] == current){
                    isVisited[i][j] = true;
                }
            }
        }
    }

    //if ALL positions left (not visited) are bombs, return true
    public boolean gameOver(){
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                if(isVisited[i][j] == false){
                    if(buttonsMatrix[i][j].getSurroundingBombs() == -1){
                        continue;
                    }
                    else{
                        return false;
                    }
                }
            }
        }
        return true;
    }

}