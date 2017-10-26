import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MazeWindow extends JFrame implements ActionListener {

    private Box box = Box.createHorizontalBox();

    private JPanel maze;
    private JPanel options;

    private JButton generateButton, solveButton, stopButton;
    private JCheckBox generateCheck, solveCheck;
    private JSlider speedSlider, rowSlider, columnSlider;

    public MazeWindow(){

        maze = new JPanel();
        options = new JPanel(new GridLayout(8,1));

        generateButton = new JButton("Generate");
        solveButton = new JButton("Solve");
        stopButton = new JButton("Stop");

        generateCheck = new JCheckBox("Show Generation");
        solveCheck = new JCheckBox("Show Solver");

        speedSlider = new JSlider(JSlider.HORIZONTAL);
        rowSlider = new JSlider(JSlider.HORIZONTAL, 10, 50, 50);
        columnSlider = new JSlider(JSlider.HORIZONTAL, 10, 50, 50);

        options.add(generateButton);
        options.add(generateCheck);
        options.add(solveButton);
        options.add(solveCheck);
        options.add(speedSlider);
        options.add(rowSlider);
        options.add(columnSlider);
        options.add(stopButton);

        Container c = getContentPane();

        c.add(options, BorderLayout.EAST);
        c.add(maze, BorderLayout.WEST);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600,600);
        setVisible(true);

    }


    public void actionPerformed(ActionEvent e){

    }

    public static void main(String args[]){
        MazeWindow newMaze = new MazeWindow();
    }

}
