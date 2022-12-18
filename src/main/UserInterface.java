package main;

import object.Object_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UserInterface {


    // KEYS DISPLAY
    GamePanel gamePanel;
    Font screenFont, gameWonFont, gamePauseFont;
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
        gamePauseFont = new Font("Arial", Font.BOLD, 48);


        Object_Key key = new Object_Key(gamePanel);
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

        //set the fonts
        graphics2D.setColor(Color.white);
        graphics2D.setFont(screenFont);

        if(gamePanel.getGameState() == gamePanel.playState){

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
        } else if (gamePanel.getGameState() == gamePanel.pauseState) {

            drawPauseState(graphics2D);


        }
    }

    public void drawPauseState(Graphics2D graphics2D){

        graphics2D.setFont(gamePauseFont);
        String pausedText = "PAUSED";
        int x;
        int y = gamePanel.getScreenHeight()/2;
        x = getXValueForCenteredText(pausedText, graphics2D);


        graphics2D.drawString(pausedText, x, y);
    }


    public int getXValueForCenteredText(String Text, Graphics2D graphics2D){
        int length = (int) graphics2D.getFontMetrics().getStringBounds(Text, graphics2D).getWidth();
        int x = (gamePanel.getScreenWidth()/2) - (length/2);
        return x;
    }


    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }
}
