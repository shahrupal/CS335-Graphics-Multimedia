// Uploading an Image: https://stackoverflow.com/questions/13516939/how-to-upload-and-display-image-in-jframe-using-jfilechooser

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.lang.Math.*;

public class Images extends JPanel {

    private Point controlPointsMatrix[][];
    private int numControlPoints;
    private int numRows, numCols;
    private int imageWidth, imageHeight;
    private BufferedImage buffer;

    public Images(){

        setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        setBackground(Color.PINK);

    }


    public void selectImage(){

        JFileChooser browse = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        // new FileNameExtensionFilter

        int approve = browse.showOpenDialog(null);

        if(approve == JFileChooser.APPROVE_OPTION){


            try {

                File selected = browse.getSelectedFile();
                buffer = ImageIO.read(selected);

                drawControlPoints();

            }
            catch(IOException e) {

            }

        }
    }


    public void drawControlPoints(){

        ControlGrid(100,450,450);  // use width and height of image
        setVisible(true);

    }



    public void ControlGrid(int num, int width, int height) {

        numControlPoints = num;
        imageWidth = width;
        imageHeight = height;

        numRows = (int)Math.sqrt(numControlPoints);
        numCols = (int)Math.sqrt(numControlPoints);

        controlPointsMatrix  = new Point[(int)Math.sqrt(numControlPoints)][(int)Math.sqrt(numControlPoints)];

        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                Point cp = new Point(i,j);
                cp.setLocation(i * (imageWidth / numRows), j * (imageHeight / numCols));
                controlPointsMatrix[i][j] = cp;
            }
        }

        repaint();
        setVisible(true);

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.setColor(Color.BLACK);


        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(buffer, 0,0, this);

        // draws control points
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                g2d.drawOval((int)controlPointsMatrix[i][j].getX(),(int)controlPointsMatrix[i][j].getY(),5,5);
                g2d.fillOval((int)controlPointsMatrix[i][j].getX(),(int)controlPointsMatrix[i][j].getY(),5,5);
            }
        }

        // draws lines between control points
        // m = x, n = y
        // drawLine(x1,y1,x2,y2)
        for(int m = 0; m < numRows; m++){
            for(int n = 0; n < numCols; n++){

                // current point to top point
                if(n - 1 > -1){
                    g2d.drawLine((int)controlPointsMatrix[m][n].getX(), (int)controlPointsMatrix[m][n].getY(), (int)controlPointsMatrix[m][n - 1].getX(),(int)controlPointsMatrix[m][n - 1].getY());
                }

                // current point to right point
                if(m + 1 < numCols){
                    g2d.drawLine((int)controlPointsMatrix[m][n].getX(), (int)controlPointsMatrix[m][n].getY(), (int)controlPointsMatrix[m + 1][n].getX(),(int)controlPointsMatrix[m + 1][n].getY());
                }

                // current point to bottom point
                if((n + 1) < numRows){
                    g2d.drawLine((int)controlPointsMatrix[m][n].getX(), (int)controlPointsMatrix[m][n].getY(), (int)controlPointsMatrix[m][n + 1].getX(),(int)controlPointsMatrix[m][n + 1].getY());
                }

               // current point to left point
                if((m - 1) > -1){
                    g2d.drawLine((int)controlPointsMatrix[m][n].getX(), (int)controlPointsMatrix[m][n].getY(), (int)controlPointsMatrix[m - 1][n].getX(),(int)controlPointsMatrix[m - 1][n].getY());
                }

            }
        }

    }

}


