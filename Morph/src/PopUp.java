import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class PopUp extends JPanel {

    private Point start[][];
    private Point end[][];
    private int size;
    private Timer gameTimer;
    private boolean beginning = true;
    private double time = 0;
    private int tempI, tempJ;
    private double frames = 5.0;

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

        ActionListener timer = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < size - 1; i++) {
                    for (int j = 0; j < size - 1; j++) {

                        if ((start[i][j].getX() != end[i][j].getX()) && (start[i][j].getY() != end[i][j].getY())) {

                            if (time != 1) {
                                tempI = i;
                                tempJ = j;
                                //System.out.println(start[i][j].getX() + " " + start[i][j].getY());
                                repaint();
                                revalidate();
                            }
                            else {
                                gameTimer.stop();
                            }

                        }
                    }
                }
            }
        };
        gameTimer = new Timer(1000, timer);
        gameTimer.start();


    }


    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        if (beginning) {
            for (int a = 0; a < size; a++) {
                for (int b = 0; b < size; b++) {
                    g2d.draw(new Ellipse2D.Double(start[a][b].getX() - 2, start[a][b].getY() - 2, 4, 4));
                }
            }
            beginning = false;
        } else {

            time += 1 / frames;

            System.out.println("i: " + tempI + ", j: " + tempJ);

            double x = start[tempI][tempJ].getX() + (time * (end[tempI][tempJ].getX() - start[tempI][tempJ].getX()));
            double y = start[tempI][tempJ].getY() + (time * (end[tempI][tempJ].getY() - start[tempI][tempJ].getY()));
            System.out.println("x: " + x + ", y: " + y);

            start[tempI][tempJ].setLocation(x, y);
            //g2d.draw(new Ellipse2D.Double(x, y, 4,4));

            for (int c = 0; c < size; c++) {
                for (int d = 0; d < size; d++) {
                    g2d.draw(new Ellipse2D.Double(start[c][d].getX() - 2, start[c][d].getY() - 2, 4, 4));
                }
            }

        }

    }


}




