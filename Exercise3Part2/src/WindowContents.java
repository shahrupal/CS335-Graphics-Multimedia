import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WindowContents extends JFrame implements ActionListener{

    private JPanel buttonPanel;
    private JButton reset;

    DraggableRectangle rect = new DraggableRectangle();

    public WindowContents(){

        setTitle("Draggable Rectangle");

        Container c = getContentPane();

        buttonPanel = new JPanel();

        reset = new JButton("Reset");
        reset.addActionListener(this);

        buttonPanel.add(reset);

        c.add(buttonPanel, BorderLayout.SOUTH);
        c.add(rect);

        setSize(400,400);
        setResizable(false);
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == reset){
            rect.resetRect();
        }
    }

    public static void main(String a[]){
        new WindowContents();
    }

}