import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener{

    private JPanel gridButtons;  //will hold the actual minesweeper game board
    private JPanel middlePanel;  //will hold time, number of buttons cleared, and reset

    private JButton reset;
    private ClassLoader loader = getClass().getClassLoader();

    private JLabel timerLabel;
    private Timer timer;

    private int timeCounter = 0;

    private boolean firstClick = true;  //will allow to start timer at first click

    private GameBoard board;

    private int numRows, numCols, numBombs;

    // ------------ JMENU STUFF --------------- //
    private JMenuBar menuBar;
    private JMenu menu1;
    private JMenuItem menu2;
    private JMenuItem newItem, setupItem, exitItem;
    // ---------------------------------------- //

    private JFrame settings;  //pop-up with setting options
    private JPanel difficulty;  //will hold difficulty levels on settings pop-up
    private JPanel custom;  //will hold custom dimensions on settings pop-up
    private JPanel options;  //will hold "apply" buttons on settings pop-up
    private JButton easy, medium, hard;  //difficulties
    private JButton apply;  //applies new dimensions
    private JLabel userRowLabel, userColLabel, userBombLabel;  //outputs request for user to enter dimensions
    private JTextField userRows, userColumns, userBombs;  //outputs text box to hold user's inputs
    private String rowsInput, colsInput, bombsInput;  //will store user's inputs

    /*CONSTRUCTOR*/
    public GameWindow(){

        super("Minesweeper");  //sets window title

        //sets default dimensions
        numRows = 10;
        numCols = 10;
        numBombs = 25;

        //creates menu buttons
        menuBar = new JMenuBar();
        menu1 = new JMenu("Game");
        menu2 = new JMenuItem("Help");
        menu2.addActionListener(this);

        //creates drop-down on menu buttons
        newItem = new JMenuItem("New");
        setupItem = new JMenuItem("Setup");
        exitItem = new JMenuItem("Exit");

        //adds action listeners to drop-down items
        newItem.addActionListener(this);
        setupItem.addActionListener(this);
        exitItem.addActionListener(this);

        //adds drop-down items to menu button
        menu1.add(newItem);
        menu1.add(setupItem);
        menu1.add(exitItem);

        //adds menus to menu bar
        menuBar.add(menu1);
        menuBar.add(menu2);
        setJMenuBar(menuBar);

        //creates new panel - will hold reset and timer
        middlePanel = new JPanel();

        //creates reset button
        btnResetImg();

        //creates timer label
        timerLabel = new JLabel("Timer: 0");

        //adds action listener to reset button
        reset.addActionListener(this);

        //physically adds reset button and timer label to panel
        middlePanel.add(reset);
        middlePanel.add(timerLabel);

        //creates new panel, with grid layout (used to hold minesweeper buttons)
        gridButtons = new JPanel();
        gridButtons.setLayout(new GridLayout(numRows, numCols, 0, 0));

        //creates new board with above default values and fills the board with specified number of buttons and bombs
        board = new GameBoard(numRows, numCols, numBombs,this);
        board.fillBoard(gridButtons);

        //creates timer that counts every 1 second
        timer = new Timer(1000, this);

        Container c = getContentPane();

        //adds all panels to frame
        c.add(middlePanel, BorderLayout.NORTH);
        c.add(gridButtons);


        setSize(numCols*24,115 + (numRows*22));  //sets dimensions
        setLocationRelativeTo(null);  //sets window to show up in middle of screen
        setVisible(true);  //allows user to view window
        setDefaultCloseOperation(EXIT_ON_CLOSE);  //exits window when "x" is clicked
    }

    /*ACTIONS*/
    public void actionPerformed(ActionEvent e){

        //increment timer label every second (so user can visibly see timer changing)
        if(e.getSource() == timer){
            timeCounter++;
            timerLabel.setText("Timer: " + timeCounter);
        }

        //if user presses reset button, call reset function
        else if(e.getSource() == reset){
            resetGame();
        }

        //if user presses help button, show instructions
        else if(e.getSource() == menu2){
            JOptionPane.showMessageDialog(null, helpHTML());
        }

        //if user presses new button, call reset function
        else if(e.getSource() == newItem){
            resetGame();
        }

        //if user presses setup button, call settings function
        else if(e.getSource() == setupItem){
            settingsFrame();
        }

        //if user presses exit button, close out of window
        else if(e.getSource() == exitItem){
            System.exit(0);
        }

        //if user presses "easy" difficulty (on settings pop-up)
        else if(e.getSource() == easy){

            easy.setForeground(Color.blue);  //show text on easy button to be blue
            medium.setForeground(Color.black);  //reset text on medium button to be black
            hard.setForeground(Color.black);  //reset text on hard button to be black

            //automatically fill custom fields with pre-determined dimensions of easy difficulty
            userRows.setText("5");
            userColumns.setText("5");
            userBombs.setText("5");
        }

        //if user presses "medium" difficulty (on settings pop-up)
        else if(e.getSource() == medium){
            easy.setForeground(Color.black);  //reset text on easy button to be black
            medium.setForeground(Color.blue);  //show text on medium button to be blue
            hard.setForeground(Color.black);  //reset text on hard button to be black

            //automatically fill custom fields with pre-determined dimensions of medium difficulty
            userRows.setText("8");
            userColumns.setText("8");
            userBombs.setText("15");
        }

        //if user presses "hard" difficulty (on settings pop-up)
        else if(e.getSource() == hard){
            easy.setForeground(Color.black);  //reset text on easy button to be black
            medium.setForeground(Color.black);  //reset text on medium button to be black
            hard.setForeground(Color.blue);  //show text on hard button to be blue

            //automatically fill custom fields with pre-determined dimensions of hard difficulty
            userRows.setText("10");
            userColumns.setText("10");
            userBombs.setText("30");
        }

        //if user presses "apply button (on settings pop-up)
        else if(e.getSource() == apply){

            //store user inputs for dimensions and bomb number
            rowsInput = userRows.getText();
            colsInput = userColumns.getText();
            bombsInput = userBombs.getText();

            try{

                int r = Integer.parseInt(rowsInput.trim());  //converts user's input for num of rows to int
                int c = Integer.parseInt(colsInput.trim());  //converts user's input for num of columns to int
                int b = Integer.parseInt(bombsInput.trim());  //converts user's input for num of bombs to int

                //make sure num of rows and columns are between 5 and 10 - output pop-up warning, if not
                if((r < 5 ) || (r > 10) || (c < 5) || (c > 10)){
                    JOptionPane.showMessageDialog(settings, "Number of rows and columns must be between 5 and 10!");

                //make sure num of bombs is less than half the number of tiles on the board - output pop-up warning, if not
                } else if(b > ((r * c)/2)){
                    JOptionPane.showMessageDialog(settings, "Number of bombs should be less than (row*column)/2");
                }

                //make sure the num of bombs is greater than 1 - output pop-up warning, if not
                else if(b < 1){
                    JOptionPane.showMessageDialog(settings, "Number of bombs must be greater than 1!");
                }

                //otherwise, inputs should be valid (if not thrown to catch statement)
                else{

                    //set variables to user's inputs of specified dimensions
                    numRows = r;
                    numCols = c;
                    numBombs = b;

                    //reset game with new dimensions
                    resetGame();

                }

            }

            //if the try statement causes and error in that the user's inputs are not integers, output pop-up with warning
            catch (NumberFormatException nfe){
                JOptionPane.showMessageDialog(settings, "Only integer inputs are accepted!");
            }

        }

        //no other options to click on board, OTHER THAN actual minesweeper game
        else {

            GameButton currButton = (GameButton) e.getSource();  //cast button clicked as a GameButton
            currButton.showBack();  //when user clicks button, it shows number/bomb


            //store position of first click
            int firstRowPos = board.getRowPosition(currButton);
            int firstColPos = board.getColPosition(currButton);

            //PREVENTS FIRST CLICK BEING A BOMB
           while((firstClick == true) && (currButton.getSurroundingBombs() == -1)){  //if first click is a bomb
                if(!board.isFirstBomb(currButton, firstRowPos, firstColPos)){  //if new current position is NOT a bomb
                    currButton = board.newCurrent();  //make this the new current position
                    currButton.showBack();  //show the back of this button
                    break;
                }

                //reset - this will create a new board
               //do this until the position of the first click is not a bomb
                resetGame();
            }


            //if first click of game (and if it's not a bomb), start timer
            if ((firstClick == true) && (currButton.getSurroundingBombs() != -1)) {
                timer.start();
                firstClick = false;
            }

            //if user clicked bomb
            if (currButton.getSurroundingBombs() == -1) {

                board.showAllBombs();  //show all the bombs on the board
                currButton.showError();  //show bomb user clicked in red

                //output game over message
                JOptionPane.showMessageDialog(settings, "You lost!");

                //stop timer
                if (firstClick == false) {
                    timer.stop();
                }

                //after bomb is clicked, does not allow user to continue playing game
                board.removeGridAL(this);
            }
            else {

                //if bomb has no surrounding bombs, show its neighbors
                if (currButton.getSurroundingBombs() == 0) {
                    board.clearNeighbors(currButton);  //function also states all cleared buttons have been visited
                }
                else{
                    board.visited(currButton);  //states that button clicked is considered visited
                }

                //if the only non-visited buttons that are left are bombs, output win pop-up
                if(board.gameOver()){
                    JOptionPane.showMessageDialog(settings, "You won!");
                }

            }
        }
    }

    /*RESET GAME*/
    private void resetGame(){

        timeCounter = 0;  //reset time shown on label

        timer.stop();  //stop timer
        timerLabel.setText("Timer: 0");  //output restart of time

        gridButtons.removeAll();  //remove all buttons from panel

        board = new GameBoard(numRows, numCols, numBombs, this);  //creates new board with new dimensions (if applicable - stays the same otherwise)
        gridButtons.setLayout(new GridLayout(numRows, numCols,0,0));  //lays out buttons according to new dimensions (if applicable - stays the same otherwise)
        board.fillBoard(gridButtons);  //re-fills the board with new dimensions (if applicable - stays the same otherwise)

        //re-creates panel
        gridButtons.revalidate();
        gridButtons.repaint();

        firstClick = true;  //resets firstClick to true

        //resets size of window, according to new dimensions (if applicable - stays the same otherwise)
        setSize(numCols*24,115 + (numRows*22));
    }

    /*SETTINGS FRAME*/
    //when "setup" button is clicked, calls this function
    //creates window and sets to visible in this function (creates sensation of pop-up)
    public void settingsFrame(){

        settings = new JFrame("Settings");  //set window title

        //create panels to hold difficulty buttons, customizable dimensions text fields, and apply button
        difficulty = new JPanel();
        custom = new JPanel();
        custom.setLayout(new GridLayout(3,2));  //sets grid layout of the 3 difficulty buttons
        options = new JPanel();

        //creates text on difficulty buttons
        easy = new JButton("easy");
        medium = new JButton("medium");
        hard = new JButton("hard");

        //set background colors of the buttons
        easy.setBackground(new Color(247,98,98));
        medium.setBackground(new Color(247,98,98));
        hard.setBackground(new Color(247,98,98));

        //adds action listeners to each difficulty button
        easy.addActionListener(this);
        medium.addActionListener(this);
        hard.addActionListener(this);

        //sets default values for customizable dimensions text fields
        userRows = new JTextField("8");
        userColumns = new JTextField("8");
        userBombs = new JTextField("15");

        //creates labels to request user to enter new dimensions
        userRowLabel = new JLabel();
        userColLabel = new JLabel();
        userBombLabel = new JLabel();

        userRowLabel.setText("    Enter # of Rows: ");
        userColLabel.setText("    Enter # of Rows: ");
        userBombLabel.setText("   Enter # of Bombs: ");

        //creates apply button and adds action listener to it
        apply = new JButton("apply");
        apply.addActionListener(this);

        //adds difficulty buttons to panel
        difficulty.add(easy);
        difficulty.add(medium);
        difficulty.add(hard);

        //adds request labels and text fields to panel
        custom.add(userRowLabel);
        custom.add(userRows);
        custom.add(userColLabel);
        custom.add(userColumns);
        custom.add(userBombLabel);
        custom.add(userBombs);

        //adds apply button to panel
        options.add(apply);

        settings.add(difficulty, BorderLayout.NORTH);  //adds panel to top of window
        settings.add(custom);  //adds panel to center of window
        settings.add(options, BorderLayout.SOUTH);  //adds panel to bottom of window

        settings.setLocationRelativeTo(null);  //sets window to appear in middle of screen
        settings.setSize(250,200);  //sets dimensions of "pop-up" window
        settings.setVisible(true);  //allows user to see window

    }

    //adds smiley image to reset button
    public void btnResetImg(){
        //add smiley image to reset button
        ImageIcon img = new ImageIcon(loader.getResource("images/smiley.jpg"));

        //resize image to fit button
        Image storeImg = img.getImage();
        Image resizeImg = storeImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        img = new ImageIcon(resizeImg);

        //adds image to button and resizes button
        reset = new JButton(img);
        reset.setPreferredSize(new Dimension(40,40));
    }

    //instructions on how to play the game
    public String helpHTML(){
        String helpmsg = "<html>" +
                "How To Play:" +
                "<ul>" +
                "  <li>Each square on the board is a button. When clicked, some will contain bombs.</li>" +
                "  <li>The squares not containing bombs will contain numbers - these indicate the number of bombs that specific square is touching.</li>" +
                "  <li>If a bomb is clicked, the game is over. To win, you must click on ALL squares of the board, EXCEPT ones with the bombs. </li>" +
                "</ul>" +
                "</html>";
        return helpmsg;
    }

    /*MAIN - CREATES NEW GAME*/
    public static void main(String args[]){
        GameWindow newGame = new GameWindow();
    }

}
