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
    public void solveMaze(){

        // Initialize starting values.
        Stack<Cell> queue = new Stack<>();

        // Initialize `current` to be top-left cell and then adjust internal row- and column-variables.
        current = cellMatrix[0][0];
        current.setBackground(Color.ORANGE);
        current.repaint();

        //while all the cells have not been visited, continue with loop
        neighborsLeft = true;
        end = true;

        ActionListener timer = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!allCellsVisited() && neighborsLeft && end) {

                    //add current cell to queue
                    queue.add(current);

                    // Mark the top-left spot as visited.
                    visited[current.getCellRow()][current.getCellColumn()] = true;

                    LinkedList<Cell> neighbors = getAccessibleNeighbors(current); // Return neighbors that have not been visited.

                    if (neighbors.isEmpty()) {  //if no neighbors (all have been visited)
                        boolean keepGoing = true;

                        while (queue.size() > 0 && keepGoing) {

                            Cell temp = queue.pop();
//                  temp.setBackground(Color.DARK_GRAY);
                            neighbors = getAccessibleNeighbors(temp);

                            //if there are unvisited neighbors
                            if (neighbors.size() > 0) {
                                current = temp;
  //                              current.setBackground(Color.DARK_GRAY);
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
//                        continue;
                    }

                    //use first value from neighbors
                    Cell neighbor = neighbors.get(0);

                    current = neighbor;
                    current.setBackground(Color.ORANGE);

                    System.out.println("Row: " + current.getCellRow() + ", Col: " + current.getCellColumn());
                    System.out.println("Row Check: " + (numRows - 1) + ", Col Check: " + (numCols - 1));
                    if ((current.getCellRow() == numRows - 1) && (current.getCellColumn() == numCols - 1)) {
                        end = false;
                        current.setBackground(Color.CYAN);
                        System.out.println("WHAT THE HECK");
//                        continue;
                    }


                }
                else{
                    gameTimer.stop();
                }

            }
        };

        gameTimer = new Timer(200, timer);
        gameTimer.start();
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
