/***************************************************************************

Loads an image (JPEG or GIF), displays it, selects from
a small set of image processing routines, and shows the results

***************************************************************************/

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;

public class Exercise4 extends JFrame {

   // This frame will hold the primary components:
   // 	An object to hold the buffered image and its associated operations
   //	Components to control the interface

   // here are a few kernels to try
   private final float[] LOWPASS3x3 =
           {0.1f, 0.1f, 0.1f, 0.1f, 0.2f, 0.1f, 0.1f, 0.1f, 0.1f};

   private final float[] LOWPASS5x5 =
           {0.003765f, 0.015019f, 0.023792f, 0.015019f, 0.003765f,
                   0.015019f, 0.059912f, 0.094907f, 0.059912f, 0.015019f,
                   0.023792f, 0.094907f, 0.150342f, 0.094907f, 0.023792f,
                   0.015019f, 0.059912f, 0.094907f, 0.059912f, 0.015019f,
                   0.003765f, 0.015019f, 0.023792f, 0.015019f, 0.003765f};

   private final float[] LOWPASS7x7 =
           {0.000036f, 0.000363f, 0.001446f, 0.002291f, 0.001446f, 0.000363f, 0.000036f,
                   0.000363f, 0.003676f, 0.014662f, 0.023226f, 0.014662f, 0.003676f, 0.000363f,
                   0.001446f, 0.014662f, 0.058488f, 0.092651f, 0.058488f, 0.014662f, 0.0001446f,
                   0.002291f, 0.023226f, 0.092651f, 0.146768f, 0.092651f, 0.023226f, 0.002291f,
                   0.001446f, 0.014662f, 0.058488f, 0.092651f, 0.058488f, 0.014662f, 0.0001446f,
                   0.000363f, 0.003676f, 0.014662f, 0.023226f, 0.014662f, 0.003676f, 0.000363f,
                   0.000036f, 0.000363f, 0.001446f, 0.002291f, 0.001446f, 0.000363f, 0.000036f};

   private final float[] LOWPASS9x9 =
           {0.000000f, 0.000001f, 0.000014f, 0.000055f, 0.000088f, 0.000055f, 0.000014f, 0.000001f, 0.000000f,
                   0.000001f, 0.000036f, 0.000362f, 0.001445f, 0.002289f, 0.001445f, 0.000362f, 0.000036f, 0.000001f,
                   0.000014f, 0.000362f, 0.003672f, 0.014648f, 0.023205f, 0.014648f, 0.003672f, 0.000362f, 0.000014f,
                   0.000055f, 0.001445f, 0.014648f, 0.058434f, 0.092566f, 0.058434f, 0.014648f, 0.001445f, 0.000055f,
                   0.000088f, 0.002289f, 0.023205f, 0.092566f, 0.146634f, 0.092566f, 0.023205f, 0.002289f, 0.000088f,
                   0.000055f, 0.001445f, 0.014648f, 0.058434f, 0.092566f, 0.058434f, 0.014648f, 0.001445f, 0.000055f,
                   0.000014f, 0.000362f, 0.003672f, 0.014648f, 0.023205f, 0.014648f, 0.003672f, 0.000362f, 0.000014f,
                   0.000001f, 0.000036f, 0.000362f, 0.001445f, 0.002289f, 0.001445f, 0.000362f, 0.000036f, 0.000001f,
                   0.000000f, 0.000001f, 0.000014f, 0.000055f, 0.000088f, 0.000055f, 0.000014f, 0.000001f, 0.000000f};

