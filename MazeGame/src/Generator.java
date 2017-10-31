// Created By: Rupal Shah

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;

public class Generator {

    private int numRows, numCols;  // to store number of rows and columns

    private Cell cellMatrix[][];  // to store 2D array of cells
    private boolean visited[][];  // to store 2D array of booleans - if visited: true
    private Timer gameTimer;  // create timer

    boolean neighborsLeft;  // to store if there are any existent neighbors of cell
    Cell current;  // create cell to store current cell


    /** CONSTRUCTOR **/
    public Generator(Cell cells[][], int rows, int columns){

        // set number of row and columns to given values
        numRows = rows;
        numCols = columns;

        // create empty 2D array to store if each cell has been visited or not
        visited = new boolean[numRows][numCols];

        // set matrix to given matrix of cells
        cellMatrix = new Cell[numRows][numCols];
        cellMatrix = cells;

    }


    /** GENERATES MAZE WITH ANIMATION - TIMER INCLUDED **/
    public void generateMazeAnimated(int userTime){

        LinkedList<Cell> queue = new LinkedList<>();  // initialize queue
        Random rand = new Random();  // initialize random number generator

        // initialize 'current' to be top-left cell and set it to be green
        current = cellMatrix[0][0];
        current.setBackground(Color.GREEN);
        current.repaint();

        // initialize variable to be true
        neighborsLeft = true;

        // for animation - create timer and for every second, perform actionPerformed()
        ActionListener timer = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // if all cells have not been visited and there are no neighbors left (even after queue is empty)
                if (!allCellsVisited() || neighborsLeft) {

                    queue.add(current);  // add current cell to queue
                    visited[current.getCellRow()][current.getCellColumn()] = true;  // mark current cell as visited
                    LinkedList<Cell> neighbors = getNeighbors(current);  // store neighbors that have not been visited

                    if (neighbors.isEmpty()) {  // if there are no neighbors (all have been visited)

                        boolean keepGoing = true;  // initialize boolean to act as a break

                        // if the queues is not empty and there are not any unvisited neighbors
                        while (queue.size() > 0 && keepGoing) {

                            Cell temp = queue.pop();  // pop off first index from queue and store as temp
                            neighbors = getNeighbors(temp);  // find the unvisited neighbors of temp

                            // if there are unvisited neighbors
                            if (neighbors.size() > 0) {
                                current = temp;  // temp is now current cell
                                keepGoing = false;  // acts as break
                            }

                            // if queue is empty
                            if (temp == null) {
                                keepGoing = false;  // acts as break
                            }
                        }

                    }

                    // if neighbors is empty after queue is empty, quit loop
                    if (neighbors.isEmpty()) {
                        neighborsLeft = false;  // acts as break
                    }

                    // find a random neighbor from 'neighbors' linked list
                    Cell neighbor = neighbors.get(rand.nextInt(neighbors.size()));

                    // break the wall between current and neighbor
                    removeEdge(current, neighbor);

                    // neighbor is not the current cell
                    current = neighbor;

                }
                else{

                    // after maze has been generated (all cells have been visited), stop timer
                    gameTimer.stop();

                }
            }
        };  // end of actionPerformed()

        // create and start timer, using speed from sliders
        gameTimer = new Timer(userTime, timer);
        gameTimer.start();

    }


    /** GENERATES MAZE WITHOUT ANIMATION - NO TIMER INCLUDED **/
    public void generateMazeNotAnimated(){

        LinkedList<Cell> queue = new LinkedList<>();  // initialize queue
        Random rand = new Random();  // initialize random number generator

        // initialize 'current' to be top-left cell and set it to be green
        current = cellMatrix[0][0];
        current.setBackground(Color.GREEN);
        current.repaint();

        // initialize variable to be true
        neighborsLeft = true;

        // while all cells have not been visited and there are no neighbors left (even after queue is empty)
        while (!allCellsVisited() || neighborsLeft) {

            queue.add(current);  // add current cell to queue
            visited[current.getCellRow()][current.getCellColumn()] = true;  // mark current cell as visited
            LinkedList<Cell> neighbors = getNeighbors(current);  // store neighbors that have not been visited

            if (neighbors.isEmpty()) {  // if there are no neighbors (all have been visited)

                boolean keepGoing = true;  // initialize boolean to act as a break

                // if the queues is not empty and there are not any unvisited neighbors
                while (queue.size() > 0 && keepGoing) {

                    Cell temp = queue.pop();  // pop off first index from queue and store as temp
                    neighbors = getNeighbors(temp);  // find the unvisited neighbors of temp

                    // if there are unvisited neighbors
                    if (neighbors.size() > 0) {
                        current = temp;  // temp is now current cell
                        keepGoing = false;  // acts as break
                    }

                    // if queue is empty
                    if (temp == null) {
                        keepGoing = false;  // acts as break
                    }
                }

            }

            // if neighbors is empty after queue is empty, quit loop
            if (neighbors.isEmpty()) {
                neighborsLeft = false;  // acts as break
            }

            // find a random neighbor from 'neighbors' linked list
            Cell neighbor = neighbors.get(rand.nextInt(neighbors.size()));

            // break the wall between current and neighbor
            removeEdge(current, neighbor);

            // neighbor is not the current cell
            current = neighbor;

        }

    }


    /** STOP TIMER **/
    public void stopGeneratorTimer(){
        gameTimer.stop();
    }


    /** START TIMER **/
    public void startGeneratorTimer(){
        gameTimer.start();
    }


    /** REMOVE EDGE OF CURRENT CELL AND NEIGHBOR CELL TO REMOVE ENTIRE SIDE **/
    private void removeEdge(Cell current, Cell neighbor){

        // store the current row/col number and the neighbor row/col number
        int currentRow = current.getCellRow();
        int currentColumn = current.getCellColumn();
        int neighborRow =  neighbor.getCellRow();
        int neighborColumn = neighbor.getCellColumn();

        // if the neighbor is the top cell
        if((neighborRow == currentRow - 1) && (neighborColumn == currentColumn)){

            current.setTopEdge(false);  // erase top border of current cell
            neighbor.setBottomEdge(false);  // erase bottom border of neighbor cell

        }

        // if the neighbor is the right cell
        if((neighborRow == currentRow) && (neighborColumn == currentColumn + 1)){

            current.setRightEdge(false);  // erase right border of current cell
            neighbor.setLeftEdge(false);  //erase left border of neighbor cell

        }

        // if neighbor is the bottom cell
        if((neighborRow == currentRow + 1) && (neighborColumn == currentColumn)){

            current.setBottomEdge(false);  // erase bottom border of current cell
            neighbor.setTopEdge(false);  // erase top border of neighbor cell

        }

        // if neighbor is the left cell
        if((neighborRow == currentRow) && (neighborColumn == currentColumn - 1)){

            current.setLeftEdge(false);  // erase left border of current cell
            neighbor.setRightEdge(false);  // erase right border of neighbor cell

        }

        current.drawBorders(current);  // redraw only existent borders of current cell
        neighbor.drawBorders(neighbor);  // redraw only existent border of neighbor cell

    }


    /** RETURNS LINKED LIST OF ALL (VALID & NOT-VISITED) NEIGHBORS OF GIVEN CELL **/
    private LinkedList<Cell> getNeighbors(Cell c){

        // initializes list to store all neighbors
        LinkedList<Cell> neighbors = new LinkedList<>();

        // store current cell's number of rows ad columns
        int i = c.getCellRow();
        int j = c.getCellColumn();

        // TOP - if top neighbor is valid (on grid) and has not been visited, add to list
        if (isValid(i-1, j)) {
            if (!visited[i-1][j]) {
                neighbors.add(cellMatrix[i-1][j]);
            }
        }

        // RIGHT - if right neighbor is valid (on grid) and has not been visited, add to list
        if (isValid(i, j+1)) {
            if (!visited[i][j+1]) {
                neighbors.add(cellMatrix[i][j+1]);
            }
        }

        // BOTTOM - if bottom neighbor is valid (on grid) and has not been visited, add to list
        if (isValid(i+1, j)) {
            if (!visited[i+1][j]) {
                neighbors.add(cellMatrix[i+1][j]);
            }
        }

        // LEFT - if left neighbor is valid (on grid) and has not been visited, add to list
        if (isValid(i, j-1)) {
            if (!visited[i][j-1]) {
                neighbors.add(cellMatrix[i][j-1]);
            }
        }

        // return linked list of all valid, non-visited neighbors of given cell
        return neighbors;

    }


    /** RETURNS TRUE IF ALL CELLS HAVE BEEN VISITED **/
    /** RETURNS FALSE IF NOT ALL CELLS HAVE BEEN VISITED **/
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


    /** RETURNS TRUE IF GIVEN POSITION IS VALID **/
    /** RETURNS FALSE IF GIVEN POSITION IS INVALID **/
    private boolean isValid(int row, int col){

        if((row >= 0) && (row < numRows) && (col >= 0)  && (col < numCols)){
            return true;
        }
        return false;

    }


} /** END OF CLASS **/
