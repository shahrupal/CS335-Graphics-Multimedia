// Uploading an Image: https://stackoverflow.com/questions/13516939/how-to-upload-and-display-image-in-jframe-using-jfilechooser

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class Images extends JPanel {


    public Images(){

        setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        setBackground(Color.PINK);

    }

    public void selectImage(){

        JFileChooser browse = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int approve = browse.showOpenDialog(null);
        if(approve == JFileChooser.APPROVE_OPTION){

            File selected = browse.getSelectedFile();
            System.out.println(selected.getAbsolutePath());

            BufferedImage buffer;

            try {
                buffer = ImageIO.read(selected);
                ImageIcon image = new ImageIcon(buffer);
                

                JLabel label = new JLabel();
                label.setIcon(image);
                add(label);
                revalidate();
                repaint();
            }
            catch(IOException e) {
            }



            }
        }


    }


