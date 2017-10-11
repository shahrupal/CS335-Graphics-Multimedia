import java.awt.*;
import javax.swing.*;

public class GameButton extends JButton {  //object in form of buttons

    private JButton b;
    private int neighboringBombs;

    private ClassLoader loader = getClass().getClassLoader();
    private ImageIcon error = new ImageIcon(loader.getResource("images/error.jpg"));
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
        Image resizeImg = frontImg.getScaledInstance(22,22,Image.SCALE_SMOOTH);
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

    //"show" (set) image of JButton to front (blank button)
    public void showFront(){
        super.setIcon(front);
    }

    //"show" (set) image of JButton to number/bomb
    public void showBack(){
        super.setIcon(back);
    }

    //show red bomb to convey button user clicked was a bomb
    public void showError(){

        //resize image to fit button
        Image errorImg = error.getImage();
        Image resizeImg = errorImg.getScaledInstance(20,20,Image.SCALE_SMOOTH);
        error = new ImageIcon(resizeImg);

        //set image to new resized error image
        super.setIcon(error);
    }

}
