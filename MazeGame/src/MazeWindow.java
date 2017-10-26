import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MazeWindow extends JFrame implements ActionListener {

    Container c = getContentPane();

    private Grid grid;
    private JPanel maze;
    private JPanel options;

    private JButton generateButton, solveButton, stopButton;
    private JCheckBox generateCheck, solveCheck;
    private JSlider speedSlider, rowSlider, columnSlider;


    public MazeWindow(){

        int row = 25;
        int col = 25;

        options = new JPanel(new GridLayout(8,1));
        maze = new JPanel();

        generateButton = new JButton("Generate");
        solveButton = new JButton("Solve");
        stopButton = new JButton("Stop");

        generateButton.addActionListener(this);
        solveButton.addActionListener(this);
        stopButton.addActionListener(this);

        generateCheck = new JCheckBox("Show Generation");
        solveCheck = new JCheckBox("Show Solver");

        speedSlider = new JSlider(JSlider.HORIZONTAL);
        rowSlider = new JSlider(JSlider.HORIZONTAL, 10, 50, 30);
        columnSlider = new JSlider(JSlider.HORIZONTAL, 10, 50, 30);

        options.add(generateButton);
        options.add(generateCheck);
        options.add(solveButton);
        options.add(solveCheck);
        options.add(speedSlider);
        options.add(rowSlider);
        options.add(columnSlider);
        options.add(stopButton);

        grid = new Grid(row, col);
        grid.fillGrid(maze);
        maze.setLayout(new GridLayout(row, col));

        c.add(options, BorderLayout.EAST);
        c.add(maze);

        /**DO NOT ALLOW USER TO RESIZE WINDOW**/

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600,500);
        setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == generateButton){
            restart();

        }

    }

    public void restart(){

        maze.removeAll();

        grid = new Grid(5,5);
        grid.fillGrid(maze);
        maze.setLayout(new GridLayout(5, 5));
        maze.repaint();
        setVisible(true);

    }

    public static void main(String args[]){
        MazeWindow newMaze = new MazeWindow();
    }

}
