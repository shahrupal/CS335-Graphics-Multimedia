import java.awt.*;
import javax.swing.*;

public class GameButton extends JButton {  //object in form of buttons

    private JButton b;
    private int neighboringBombs;

    private ClassLoader loader = getClass().getClassLoader();
    private ImageIcon front = new ImageIcon(loader.getResource("images/front.jpg"));
    private Icon back;

    //default constructor
    public GameButton(){
        super();  //creates JButton
    }

    //constructor, setting the image to "front" image
    public GameButton(ImageIcon backImage){

        //creates JButton and sets image
        super();
        back = backImage;

        //resize image to fit button
        Image frontImg = front.getImage();
        Image resizeImg = frontImg.getScaledInstance(20,20,Image.SCALE_SMOOTH);
        front = new ImageIcon(resizeImg);

        //set image to "front" image
        super.setIcon(front);

    }

    //set number of bombs the current position is touching
    public void setSurroundingBombs(int numBombs){
        neighboringBombs = numBombs;
    }

    //access number of bombs the current position is touching
    public int getSurroundingBombs(){
        return neighboringBombs;
    }
}
