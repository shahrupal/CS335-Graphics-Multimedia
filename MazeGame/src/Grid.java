import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;
import javax.swing.*;

public class Grid {

    private int numRows, numCols;

    private Cell cellMatrix[][];
    private boolean visited[][];

    private int newRow = 0, newCol = 0;


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

        // Initialize starting values.
        LinkedList<Cell> queue = new LinkedList<>();
        Random rand = new Random();
        Cell neighbor = null;

        Cell current = new Cell();  //create new cell to store current cell

        // Initialize `current` to be top-left cell and then adjust internal row- and column-variables.
        current = cellMatrix[0][0];
        current.setCellRow(0);
        current.setCellColumn(0);
        current.setBackground(Color.ORANGE);
        current.repaint();

        // Mark the top-left spot as visited.
        visited[current.getCellRow()][current.getCellColumn()] = true;

        //while all the cells have not been visited, continue with loop
        while (!allCellsVisited()) {

            //add current cell to queue
            queue.add(current);

            LinkedList<Cell> neighbors = getNeighbors(current); // Return neighbors that have not been visited.
            if (neighbors.size() > 0)
                neighbor = neighbors.get(rand.nextInt(neighbors.size()));
            else {  //IF ALL NEIGHBORS HAVE BEEN VISITED
                boolean keepGoing = true;
                while (queue.size() > 0 && keepGoing) {
                    Cell temp = queue.pop();
                    neighbors = getNeighbors(temp);
                    if (neighbors.size() > 0) {
                        neighbor = temp;
                        keepGoing = false;  //acts as break
                    }
                }
            }
            // break the wall between current and neighbor
            current = neighbor;
            visited[current.getCellRow()][current.getCellColumn()] = true;

        }

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

    //returns linked list of all (valid) neighbors of current cell
    public LinkedList<Cell> getNeighbors(Cell c){

        LinkedList<Cell> neighbors = new LinkedList<>();

        int cellRow = c.getCellRow();
        int cellColumn = c.getCellColumn();

        //TOP
        int topX = cellColumn;
        int topY = cellRow - 1;
        if(isValid(topX, topY)){
            if(visited[topX][topY] == false){
                Cell c1 = new Cell();
                c1.setCellRow(topX);
                c1.setCellColumn(topY);
                neighbors.add(c1);
            }
        }

        //RIGHT
        int rightX = cellColumn + 1;
        int rightY = cellRow;
        if(isValid(rightX, rightY)){
            if(visited[rightX][rightY] == false){
                Cell c2 = new Cell();
                c2.setCellRow(rightX);
                c2.setCellColumn(rightY);
                neighbors.add(c2);
            }
        }

        //BOTTOM
        int bottomX = cellColumn;
        int bottomY = cellRow + 1;
        if(isValid(bottomX, bottomY)){
            if(visited[bottomX][bottomY]){
                Cell c3 = new Cell();
                c3.setCellRow(bottomX);
                c3.setCellColumn(bottomY);
                neighbors.add(c3);
            }
        }

        //LEFT
        int leftX = cellColumn - 1;
        int leftY = cellRow;
        if(isValid(leftX, leftY)){
            if(visited[leftX][leftY]){
                Cell c4 = new Cell();
                c4.setCellRow(leftX);
                c4.setCellRow(leftY);
                neighbors.add(c4);
            }
        }

        return neighbors;

    }

    //checks if position is valid
    public boolean isValid(int row, int col){
        if((row >= 0) && (row < numRows) && (col >= 0)  && (col < numCols)){
            return true;
        }
        return false;
    }




} /** END OF CLASS **/
