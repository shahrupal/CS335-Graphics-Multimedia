import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener{

    private JPanel gridButtons;
    private GameBoard board;
    private int numRows, numCols;

    public GameWindow(){

        super("Minesweeper");

        numRows = 15;
        numCols = 15;

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

    public void actionPerformed(ActionEvent e){

        GameButton currButton = (GameButton) e.getSource();
        currButton.showBack();  //when user clicks button, it shows number/bomb

        //if user clicked bomb
        if(currButton.getSurroundingBombs() == -1){
            board.showAllBombs();  //show all the bombs on the board
            currButton.showError();  //show bomb user clicked in red
        }

        //if bomb has no surrounding bombs, show its neighbors
        if(currButton.getSurroundingBombs() == 0){
            board.clearNeighbors(currButton);
        }

    }

    public static void main(String args[]){
        GameWindow newGame = new GameWindow();
    }

}
