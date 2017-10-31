import javax.swing.*;

public class Grid {

    private int numRows, numCols;

    private Cell cellMatrix[][];

    private Generator generate;
    private Solver solve;

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

    public void solve(int solveTime, JLabel percentLabel, String animationChoice){

        solve = new Solver(cellMatrix, numRows, numCols);

        if(animationChoice == "ANIMATED") {
            solve.solveMazeAnimated(solveTime, percentLabel);
        }
        else{
            solve.solveMazeNotAnimated(percentLabel);
        }

    }

    public void generate(int generateTime, String animationChoice){

        generate = new Generator(cellMatrix, numRows, numCols);

        if(animationChoice == "ANIMATED") {
            generate.generateMazeAnimated(generateTime);
        }
        else{
            generate.generateMazeNotAnimated();
        }

    }

    public void stopGenerator(){
        generate.stopGeneratorTimer();
    }

    public void startGenerator(){
        generate.startGeneratorTimer();
    }


    public void stopSolver(){
        solve.stopSolverTimer();
    }

    public void startSolver(){
        solve.startSolverTimer();
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
