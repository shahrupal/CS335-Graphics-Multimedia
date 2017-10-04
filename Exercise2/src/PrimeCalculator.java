//Author: Rupal Shah
//Date: 9/27/2017
//Description: this program creates the layout of a calculator
//             allows user to input numbers and outputs them onto screen
//             when user presses "prime" button, program will state if number is prime or not (using sieve method)
//             if number is not between 0 and 1000, error message will be outputted

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PrimeCalculator extends JFrame implements ActionListener{

    private JFrame frame;
    private Container c = getContentPane();

    private JPanel inputArea;
    private JPanel buttonsArea;

    private JButton op1, op2, op3, op4, op5, op6, op7, op8, op9, op0;
    private JButton primeButton, resetButton;

    private JLabel inputLabel;

    private String userInput = "";
    private String errorMessage = "";
    private String primeMessage = "";


    public PrimeCalculator(){

        //creates frame with window title
        frame = new JFrame("Calculator");

        //sets size of panel that will output user's input
        inputArea = new JPanel();
        inputArea.setPreferredSize(new Dimension(10,30));

        //adds label to panel
        inputLabel = new JLabel();
        inputArea.add(inputLabel);

        //creates panel in grid layout
        buttonsArea = new JPanel();
        buttonsArea.setLayout(new GridLayout(4,3,0,0));


        //calls makeButtons function to actually create operator buttons
        makeButtons();

        //adds panels to container
        c.add(inputArea, BorderLayout.NORTH);  //sets position of input panel to top of screen
        c.add(buttonsArea);

        //adds container to frame
        frame.add(c);

        //sets dimension of frame
        frame.setSize(250,300);

        //closes window when user presses "x" button
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //allows user to view window
        frame.setVisible(true);
    }

    //what occurs when user creates an action
    public void actionPerformed(ActionEvent e){

        /**when user presses a button from 0-9**/
        /**program will concatenate numbers and output each number as button is pressed**/
        if(e.getSource() == op1){
            userInput = userInput + op1.getText();
            inputLabel.setText(userInput);
        }
        else if(e.getSource() == op2){
            userInput = userInput + op2.getText();
            inputLabel.setText(userInput);
        }
        else if(e.getSource() == op3){
            userInput = userInput + op3.getText();
            inputLabel.setText(userInput);
        }
        else if(e.getSource() == op4){
            userInput = userInput + op4.getText();
            inputLabel.setText(userInput);
        }
        else if(e.getSource() == op5){
            userInput = userInput + op5.getText();
            inputLabel.setText(userInput);
        }
        else if(e.getSource() == op6){
            userInput = userInput + op6.getText();
            inputLabel.setText(userInput);
        }
        else if(e.getSource() == op7){
            userInput = userInput + op7.getText();
            inputLabel.setText(userInput);
        }
        else if(e.getSource() == op8){
            userInput = userInput + op8.getText();
            inputLabel.setText(userInput);
        }
        else if(e.getSource() == op9){
            userInput = userInput + op9.getText();
            inputLabel.setText(userInput);
        }
        else if(e.getSource() == op0){
            userInput = userInput + op0.getText();
            inputLabel.setText(userInput);
        }

        /**when user presses "=" button**/
        else if(e.getSource() == primeButton){

            //if number is not between 0-1000, output error message
            //reset user input to nothing
            if(Integer.parseInt(userInput) < 1){
                errorMessage = "error: number too small";
                userInput = "";
                inputLabel.setText(errorMessage);
            }

            else if(Integer.parseInt(userInput) > 999){
                errorMessage = "error: number too large";
                userInput = "";
                inputLabel.setText(errorMessage);
            }

            //if number is in correct range
            else{

                //call sieve function
                //if sieve array index of inputted number is prime, set string accordingly
                if(sieve()[(Integer.parseInt(userInput))] == true){
                    primeMessage = "number is prime";
                }
                //if sieve array index of inputted number is not prime, set string accordingly
                else{
                    primeMessage = "number is not prime";
                }

                userInput = "";  //resets user input
                inputLabel.setText(primeMessage);  //adds string to panel
            }

            userInput = "";  //resets user input
        }

        //resets calculator
        //"enter" basically does this, but it is more obvious to user this way
        else if(e.getSource() == resetButton){
            userInput = "";
            inputLabel.setText(userInput);
        }
    }

    //outputs a boolean array, stating whether each index/number between 0 and 1000 is prime (true) or not (false)
    //uses sieve method
    public static boolean[] sieve(){

        //creates an array with 1000 indices
        boolean PrimeNums[] = new boolean [1000];

        //sets all array indices to "true"
        for(int i = 0; i < PrimeNums.length; i++){
            PrimeNums[i] = true;
        }

        //goes through each index of PrimeNums array
        for(int j = 2; j < PrimeNums.length; j++) {

            //call IsPrime function
            // set it equal to true/false (respectively: prime/not prime)
            PrimeNums[j] = isPrime(j);

            //if index is "true" (prime)
            if (PrimeNums[j] == true) {

                //find all its multiples and set them to false (not prime)
                for (int k = (j + 1); k < PrimeNums.length; k++) {
                    if ((k % j) == 0) {
                        PrimeNums[k] = false;
                    }
                }

            }

        }

        return PrimeNums;  //returns boolean array

    }

    //aids sieve function
    //outputs boolean if number is prime (true) or not (false)
    public static boolean isPrime(int currentNum){

        //goes through all numbers below CurrentNum and divides the two
        for(int i = 2; i < currentNum; i++){

            //if there is no remainder (divides evenly) the number is not prime
            if((currentNum % i) == 0){
                return false;
            }
        }

        //otherwise, it is prime
        return true;

    }

    //creates buttons for each operation
    //adds buttons to panel
    //adds action listeners to each button, allowing program to know which button is being pressed
    private void makeButtons(){

         op7 = new JButton("7");
         op7.addActionListener(this);
         buttonsArea.add(op7);

        op8 = new JButton("8");
        op8.addActionListener(this);
        buttonsArea.add(op8);

        op9 = new JButton("9");
        op9.addActionListener(this);
        buttonsArea.add(op9);

        op4 = new JButton("4");
        op4.addActionListener(this);
        buttonsArea.add(op4);

        op5 = new JButton("5");
        op5.addActionListener(this);
        buttonsArea.add(op5);

        op6 = new JButton("6");
        op6.addActionListener(this);
        buttonsArea.add(op6);

        op1 = new JButton("1");
        op1.addActionListener(this);
        buttonsArea.add(op1);

        op2 = new JButton("2");
        op2.addActionListener(this);
        buttonsArea.add(op2);

        op3 = new JButton("3");
        op3.addActionListener(this);
        buttonsArea.add(op3);

        op0 = new JButton("0");
        op0.addActionListener(this);
        buttonsArea.add(op0);

        primeButton = new JButton("enter");
        primeButton.addActionListener(this);
        buttonsArea.add(primeButton);

        resetButton = new JButton("reset");
        resetButton.addActionListener(this);
        buttonsArea.add(resetButton);

    }

    public static void main(String args[]){

        PrimeCalculator calculator = new PrimeCalculator();

    }
}