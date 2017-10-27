import java.awt.*;
import java.awt.event.*;
import java.util.Stack;
import javax.swing.*;

public class Grid {

    private int numRows, numCols;

    private Cell cellMatrix[][];
    private boolean visited[][];

    private Stack stack;

    public Grid(int rows, int columns){

        numRows = rows;
        numCols = columns;

        visited = new boolean[numRows][numCols];

        cellMatrix = new Cell[numRows][numCols];

        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                Cell c = new Cell();
                cellMatrix[i][j] = c;
            }
        }

    }

    //adds matrix of cells to given panel
    public void fillGrid(JPanel panel){
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                panel.add(cellMatrix[i][j]);
            }
        }
    }

    /** DFS FOR GENERATING MAZE **/
    public void generateMaze(){

        Cell current = new Cell();  //create new cell to store current cell

        current = cellMatrix[0][0];  //force first cell to be top left -- position (0, 0)

        current.setCellRow(0);
        current.setCellColumn(0);

        visited[current.getCellRow()][current.getCellColumn()] = true;

 /**    stack.push(current);  //add first cell to stack      **/

        //while all the cells have not been visited, continue with loop
        while(allCellsVisited() == false){

            //if all the neighbors of the current cell are NOT visited
            if(allNeighborsVisited(current.getCellRow(), current.getCellColumn())){


            }

            //IF ALL NEIGHBORS OF CURRENT CELL ARE VISITED, POP FROM STACK???
            else{ }

        }


        // TESTING ALL NEIGHBORS VISITED FUNCTION
/*      visited[0][1] = true;
        visited[1][0] = true;
        System.out.println(allNeighborsVisited(current.getCellRow(), current.getCellColumn()));
*/


    }

    //"erases" border by coloring given border black
    public void eraseBorder(){

    }

    //if (valid) positions are not ALL visited, return false
    //if (valid) positions are all visited, return true
    public boolean allNeighborsVisited(int currentRow, int currentColumn){

        if((positionIsValid(currentRow, currentColumn, "TOP") == true) && (visited[currentRow - 1][currentColumn] == false)){
            return false;
        }

        if((positionIsValid(currentRow, currentColumn, "BOTTOM") == true) && (visited[currentRow + 1][currentColumn] == false)){
            return false;
        }

        if((positionIsValid(currentRow, currentColumn, "RIGHT") == true) && (visited[currentRow][currentColumn + 1] == false)){
            return false;
        }

        if((positionIsValid(currentRow, currentColumn, "LEFT") == true) && (visited[currentRow][currentColumn - 1] == false)){
            return false;
        }

        return true;
    }

    //given which position (relative to current cell) to test, return if position is valid or not
    public boolean positionIsValid(int currentRow, int currentColumn, String choice){

        //if top cell is invalid, return false
        if((choice == "TOP") && ((currentRow - 1) < 0)){
            return false;
        }

        //if bottom cell is invalid, return false
        if((choice == "BOTTOM") && ((currentRow + 1) >= numRows)){
           return false;
        }

        //if right cell is invalid, return false
        if((choice == "RIGHT") && ((currentColumn + 1) >= numCols)){
            return false;
        }

        //if left cell is invalid, return false
        if((choice == "LEFT") && ((currentColumn - 1) < 0)){
            return false;
        }

        return true;

    }

    //returns false if not ALL cells have been visited
    public boolean allCellsVisited(){

        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                if(visited[i][j] == false){
                    return false;
                }
            }
        }

        return true;
    }


} /** END OF CLASS **/
