package main;

import object.Object_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UserInterface {


    // KEYS DISPLAY
    GamePanel gamePanel;
    Font screenFont, gameWonFont;
    BufferedImage keyImage;


    // DISPLAY ITEM MESSAGE
    boolean itemMessageOn = false;
    String itemMessage= "";
    int messageTimer = 0;


    // GAME FINISHED
    boolean gameFinished = false;


    // GAME TIMER
    double playTime;
    DecimalFormat playTimeFormat = new DecimalFormat("#0.00");

    public UserInterface(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        screenFont = new Font("Arial", Font.PLAIN, 32);
        gameWonFont = new Font("Arial", Font.BOLD, 48);
        Object_Key key = new Object_Key();
        keyImage = key.getImage();
    }


    public void displayItemMessage(String itemName) {


        // Display a message about picking up an item/unlocking door
        if(itemName.equals("Door")){
            itemMessage = "You unlocked a " + itemName;

        } else {
            itemMessage = "You received " + itemName;
        }

        itemMessageOn = true;
    }


    public void displayLockedMessage(String itemName){
        itemMessage = itemName + " is locked";
        itemMessageOn = true;
    }


    public void draw(Graphics2D graphics2D){

        if(gameFinished == true){

            graphics2D.setFont(gameWonFont);
            graphics2D.setColor(Color.white);

            // centering the font on the screen
            // centering above player head
            String gameFinishedText = "You won!";
            int textLength = (int) graphics2D.getFontMetrics().getStringBounds(gameFinishedText, graphics2D).getWidth();
            int x = (gamePanel.getScreenWidth()/2) - textLength/2;
            int y = gamePanel.getScreenHeight()/2 - (gamePanel.getTileSize()*2);

            graphics2D.drawString(gameFinishedText, x, y);


            gameFinishedText = "Congratulations!";
            textLength = (int) graphics2D.getFontMetrics().getStringBounds(gameFinishedText, graphics2D).getWidth();
            x = (gamePanel.getScreenWidth()/2) - textLength/2;
            y = gamePanel.getScreenHeight()/2 + (gamePanel.getTileSize()*2);
            graphics2D.drawString(gameFinishedText, x, y);

            gameFinishedText = "Play Time: " + playTimeFormat.format(playTime);
            textLength = (int) graphics2D.getFontMetrics().getStringBounds(gameFinishedText, graphics2D).getWidth();
            x = (gamePanel.getScreenWidth()/2) - textLength/2;
            y = gamePanel.getScreenHeight()/2 + (gamePanel.getTileSize()*4);
            graphics2D.drawString(gameFinishedText, x, y);

            gamePanel.gameThread = null;


        }else {
            graphics2D.setFont(screenFont);
            graphics2D.setColor(Color.white);
            graphics2D.drawImage(keyImage, gamePanel.getTileSize()/2, gamePanel.getTileSize()/2, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
            graphics2D.drawString("x " + gamePanel.getPlayer().getNumberOfKeys(), 74, 65);


            // TIMER
            playTime += (double) 1/60;
            graphics2D.drawString("Time: " + playTimeFormat.format(playTime), gamePanel.getTileSize()*11, 65);


            // ITEM MESSAGE
            if(itemMessageOn == true){
                graphics2D.setFont(graphics2D.getFont().deriveFont(30F));
                graphics2D.drawString(itemMessage, gamePanel.getTileSize()/2, gamePanel.getTileSize()*5);
                messageTimer++;


                if(messageTimer > 90){
                    messageTimer = 0;
                    itemMessage = "";
                    itemMessageOn = false;
                }

            }
        }

    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }
}
