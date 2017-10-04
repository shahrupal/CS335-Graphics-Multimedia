//Author: Rupal Shah
//Date: 10/03/2017
//Purpose: creates the window the rectangle is included on
//         adds reset button - when clicked, rectangle should go back to origin shape/position

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WindowContents extends JFrame implements ActionListener{

    private JPanel buttonPanel;
    private JButton reset;

    DraggableRectangle rect = new DraggableRectangle();

    public WindowContents(){

        setTitle("Draggable Rectangle");

        Container c = getContentPane();  //creates container to hold panel and rectangle "panel"

        buttonPanel = new JPanel();

        reset = new JButton("Reset");  //sets button to show string
        reset.addActionListener(this);  //adds action listener to button

        buttonPanel.add(reset); //adds button to panel

        //adds panel and rectangle "panel" to container
        c.add(buttonPanel, BorderLayout.SOUTH);
        c.add(rect);

        setSize(400,400);  //sets dimension of window
        setResizable(false);  //does not allow user to resize window
        setVisible(true);  //allows user to see window

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //exits window when "x" is clicked
    }

    //when user clicks reset button, call function to reset rectangle to original size and position
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == reset){
            rect.resetRect();
        }
    }

    public static void main(String a[]){
        new WindowContents();
    }

}