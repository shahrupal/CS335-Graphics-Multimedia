import javax.swing.*;

public class Grid {

    private int numRows, numCols;

    private Cell cellMatrix[][];

    private Solver solve;
    private Generator generate;

    public Grid(int rows, int columns){

        numRows = rows;
        numCols = columns;

        cellMatrix = new Cell[numRows][numCols];

        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                Cell c = new Cell();
                c.setDimensions(i, j);
                cellMatrix[i][j] = c;
            }
        }

    }

    public void solve(){

        Solver solve = new Solver(cellMatrix, numRows, numCols);
        solve.solveMaze();

    }

    public void generate(){

        Generator generate = new Generator(cellMatrix, numRows, numCols);
        generate.generateMaze();

    }

    //adds matrix of cells to given panel
    public void fillGrid(JPanel panel){
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                panel.add(cellMatrix[i][j]);
            }
        }
    }



} /** END OF CLASS **/
