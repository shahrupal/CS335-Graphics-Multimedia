//Source: Dr. Brent Seales
//Rupal Shah

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends JFrame implements ActionListener {

    //creates objects
    private Board gameBoard;
    private Card prevCard1, prevCard2;

    private JPanel boardView, labelView;  //creates panels to help layout buttons and labels
    private JButton restart, quit;  //creates buttons to restart or quit game
    private JLabel timerLabel, attemptLabel, matchLabel;  //creates labels to display the time and number of attempts

    private boolean isFlipped = false;  //keeps track of if card is facing front
    private int flipTimer = 0;  //time of how long incorrect pair of cards will be shown

    private boolean firstCard = true;  //first card to be clicked out of the two being compared

    private Timer gameTimer;  //timer triggers an event every second

    private int timeCounter = 0;  //keeps track of time
    private double attemptCounter = 0;  //keeps track of number of attempts
    private int matchCounter = 0;  //keeps track of number of correctly matched pairs

    public Game() {

        //calls base class constructor
        super("I Swear I'm Not Narcissistic Memory Game");

        //stores and displays text on buttons
        restart = new JButton("Restart");
        quit = new JButton("Quit");

        //stores and displays text on labels
        timerLabel = new JLabel("Timer: 0");
        attemptLabel = new JLabel("Attempts: 0");
        matchLabel = new JLabel("Matches: 0");

        //creates timer that counts every 1 second
        gameTimer = new Timer(1000, this);
        gameTimer.start();

        //add action listeners to the two buttons
        restart.addActionListener(this);
        quit.addActionListener(this);

        //store panels to hold labels and buttons
        labelView = new JPanel();
        boardView = new JPanel();

        //create content pane - everything (panels, buttons, labels, etc.) is added to this
        Container c = getContentPane();

        //create board with 24 cards
        gameBoard = new Board(24, this);

        //set boardView to be a grid and fill grid with cards and add it to board
        boardView.setLayout(new GridLayout(4, 6, 0, 0));
        gameBoard.fillBoard(boardView);

        //add buttons and labels to panel
        labelView.setLayout(new GridLayout(1, 5, 3, 2));  //laysout panel
        labelView.add(quit);
        labelView.add(restart);
        labelView.add(timerLabel);
        labelView.add(attemptLabel);
        labelView.add(matchLabel);

        //add both panels to container and position containers
        c.add(labelView, BorderLayout.NORTH);
        c.add(boardView, BorderLayout.CENTER);

        //set frame size and allow it to be visible
        setSize(745, 600);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        //if the action being performed is the timer (which occurs every 1 second)
        if (e.getSource() == gameTimer) {

            //increment time and output to user
            timeCounter++;
            timerLabel.setText("Timer: " + timeCounter);

            //increment time of how long incorrect flipped cards should be shown for
            if (isFlipped) {
                flipTimer++;
            }

        } else if (e.getSource() == restart) {  //if restart button is clicked

            restartGame();

        } else if (e.getSource() == quit) {  //if quit button is clicked

            System.exit(0);  //exit window

        } else if (!isFlipped) {  //if any other button (only ones left are the cards) is clicked AND card is not showing face

            Card currCard = (Card) e.getSource();  //store card being clicked
            currCard.showFront();  //show front image when card is clicked

            attemptCounter += 1;  //actually counts number of clicks
            if ((attemptCounter % 2) == 0) {  //attempts made = 1/2 number of clicks
                attemptLabel.setText("Attempts: " + (int)(attemptCounter/2));
            }

            if (firstCard == true) {  //if this is the first card being clicked out of the two being compared
                prevCard1 = currCard;
                firstCard = false;  //false because the second card will now be clicked
            } else {  //if card being clicked is second card out of the two being compared
                prevCard2 = currCard;
                firstCard = true;  //next card being clicked will be the first card of the next pair
                isFlipped = true;  //both cards are now facing front
            }
        }

        if (isFlipped) {  //if two cards are front-facing
            if (prevCard1.id() != prevCard2.id()) {  //if the cards are not a match
                if (flipTimer == 3) {  //after three seconds (see first if statement in function)

                    flipTimer = 0;

                    prevCard1.showBack();  //flip over the two cards so that backs are showing
                    prevCard2.showBack();

                    isFlipped = false;  //notify boolean that cards are not facing front anymore
                }
            } else {  //if cards ARE a match

                matchCounter++;  //adds a match to counter
                matchLabel.setText("Matches: " + matchCounter);  //outputs number of matches to user

                prevCard1.setEnabled(false);  //since cards match don't allow user to re-click them
                prevCard2.setEnabled(false);

                isFlipped = false;

                //note: cards would permanently being facing front (if a match) bc the only way they would flip back over
                //is if they weren't a match - see if statement above
                //that is the only place where showBack() is being used in this function - meaning unless in that if statement
                //the cards will not flip back over
            }
        }
        if(matchCounter == 12){
            System.exit(0);  //when game is over, exit window
        }
    }

    private void restartGame() {

        timeCounter = 0;  //reset time
        attemptCounter = 0;  //reset attempts
        matchCounter = 0;  //reset matches

        //output reset of timer and attempts to user
        timerLabel.setText("Timer: 0");
        attemptLabel.setText("Attempts: 0");
        matchLabel.setText("Matches: 0");

        //clear panel (otherwise "cards" - buttons - will double since its being added to previous panel)
        boardView.removeAll();
        Board newBoard = new Board(24, this);  //create new board - class automatically re-shuffles
        newBoard.fillBoard(boardView);  //re-fill the board with panel of cards

    }

    public static void main(String args[]) {

        Game newGame = new Game();
        newGame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

}//end of class
