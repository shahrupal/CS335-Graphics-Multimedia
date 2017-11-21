// Uploading an Image: https://stackoverflow.com/questions/13516939/how-to-upload-and-display-image-in-jframe-using-jfilechooser

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
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
    private boolean isDragging = false;
    Point temp;
    // OFFSET = 2
    // DIAMETER = 4

    public Images(){

        setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        setBackground(Color.PINK);
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

                if(!isDragging){
                    for(int i = 0; i < numRows; i++){
                        for(int j = 0; j < numCols; j++){
                            if((e.getX() > (controlPointsMatrix[i][j].getX() - 2)) && (e.getX() < (controlPointsMatrix[i][j].getX() + 2)) && (e.getY() > (controlPointsMatrix[i][j].getY() - 2)) && (e.getY() < (controlPointsMatrix[i][j].getY() + 2))){
                                temp = controlPointsMatrix[i][j];
                                isDragging = true;
                            }
                        }
                    }
                }

                if(isDragging){
                    //set new point coordinates
                    temp.setLocation(e.getX(), e.getY());
                    repaint();

                }


              // System.out.println(e.getX() + "   " + e.getY());

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                isDragging = false;
            }
        });

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
                g2d.draw(new Ellipse2D.Double(controlPointsMatrix[i][j].getX()-2, controlPointsMatrix[i][j].getY()-2, 4,4));
            }
        }

        // draws lines between control points
        // m = x, n = y
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




    /*@Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e.getX());
        System.out.println(e.getY());
        System.out.println("Hello, World!");

        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                System.out.println(controlPointsMatrix[i][j]);
                System.out.println(e.getX());
                System.out.println(e.getY());
              //  if()
                if((e.getX() > (controlPointsMatrix[i][j].getX() - 2)) && (e.getX() < (controlPointsMatrix[i][j].getX() + 2)) && (e.getY() > (controlPointsMatrix[i][j].getY() - 2)) && (e.getY() < (controlPointsMatrix[i][j].getY() + 2))){
                    System.out.println("YES");
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }*/
}


