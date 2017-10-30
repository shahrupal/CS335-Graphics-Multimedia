import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MazeWindow extends JFrame implements ActionListener {

    Container c = getContentPane();

    private Grid grid;
    private JPanel maze;
    private JPanel options;
    private JPanel statistics;

    private JButton generateButton, solveButton, stopButton;
    private JCheckBox generateCheck, solveCheck;
    private JLabel speedLabel, rowsLabel, colsLabel;
    private JLabel visitedLabel;
    private JSlider speedSlider, rowSlider, columnSlider;

    private int rowInput, colInput;


    public MazeWindow(){

        super("Maze Generator & Solver");

        int row = 10;
        int col = 10;

        maze = new JPanel();
        options = new JPanel(new GridLayout(11,1));
        statistics = new JPanel();

        generateButton = new JButton("Generate");
        solveButton = new JButton("Solve");
        stopButton = new JButton("Stop");

        generateButton.addActionListener(this);
        solveButton.addActionListener(this);
        stopButton.addActionListener(this);

        generateCheck = new JCheckBox("Show Generation");
        solveCheck = new JCheckBox("Show Solver");

        generateCheck.addActionListener(this);
        solveCheck.addActionListener(this);

        speedLabel = new JLabel("Speed: ");
        rowsLabel = new JLabel("Rows: 30");
        colsLabel = new JLabel("Columns: 30");
        visitedLabel = new JLabel("Percent Visited: 0%");

        speedSlider = new JSlider(JSlider.HORIZONTAL,0,1000,200);
        rowSlider = new JSlider(JSlider.HORIZONTAL, 10, 50, 10);
        columnSlider = new JSlider(JSlider.HORIZONTAL, 10, 50, 10);

        //add tick marks to sliders
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

        setOptionColors();

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
        options.add(stopButton);

        statistics.add(visitedLabel);

        //add grid to maze panel
        grid = new Grid(row, col);
        grid.fillGrid(maze);
        maze.setLayout(new GridLayout(row, col));
        maze.setBackground(Color.BLACK);

        c.add(statistics, BorderLayout.SOUTH);
        c.add(options, BorderLayout.EAST);
        c.add(maze);


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600,500);
        setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == generateButton){

            rowInput = rowSlider.getValue();
            colInput = columnSlider.getValue();
            restart();

            //if check box is selected, use value from speed slider to determine amount of time
            if(generateCheck.isSelected()){
                grid.generate(speedSlider.getValue());
            }
            //if check box is not selected, automatically create maze - do not show animation
            else {
                grid.generate(0);
            }

        }

        if(e.getSource() == solveButton){

            if(solveCheck.isSelected()){
                grid.solve(speedSlider.getValue(), visitedLabel);
            }
            else{
                grid.solve(0, visitedLabel);
            }
        }


    }

    public void restart(){

        //remove previous panel
        //reset panel to new grid with values from slider
        maze.removeAll();
        grid = new Grid(rowInput, colInput);
        grid.fillGrid(maze);
        maze.setLayout(new GridLayout(rowInput, colInput));

        //recreate and make viewable
        maze.repaint();
        setVisible(true);

    }

    public void setOptionColors(){

        options.setBackground(new Color(186, 230, 252));

        speedSlider.setBackground(new Color(186, 230, 252));
        rowSlider.setBackground(new Color(186, 230, 252));
        columnSlider.setBackground(new Color(186, 230, 252));

        generateCheck.setBackground(new Color(186, 230, 252));
        solveCheck.setBackground(new Color(186, 230, 252));

        speedLabel.setBackground(new Color(186, 230, 252));
        rowsLabel.setBackground(new Color(186, 230, 252));
        colsLabel.setBackground(new Color(186, 230, 252));

        generateButton.setBackground(new Color(255, 232, 160));
        solveButton.setBackground(new Color(255, 232, 160));
        stopButton.setBackground(new Color(255, 232, 160));

    }

    public static void main(String args[]){
        MazeWindow newMaze = new MazeWindow();
    }

}
