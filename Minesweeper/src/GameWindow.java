import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener{

    private JPanel gridButtons;
    private JPanel middlePanel;  //will hold time, number of buttons cleared, and reset

    private JButton reset;
    private JLabel timerLabel;
    private Timer timer;

    private int timeCounter = 0;

    private boolean firstClick = true;  //will allow to start timer at first click

    private GameBoard board;

    private int numRows, numCols, numBombs;

    // ------------ JMENU STUFF --------------- //
    private JMenuBar menuBar;
    private JMenu menu1, menu2;
    private JMenuItem newItem, setupItem, exitItem;
    // ---------------------------------------- //

    private JFrame settings;
    private JPanel difficulty;
    private JPanel custom;
    private JPanel options;
    private JButton easy, medium, hard;
    private JButton apply;
    private JLabel userRowLabel, userColLabel, userBombLabel;
    private JTextField userRows, userColumns, userBombs;
    private String rowsInput, colsInput, bombsInput;

    public GameWindow(){

        super("Minesweeper");

        numRows = 10;
        numCols = 10;
        numBombs = 25;

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

        reset = new JButton("Reset");
        timerLabel = new JLabel("Timer: 0");

        reset.addActionListener(this);

        middlePanel.add(reset);
        middlePanel.add(timerLabel);

        gridButtons = new JPanel();
        gridButtons.setLayout(new GridLayout(numRows, numCols, 0, 0));

        board = new GameBoard(numRows, numCols, numBombs,this);
        board.fillBoard(gridButtons);

        //creates timer that counts every 1 second
        timer = new Timer(1000, this);

        Container c = getContentPane();

        c.add(middlePanel, BorderLayout.NORTH);
        c.add(gridButtons);

        setSize((20 * numCols)+40,(30 * numRows)+40);
        setLocationRelativeTo(null);
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

            userRows.setText("5");
            userColumns.setText("5");
            userBombs.setText("5");
        }

        else if(e.getSource() == medium){
            easy.setForeground(Color.black);
            medium.setForeground(Color.blue);
            hard.setForeground(Color.black);

            userRows.setText("8");
            userColumns.setText("8");
            userBombs.setText("15");
        }

        else if(e.getSource() == hard){
            easy.setForeground(Color.black);
            medium.setForeground(Color.black);
            hard.setForeground(Color.blue);

            userRows.setText("10");
            userColumns.setText("10");
            userBombs.setText("30");
        }

        else if(e.getSource() == apply){

            rowsInput = userRows.getText();
            colsInput = userColumns.getText();
            bombsInput = userBombs.getText();

            System.out.println(rowsInput + " " + colsInput + " " + bombsInput);

            try{
                int r = Integer.parseInt(rowsInput.trim());
                int c = Integer.parseInt(colsInput.trim());
                int b = Integer.parseInt(bombsInput.trim());
                System.out.println(r + " " + c + " " + b);

                if((r < 5 ) || (r > 10) || (c < 5) || (c > 10)){
                    JOptionPane.showMessageDialog(settings, "Number of rows and columns must be between 5 and 10!");
                } else if(b > ((r * c)/2)){
                    JOptionPane.showMessageDialog(settings, "Number of bombs should be less than (row*column)/2");
                }
                else if(b < 1){
                    JOptionPane.showMessageDialog(settings, "Number of bomb must be greater than 1!");
                }
                else{
                    numRows = r;
                    numCols = c;
                    numBombs = b;

                    resetGame();

                }

            }
            catch (NumberFormatException nfe){
                JOptionPane.showMessageDialog(settings, "Only integer inputs are accepted!");
            }

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
                JOptionPane.showMessageDialog(settings, "U LOST UGH THE HUMAN RACE WAS COUNTING ON U - ARE U KIDDING ME????");
                if (firstClick == false) {
                    timer.stop();  //stops timer
                }

            } else {

                //if bomb has no surrounding bombs, show its neighbors
                if (currButton.getSurroundingBombs() == 0) {
                    board.clearNeighbors(currButton);  //function states all cleared buttons have been visited
                }
                else{
                    board.visited(currButton);  //states that button clicked is considered visited
                }

              //  System.out.println(board.numCleared(currButton));

                if(board.gameOver()){
                    JOptionPane.showMessageDialog(settings, "WOWOW U WON SWAG NAILED IT!!!!!!");
                }

            }
        }
    }

    private void resetGame(){

        timeCounter = 0;

        timer.stop();
        timerLabel.setText("Timer: 0");

        gridButtons.removeAll();

        board = new GameBoard(numRows, numCols, numBombs, this);
        gridButtons.setLayout(new GridLayout(numRows, numCols,0,0));
        board.fillBoard(gridButtons);

        gridButtons.revalidate();
        gridButtons.repaint();
        firstClick = true;

        setSize((20 * numCols)+40,(30 * numRows)+40);

    }

    public void settingsFrame(){
        settings = new JFrame("Settings");

        difficulty = new JPanel();
        custom = new JPanel();
        custom.setLayout(new GridLayout(3,2));
        options = new JPanel();

        easy = new JButton("easy");
        medium = new JButton("medium");
        hard = new JButton("hard");

        easy.addActionListener(this);
        medium.addActionListener(this);
        hard.addActionListener(this);

        userRows = new JTextField("8");
        userColumns = new JTextField("8");
        userBombs = new JTextField("15");

        userRowLabel = new JLabel();
        userColLabel = new JLabel();
        userBombLabel = new JLabel();

        userRowLabel.setText("  Enter # of Rows: ");
        userColLabel.setText("  Enter # of Rows: ");
        userBombLabel.setText("  Enter # of Bombs: ");

        apply = new JButton("apply");
        apply.addActionListener(this);

        difficulty.add(easy);
        difficulty.add(medium);
        difficulty.add(hard);

        custom.add(userRowLabel);
        custom.add(userRows);
        custom.add(userColLabel);

        custom.add(userColumns);
        custom.add(userBombLabel);
        custom.add(userBombs);

        options.add(apply);

        settings.add(difficulty,BorderLayout.NORTH);
        settings.add(custom);
        settings.add(options,BorderLayout.SOUTH);

        settings.setLocationRelativeTo(null);
        settings.setSize(300,200);
        settings.setVisible(true);

    }

    public static void main(String args[]){
        GameWindow newGame = new GameWindow();
    }

}
