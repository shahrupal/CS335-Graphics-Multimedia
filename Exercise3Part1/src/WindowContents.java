import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class WindowContents extends JFrame implements ActionListener{

    private JPanel buttonPanel;
    private JButton stop, restart;
    private RotatingRectangle rect = new RotatingRectangle();

    public WindowContents(){

        setTitle("Rotating Rectangle");

        Container c = getContentPane();

        buttonPanel = new JPanel();

        stop = new JButton("Stop");
        restart = new JButton("Restart");

        stop.addActionListener(this);
        restart.addActionListener(this);

        buttonPanel.add(stop);
        buttonPanel.add(restart);

        c.add(buttonPanel, BorderLayout.SOUTH);
        c.add(rect);

        setSize(400,400);
        setResizable(false);
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == stop){
            rect.stopTimer();
        }
        if(e.getSource() == restart){
            rect.resetRect();
        }
    }

    public static void main(String a[]){
        new WindowContents();
    }

}