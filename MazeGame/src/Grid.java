// Created By: Rupal Shah

import javax.swing.*;

public class Grid {

    private int numRows, numCols;  // to store number of rows and columns

    private Cell cellMatrix[][];  // to store 2D array of cells

    private Generator generate;  // create 'generator' object
    private Solver solve;  // create 'solver' object


    /** CONSTRUCTOR **/
    public Grid(int rows, int columns){

        // set number of row and columns to given values
        numRows = rows;
        numCols = columns;

        // create empty 2D array of size rows x cols
        cellMatrix = new Cell[numRows][numCols];

        // fill array with cells - set dimensions of each cell to (row number, column number)
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                Cell c = new Cell();
                c.setDimensions(i, j);
                cellMatrix[i][j] = c;
            }
        }

    }


    /** GENERATES MAZE -- ANIMATED OR NOT **/
    public void generate(int generateTime, String animationChoice){

        // create 'generator' maze -- pass through appropriate parameters
        generate = new Generator(cellMatrix, numRows, numCols);

        // if user wants to animate generation, call generateMazeAnimated() - pass through given speed
        if(animationChoice == "ANIMATED") {
            generate.generateMazeAnimated(generateTime);
        }
        // if user does not want to animate generation, call generateMazeNotAnimated()
        else{
            generate.generateMazeNotAnimated();
        }

    }


    /** SOLVES MAZE -- ANIMATED OR NOT **/
    public void solve(int solveTime, JLabel percentLabel, String animationChoice){

        // create 'solver' object - pass through appropriate parameters
        solve = new Solver(cellMatrix, numRows, numCols);

        // if user wants to animate solution, call solveMazeAnimated() - pass through given speed and label (for percentage)
        if(animationChoice == "ANIMATED") {
            solve.solveMazeAnimated(solveTime, percentLabel);
        }
        // if user does not want to animate solution, call solveMazeNotAnimated() - pass through label (for percentage)
        else{
            solve.solveMazeNotAnimated(percentLabel);
        }

    }


    /** STOPS ANIMATION OF GENERATOR IN ITS TRACKS **/
    public void stopGenerator(){
        generate.stopGeneratorTimer();  // stops generator timer
    }


    /** STARTS ANIMATION OF GENERATOR FROM WHERE IT LEFT OFF **/
    public void startGenerator(){
        generate.startGeneratorTimer();  //starts generator timer
    }


    /** STOPS ANIMATION OF SOLVER IN ITS TRACKS**/
    public void stopSolver(){
        solve.stopSolverTimer();  // stops solver timer
    }

    /** STARTS ANIMATION OF SOLVER FROM WHERE IT LEFT OFF **/
    public void startSolver(){
        solve.startSolverTimer();  // starts solver timer
    }


    /** ADDS MATRIX OF CELLS TO GIVEN PANEL **/
    public void fillGrid(JPanel panel){

        // adds each cell from matrix to panel
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                panel.add(cellMatrix[i][j]);
            }
        }

    }



} /** END OF CLASS **/
