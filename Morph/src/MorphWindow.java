import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

public class MorphWindow extends JFrame implements ActionListener{

    private JFrame morphFrame;
    PopUp morphPanel = new PopUp();

    private JMenuBar menuBar;
    private JMenu menu1, menu2;
    private JMenuItem imageItem1, imageItem2;
    private JMenuItem grid3x3, grid5x5, grid10x10;

    private Images image1;
    private Images image2;

    private JPanel imagePanel;
    private JPanel resetPanel;

    private JButton resetButton;
    private JButton morphButton;

    private int numPoints = 100;

    JSlider frames;
    Hashtable label = new Hashtable();

    public MorphWindow(){

            setTitle("Morph");

            menuBar = new JMenuBar();

            menu1 = new JMenu("Images");
            imageItem1 = new JMenuItem("Load Image 1");
            imageItem2 = new JMenuItem("Load Image 2");
            imageItem1.addActionListener(this);
            imageItem2.addActionListener(this);

            menu2 = new JMenu("Grid");
            grid3x3 = new JMenuItem("3x3");
            grid5x5 = new JMenuItem("5x5");
            grid10x10 = new JMenuItem("10x10");
            grid3x3.addActionListener(this);
            grid5x5.addActionListener(this);
            grid10x10.addActionListener(this);

            menuBar.add(menu1);
            menu1.add(imageItem1);
            menu1.add(imageItem2);

            menuBar.add(menu2);
            menu2.add(grid3x3);
            menu2.add(grid5x5);
            menu2.add(grid10x10);

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
            frames = new JSlider(JSlider.HORIZONTAL, 0,30,10);
            label.put(15,new JLabel("# of frames"));
            frames.setLabelTable(label);
            frames.setPaintLabels(true);
            resetPanel.add(resetButton);
            resetPanel.add(frames);
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

        if(e.getSource() == grid3x3){
            numPoints = 9;
            image1.drawControlPoints(numPoints);
            image2.drawControlPoints(numPoints);
        }

        if(e.getSource() == grid5x5){
            numPoints = 25;
            image1.drawControlPoints(numPoints);
            image2.drawControlPoints(numPoints);
        }

        if(e.getSource() == grid10x10){
            numPoints = 100;
            image1.drawControlPoints(numPoints);
            image2.drawControlPoints(numPoints);
        }

        if(e.getSource() == morphButton){
            morphWindow();
        }

    }


    public void morphWindow(){

        morphFrame = new JFrame("Morph");
        morphFrame.add(morphPanel);

        Point[][] img1points = image1.getPointMatrix();
        Point[][] img2points = image2.getPointMatrix();
        BufferedImage img1 = image1.getImage();
        BufferedImage img2 = image2.getImage();
        System.out.println(img1);
        System.out.println(img2);


        morphPanel.createMorph(img1points, img2points, (int)Math.sqrt(numPoints), frames.getValue(), img1, img2);


        morphFrame.setSize(450,450);
        morphFrame.setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public static void main(String a[]){
        new MorphWindow();
    }

}


