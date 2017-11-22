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
    private int i,j;
    private boolean beginning = true;

    public PopUp(){

    }

    public void createMorph(Point[][] s, Point[][] e, int length){
        setBackground(Color.ORANGE);
        start = s;
        end = e;
        size = length;

        generate();

    }


        public void generate(){
                ActionListener timer = new ActionListener(){
                public void actionPerformed(ActionEvent e) {

                    for (i = 0; i < size-1; i++) {
                        for (j = 0; j < size-1; j++) {

                            if ((start[i][j].getX() != end[i][j].getX()) && (start[i][j].getY() != end[i][j].getY())) {
                                System.out.println(start[i][j].getX() + " " + start[i][j].getY());
                                repaint();

                            } else {
                                gameTimer.stop();
                            }
                        }
                    }
                }
        };

        gameTimer = new Timer(1000, timer);
        gameTimer.start();



    }

    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g.create();

        if(beginning){
            for(int i = 0; i < size; i++){
                for(int j = 0; j < size; j++){
                    g2d.draw(new Ellipse2D.Double(start[i][j].getX()-2, start[i][j].getY()-2, 4,4));
                }
            }
            beginning = false;
        }else{
            
        }

          //  int frames = 30;
          //  g2d.draw(new Ellipse2D.Double(((end[i][j].getX()-2)-(start[i][j].getX()-2))/frames, ((end[i][j].getY()-2)-(start[i][j].getY()-2))/frames, 4,4));

    }


}




