// Created By: Rupal Shah

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Stack;

public class Solver {

    private int numRows, numCols;  // to store number of rows and columns
    private Cell cellMatrix[][];  // to store 2D array of cells
    private boolean visited[][];  // to store 2D array of booleans - if visited: true

    private Timer gameTimer;  // create timer

    boolean neighborsLeft, end;  // to act as breaks
    Cell current;  // create cell to store current cell


    /** CONSTRUCTOR **/
    public Solver(Cell[][] cells, int rows, int cols){

        // set number of row and columns to given values
        numRows = rows;
        numCols = cols;

        // create empty 2D array to store if each cell has been visited or not
        visited = new boolean[numRows][numCols];

        // set matrix to given matrix of cells
        cellMatrix = new Cell[numRows][numCols];
        cellMatrix = cells;

    }


    /** SOLVES MAZE WITH ANIMATION - TIMER INCLUDED **/
    /** IMPLEMENTS DFS + LEFT-HAND RULE **/
    public void solveMazeAnimated(int userTime, JLabel percentLabel){

        // initialize stack
        Stack<Cell> stack = new Stack<>();

        // initialize 'current' to be top-left cell and set it to be green
        current = cellMatrix[0][0];
        current.setBackground(Color.GREEN);
        current.repaint();

        // initialize variables to be true
        neighborsLeft = true;
        end = true;

        // for animation - create timer and for every second, perform actionPerformed()
        ActionListener timer = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // if all cells have not been visited and there are no neighbors left (even after queue is empty) and solution has reached bottom-right corner
                if (!allCellsVisited() && neighborsLeft && end) {

                    // if the current cell is the top-left, set it to be green
                    if(current.getCellRow() == 0 && current.getCellColumn() == 0){
                        current.setBackground(Color.GREEN);
                    }
                    // otherwise set the current cell to be pink
                    else{
                        current.setBackground(Color.PINK);
                    }

                    // add current cell to stack
                    stack.add(current);

                    // mark the current cell to be visited
                    visited[current.getCellRow()][current.getCellColumn()] = true;

                    // return neighbors that can be accessed (no border between current and neighbor) and have not been visited
                    LinkedList<Cell> neighbors = getAccessibleNeighbors(current);

                    // if all neighbors have been visited
                    if (neighbors.isEmpty()) {

                        // initialize boolean to act as a break
                        boolean keepGoing = true;

                        // while the stack is not empty
                        while (stack.size() > 0 && keepGoing) {

                            // pop the last index off from the stack and store it as 'temp'
                            Cell temp = stack.pop();

                            // get accessible neighbors of the 'temp' cell
                            neighbors = getAccessibleNeighbors(temp);

                            // if there are unvisited neighbors
                            if (neighbors.size() > 0) {

                                current = temp;  // set 'temp' as the current cell
                                stack.add(current);  // add current cell to the stack
                                keepGoing = false;  //acts as break

                            }
                            // if there are not unvisited neighbors, set panels to be gray
                            else{
                                temp.setBackground(Color.GRAY);
                            }

                            // if the stack is empty
                            if (temp == null) {
                                keepGoing = false;  // acts a break
                            }
                        }

                    }

                    // if 'neighbors' is empty after stack is empty, quit loop
                    if (neighbors.isEmpty()) {
                        neighborsLeft = false;  // acts as a break
                    }

                    // use first value from neighbors (left-hand rule -> top, right, bottom, left)
                    Cell neighbor = neighbors.get(0);

                    // set neighbor cell as current cell and mark it as visited
                    current = neighbor;
                    visited[current.getCellRow()][current.getCellColumn()] = true;

                    // update percent visited label with correct value
                    updatePercentLabel(percentLabel);

                    // if solution is complete (reached bottom-right corner), set break and set panel color to be red
                    if ((current.getCellRow() == numRows - 1) && (current.getCellColumn() == numCols - 1)) {
                        end = false;
                        current.setBackground(Color.RED);
                    }

                }
                // after solution is complete, stop timer
                else{
                    gameTimer.stop();
                }

            }
        };

        // create and start timer, using speed from sliders
        gameTimer = new Timer(userTime, timer);
        gameTimer.start();

    }


    /** SOLVES MAZE WITHOUT ANIMATION - TIMER NOT INCLUDED **/
    /** IMPLEMENTS DFS + LEFT-HAND RULE **/
    public void solveMazeNotAnimated(JLabel percentLabel){

        // Initialize starting values.
        Stack<Cell> stack = new Stack<>();

        // Initialize `current` to be top-left cell and then adjust internal row- and column-variables.
        current = cellMatrix[0][0];
        current.setBackground(Color.GREEN);
        current.repaint();

        //while all the cells have not been visited, continue with loop
        neighborsLeft = true;
        end = true;

        // while all cells have not been visited and there are no neighbors left (even after queue is empty) and solution has reached bottom-right corner
        while (!allCellsVisited() && neighborsLeft && end) {

            // if the current cell is the top-left, set it to be green
            if(current.getCellRow() == 0 && current.getCellColumn() == 0){
                current.setBackground(Color.GREEN);
            }
            // otherwise set the current cell to be pink
            else{
                current.setBackground(Color.PINK);
            }

            // add current cell to stack
            stack.add(current);

            // mark the current cell to be visited
            visited[current.getCellRow()][current.getCellColumn()] = true;

            // return neighbors that can be accessed (no border between current and neighbor) and have not been visited
            LinkedList<Cell> neighbors = getAccessibleNeighbors(current);

            // if all neighbors have been visited
            if (neighbors.isEmpty()) {

                // initialize boolean to act as a break
                boolean keepGoing = true;

                // while the stack is not empty
                while (stack.size() > 0 && keepGoing) {

                    // pop the last index off from the stack and store it as 'temp'
                    Cell temp = stack.pop();

                    // get accessible neighbors of the 'temp' cell
                    neighbors = getAccessibleNeighbors(temp);

                    // if there are unvisited neighbors
                    if (neighbors.size() > 0) {

                        current = temp;  // set 'temp' as the current cell
                        stack.add(current);  // add current cell to the stack
                        keepGoing = false;  //acts as break

                    }
                    // if there are not unvisited neighbors, set panels to be gray
                    else{
                        temp.setBackground(Color.GRAY);
                    }

                    // if the stack is empty
                    if (temp == null) {
                        keepGoing = false;  // acts a break
                    }
                }

            }

            // if 'neighbors' is empty after stack is empty, quit loop
            if (neighbors.isEmpty()) {
                neighborsLeft = false;  // acts as a break
            }

            // use first value from neighbors (left-hand rule -> top, right, bottom, left)
            Cell neighbor = neighbors.get(0);

            // set neighbor cell as current cell and mark it as visited
            current = neighbor;
            visited[current.getCellRow()][current.getCellColumn()] = true;

            // update percent visited label with correct value
            updatePercentLabel(percentLabel);

            // if solution is complete (reached bottom-right corner), set break and set panel color to be red
            if ((current.getCellRow() == numRows - 1) && (current.getCellColumn() == numCols - 1)) {
                end = false;
                current.setBackground(Color.RED);
            }

        }
    }


    /** STOP TIMER **/
    public void stopSolverTimer(){
        gameTimer.stop();
    }


    /** START TIMER **/
    public void startSolverTimer(){
        gameTimer.start();
    }


    /** RETURNS TRUE IF ALL CELLS HAVE BEEN VISITED **/
    /** RETURNS FALSE IF NOT ALL CELLS HAVE BEEN VISITED **/
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

    /** RETURNS A LINKED LIST OF ACCESSIBLE NEIGHBORS **/
    /** ACCESSIBLE NEIGHBORS: DO NOT HAVE A BORDER TOUCHING CURRENT CELL, HAVE NOT BEEN VISITED**/
    public LinkedList<Cell> getAccessibleNeighbors(Cell c){

        
            LinkedList<Cell> neighbors = new LinkedList<>();

            int i = c.getCellRow();
            int j = c.getCellColumn();

            if(c.getTopEdge() == false){
                if(visited[i - 1][j] == false){
                    neighbors.add(cellMatrix[i - 1][j]);
                }
            }

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

    private double percentVisited(){

        int total = 0;
        double visitedCounter = 0;

        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                if(visited[i][j] == true){
                    visitedCounter++;
                }
                total++;
            }
        }

        visitedCounter = (visitedCounter / total) * 100;  //calculates percentage
        visitedCounter = Math.round((visitedCounter * 1000) / 1000);  //rounds to the nearest decimal
        return visitedCounter;

    }

    public void updatePercentLabel(JLabel label){
        label.setText("Percent Visited: " + percentVisited() + "%");
    }

}  /** END OF CLASS **/
