// Uploading an Image: https://stackoverflow.com/questions/13516939/how-to-upload-and-display-image-in-jframe-using-jfilechooser

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

public class Images extends JPanel{

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

                    if(createPolygon(temp).contains(e.getPoint())){
                        temp.setLocation(e.getX(), e.getY());
                        repaint();
                    }

                }

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
                g2d.drawOval(controlPointsMatrix[i][j].x-2, controlPointsMatrix[i][j].y-2, 4,4);
                g2d.fillOval(controlPointsMatrix[i][j].x-2, controlPointsMatrix[i][j].y-2, 4,4);

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

//    public double getDistance(Point current, Point neighbor){
//
//        double x1 = current.getX();
//        double x2 = current.getY();
//        double y1 = neighbor.getX();
//        double y2 = neighbor.getY();
//
//        double distance = Math.sqrt((Math.pow((x2 - x1),2)) + (Math.pow((y2 - y1),2)));
//        return distance;
//
//    }


    // create polygon based on neighboring coordinates
    // this is to use to disallow current point being dragged across its neighbors, using contain method
    public Polygon createPolygon(Point current){

        int x[] = new int[8];
        int y[] = new int[8];

        for(int m = 0; m < numRows; m++){
            for(int n = 0; n < numCols; n++){

                if(controlPointsMatrix[m][n] == current) {

                    if(((m - 1) > -1) && ((n - 1) > -1) && ((m + 1) < numRows) && ((n + 1) < numCols)){

                        x[0] = controlPointsMatrix[m - 1][n - 1].x;
                        x[1] = controlPointsMatrix[m][n - 1].x;
                        x[2] = controlPointsMatrix[m + 1][n - 1].x;
                        x[3] = controlPointsMatrix[m + 1][n].x;
                        x[4] = controlPointsMatrix[m + 1][n + 1].x;
                        x[5] = controlPointsMatrix[m][n + 1].x;
                        x[6] = controlPointsMatrix[m - 1][n + 1].x;
                        x[7] = controlPointsMatrix[m - 1][n].x;

                        y[0] = controlPointsMatrix[m - 1][n - 1].y;
                        y[1] = controlPointsMatrix[m][n - 1].y;
                        y[2] = controlPointsMatrix[m + 1][n - 1].y;
                        y[3] = controlPointsMatrix[m + 1][n].y;
                        y[4] = controlPointsMatrix[m + 1][n + 1].y;
                        y[5] = controlPointsMatrix[m][n + 1].y;
                        y[6] = controlPointsMatrix[m - 1][n + 1].y;
                        y[7] = controlPointsMatrix[m - 1][n].y;

                    }

                }
            }
        }

        Polygon p = new Polygon(x,y,8);
        return p;
    }

    public Point[][] getPointMatrix(){
        return controlPointsMatrix;
    }

}


