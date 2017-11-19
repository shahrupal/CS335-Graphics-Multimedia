import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


/** STORES CONTROL POINT INFO -- DOES NOT DRAW IT **/

public class ControlPoint {

    private int x, y, r;
    public ControlPoint(int xCoord, int yCoord, int radius){

        x = xCoord;
        y = yCoord;
        r = radius;

    }

    public void setX(int xVal) { x = xVal; }
    public void setY(int yVal) { y = yVal; }

    public int getX() { return x; }
    public int getY() { return y; }

}
