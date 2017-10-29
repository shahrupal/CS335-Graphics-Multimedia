import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.*;

public class Solver {

    private boolean visited[][];
    private Cell cellMatrix[][];
    private int numRows, numCols;

    public Solver(Cell[][] cells, int rows, int cols){

        numRows = rows;
        numCols = cols;

        visited = new boolean[numRows][numCols];

        cellMatrix = new Cell[numRows][numCols];
        cellMatrix = cells;

    }

    public void solveMaze(){

        // Initialize starting values.
        LinkedList<Cell> queue = new LinkedList<>();

        // Initialize `current` to be top-left cell and then adjust internal row- and column-variables.
        Cell current = cellMatrix[0][0];
        current.setBackground(Color.ORANGE);
        current.repaint();

        //while all the cells have not been visited, continue with loop
        boolean neighborsLeft = true;
        boolean end = true;
        while (!allCellsVisited() && neighborsLeft && end) {

            //add current cell to queue
            queue.add(current);

            // Mark the top-left spot as visited.
            visited[current.getCellRow()][current.getCellColumn()] = true;

            LinkedList<Cell> neighbors = getAccessibleNeighbors(current); // Return neighbors that have not been visited.

            if (neighbors.isEmpty()) {  //if no neighbors (all have been visited)
                boolean keepGoing = true;

                while (queue.size() > 0 && keepGoing) {

                    Cell temp = queue.pop();
                    temp.setBackground(Color.ORANGE);
                    neighbors = getAccessibleNeighbors(temp);

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

            if(current == cellMatrix[numRows - 1][numCols - 1]){
                end = false;
                System.out.println("WHAT THE HECK");
                continue;
            }

            //use first value from neighbors
            Cell neighbor = neighbors.get(0);

            current = neighbor;
            current.setBackground(Color.ORANGE);

        }

        System.out.println("NOICE 2");

    }

    //returns false if not ALL cells have been visited
    private boolean allCellsVisited() {

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (visited[i][j] == false) {
                    return false;
                }
            }
        }

        return true;
    }

    //returns a list of valid, accessible neighbors that have not already been visited
    public LinkedList<Cell> getAccessibleNeighbors(Cell c){

            LinkedList<Cell> neighbors = new LinkedList<>();

            int i = c.getCellRow();
            int j = c.getCellColumn();

            if(c.getTopEdge() == false){
                if(visited[i - 1][j] == false){
                    neighbors.add(cellMatrix[i - 1][j]);
                }
            }

            /**IF DOESNT WORK, DO TOP LEFT BOTTOM RIGHT**/
            if(c.getRightEdge() == false){
                if(visited[i][j + 1] == false){
                    neighbors.add(cellMatrix[i][j + 1]);
                }
            }

            if(c.getBottomEdge() == false){
                if(visited[i + 1][j] == false){
                    neighbors.add(cellMatrix[i + 1][j]);
                }
            }

            if(c.getLeftEdge() == false){
                if(visited[i][j - 1] == false){
                    neighbors.add(cellMatrix[i][j - 1]);
                }
            }

            return neighbors;

    }

}
