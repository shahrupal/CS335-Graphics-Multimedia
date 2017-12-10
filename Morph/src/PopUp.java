import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class PopUp extends JPanel {

    private Point start[][];
    private Point end[][];
    private int size;
    private Timer gameTimer;
    private double time = 0;
    int DIAMETER = 6;
    int OFFSET = DIAMETER / 2;
    private ArrayList<Point> morphPointsStart = new ArrayList<Point>();
    private ArrayList<Point> morphPointsEnd = new ArrayList<Point>();
    private ArrayList<Integer> morphPointsX = new ArrayList<Integer>();
    private ArrayList<Integer> morphPointsY = new ArrayList<Integer>();
    private BufferedImage image1, image2;
    boolean first = true;
    private int frames;
    private int frameCount = 0;

    public PopUp() { }

    // serves as a constructor
    public void createMorph(Point[][] s, Point[][] e, int length, int numFrames, BufferedImage img1, BufferedImage img2) {

        frames = numFrames;

        start = s;
        end = e;
        size = length;
        image1 = img1;
        image2 = img2;

        generate();

    }


    public void generate() {

        // each time timer ticks
            ActionListener timer = new ActionListener() {

                public void actionPerformed(ActionEvent e) {

                    if(first){
                        try {
                            sleep(2000);
                        } catch (InterruptedException p) {
                        }
                        first = false;
                    }

                    if(frameCount == frames){
                        gameTimer.stop();
                    }
                    else{
                        frameCount++;
                        for (int i = 0; i < size - 1; i++) {
                            for (int j = 0; j < size - 1; j++) {

                                // add starting and end point to list
                                morphPointsStart.add(start[i][j]);
                                morphPointsEnd.add(end[i][j]);
                                morphPointsX.add(i);
                                morphPointsY.add(j);

                                float opacity = (float) frameCount / frames;

                                // create right triangle for start and end images, then warp them
                                Triangle t1 = new Triangle(start[i][j].x, start[i][j].y, start[i + 1][j].x, start[i + 1][j].y, start[i + 1][j + 1].x, start[i + 1][j + 1].y);
                                Triangle t2 = new Triangle(end[i][j].x, end[i][j].y, end[i + 1][j].x, end[i + 1][j].y, end[i + 1][j + 1].x, end[i + 1][j + 1].y);
                                eate left triangle for start and end images, then warp them
                                Triangle t3 = new Triangle(start[i][j].x, start[i][j].y, start[i][j + 1].x, start[i][j + 1].y, start[i + 1][j + 1].x, start[i + 1][j + 1].y);
                                Triangle t4 = new Triangle(end[i][j].x, end[i][j].y, end[i][j + 1].x, end[i][j + 1].y, end[i + 1][j + 1].x, end[i + 1][j + 1].y);
                                MorphTools.warpTriangle(image2, image1, t4, t3, null, null, opacity);

                                repaint();
                                revalidate();
                            }
                        }
                    }
                    MorphTools.warpTriangle(image2, image1, t2, t1, null, null, opacity);

                                // cr
                }

            };

            gameTimer = new Timer((1000 * 5)/frames, timer);
            gameTimer.start();

    }


    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // re-draw the now morphed image
        g2d.drawImage(image1,0,0,this);

        // increment 'time', which serves as increment to point placement
        time += (1/(double)frames);

        // for each point, use the x = x(i) + t*(x(f) - x(i)) function
        // this, over time, moves the starting point to the end point
        for (int i = 0; i < morphPointsStart.size(); i++) {

            double x = morphPointsStart.get(i).getX() + (time * (morphPointsEnd.get(i).getX() - morphPointsStart.get(i).getX()));
            double y = morphPointsStart.get(i).getY() + (time * (morphPointsEnd.get(i).getY() - morphPointsStart.get(i).getY()));

            // store new point
            start[morphPointsX.get(i)][morphPointsY.get(i)].setLocation(x, y);

        }

    }

}




