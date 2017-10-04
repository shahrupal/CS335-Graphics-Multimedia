//Author: Rupal Shah
//Date: 9/27/2017
//Description: this program creates the layout of a calculator
//             buttons do not perform any actions

import java.awt.*;
import javax.swing.*;

public class CalculatorLayout extends JFrame {

    private String calculatorOptions[] = {"7","8","9","/","4","5","6","*","1","2","3","-","0",".","=","+"};

    private JPanel inputArea;
    private JPanel buttonsArea;

    public CalculatorLayout(){

        //creates frame with window title
        JFrame frame = new JFrame("Calculator");

        //panels will be added onto container
        Container c = getContentPane();

        //creates new panel for space to output user input
        inputArea = new JPanel();
        inputArea.setPreferredSize(new Dimension(10,20));  //sets size of panel
        c.add(inputArea, BorderLayout.NORTH);  //adds panel to top of container

        //creates new panel in grid layout
        buttonsArea = new JPanel();
        buttonsArea.setLayout(new GridLayout(4,4,0,0));

        //creates button for each number/operator
        //adds button to panel grid
        for(int i = 0; i < calculatorOptions.length; i++){
            JButton operation = new JButton(calculatorOptions[i]);
            buttonsArea.add(operation);
        }

        //button panel added to container
        c.add(buttonsArea);

        //container (with input and button panel) added to frame
        frame.add(c);

        //sets dimension on frame
        frame.setSize(200,250);

        //closes window when user presses "x" button
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //allows user to see frame
        frame.setVisible(true);
    }


    public static void main(String args[]){

        CalculatorLayout calculator = new CalculatorLayout();

    }
}