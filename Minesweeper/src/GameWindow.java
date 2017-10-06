import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener{

    private JPanel gridButtons;
    private GameBoard board;
    private int numRows, numCols;

    public GameWindow(){

        super("Minesweeper");

        numRows = 8;
        numCols = 8;

        gridButtons = new JPanel();
        gridButtons.setLayout(new GridLayout(numRows, numCols, 0, 0));

        board = new GameBoard(numRows, numCols, this);
        board.fillBoard(gridButtons);

        Container c = getContentPane();

        c.add(gridButtons);

        setSize((20 * numCols),(25 * numRows));
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e){ }

    public static void main(String args[]){
        GameWindow newGame = new GameWindow();
    }

}
