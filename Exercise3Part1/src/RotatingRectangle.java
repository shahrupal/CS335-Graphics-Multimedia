//Author: Rupal Shah
//Date: 10/02/2017
//Purpose: if click is inside rectangle, rotates rectangle around that point
//         does this by repainting the rectangle in increment of 1 degree per 50ms

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class RotatingRectangle extends JPanel implements ActionListener, MouseListener{

    //rectangle is drawn from top left corner, clockwise
    private int xCoord[] = {150, 250, 250, 150};  //original x-axis points of rectangle
    private int yCoord[] = {80, 80, 200, 200};  //original y-axis points of rectangle

    private int degrees = 0;  //will keep track of number of degrees rectangle is rotating from original

    private int getX, getY;  //will store coordinate of user's click

    Timer timer;  //creates timer - allows action to occur every [insert specified time] milliseconds

    private Polygon rectangle;  //creates polygon - will be coded as a rectangle shape


    public RotatingRectangle(){

        rectangle = new Polygon(xCoord, yCoord, 4);  //creates rectangle with points in arrays

        super.addMouseListener(this);  //adds mouse listener to window

        timer = new Timer(50, this);  //creates a timer that runs every 50ms

    }


    @Override  //overrides built-in paintComponent function
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.rotate(degrees, getX, getY);  //rotates rectangle by first parameter degrees, around last two parameters' coordinate

        //physically creates rectangle
        g2d.setColor(Color.PINK);
        g2d.fillPolygon(rectangle);
        g2d.drawPolygon(rectangle);

    }


    //user timer to repaint ("rotate") rectangle every 50ms
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == timer){  //every 50ms, timer is running
            repaint();  //calls paintComponent function
            degrees = degrees + 1;  //increments degrees by which rectangle is rotating
        }

        //this will prohibit user from making any more clicks in/out of rectangle (does not include buttons)
        //occurs after user clicks inside rectangle and timer starts
        removeMouseListener(this);

    }


    public void mouseClicked(MouseEvent e){

        //when user clicks inside rectangle, start timer
        if(rectangle.contains(e.getPoint())) {
            timer.start();
        }

        getX = e.getX();  //stores new x-coord of user click
        getY = e.getY();  //stores new y-coord of user click

    }

    public void mousePressed(MouseEvent e){ }  //required by mouse listener
    public void mouseEntered(MouseEvent e){ }  //required by mouse listener
    public void mouseExited(MouseEvent e){ }  //required by mouse listener
    public void mouseReleased(MouseEvent e){ }  //require by mouse listener


    //stops timer - this will stop the repaint function from being called
    public void stopTimer(){
        timer.stop();
    }


    public void resetRect(){

        rectangle = new Polygon(xCoord, yCoord, 4);  //resets rectangle to original position

        degrees = 0;  //resets degrees to 0 (0 degrees from original rectangle)

        repaint();  //repaints newly created (previous two lines) rectangle

        timer.stop();  //stops timer - will prevent repaint function from being called

        super.addMouseListener(this);  //allows user to click in rectangle again

    }

}
