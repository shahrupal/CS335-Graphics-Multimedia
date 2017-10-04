//Source: Dr. Brent Seales
//For Resizing Image: https://coderanch.com/t/331731/java/Resize-ImageIcon
//Rupal Shah

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board {

    //resource loader
    private ClassLoader loader = getClass().getClassLoader();

    //array to hold all board cards
    private Card cardsArray[];

    public Board(int size, ActionListener AL) {

        //store array of amount "size" Cards
        cardsArray = new Card[size];

        int imageIdx = 1;

        //fill the cardsArray with twelve images - repeat each imagetwice
        for (int i = 0; i < size; i += 2) {

            //load the front image from "images folder"
            String imgPath = "images/front" + imageIdx + ".jpg";
            ImageIcon img = new ImageIcon(loader.getResource(imgPath));

            //resize images to fit board (source located before import statements)
            Image storeImg = img.getImage();
            Image resizeImg = storeImg.getScaledInstance(125, 125, Image.SCALE_SMOOTH);
            img = new ImageIcon(resizeImg);

            //setup two cards at a time - want a repeat of images
            Card c1 = new Card(img);
            Card c2 = new Card(img);

            //add action listeners to "cards" (buttons with images)
            c1.addActionListener(AL);
            c2.addActionListener(AL);

            //add cards to array
            cardsArray[i] = c1;
            cardsArray[i + 1] = c2;

            //set same ID to both cards (so you can identify if cards are a match or not)
            cardsArray[i].setID(imageIdx);
            cardsArray[i + 1].setID(imageIdx);

            //increment to use next image
            imageIdx++;
        }

        /**shuffle cards for a randomized order**/
        boolean used[] = new boolean[size];  //keeps track of which cards have been shuffled
        for (int j = 0; j < size; j++) {
            used[j] = false;  //set all indices to false - no cards have been shuffled yet
        }

        Card temp = new Card();  //will temporarily store card being shuffled

        //for each card in cards array
        //swap it with a random card (that hasn't been used yet)
        for (int k = 0; k < size; k++) {

            int random = (int) (Math.random() * size);
            while (used[random] == true) {  //search for random number until image that hasn't been shuffled yet is found
                random = (int) (Math.random() * size);
            }

            used[random] = true;  //image has now been used

            //switch cards
            temp = cardsArray[k];
            cardsArray[k] = cardsArray[random];
            cardsArray[random] = temp;
        }
    }


    //adds each card to the panel
    public void fillBoard(JPanel view) {
        for (int i = 0; i < cardsArray.length; i++) {
            view.add(cardsArray[i]);
        }
    }


    //flips all cards in array to their back
    public void resetBoard(){
        for(int i = 0; i <cardsArray.length; i++){
            cardsArray[i].showBack();
        }
    }

}//end of class








