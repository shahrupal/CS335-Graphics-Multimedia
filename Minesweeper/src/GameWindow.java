import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener{

    private JPanel gridButtons;
    private JPanel topPanel;  //will hold "game" and "help" drop-downs
    private JPanel middlePanel;  //will hold time, number of buttons cleared, and reset

    private JButton reset;
    private JLabel timerLabel, clearedLabel;
    private Timer timer;

    private int timeCounter = 0, clearedCounter = 0;

    private boolean firstClick = true;  //will allow to start timer at first click

    private GameBoard board;

    private int numRows, numCols;

    // ------------ JMENU STUFF --------------- //
    private JMenuBar menuBar;
    private JMenu menu1, menu2;
    private JMenuItem newItem, setupItem, exitItem;
    // ---------------------------------------- //

    private JFrame settings;
    private JPanel difficulty;
    private JButton easy, medium, hard;

    public GameWindow(){

        super("Minesweeper");

        numRows = 15;
        numCols = 15;

        menuBar = new JMenuBar();
        menu1 = new JMenu("Game");
        menu2 = new JMenu("Help");

        newItem = new JMenuItem("New");
        newItem.addActionListener(this);

        setupItem = new JMenuItem("Setup");
        setupItem.addActionListener(this);

        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(this);

        menu1.add(newItem);
        menu1.add(setupItem);
        menu1.add(exitItem);

        menuBar.add(menu1);
        menuBar.add(menu2);
        setJMenuBar(menuBar);

        middlePanel = new JPanel();

        timerLabel = new JLabel("Timer: 0");
        clearedLabel = new JLabel("Cleared: 0");
        reset = new JButton("RESET");

        reset.addActionListener(this);

        middlePanel.add(timerLabel);
        middlePanel.add(reset);
        middlePanel.add(clearedLabel);


        gridButtons = new JPanel();
        gridButtons.setLayout(new GridLayout(numRows, numCols, 0, 0));

        board = new GameBoard(numRows, numCols, this);
        board.fillBoard(gridButtons);

        //creates timer that counts every 1 second
        timer = new Timer(1000, this);

        Container c = getContentPane();

        c.add(middlePanel, BorderLayout.NORTH);
        c.add(gridButtons);

        setSize((20 * numCols),(25 * numRows));
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e){

        if(e.getSource() == timer){
            timeCounter++;
            timerLabel.setText("Timer: " + timeCounter);
        }

        else if(e.getSource() == reset){
            resetGame();
        }

        else if(e.getSource() == newItem){
            resetGame();
        }

        else if(e.getSource() == setupItem){
            settingsFrame();
        }

        else if(e.getSource() == exitItem){
            System.exit(0);
        }

        else if(e.getSource() == easy){
            easy.setForeground(Color.blue);
            medium.setForeground(Color.black);
            hard.setForeground(Color.black);
        }

        else if(e.getSource() == medium){
            easy.setForeground(Color.black);
            medium.setForeground(Color.blue);
            hard.setForeground(Color.black);
        }

        else if(e.getSource() == hard){
            easy.setForeground(Color.black);
            medium.setForeground(Color.black);
            hard.setForeground(Color.blue);
        }

        else {
            GameButton currButton = (GameButton) e.getSource();
            currButton.showBack();  //when user clicks button, it shows number/bomb

            //if first click of game, start timer
            if ((firstClick == true) && (currButton.getSurroundingBombs() != -1)) {
                timer.start();
                firstClick = false;  //to stop from re-starting
            }

            //if user clicked bomb
            if (currButton.getSurroundingBombs() == -1) {
                board.showAllBombs();  //show all the bombs on the board
                currButton.showError();  //show bomb user clicked in red
                if (firstClick == false) {
                    timer.stop();  //stops timer
                }

            } else {

                //if bomb has no surrounding bombs, show its neighbors
                if (currButton.getSurroundingBombs() == 0) {
                    board.clearNeighbors(currButton);
                }

            }
        }
    }

    private void resetGame(){

        timeCounter = 0;
        clearedCounter = 0;
       // board.resetClearedCounter();

        timer.stop();
        timerLabel.setText("Timer: 0");
        clearedLabel.setText("Cleared: 0");

        gridButtons.removeAll();
        board = new GameBoard(numRows, numCols, this);
        board.fillBoard(gridButtons);
        firstClick = true;

    }

    public void settingsFrame(){
        settings = new JFrame("Settings");

        easy = new JButton("easy");
        medium = new JButton("medium");
        hard = new JButton("hard");

        easy.addActionListener(this);
        medium.addActionListener(this);
        hard.addActionListener(this);

        difficulty = new JPanel();

        difficulty.add(easy);
        difficulty.add(medium);
        difficulty.add(hard);

        settings.add(difficulty);

        settings.setSize(300,200);
        settings.setVisible(true);

    }

    public static void main(String args[]){
        GameWindow newGame = new GameWindow();
    }

}
