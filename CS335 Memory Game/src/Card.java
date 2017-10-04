//Source: Dr. Brent Seales
//For Resizing Image: https://coderanch.com/t/331731/java/Resize-ImageIcon
//Rupal Shah

import java.awt.*;
import javax.swing.*;

public class Card extends JButton {

    //resource loader
    private ClassLoader loader = getClass().getClassLoader();

    //front and back card images
    private Icon front;
    private ImageIcon back = new ImageIcon(loader.getResource("images/back.jpg"));

    private int id;

    //default constructor
    public Card(){ super(); }

    //constructor that sets the image to the "back" image
    public Card(ImageIcon frontImage){
        super();
        front = frontImage;

        //resize image to fit buttons (source located before import statements)
        Image backImg = back.getImage();
        Image resizeImg = backImg.getScaledInstance(150,150,Image.SCALE_SMOOTH);
        back = new ImageIcon(resizeImg);

        //sets image to "back" image
        super.setIcon(back);
    }

    //card flipping functions
    public void showFront(){ super.setIcon(front); }
    public void showBack(){ super.setIcon(back); }

    //keep track of id's so you know which pairs match
    public int id(){ return id; }
    public void setID(int i){ id = i; }

}
