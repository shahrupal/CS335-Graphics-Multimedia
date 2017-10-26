import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MazeWindow extends JFrame implements ActionListener {

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

        generateButton = new JButton("Generate");
        solveButton = new JButton("Solve");
        stopButton = new JButton("Stop");

        generateButton.addActionListener(this);
        solveButton.addActionListener(this);
        stopButton.addActionListener(this);

        generateCheck = new JCheckBox("Show Generation");
        solveCheck = new JCheckBox("Show Solver");

        speedSlider = new JSlider(JSlider.HORIZONTAL);
        rowSlider = new JSlider(JSlider.HORIZONTAL, 10, 50, 25);
        columnSlider = new JSlider(JSlider.HORIZONTAL, 10, 50, 25);

        options.add(generateButton);
        options.add(generateCheck);
        options.add(solveButton);
        options.add(solveCheck);
        options.add(speedSlider);
        options.add(rowSlider);
        options.add(columnSlider);
        options.add(stopButton);

        Grid grid = new Grid(row, col);
        grid.fillGrid();
        grid.setLayout(new GridLayout(row, col));

        Container c = getContentPane();
        c.add(options, BorderLayout.EAST);
        c.add(grid);

        /**DO NOT ALLOW USER TO RESIZE WINDOW**/

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600,600);
        setVisible(true);

    }


    public void actionPerformed(ActionEvent e){
        if(e.getSource() == generateButton){

        }
    }

    public static void main(String args[]){
        MazeWindow newMaze = new MazeWindow();
    }

}
