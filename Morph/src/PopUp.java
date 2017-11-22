import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

                                repaint();
                                revalidate();

                            }
                            else{
                                time = 0;
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
                    g2d.drawOval(start[a][b].x - 2, start[a][b].y - 2, 4, 4);
                    g2d.fillOval(start[a][b].x - 2, start[a][b].y - 2, 4, 4);
                    if(b - 1 > -1){ g2d.drawLine(start[a][b].x, start[a][b].y, start[a][b - 1].x, start[a][b - 1].y); }
                    if(a + 1 < size){ g2d.drawLine(start[a][b].x, start[a][b].y, start[a + 1][b].x, start[a + 1][b].y); }
                    if((b + 1) < size){ g2d.drawLine(start[a][b].x, start[a][b].y, start[a][b + 1].x, start[a][b + 1].y); }
                    if((a - 1) > -1){ g2d.drawLine(start[a][b].x, start[a][b].y, start[a - 1][b].x, start[a - 1][b].y); }

                }
            }
            beginning = false;
        } else {

            time += 1 / frames;

            double x = start[tempI][tempJ].getX() + (time * (end[tempI][tempJ].getX() - start[tempI][tempJ].getX()));
            double y = start[tempI][tempJ].getY() + (time * (end[tempI][tempJ].getY() - start[tempI][tempJ].getY()));

            start[tempI][tempJ].setLocation(x, y);

            for (int c = 0; c < size; c++) {
                for (int d = 0; d < size; d++) {
                    g2d.drawOval(start[c][d].x - 2, start[c][d].y - 2, 4, 4);
                    g2d.fillOval(start[c][d].x - 2, start[c][d].y - 2, 4, 4);
                    if(d - 1 > -1){ g2d.drawLine(start[c][d].x, start[c][d].y, start[c][d - 1].x, start[c][d - 1].y); }
                    if(c + 1 < size){ g2d.drawLine(start[c][d].x, start[c][d].y, start[c + 1][d].x, start[c + 1][d].y); }
                    if((d + 1) < size){ g2d.drawLine(start[c][d].x, start[c][d].y, start[c][d + 1].x, start[c][d + 1].y); }
                    if((c - 1) > -1){ g2d.drawLine(start[c][d].x, start[c][d].y, start[c - 1][d].x, start[c - 1][d].y); }
                }
            }



        }

    }


}




