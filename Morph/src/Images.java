// Uploading an Image: https://stackoverflow.com/questions/13516939/how-to-upload-and-display-image-in-jframe-using-jfilechooser

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class Images extends JPanel {

    private ControlPoint controlPointsMatrix[][];
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

        ControlGrid(100,1000,1000);  // use width and height of image
        setVisible(true);

    }



    public void ControlGrid(int num, int width, int height) {

        numControlPoints = num;
        imageWidth = width;
        imageHeight = height;

        numRows = numControlPoints / 2;
        numCols = numControlPoints / 2;

        controlPointsMatrix  = new ControlPoint[numControlPoints/2][numControlPoints/2];

        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                ControlPoint cp = new ControlPoint(i*(imageWidth/numControlPoints),j*(imageHeight/numControlPoints),5);
                cp.setX(i * (imageWidth / numControlPoints));
                cp.setY(j * (imageHeight / numControlPoints));
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


        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                g2d.drawOval(controlPointsMatrix[i][j].getX(),controlPointsMatrix[i][j].getY(),5,5);
                g2d.fillOval(controlPointsMatrix[i][j].getX(),controlPointsMatrix[i][j].getY(),5,5);
            }
        }

    }

}


