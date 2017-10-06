import java.awt.*;
import javax.swing.*;

public class GameButton extends JButton {  //object in form of buttons

    private JButton b;
    private int neighboringBombs;

    //default constructor
    public GameButton(){
        b = new JButton();  //same as "super();"
    }


    public void setSurroundingBombs(int numBombs){
        neighboringBombs = numBombs;
    }

    public int getSurroundingBombs(){
        return neighboringBombs;
    }
}
