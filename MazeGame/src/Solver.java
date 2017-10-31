import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Stack;

public class Solver {

    private boolean visited[][];
    private Cell cellMatrix[][];
    private int numRows, numCols;
    boolean neighborsLeft, end;
    Cell current;
    private Timer gameTimer;


    public Solver(Cell[][] cells, int rows, int cols){

        numRows = rows;
        numCols = cols;

        visited = new boolean[numRows][numCols];

        cellMatrix = new Cell[numRows][numCols];
        cellMatrix = cells;

    }

    /** USES LEFT-HAND RULE **/
    public void solveMazeAnimated(int userTime, JLabel percentLabel){

    //    percentVisited = 0;

        // Initialize starting values.
        Stack<Cell> stack = new Stack<>();

        // Initialize `current` to be top-left cell and then adjust internal row- and column-variables.
        current = cellMatrix[0][0];
        current.setBackground(Color.GREEN);
        current.repaint();

        //while all the cells have not been visited, continue with loop
        neighborsLeft = true;
        end = true;

        ActionListener timer = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!allCellsVisited() && neighborsLeft && end) {

                    if(current.getCellRow() == 0 && current.getCellColumn() == 0){
                        current.setBackground(Color.GREEN);
                    }
                    else{
                        current.setBackground(Color.PINK);
                    }

                    //add current cell to stack
                    stack.add(current);

                    // Mark the top-left spot as visited.
                    visited[current.getCellRow()][current.getCellColumn()] = true;

                    LinkedList<Cell> neighbors = getAccessibleNeighbors(current); // Return neighbors that have not been visited.

                    if (neighbors.isEmpty()) {  //if no neighbors (all have been visited)
                        boolean keepGoing = true;


                        while (stack.size() > 0 && keepGoing) {

                            Cell temp = stack.pop();

                            neighbors = getAccessibleNeighbors(temp);

                            //if there are unvisited neighbors
                            if (neighbors.size() > 0) {

                                current = temp;
                                stack.add(current);
                                keepGoing = false;  //acts as break

                            }
                            else{
                                temp.setBackground(Color.GRAY);
                            }

                            //if stack is empty
                            if (temp == null) {
                                keepGoing = false;
                            }
                        }

                    }


                    //if neighbors is empty after stack is empty, quit loop
                    if (neighbors.isEmpty()) {
                        neighborsLeft = false;
                    }

                    //use first value from neighbors
                    Cell neighbor = neighbors.get(0);

                    current = neighbor;
                    visited[current.getCellRow()][current.getCellColumn()] = true;

                    System.out.println("Percent Visited: " + percentVisited());
                    updatePercentLabel(percentLabel);


                    if ((current.getCellRow() == numRows - 1) && (current.getCellColumn() == numCols - 1)) {
                        end = false;
                        current.setBackground(Color.RED);
                    }


                }
                else{
                    gameTimer.stop();
                }

            }
        };

        gameTimer = new Timer(userTime, timer);
        gameTimer.start();
        System.out.println("NOICE 2");

    }

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


        while (!allCellsVisited() && neighborsLeft && end) {

            if(current.getCellRow() == 0 && current.getCellColumn() == 0){
                    current.setBackground(Color.GREEN);
                }
                else{
                    current.setBackground(Color.PINK);
                }

                //add current cell to stack
                stack.add(current);

                // Mark the top-left spot as visited.
                visited[current.getCellRow()][current.getCellColumn()] = true;

                LinkedList<Cell> neighbors = getAccessibleNeighbors(current); // Return neighbors that have not been visited.

                if (neighbors.isEmpty()) {  //if no neighbors (all have been visited)
                    boolean keepGoing = true;


                    while (stack.size() > 0 && keepGoing) {

                        Cell temp = stack.pop();

                        neighbors = getAccessibleNeighbors(temp);

                        //if there are unvisited neighbors
                        if (neighbors.size() > 0) {

                            current = temp;
                            stack.add(current);
                            keepGoing = false;  //acts as break

                        }
                        else{
                            temp.setBackground(Color.GRAY);
                        }

                        //if stack is empty
                        if (temp == null) {
                            keepGoing = false;
                        }
                    }

                }


                //if neighbors is empty after stack is empty, quit loop
                if (neighbors.isEmpty()) {
                    neighborsLeft = false;
                }

                //use first value from neighbors
                Cell neighbor = neighbors.get(0);

                current = neighbor;
                visited[current.getCellRow()][current.getCellColumn()] = true;

                System.out.println("Percent Visited: " + percentVisited());
                updatePercentLabel(percentLabel);


                if ((current.getCellRow() == numRows - 1) && (current.getCellColumn() == numCols - 1)) {
                    end = false;
                    current.setBackground(Color.RED);
                }


            }

    }

    public void stopSolverTimer(){
        gameTimer.stop();
    }

    public void startSolverTimer(){
        gameTimer.start();
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

    //returns a list of accessible neighbors that have not already been visited
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

        visitedCounter = (visitedCounter / total) * 100;
        visitedCounter = Math.round((visitedCounter * 1000) / 1000);
        return visitedCounter;

    }

    public void updatePercentLabel(JLabel label){
        label.setText("Percent Visited: " + percentVisited() + "%");
    }

}
