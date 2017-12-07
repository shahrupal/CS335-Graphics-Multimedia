import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PopUp extends JPanel {

    private Point start[][];
    private Point end[][];
    private int size;
    private Timer gameTimer;
    private boolean beginning = true;
    private double time = 0;
    private double frames = 10.0;
    int DIAMETER = 6;
    int OFFSET = DIAMETER / 2;
    private ArrayList<Point> morphPointsStart = new ArrayList<Point>();
    private ArrayList<Point> morphPointsEnd = new ArrayList<Point>();
    private ArrayList<Integer> morphPointsX = new ArrayList<Integer>();
    private ArrayList<Integer> morphPointsY = new ArrayList<Integer>();
    private BufferedImage image1, image2;

    public PopUp() {
        //setBackground(Color.ORANGE);
    }

    public void createMorph(Point[][] s, Point[][] e, int length, double numFrames, BufferedImage img1, BufferedImage img2) {

        frames = numFrames;
        start = s;
        end = e;
        size = length;
        image1 = img1;
        image2 = img2;

        generate();

    }


    public void generate() {

        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1; j++) {

                // right triangle
                Triangle t1 = new Triangle(start[i][j].x, start[i][j].y, start[i+1][j].x, start[i+1][j].y, start[i+1][j+1].x, start[i+1][j+1].y);
                Triangle t2 = new Triangle(end[i][j].x, end[i][j].y, end[i+1][j].x, end[i+1][j].y, end[i+1][j+1].x, end[i+1][j+1].y);
                MorphTools.warpTriangle(image2, image1, t2, t1, null, null);

                // left triangle
                Triangle t3 = new Triangle(start[i][j].x, start[i][j].y, start[i][j+1].x, start[i][j+1].y, start[i+1][j+1].x, start[i+1][j+1].y);
                Triangle t4 = new Triangle(end[i][j].x, end[i][j].y, end[i][j+1].x, end[i][j+1].y, end[i+1][j+1].x, end[i+1][j+1].y);
                MorphTools.warpTriangle(image2, image1, t4, t3, null, null);

                if ((start[i][j].getX() != end[i][j].getX()) && (start[i][j].getY() != end[i][j].getY())) {

                    morphPointsStart.add(start[i][j]);
                    morphPointsEnd.add(end[i][j]);
                    morphPointsX.add(i);
                    morphPointsY.add(j);



                    ActionListener timer = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if (time < 1) {
                                repaint();
                                revalidate();

                            }
                            else{
                                gameTimer.stop();
                            }
                            //IF YOU WANT CONTINUOUS LOOP OF MORPHING:
                            //else{
                            //    time = 0;
                            //}
                        }
                    };
                    gameTimer = new Timer(1000, timer);
                    gameTimer.start();
                 }
            }
        }

    }


    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.drawImage(image1,0,0,this);

        if (beginning) {

            for (int a = 0; a < size; a++) {
                for (int b = 0; b < size; b++) {
                    g2d.drawOval(start[a][b].x - OFFSET, start[a][b].y - OFFSET, DIAMETER, DIAMETER);
                    g2d.fillOval(start[a][b].x - OFFSET, start[a][b].y - OFFSET, DIAMETER, DIAMETER);
                    if(b - 1 > -1){ g2d.drawLine(start[a][b].x, start[a][b].y, start[a][b - 1].x, start[a][b - 1].y); }
                    if(a + 1 < size){ g2d.drawLine(start[a][b].x, start[a][b].y, start[a + 1][b].x, start[a + 1][b].y); }
                    if((b + 1) < size){ g2d.drawLine(start[a][b].x, start[a][b].y, start[a][b + 1].x, start[a][b + 1].y); }
                    if((a - 1) > -1){ g2d.drawLine(start[a][b].x, start[a][b].y, start[a - 1][b].x, start[a - 1][b].y); }
                    if(((a - 1) > -1) && ((b - 1) > -1)){ g2d.drawLine(start[a][b].x, start[a][b].y, start[a - 1][b - 1].x,start[a - 1][b - 1].y); }
                }
            }

            beginning = false;
        }
        else {

            time += 1 / frames;

            for (int l = 0; l < morphPointsStart.size(); l++) {

                double x = morphPointsStart.get(l).getX() + (time * (morphPointsEnd.get(l).getX() - morphPointsStart.get(l).getX()));
                double y = morphPointsStart.get(l).getY() + (time * (morphPointsEnd.get(l).getY() - morphPointsStart.get(l).getY()));

                start[morphPointsX.get(l)][morphPointsY.get(l)].setLocation(x, y);
            }

            for (int a = 0; a < size-1; a++) {
                for (int b = 0; b < size-1; b++) {

                    // right triangle
                    Triangle t1 = new Triangle(start[a][b].x, start[a][b].y, start[a+1][b].x, start[a+1][b].y, start[a+1][b+1].x, start[a+1][b+1].y);
                    Triangle t2 = new Triangle(end[a][b].x, end[a][b].y, end[a+1][b].x, end[a+1][b].y, end[a+1][b+1].x, end[a+1][b+1].y);
                    MorphTools.warpTriangle(image2, image1, t2, t1, null, null);

                    // left triangle
                    Triangle t3 = new Triangle(start[a][b].x, start[a][b].y, start[a][b+1].x, start[a][b+1].y, start[a+1][b+1].x, start[a+1][b+1].y);
                    Triangle t4 = new Triangle(end[a][b].x, end[a][b].y, end[a][b+1].x, end[a][b+1].y, end[a+1][b+1].x, end[a+1][b+1].y);
                    MorphTools.warpTriangle(image2, image1, t4, t3, null, null);

                    g2d.drawImage(image1,0,0,this);

//                    g2d.drawOval(start[a][b].x - OFFSET, start[a][b].y - OFFSET, DIAMETER, DIAMETER);
//                    g2d.fillOval(start[a][b].x - OFFSET, start[a][b].y - OFFSET, DIAMETER, DIAMETER);
//                    if(b - 1 > -1){ g2d.drawLine(start[a][b].x, start[a][b].y, start[a][b - 1].x, start[a][b - 1].y); }
//                    if(a + 1 < size){ g2d.drawLine(start[a][b].x, start[a][b].y, start[a + 1][b].x, start[a + 1][b].y); }
//                    if((b + 1) < size){ g2d.drawLine(start[a][b].x, start[a][b].y, start[a][b + 1].x, start[a][b + 1].y); }
//                    if((a - 1) > -1){ g2d.drawLine(start[a][b].x, start[a][b].y, start[a - 1][b].x, start[a - 1][b].y); }
//                    if(((a - 1) > -1) && ((b - 1) > -1)){ g2d.drawLine(start[a][b].x, start[a][b].y, start[a - 1][b - 1].x,start[a - 1][b - 1].y); }


                }
            }

        }
    }



}




