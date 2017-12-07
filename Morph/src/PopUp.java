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

        Point p1start = start[1][1];
        Point p2start = start[1][2];
        Point p3start = start[2][2];

        Point p1end = end[1][1];
        Point p2end = end[1][2];
        Point p3end = end[2][2];

        Triangle t1 = new Triangle(p1start.x, p1start.y, p2start.x, p2start.y, p3start.x, p3start.y);
        Triangle t2 = new Triangle(p1end.x, p1end.y, p2end.x, p2end.y, p3end.x, p3end.y);
        MorphTools.warpTriangle(image1, image2, t1, t2, null, null);

        /*        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1; j++) {

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

    */
    }


    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.drawImage(image2,0,0,this);
        //g2d.drawImage(image1,0,0,null);



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

        }
    }



}