   private final float[] LOWPASS11x11 =
           {0f, 0f, 0f, 0f, 0.000001f, 0.000001f, 0.000001f, 0f, 0f, 0f, 0f,
                   0f, 0f, 0.000001f, 0.000014f, 0.000055f, 0.000088f, 0.000055f, 0.000014f, 0.000001f, 0f, 0f,
                   0f, 0.000001f, 0.000036f, 0.000362f, 0.001445f, 0.002289f, 0.001445f, 0.000362f, 0.000036f, 0.000001f, 0f,
                   0f, 0.000014f, 0.000362f, 0.003672f, 0.014648f, 0.023204f, 0.014648f, 0.003672f, 0.000362f, 0.000014f, 0f,
                   0.000001f, 0.000055f, 0.001445f, 0.014648f, 0.058433f, 0.092564f, 0.058433f, 0.014648f, 0.001445f, 0.000055f, 0.000001f,
                   0.000001f, 0.000088f, 0.002289f, 0.023204f, 0.092564f, 0.146632f, 0.092564f, 0.023204f, 0.002289f, 0.000088f, 0.000001f,
                   0.000001f, 0.000055f, 0.001445f, 0.014648f, 0.058433f, 0.092564f, 0.058433f, 0.014648f, 0.001445f, 0.000055f, 0.000001f,
                   0f, 0.000014f, 0.000362f, 0.003672f, 0.014648f, 0.023204f, 0.014648f, 0.003672f, 0.000362f, 0.000014f, 0f,
                   0f, 0.000001f, 0.000036f, 0.000362f, 0.001445f, 0.002289f, 0.001445f, 0.000362f, 0.000036f, 0.000001f, 0f,
                   0f, 0f, 0.000001f, 0.000014f, 0.000055f, 0.000088f, 0.000055f, 0.000014f, 0.000001f, 0f, 0f,
                   0f, 0f, 0f, 0f, 0.000001f, 0.000001f, 0.000001f, 0f, 0f, 0f, 0f};

   // Instance variables
   private BufferedImage image;   // the image
   private MyImageObj view;       // a component in which to display an image
   private JButton original;
   private JButton Blur3x3;// Button to restore original image
   private JButton Blur5x5; // Button to trigger sharpen operator
   private JButton Blur7x7;    // Button to trigger blur operator
   private JButton Blur9x9;    // Button to trigger edge detect operator
   private JButton Blur11x11;  // Button to hold custom filter
   private JTextField filterfield[];
   private int x, y;              // Store x, y mouse position for paint
   private boolean firstdrag=true;// Flag to toggle draw mode
   private JLabel ThreshLabel;   // Label for threshold slider
   private JSlider thresholdslider;
   private float customfiltervalues[];
   private int rotation=0;

   // Constructor for the frame
   public Exercise4 () {

      super();				// call JFrame constructor

      this.buildMenus();		// helper method to build menus
      this.buildComponents();		// helper method to set up components
      this.buildDisplay();		// Lay out the components on the display
   }

   //  Builds the menus to be attached to this JFrame object
   //  Primary side effect:  menus are added via the setJMenuBar call
   //  		Action listeners for the menu items are anonymous inner
   //		classes here
   //  This helper method is called once by the constructor

   private void buildMenus () {

      final JFileChooser fc = new JFileChooser(".");
      JMenuBar bar = new JMenuBar();
      this.setJMenuBar (bar);
      JMenu fileMenu = new JMenu ("File");
      JMenuItem fileopen = new JMenuItem ("Open");
      JMenuItem fileexit = new JMenuItem ("Exit");

      fileopen.addActionListener(
	 new ActionListener () {
             public void actionPerformed (ActionEvent e) {
                int returnVal = fc.showOpenDialog(Exercise4.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                   File file = fc.getSelectedFile();
                   try {
                     image = ImageIO.read(file);
                   } catch (IOException e1){};

		   view.setImage(image);
		   view.showImage();
                }
             }
         }
      );
      fileexit.addActionListener(
	 new ActionListener () {
             public void actionPerformed (ActionEvent e) {
                   System.exit(0);
             }
         }
      );

      fileMenu.add(fileopen);
      fileMenu.add(fileexit);
      bar.add(fileMenu);
   }

   //  Allocate components (these are all instance vars of this frame object)
   //  and set up action listeners for each of them 
   //  This is called once by the constructor

