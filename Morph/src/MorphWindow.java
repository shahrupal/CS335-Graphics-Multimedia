// import libraries
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MorphWindow extends JFrame implements ActionListener{

    private JFrame morphFrame;
    private PopUp morphPanel = new PopUp();

    private JMenuBar menuBar;
    private JMenu menu1, menu2;
    private JMenuItem imageItem1, imageItem2;
    private JMenuItem grid5x5, grid10x10, grid20x20, gridCustom;

    private Images image1;
    private Images image2;

    private JPanel imagePanel;
    private JPanel settingsPanel;

    private JButton resetButton;
    private JButton morphButton;

    private int numPoints = 11*11;
    private int numOfFrames = 30;

    private JSlider frames;
    private JLabel frameLabel = new JLabel();

    private JSlider startBrightnessSlider;
    private JLabel startBrightnessLabel = new JLabel();
    private int startBrightness = 50;
    private JSlider endBrightnessSlider;
    private JLabel endBrightnessLabel = new JLabel();
    private int endBrightness = 50;


    // constructor
    public MorphWindow(){

        // title of frame
        setTitle("Morph");

        // create menu bar
        menuBar = new JMenuBar();

        // create first menu and its buttons
        menu1 = new JMenu("Images");
        imageItem1 = new JMenuItem("Load Image 1");
        imageItem2 = new JMenuItem("Load Image 2");
        imageItem1.addActionListener(this);
        imageItem2.addActionListener(this);

        // create second menu and its buttons
        menu2 = new JMenu("Grid");
        grid5x5 = new JMenuItem("5x5");
        grid10x10 = new JMenuItem("10x10");
        grid20x20 = new JMenuItem("20x20");
        gridCustom = new JMenuItem("Custom...");
        grid5x5.addActionListener(this);
        grid10x10.addActionListener(this);
        grid20x20.addActionListener(this);
        gridCustom.addActionListener(this);

        // add menu buttons to menu and and menu to menu bar
        menuBar.add(menu1);
        menu1.add(imageItem1);
        menu1.add(imageItem2);

        // add menu buttons to menu and and menu to menu bar
        menuBar.add(menu2);
        menu2.add(grid5x5);
        menu2.add(grid10x10);
        menu2.add(grid20x20);
        menu2.add(gridCustom);

        // initialize Images objects
        image1 = new Images();
        image2 = new Images();

        // add Images objects to the two panels
        imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(1,2));
        imagePanel.add(image1);
        imagePanel.add(image2);

        // add buttons to left panel
        settingsPanel = new JPanel(new GridLayout(8,1));
        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        morphButton = new JButton("Morph");
        morphButton.addActionListener(this);

        // slider bar for frames - stores in real time
        frames = new JSlider(JSlider.HORIZONTAL, 0,100, numOfFrames);
        frameLabel.setHorizontalAlignment(JLabel.CENTER);
        frameLabel.setText("Number of Frames: " + numOfFrames);
        frames.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                numOfFrames = frames.getValue();
                frameLabel.setText("Number of Frames: " + numOfFrames);
            }
        });

        // slider bar for the brightness of left image - stores in real time
        startBrightnessSlider = new JSlider(JSlider.HORIZONTAL, 0,100, startBrightness);
        startBrightnessLabel.setHorizontalAlignment(JLabel.CENTER);
        startBrightnessLabel.setText("Brightness of Start Image:  " + startBrightness);
        startBrightnessSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                startBrightness = startBrightnessSlider.getValue();
                startBrightnessLabel.setText("Brightness of Start Image:  " + startBrightness);
                image1.setBrightness(startBrightness);
            }
        });

        // slider bar for the brightness of right image - stores in real time
        endBrightnessSlider = new JSlider(JSlider.HORIZONTAL, 0,100, endBrightness);
        endBrightnessLabel.setHorizontalAlignment(JLabel.CENTER);
        endBrightnessLabel.setText("Brightness of End Image:  " + endBrightness);
        endBrightnessSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                endBrightness = endBrightnessSlider.getValue();
                endBrightnessLabel.setText("Brightness of End Image:  " + endBrightness);
                image2.setBrightness(endBrightness);
            }
        });

        // add buttons and sliders to left panel
        settingsPanel.add(resetButton);
        settingsPanel.add(frames);
        settingsPanel.add(frameLabel);
        settingsPanel.add(startBrightnessSlider);
        settingsPanel.add(startBrightnessLabel);
        settingsPanel.add(endBrightnessSlider);
        settingsPanel.add(endBrightnessLabel);
        settingsPanel.add(morphButton);

        // add panels and menu bar to frame
        Container c = getContentPane();
        setJMenuBar(menuBar);
        c.add(settingsPanel, BorderLayout.WEST);
        c.add(imagePanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000,455);  //sets dimension of window
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

        if(e.getSource() == grid5x5){
            numPoints = 6*6;
            image1.drawControlPoints(numPoints);
            image2.drawControlPoints(numPoints);
        }

        if(e.getSource() == grid10x10){
            numPoints = 11*11;
            image1.drawControlPoints(numPoints);
            image2.drawControlPoints(numPoints);
        }

        if(e.getSource() == grid20x20){
            numPoints = 21*21;
            image1.drawControlPoints(numPoints);
            image2.drawControlPoints(numPoints);
        }

        // create pop-up that allows user to enter custom grid dimension
        if(e.getSource() == gridCustom){

            try{
                int input = Integer.parseInt(JOptionPane.showInputDialog("Enter custom number of rows/columns (example input- 5):"));
                if(input < 3 || input > 25){
                    JOptionPane.showMessageDialog(this,"ERROR: Input must be between 3 and 25.");
                }
                else{
                    numPoints = (input+1)*(input+1);
                    image1.drawControlPoints(numPoints);
                    image2.drawControlPoints(numPoints);
                }
            }
            catch(NumberFormatException e1){
                JOptionPane.showMessageDialog(this,"ERROR: Must input an integer.");
            }

        }

        if(e.getSource() == morphButton){
            morphWindow();
        }


    }


    // create morph pop-up window
    public void morphWindow(){

        morphFrame = new JFrame("Morph");
        morphFrame.add(morphPanel);

        Point[][] img1points = image1.getPointMatrix();
        Point[][] img2points = image2.getPointMatrix();
        BufferedImage img1 = image1.getImage();
        BufferedImage img2 = image2.getImage();

        morphPanel.createMorph(img1points, img2points, (int)Math.sqrt(numPoints), numOfFrames, img1, img2);

        morphFrame.setSize(450,450);
        morphFrame.setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public static void main(String a[]){
        new MorphWindow();
    }

}


