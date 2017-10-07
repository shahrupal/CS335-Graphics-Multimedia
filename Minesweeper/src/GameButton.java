import java.awt.*;
import javax.swing.*;

public class GameButton extends JButton {  //object in form of buttons

    private JButton b;
    private int neighboringBombs;

    //default constructor
    public GameButton(){
        b = new JButton();  //same as "super();"
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