   private void buildComponents() {

      // Create components to in which to display image and GUI controls
      // reads a default image
      view = new MyImageObj(readImage("boat.gif"));
      //view = new MyImageObj();
      original = new JButton("Reset");
      Blur3x3 = new JButton("Blur 3x3");
      Blur5x5 = new JButton("Blur 5x5");
      Blur7x7 = new JButton("Blur 7x7");
      Blur9x9 = new JButton("Blur 9x9");
      Blur11x11 = new JButton ("Blur 11x11");
      filterfield = new JTextField[9];
      customfiltervalues = new float[9];
      for (int i=0; i<filterfield.length; i++) {
         filterfield[i] = new JTextField (5);
         filterfield[i].setText("0.0");
      }
      filterfield[4].setText("1.0");

      // Listen for mouse events to allow painting on image
      view.addMouseMotionListener(
         new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent event) {
	       Graphics g = view.getGraphics();
	       g.setColor (Color.white);
	       if (firstdrag) {
		  x = event.getX();  y = event.getY();
		  firstdrag = false;
               }
               else {
	          view.showImage();
                  x=event.getX();
                  y=event.getY();
                  int w=view.getImage().getWidth();
	          int h=view.getImage().getHeight();
                  g.fillOval (x-5, y-5, 10, 10);
                  g.drawLine (0,0, x, y);
                  g.drawLine (0,h, x, y);
                  g.drawLine (w,h, x, y);
                  g.drawLine (w,0, x, y);
               }
            }
         }
      );

      // Listen for mouse release to detect when we've stopped painting
      view.addMouseListener(
         new MouseAdapter() {
            public void mouseReleased(MouseEvent event) {
	       Graphics g = view.getGraphics();
	       firstdrag = true;
               x=event.getX();
               y=event.getY();
               int w=view.getImage().getWidth();
	       int h=view.getImage().getHeight();
               g.fillOval (x-5, y-5, 10, 10);
               g.drawLine (0,0, x, y);
               g.drawLine (0,h, x, y);
               g.drawLine (w,h, x, y);
               g.drawLine (w,0, x, y);

	
            }
         }
      );

      original.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               view.showImage();
            }
         }
      );

      Blur3x3.addActionListener(
	 new ActionListener () {
             public void actionPerformed (ActionEvent e) {
                view.BlurImage(3, LOWPASS3x3);
             }
         }
      );

      Blur5x5.addActionListener(
              new ActionListener () {
                 public void actionPerformed (ActionEvent e) {
                    view.BlurImage(5, LOWPASS5x5);
                 }
              }
      );

      Blur7x7.addActionListener(
              new ActionListener () {
                 public void actionPerformed (ActionEvent e) {
                    view.BlurImage(7, LOWPASS7x7);
                 }
              }
      );

      Blur7x7.addActionListener(
              new ActionListener () {
                 public void actionPerformed (ActionEvent e) {
                    view.BlurImage(9, LOWPASS9x9);
                 }
              }
      );

      Blur11x11.addActionListener(
              new ActionListener () {
                 public void actionPerformed (ActionEvent e) {
                    view.BlurImage(11, LOWPASS11x11);
                 }
              }
      );


   }

   private void buildDisplay () {

      // Build first JPanel
      JPanel controlPanel = new JPanel();
      GridLayout grid = new GridLayout (1, 5);
      controlPanel.setLayout(grid);
      controlPanel.add(original);
      controlPanel.add(Blur3x3);
      controlPanel.add(Blur5x5);
      controlPanel.add(Blur7x7);
      controlPanel.add(Blur9x9);
      controlPanel.add(Blur11x11);

      // Build third JPanel
      JPanel filterPanel = new JPanel();
      grid = new GridLayout (3, 3);
      filterPanel.setLayout(grid);
      for (int i=0; i<filterfield.length; i++)
         filterPanel.add(filterfield[i]);

      // Add panels and image data component to the JFrame
      Container c = this.getContentPane();
      c.add(view, BorderLayout.CENTER);
      c.add(controlPanel, BorderLayout.SOUTH);
      c.add(filterPanel, BorderLayout.NORTH);

   }

   // This method reads an Image object from a file indicated by
   // the string provided as the parameter.  The image is converted
   // here to a BufferedImage object, and that new object is the returned
   // value of this method.
   // The mediatracker in this method can throw an exception

   public BufferedImage readImage (String file) {

      Image image = Toolkit.getDefaultToolkit().getImage(file);
      MediaTracker tracker = new MediaTracker (new Component () {});
      tracker.addImage(image, 0);
      try { tracker.waitForID (0); }
      catch (InterruptedException e) {}
         BufferedImage bim = new BufferedImage 
             (image.getWidth(this), image.getHeight(this), 
             BufferedImage.TYPE_INT_RGB);
      Graphics2D big = bim.createGraphics();
      big.drawImage (image, 0, 0, this);
      return bim;
   }

   // The main method allocates the "window" and makes it visible.
   // The windowclosing event is handled by an anonymous inner (adapter)
   // class
   // Command line arguments are ignored.

   public static void main(String[] argv) {

      JFrame frame = new Exercise4();
      frame.pack();
      frame.setVisible(true);
      frame.addWindowListener (
	 new WindowAdapter () {
	    public void windowClosing ( WindowEvent e) {
		System.exit(0);
	    }
         }
      );
   }

   /*****************************************************************

   This is a helper object, which could reside in its own file, that 
   extends JLabel so that it can hold a BufferedImage

   I've added the ability to apply image processing operators to the
   image and display the result

   ***************************************************************************/

   public class MyImageObj extends JLabel {

      // instance variable to hold the buffered image
      private BufferedImage bim=null;
      private BufferedImage filteredbim=null;

      //  tell the paintcomponent method what to draw 
      private boolean showfiltered=false;

      // Default constructor
      public MyImageObj() {
      }

      // This constructor stores a buffered image passed in as a parameter
      public MyImageObj(BufferedImage img) {
	 bim = img;
         filteredbim = new BufferedImage 
            (bim.getWidth(), bim.getHeight(), BufferedImage.TYPE_INT_RGB);
         setPreferredSize(new Dimension(bim.getWidth(), bim.getHeight()));

         this.repaint();
      }

      // This mutator changes the image by resetting what is stored
      // The input parameter img is the new image;  it gets stored as an
      //     instance variable
      public void setImage(BufferedImage img) {
         if (img == null) return;
         bim = img;
         filteredbim = new BufferedImage 
            (bim.getWidth(), bim.getHeight(), BufferedImage.TYPE_INT_RGB);
         setPreferredSize(new Dimension(bim.getWidth(), bim.getHeight()));
	 showfiltered=false;
         this.repaint();
      }

      // accessor to get a handle to the bufferedimage object stored here
      public BufferedImage getImage() {
         return bim;
      }

      //  apply the blur operator
      public void BlurImage(int dimension, float[] lowpass) {
         if (bim == null) return;
	 Kernel kernel = new Kernel (dimension, dimension, lowpass);
 	 ConvolveOp cop = new ConvolveOp (kernel, ConvolveOp.EDGE_NO_OP, null);

         // make a copy of the buffered image
         BufferedImage newbim = new BufferedImage
             (bim.getWidth(), bim.getHeight(),
             BufferedImage.TYPE_INT_RGB);
         Graphics2D big = newbim.createGraphics();
             big.drawImage (bim, 0, 0, null);

         // apply the filter the copied image 
         // result goes to a filtered copy
         cop.filter(newbim, filteredbim);
         showfiltered=true;
         this.repaint();
      }

      //  show current image by a scheduled call to paint()
      public void showImage() {
         if (bim == null) return;
	 showfiltered=false;
	 this.repaint();
      }

      //  get a graphics context and show either filtered image or
      //  regular image
      public void paintComponent(Graphics g) {
	 Graphics2D big = (Graphics2D) g;
	 if (showfiltered)
            big.drawImage(filteredbim, 0, 0, this);
         else
            big.drawImage(bim, 0, 0, this);
      }
   }

}
