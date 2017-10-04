import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DraggableRectangle extends JPanel implements MouseListener, MouseMotionListener{

    private int point1x = 150, point1y = 80;
    private int point2x = 250, point2y = 80;
    private int point3x = 250, point3y = 200;
    private int point4x = 150, point4y = 200;

    private int xCoord[] = {point1x, point2x, point3x, point4x};
    private int yCoord[] = {point1y, point2y, point3y, point4y};

    private Polygon rectangle;
    private Polygon topLeftRect, topRightRect, bottomLeftRect, bottomRightRect;

    private boolean topRightDrag = false, topLeftDrag = false, bottomRightDrag = false, bottomLeftDrag = false;

    public DraggableRectangle(){
        setBackground(Color.DARK_GRAY);
        rectangle = new Polygon(xCoord, yCoord, 4);
        super.addMouseListener(this);
        super.addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D largeRectangle = (Graphics2D) g.create();
        largeRectangle.setColor(Color.PINK);
        largeRectangle.fillPolygon(rectangle);
        largeRectangle.drawPolygon(rectangle);

        Graphics2D smallRectangle = (Graphics2D) g.create();
        miniRectangles(smallRectangle);
    }

    /** draws small rectangles on each corner to indicate where points can drag the rectangle **/
    public void miniRectangles(Graphics2D g){
        g.setColor(Color.GREEN);

        int topLeftX[] = {(point1x - 5), (point1x + 5), (point1x + 5), (point1x - 5)};
        int topLeftY[] = {(point1y - 5), (point1y - 5), (point1y + 5), (point1y + 5)};
        topLeftRect  = new Polygon(topLeftX, topLeftY, 4);
        g.drawPolygon(topLeftRect);

        int topRightX[] = {(point2x - 5), (point2x + 5), (point2x + 5), (point2x - 5)};
        int topRightY[] = {(point2y - 5), (point2y - 5), (point2y + 5), (point2y + 5)};
        topRightRect  = new Polygon(topRightX, topRightY, 4);
        g.drawPolygon(topRightRect);

        int bottomRightX[] = {(point3x - 5), (point3x + 5), (point3x + 5), (point3x - 5)};
        int bottomRightY[] = {(point3y - 5), (point3y - 5), (point3y + 5), (point3y + 5)};
        bottomRightRect  = new Polygon(bottomRightX, bottomRightY, 4);
        g.drawPolygon(bottomRightRect);

        int bottomLeftX[] = {(point4x - 5), (point4x + 5), (point4x + 5), (point4x - 5)};
        int bottomLeftY[] = {(point4y - 5), (point4y - 5), (point4y + 5), (point4y + 5)};
        bottomLeftRect  = new Polygon(bottomLeftX, bottomLeftY, 4);
        g.drawPolygon(bottomLeftRect);

    }

    public void mouseDragged(MouseEvent e){
        if(topLeftDrag == true){
            point1x = e.getX();
            point1y = e.getY();

            int newTopLeftX[] = {point1x, point2x, point3x, point4x};
            int newTopLeftY[] = {point1y, point2y, point3y, point4y};
            rectangle  = new Polygon(newTopLeftX, newTopLeftY,4);

            repaint();
        }
        if(topRightDrag == true){
            point2x = e.getX();
            point2y = e.getY();

            int newTopRightX[] = {point1x, point2x, point3x, point4x};
            int newTopRightY[] = {point1y, point2y, point3y, point4y};
            rectangle  = new Polygon(newTopRightX, newTopRightY,4);

            repaint();
        }
        if(bottomRightDrag == true){
            point3x = e.getX();
            point3y = e.getY();

            int newBottomRightX[] = {point1x, point2x, point3x, point4x};
            int newBottomRightY[] = {point1y, point2y, point3y, point4y};
            rectangle  = new Polygon(newBottomRightX, newBottomRightY,4);

            repaint();
        }
        if(bottomLeftDrag == true){
            point4x = e.getX();
            point4y = e.getY();

            int newBottomLeftX[] = {point1x, point2x, point3x, point4x};
            int newBottomLeftY[] = {point1y, point2y, point3y, point4y};
            rectangle  = new Polygon(newBottomLeftX, newBottomLeftY,4);

            repaint();
        }
    }
    public void mouseMoved(MouseEvent e){ }



    public void mousePressed(MouseEvent e){
        if(topLeftRect.contains(e.getPoint())){
            System.out.println("in small rect");
            topLeftDrag = true;
        }
        if(topRightRect.contains(e.getPoint())){
            topRightDrag = true;
        }
        if(bottomLeftRect.contains(e.getPoint())){
            bottomLeftDrag = true;
        }
        if(bottomRightRect.contains(e.getPoint())){
            bottomRightDrag = true;
        }
    }

    public void mouseReleased(MouseEvent e){
        topRightDrag = false;
        topLeftDrag = false;
        bottomRightDrag = false;
        bottomLeftDrag = false;
    }

    public void mouseClicked(MouseEvent e){ }
    public void mouseEntered(MouseEvent e){ }
    public void mouseExited(MouseEvent e){ }

    public void resetRect(){
        point1x = 150; point1y = 80;
        point2x = 250; point2y = 80;
        point3x = 250; point3y = 200;
        point4x = 150; point4y = 200;
        rectangle = new Polygon(xCoord, yCoord, 4);
        repaint();
    }

}
