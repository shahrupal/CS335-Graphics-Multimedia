//Author: Rupal Shah
//Date: 10/03/2017
//Purpose: creates main & four small squares located at each corner of main rectangle
//         if user clicks inside one of the squares, and drags the mouse, it will resize the polygon
//         this allows dragging sensation of the corners
//         when user releases click, rectangle will stay where user dragged the corners to instead of reverting back to original position

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DraggableRectangle extends JPanel implements MouseListener, MouseMotionListener{

    //points of original rectangle
    private int point1x = 150, point1y = 80;
    private int point2x = 250, point2y = 80;
    private int point3x = 250, point3y = 200;
    private int point4x = 150, point4y = 200;

    //adds above points to arrays
    //rectangle drawn using array, which is in following order:
    //1st set of points = top left, 2nd set = top right, 3rd = bottom left, 4th = bottom right
    private int xCoord[] = {point1x, point2x, point3x, point4x};
    private int yCoord[] = {point1y, point2y, point3y, point4y};

    private Polygon rectangle;
    private Polygon topLeftRect, topRightRect, bottomLeftRect, bottomRightRect;

    //keeps track of if user is in the process of dragging a cornder
    private boolean topRightDrag = false, topLeftDrag = false, bottomRightDrag = false, bottomLeftDrag = false;


    public DraggableRectangle(){

        setBackground(Color.DARK_GRAY);  //sets background color of rectangle "panel"

        rectangle = new Polygon(xCoord, yCoord, 4);  //creates a polygon with points in array

        super.addMouseListener(this);  //add mouse listener to window
        super.addMouseMotionListener(this);  //add mouse motion listener to window

    }


    @Override  //overrides built-in paintComponent function
    public void paintComponent(Graphics g){

        //physically creates rectangle
        super.paintComponent(g);
        Graphics2D largeRectangle = (Graphics2D) g.create();
        largeRectangle.setColor(Color.PINK);
        largeRectangle.fillPolygon(rectangle);
        largeRectangle.drawPolygon(rectangle);

        //physically creates four rectangles on corners of main rectangle
        Graphics2D smallRectangle = (Graphics2D) g.create();
        miniRectangles(smallRectangle);

    }


    // draws small rectangles on each corner to indicate from where user can click to drag rectangle
    public void miniRectangles(Graphics2D g){

        g.setColor(Color.GREEN);  //sets outline of each small rectangle to green

        //draws top left rectangle box around top left point (even when dragged/manipulated)
        int topLeftX[] = {(point1x - 5), (point1x + 5), (point1x + 5), (point1x - 5)};
        int topLeftY[] = {(point1y - 5), (point1y - 5), (point1y + 5), (point1y + 5)};
        topLeftRect  = new Polygon(topLeftX, topLeftY, 4);
        g.drawPolygon(topLeftRect);

        //draws top right rectangle box around top right point (even when dragged/manipulated)
        int topRightX[] = {(point2x - 5), (point2x + 5), (point2x + 5), (point2x - 5)};
        int topRightY[] = {(point2y - 5), (point2y - 5), (point2y + 5), (point2y + 5)};
        topRightRect  = new Polygon(topRightX, topRightY, 4);
        g.drawPolygon(topRightRect);

        //draws bottom right rectangle box around bottom right point (even when dragged/manipulated)
        int bottomRightX[] = {(point3x - 5), (point3x + 5), (point3x + 5), (point3x - 5)};
        int bottomRightY[] = {(point3y - 5), (point3y - 5), (point3y + 5), (point3y + 5)};
        bottomRightRect  = new Polygon(bottomRightX, bottomRightY, 4);
        g.drawPolygon(bottomRightRect);

        //draws bottom left rectangle box around bottom left point (even when dragged/manipulated)
        int bottomLeftX[] = {(point4x - 5), (point4x + 5), (point4x + 5), (point4x - 5)};
        int bottomLeftY[] = {(point4y - 5), (point4y - 5), (point4y + 5), (point4y + 5)};
        bottomLeftRect  = new Polygon(bottomLeftX, bottomLeftY, 4);
        g.drawPolygon(bottomLeftRect);

    }


    //when corner is being dragged, repaint (call paintComponent function)
    //this will recreate main rectangle shape and will relocate smaller rectangles
    //storing mouseclick points and using them in array allows for corner that is dragged to remain in new position when mouse is released
    public void mouseDragged(MouseEvent e){

        //if the top left corner is being dragged, store new top left corner points
        //repaint main rectangle and corner rectangles based on this new point (see point1x/point1y - used in array for new rectangle)
        if(topLeftDrag == true){
            point1x = e.getX();
            point1y = e.getY();

            int newTopLeftX[] = {point1x, point2x, point3x, point4x};
            int newTopLeftY[] = {point1y, point2y, point3y, point4y};
            rectangle  = new Polygon(newTopLeftX, newTopLeftY,4);

            repaint();
        }

        //if the top right corner is being dragged, store new top right corner points
        //repaint main rectangle and corner rectangles based on this new point (see point2x/point2y - used in array for new rectangle)
        if(topRightDrag == true){
            point2x = e.getX();
            point2y = e.getY();

            int newTopRightX[] = {point1x, point2x, point3x, point4x};
            int newTopRightY[] = {point1y, point2y, point3y, point4y};
            rectangle  = new Polygon(newTopRightX, newTopRightY,4);

            repaint();
        }

        //if the bottom right corner is being dragged, store new bottom right corner points
        //repaint main rectangle and corner rectangles based on this new point (see point3x/point3y - used in array for new rectangle)
        if(bottomRightDrag == true){
            point3x = e.getX();
            point3y = e.getY();

            int newBottomRightX[] = {point1x, point2x, point3x, point4x};
            int newBottomRightY[] = {point1y, point2y, point3y, point4y};
            rectangle  = new Polygon(newBottomRightX, newBottomRightY,4);

            repaint();
        }

        //if the bottom left corner is being dragged, store new bottom left corner points
        //repaint main rectangle and corner rectangles based on this new point (see point4x/point4y - used in array for new rectangle)
        if(bottomLeftDrag == true){
            point4x = e.getX();
            point4y = e.getY();

            int newBottomLeftX[] = {point1x, point2x, point3x, point4x};
            int newBottomLeftY[] = {point1y, point2y, point3y, point4y};
            rectangle  = new Polygon(newBottomLeftX, newBottomLeftY,4);

            repaint();
        }

    }

    public void mouseMoved(MouseEvent e){ }  //required my motion mouse listener


    //if mouse is pressed in one of the corners, set specific drag boolean to true
    public void mousePressed(MouseEvent e){

        if(topLeftRect.contains(e.getPoint())){
            topLeftDrag = true;  //indicates top left corner is about to be dragged
        }

        if(topRightRect.contains(e.getPoint())){
            topRightDrag = true;  //indicates top right corner is about to be dragged
        }

        if(bottomLeftRect.contains(e.getPoint())){
            bottomLeftDrag = true;  //indicates bottom left corner is about to be dragged
        }

        if(bottomRightRect.contains(e.getPoint())){
            bottomRightDrag = true;  //indicates bottom right corner is about to be dragged
        }

    }


    //if user lets go of mouse, set drag booleans to false - meaning, no corner is being dragged
    public void mouseReleased(MouseEvent e){
        topRightDrag = false;
        topLeftDrag = false;
        bottomRightDrag = false;
        bottomLeftDrag = false;
    }

    public void mouseClicked(MouseEvent e){ }  //required by mouse listener
    public void mouseEntered(MouseEvent e){ }  //required by mouse listener
    public void mouseExited(MouseEvent e){ }  // requited by mouse listener


    //resets dimensions of rectangle to original values and repaint rectangles based on them
    public void resetRect(){

        point1x = 150; point1y = 80;
        point2x = 250; point2y = 80;
        point3x = 250; point3y = 200;
        point4x = 150; point4y = 200;

        rectangle = new Polygon(xCoord, yCoord, 4);
        repaint();

    }

}
