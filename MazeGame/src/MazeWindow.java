// Created By: Rupal Shah
// Dates: 10/26/2017 - 10/31/2017

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MazeWindow extends JFrame implements ActionListener {

    // store content pane in container
    Container c = getContentPane();

    // create grid object
    private Grid grid;

    // create jpanels to layout content
    private JPanel maze;
    private JPanel options;
    private JPanel statistics;

    // create content (buttons, boxes, labels, sliders)
    private JButton generateButton, solveButton, stopStartButton;
    private JCheckBox generateCheck, solveCheck;
    private JLabel speedLabel, rowsLabel, colsLabel;
    private JLabel visitedLabel;
    private JSlider speedSlider, rowSlider, columnSlider;

    // use to store user's inputs
    private int rowInput, colInput;

    // use to be able to tell if maze is generating/solving
    public boolean isGenerating = false, isSolving = false;

    /** CONSTRUCTOR **/
    public MazeWindow(){

        // set title of window
        super("Maze Generator & Solver");

        // set default row/column values
        int row = 10;
        int col = 10;

        // create the panel to hold the actual maze
        maze = new JPanel();

        // create panel to hold all the options
        options = new JPanel(new GridLayout(11,1));

        // create panel to hold percentage visited
        statistics = new JPanel();
        statistics.setBackground(new Color(185, 252, 151));
        statistics.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // create buttons with texts
        generateButton = new JButton("Generate");
        solveButton = new JButton("Solve");
        stopStartButton = new JButton("Stop");

        // add action listeners to each button
        generateButton.addActionListener(this);
        solveButton.addActionListener(this);
        stopStartButton.addActionListener(this);

        // create check boxes with text
        generateCheck = new JCheckBox("Show Generation");
        solveCheck = new JCheckBox("Show Solver");

        // add action listeners to each check box
        generateCheck.addActionListener(this);
        solveCheck.addActionListener(this);

        // create labels with text
        speedLabel = new JLabel("Speed: ");
        rowsLabel = new JLabel("Rows: ");
        colsLabel = new JLabel("Columns: ");
        visitedLabel = new JLabel("Percent Visited: 0%");

        // create sliders
        speedSlider = new JSlider(JSlider.HORIZONTAL,0,1000,200);
        rowSlider = new JSlider(JSlider.HORIZONTAL, 10, 50, 10);
        columnSlider = new JSlider(JSlider.HORIZONTAL, 10, 50, 10);

        // add tick marks to sliders
        speedSlider.setMajorTickSpacing(200);
        speedSlider.setMinorTickSpacing(100);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        rowSlider.setMajorTickSpacing(10);
        rowSlider.setMinorTickSpacing(5);
        rowSlider.setPaintTicks(true);
        rowSlider.setPaintLabels(true);
        columnSlider.setMajorTickSpacing(10);
        columnSlider.setMinorTickSpacing(5);
        columnSlider.setPaintTicks(true);
        columnSlider.setPaintLabels(true);

        // add colors to 'options' panel content
        setOptionColors();

        // add content to 'options' panel
        options.add(generateButton);
        options.add(generateCheck);
        options.add(solveButton);
        options.add(solveCheck);
        options.add(speedLabel);
        options.add(speedSlider);
        options.add(rowsLabel);
        options.add(rowSlider);
        options.add(colsLabel);
        options.add(columnSlider);
        options.add(stopStartButton);

        // add content to 'statistics' label
        statistics.add(visitedLabel);

        // add grid to 'maze' panel
        grid = new Grid(row, col);
        grid.fillGrid(maze);
        maze.setLayout(new GridLayout(row, col));
        maze.setBackground(Color.BLACK);

        // add panels to frame
        c.add(statistics, BorderLayout.SOUTH);
        c.add(options, BorderLayout.EAST);
        c.add(maze);

        // set frame to exit on close, dimensions, and visibility
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600,500);
        setVisible(true);

    }


    /** WHEN USER SELECTS ANYTHING ON FRAME **/
    @Override
    public void actionPerformed(ActionEvent e){

        // if the user clicks the 'generate' button
        if(e.getSource() == generateButton){

            // store the input of the rows/columns from the sliders
            rowInput = rowSlider.getValue();
            colInput = columnSlider.getValue();

            // generate a new maze with user input dimensions
            restart();

            // reset labels
            stopStartButton.setText("Stop");
            visitedLabel.setText("Percent Visited: 0%");

            // if check box is selected, use value from speed slider to determine speed
            // animate generation using specified speed
            if(generateCheck.isSelected()){
                isGenerating = true;
                isSolving = false;
                grid.generate(speedSlider.getValue(), "ANIMATED");
            }
            // if check box is not selected, automatically create maze - do not show animation
            else {
                grid.generate(0, "NOT ANIMATED");
            }

        }

        // if the user selects the 'solve' button
        if(e.getSource() == solveButton){

            // if check box is selected, use value from speed slider to determine speed
            // animate solution using specified speed
            if(solveCheck.isSelected()){
                isSolving = true;
                isGenerating = false;
                grid.solve(speedSlider.getValue(), visitedLabel, "ANIMATED");
            }
            // if check box is not selected, automatically solve maze - do not show animation
            else{
                grid.solve(0, visitedLabel, "NOT ANIMATED");
            }
        }

        // if the user selects the stop/start button
        if(e.getSource() == stopStartButton){

            // if the button displays 'stop'
            if(stopStartButton.getText() == "Stop") {

                if(isGenerating) {  // and if the maze is in generating-phase, stop generation in tracks
                    grid.stopGenerator();
                }
                else if(isSolving){  // or if the maze is in the solving-phase, stop solution in tracks
                    grid.stopSolver();
                }

                // set text of button to now display 'start'
                stopStartButton.setText("Start");

            }

            // if the button displays 'start'
             else if(stopStartButton.getText() == "Start"){

                if(isGenerating) {  // and if the maze is in generating-phase, start generation from where it started off
                    grid.startGenerator();
                }
                 else if(isSolving){  // or if the maze is in solution-phase, start solution from where it started off
                    grid.startSolver();
                }

                // set text of button to now display 'stop'
                stopStartButton.setText("Stop");


            }

        }

    }


    /** REGENERATE COMPLETELY NEW MAZE **/
    public void restart(){

        maze.removeAll();  // remove previous panel
        grid = new Grid(rowInput, colInput);  // reset panel to new grid with values from slider
        grid.fillGrid(maze);  // fill grid with new amount of cells
        maze.setLayout(new GridLayout(rowInput, colInput));

        // recreate and make visible
        maze.repaint();
        setVisible(true);

    }


    /** STYLE COLORS OF CONTENT **/
    public void setOptionColors(){

        // 'options' panel, sliders, check boxes, and labels - all blue
        options.setBackground(new Color(186, 230, 252));

        speedSlider.setBackground(new Color(186, 230, 252));
        rowSlider.setBackground(new Color(186, 230, 252));
        columnSlider.setBackground(new Color(186, 230, 252));

        generateCheck.setBackground(new Color(186, 230, 252));
        solveCheck.setBackground(new Color(186, 230, 252));

        speedLabel.setBackground(new Color(186, 230, 252));
        rowsLabel.setBackground(new Color(186, 230, 252));
        colsLabel.setBackground(new Color(186, 230, 252));

        // buttons are yellow
        generateButton.setBackground(new Color(255, 232, 160));
        solveButton.setBackground(new Color(255, 232, 160));
        stopStartButton.setBackground(new Color(255, 232, 160));

    }


    /** MAIN FUNCTION **/
    public static void main(String args[]){
        MazeWindow newMaze = new MazeWindow();
    }

}
