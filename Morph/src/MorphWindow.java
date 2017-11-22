import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MorphWindow extends JFrame implements ActionListener{

    private JFrame morphFrame;
    PopUp morphPanel = new PopUp();

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem imageItem1, imageItem2;

    private Images image1;
    private Images image2;

    private JPanel imagePanel;
    private JPanel resetPanel;

    private JButton resetButton;
    private JButton morphButton;

    public MorphWindow(){

            setTitle("Morph");

            menuBar = new JMenuBar();
            menu = new JMenu("Menu");
            imageItem1 = new JMenuItem("Load Image 1");
            imageItem2 = new JMenuItem("Load Image 2");
            imageItem1.addActionListener(this);
            imageItem2.addActionListener(this);

            menuBar.add(menu);
            menu.add(imageItem1);
            menu.add(imageItem2);

            image1 = new Images();
            image2 = new Images();
            imagePanel = new JPanel();

            imagePanel.setLayout(new GridLayout(1,2));
            imagePanel.add(image1);
            imagePanel.add(image2);

            resetPanel = new JPanel();
            resetButton = new JButton("Reset");
            resetButton.addActionListener(this);
            morphButton = new JButton("Morph");
            morphButton.addActionListener(this);
            resetPanel.add(resetButton);
            resetPanel.add(morphButton);

            Container c = getContentPane();
            setJMenuBar(menuBar);
            c.add(resetPanel, BorderLayout.SOUTH);
            c.add(imagePanel);


            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setSize(850,500);  //sets dimension of window
            setResizable(false);
            setVisible(true);  //allows user to see window

    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == imageItem1){
            image1.selectImage();
        }

        if(e.getSource() == imageItem2){
            image2.selectImage();
        }

        if(e.getSource() == morphButton){
            morphWindow();
        }

    }

    public void highlight(){





    }

    public void morphWindow(){

        morphFrame = new JFrame("Morph");
        morphFrame.add(morphPanel);

        Point[][] img1points = image1.getPointMatrix();
        Point[][] img2points = image2.getPointMatrix();

        morphPanel.createMorph(img1points, img2points, 10);

        morphFrame.setSize(450,450);
        morphFrame.setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public static void main(String a[]){
        new MorphWindow();
    }

}


