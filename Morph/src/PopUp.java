import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

public class PopUp extends JPanel {

    private Point start[][];
    private Point end[][];
    private int size;
    private Timer gameTimer;
    private boolean beginning = true;
    private double time = 0;
    private int tempI, tempJ;
    private double frames = 5.0;
    int DIAMETER = 6;
    int OFFSET = DIAMETER / 2;
    private ArrayList<Point> morphPointsStart = new ArrayList<Point>();
    private ArrayList<Point> morphPointsEnd = new ArrayList<Point>();

    public PopUp() {

    }

    public void createMorph(Point[][] s, Point[][] e, int length) {
        setBackground(Color.ORANGE);
        start = s;
        end = e;
        size = length;

        generate();

    }


    public void generate() {


        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1; j++) {

                boolean first = true;

                if ((start[i][j].getX() != end[i][j].getX()) && (start[i][j].getY() != end[i][j].getY())) {

                    morphPointsStart.add(start[i][j]);
                    morphPointsEnd.add(end[i][j]);

                    tempI = i;
                    tempJ = j;
                    ActionListener timer = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if (time != 1) {

                                repaint();
                                revalidate();

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

        if (beginning) {

            for (int a = 0; a < size; a++) {
                for (int b = 0; b < size; b++) {
                    g2d.drawOval(start[a][b].x - OFFSET, start[a][b].y - OFFSET, DIAMETER, DIAMETER);
                    g2d.fillOval(start[a][b].x - OFFSET, start[a][b].y - OFFSET, DIAMETER, DIAMETER);
                    if(b - 1 > -1){ g2d.drawLine(start[a][b].x, start[a][b].y, start[a][b - 1].x, start[a][b - 1].y); }
                    if(a + 1 < size){ g2d.drawLine(start[a][b].x, start[a][b].y, start[a + 1][b].x, start[a + 1][b].y); }
                    if((b + 1) < size){ g2d.drawLine(start[a][b].x, start[a][b].y, start[a][b + 1].x, start[a][b + 1].y); }
                    if((a - 1) > -1){ g2d.drawLine(start[a][b].x, start[a][b].y, start[a - 1][b].x, start[a - 1][b].y); }

                }
            }

            beginning = false;
        }
        else {


            time += 1 / frames;

            System.out.println("repaint being called");

            for (int l = 0; l < morphPointsStart.size(); l++) {

                System.out.println("l: " + l);

                double x = morphPointsStart.get(l).getX() + (time * (morphPointsEnd.get(l).getX() - morphPointsStart.get(l).getX()));
                double y = morphPointsStart.get(l).getY() + (time * (morphPointsEnd.get(l).getY() - morphPointsStart.get(l).getY()));

                start[tempI][tempJ].setLocation(x, y);

                g2d.drawOval(start[tempI][tempJ].x - OFFSET, start[tempI][tempJ].y - OFFSET, DIAMETER, DIAMETER);
                g2d.fillOval(start[tempI][tempJ].x - OFFSET, start[tempI][tempJ].y - OFFSET, DIAMETER, DIAMETER);

//                if(tempJ - 1 > -1){ g2d.drawLine(start[tempI][tempJ].x, start[tempI][tempJ].y, start[tempI][tempJ - 1].x, start[tempI][tempJ - 1].y); }
//                if(tempI + 1 < size){ g2d.drawLine(start[tempI][tempJ].x, start[tempI][tempJ].y, start[tempI + 1][tempJ].x, start[tempI + 1][tempJ].y); }
//                if((tempJ + 1) < size){ g2d.drawLine(start[tempI][tempJ].x, start[tempI][tempJ].y, start[tempI][tempJ + 1].x, start[tempI][tempJ + 1].y); }
//                if((tempI - 1) > -1){ g2d.drawLine(start[tempI][tempJ].x, start[tempI][tempJ].y, start[tempI - 1][tempJ].x, start[tempI - 1][tempJ].y); }

                for (int c = 0; c < size; c++) {
                for (int d = 0; d < size; d++) {
                    if(d - 1 > -1){ g2d.drawLine(start[c][d].x, start[c][d].y, start[c][d - 1].x, start[c][d - 1].y); }
                    if(c + 1 < size){ g2d.drawLine(start[c][d].x, start[c][d].y, start[c + 1][d].x, start[c + 1][d].y); }
                    if((d + 1) < size){ g2d.drawLine(start[c][d].x, start[c][d].y, start[c][d + 1].x, start[c][d + 1].y); }
                    if((c - 1) > -1){ g2d.drawLine(start[c][d].x, start[c][d].y, start[c - 1][d].x, start[c - 1][d].y); }
                }
            }


            }
        }


//            time += 1 / frames;
//
//            double x = start[tempI][tempJ].getX() + (time * (end[tempI][tempJ].getX() - start[tempI][tempJ].getX()));
//            double y = start[tempI][tempJ].getY() + (time * (end[tempI][tempJ].getY() - start[tempI][tempJ].getY()));
//
//            start[tempI][tempJ].setLocation(x, y);
//
//            for (int c = 0; c < size; c++) {
//                for (int d = 0; d < size; d++) {
//                    g2d.drawOval(start[c][d].x - OFFSET, start[c][d].y - OFFSET, DIAMETER, DIAMETER);
//                    g2d.fillOval(start[c][d].x - OFFSET, start[c][d].y - OFFSET, DIAMETER, DIAMETER);
//                    if(d - 1 > -1){ g2d.drawLine(start[c][d].x, start[c][d].y, start[c][d - 1].x, start[c][d - 1].y); }
//                    if(c + 1 < size){ g2d.drawLine(start[c][d].x, start[c][d].y, start[c + 1][d].x, start[c + 1][d].y); }
//                    if((d + 1) < size){ g2d.drawLine(start[c][d].x, start[c][d].y, start[c][d + 1].x, start[c][d + 1].y); }
//                    if((c - 1) > -1){ g2d.drawLine(start[c][d].x, start[c][d].y, start[c - 1][d].x, start[c - 1][d].y); }
//                }
//            }
//
//        }

    }


}




