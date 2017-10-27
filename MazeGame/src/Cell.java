import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Cell extends JPanel {

    private int row, column;

    public Cell(){
        super();
        super.setBackground(Color.BLACK);  //sets cell color to black
        super.setBorder(BorderFactory.createLineBorder(Color.WHITE));  //sets cell borders to white
    }

    public void fillPanel(){
        setBackground(Color.ORANGE);
    }

    public void setCellRow(int r){ row = r; }

    public void setCellColumn(int c){ column = c; }

    public void setDimensions(int r, int c) {
        row = r;
        column = c;
    }

    public int getCellRow(){ return row; }

    public int getCellColumn(){ return column; }

 /*   //"erases" border by coloring given border black
    public void eraseBorder(){

    }
*/
}
