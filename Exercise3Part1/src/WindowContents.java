//Author: Rupal Shah
//Date: 10/02/2017
//Purpose: creates window and buttons
//         adds actions to buttons:
//         if user clicks stop, rectangle will stop in whatever position it is in; if user clicks restart, rectangle will go back to its origin position

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WindowContents extends JFrame implements ActionListener{

    private JPanel buttonPanel;
    private JButton stop, restart;
    private RotatingRectangle rect = new RotatingRectangle();

    public WindowContents(){

        setTitle("Rotating Rectangle");

        Container c = getContentPane();

        buttonPanel = new JPanel();  //creates new panel - will hold the buttons

        stop = new JButton("Stop");  //creates button showing string
        restart = new JButton("Restart");  //creates button showing string

        stop.addActionListener(this);  //adds action listener to stop button
        restart.addActionListener(this);  //adds action listener to restart button

        //adds buttons to panel
        buttonPanel.add(stop);
        buttonPanel.add(restart);

        c.add(buttonPanel, BorderLayout.SOUTH);  //adds panel to container on window
        c.add(rect);  //adds rectangle "panel" to container on window

        setSize(400,400);  //sets dimensions of window
        setResizable(false);  //does not allow user to resize window
        setVisible(true);  //allows user to view window

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //when user clicks "x", window will close

    }


    //tells program what to do when buttons are clicked
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == stop){
            rect.stopTimer();  //if stop button is clicked, stop rotating rectangle
        }

        if(e.getSource() == restart){
            rect.resetRect();  //if restart button is clicked, revert rectangle back to original position
        }

    }


    public static void main(String a[]){
        new WindowContents();
    }

}