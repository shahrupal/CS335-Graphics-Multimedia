import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.*;

public class Grid {

    private int numRows, numCols;

    private Cell cellMatrix[][];
    private boolean visited[][];

    public Grid(int rows, int columns){

        numRows = rows;
        numCols = columns;

        visited = new boolean[numRows][numCols];

        cellMatrix = new Cell[numRows][numCols];

        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                Cell c = new Cell();
                c.setDimensions(i, j);
                cellMatrix[i][j] = c;
            }
        }

    }

    public void solveMaze(){

        Solver solve = new Solver(cellMatrix, numRows, numCols);
        solve.solveMaze();

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

        // Initialize `current` to be top-left cell and then adjust internal row- and column-variables.
        Cell current = cellMatrix[0][0];
        current.setBackground(Color.ORANGE);
        current.repaint();

        //while all the cells have not been visited, continue with loop
        boolean neighborsLeft = true;
        while (!allCellsVisited() || neighborsLeft) {

            //add current cell to queue
            queue.add(current);

            // Mark the top-left spot as visited.
            visited[current.getCellRow()][current.getCellColumn()] = true;

            LinkedList<Cell> neighbors = getNeighbors(current); // Return neighbors that have not been visited.

            if (neighbors.isEmpty()) {  //if no neighbors (all have been visited)
                boolean keepGoing = true;

                while (queue.size() > 0 && keepGoing) {

                    Cell temp = queue.pop();
                    neighbors = getNeighbors(temp);

                    //if there are unvisited neighbors
                    if (neighbors.size() > 0) {
                        current = temp;
                        keepGoing = false;  //acts as break
                    }

                    //if queue is empty
                    if (temp == null) {
                        keepGoing = false;
                    }
                }

            }

            //if neighbors is empty after queue is empty, quit loop
            if (neighbors.isEmpty()) {
                neighborsLeft = false;
                continue;
            }

            //find a random neighbor from neighbors linked list
            Cell neighbor = neighbors.get(rand.nextInt(neighbors.size()));

            // break the wall between current and neighbor

            removeEdge(current, neighbor);

            current = neighbor;
//            current.setBackground(Color.ORANGE);

        }

        System.out.println("NOICE");

    }


    public void removeEdge(Cell current, Cell neighbor){

        int currentRow = current.getCellRow();
        int currentColumn = current.getCellColumn();
        int neighborRow =  neighbor.getCellRow();
        int neighborColumn = neighbor.getCellColumn();

        //if the neighbor is the top cell
        if((neighborRow == currentRow - 1) && (neighborColumn == currentColumn)){

            //erase top border of current cell
            current.setTopEdge(false);
            //erase bottom border of neighbor cell
            neighbor.setBottomEdge(false);

        }

        //if the neighbor is the right cell
        if((neighborRow == currentRow) && (neighborColumn == currentColumn + 1)){
            current.setRightEdge(false);
            neighbor.setLeftEdge(false);
        }

        //if neighbor is the bottom cell
        if((neighborRow == currentRow + 1) && (neighborColumn == currentColumn)){
            current.setBottomEdge(false);
            neighbor.setTopEdge(false);
        }

        //if neighbor is the left cell
        if((neighborRow == currentRow) && (neighborColumn == currentColumn - 1)){
            current.setLeftEdge(false);
            neighbor.setRightEdge(false);
        }

        current.drawBorders(current);
        neighbor.drawBorders(neighbor);

    }

    //returns false if not ALL cells have been visited
    private boolean allCellsVisited(){

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

        int i = c.getCellRow();
        int j = c.getCellColumn();

        //TOP
        if (isValid(i-1, j)) {
            if (!visited[i-1][j]) {
                neighbors.add(cellMatrix[i-1][j]);
            }
        }

        //RIGHT
        if (isValid(i, j+1)) {
            if (!visited[i][j+1]) {
                neighbors.add(cellMatrix[i][j+1]);
            }
        }

        //BOTTOM
        if (isValid(i+1, j)) {
            if (!visited[i+1][j]) {
                neighbors.add(cellMatrix[i+1][j]);
            }
        }

        //LEFT
        if (isValid(i, j-1)) {
            if (!visited[i][j-1]) {
                neighbors.add(cellMatrix[i][j-1]);
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
