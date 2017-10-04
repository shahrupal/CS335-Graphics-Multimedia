import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class RotatingRectangle extends JPanel implements ActionListener, MouseListener{

    private int xCoord[] = {150, 250, 250, 150};
    private int yCoord[] = {80, 80, 200, 200};
    private int degrees = 0;
    private int getX, getY;
    private JButton stop, restart;
    Timer timer;

    private Polygon rectangle;

    public RotatingRectangle(){
        rectangle = new Polygon(xCoord, yCoord, 4);

        super.addMouseListener(this);

        timer = new Timer(50, this);

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.rotate(degrees, getX, getY);

        g2d.setColor(Color.PINK);
        g2d.fillPolygon(rectangle);

        g2d.drawPolygon(rectangle);

    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == timer){
            repaint();
            degrees = degrees + 1;
        }
        removeMouseListener(this);
    }


    public void mouseClicked(MouseEvent e){
        if(rectangle.contains(e.getPoint())) {
            timer.start();
        }
        getX = e.getX();
        getY = e.getY();
    }

    public void mousePressed(MouseEvent e){ }
    public void mouseEntered(MouseEvent e){ }
    public void mouseExited(MouseEvent e){ }
    public void mouseReleased(MouseEvent e){ }

    public void stopTimer(){
        timer.stop();
    }

    public void resetRect(){
        rectangle = new Polygon(xCoord, yCoord, 4);
        degrees = 0;
        repaint();
        timer.stop();
        super.addMouseListener(this);
    }

}
