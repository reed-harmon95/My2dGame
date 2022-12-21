package main;

import object.Object_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UserInterface {



    GamePanel gamePanel;
    Font screenFont, gameWonFont, gamePauseFont;


    // DISPLAY ITEM MESSAGE
    boolean itemMessageOn = false;
    String itemMessage= "";
    int messageTimer = 0;


    // DIALOGUE
    private String displayDialogue = "";


    // GAME FINISHED
    boolean gameFinished = false;


    // GAME TIMER
    double playTime;
    DecimalFormat playTimeFormat = new DecimalFormat("#0.00");


    // MAIN MENU
    private int mainMenuSelector = 0;

    public UserInterface(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        screenFont = new Font("Arial", Font.PLAIN, 32);
        gameWonFont = new Font("Arial", Font.BOLD, 48);
        gamePauseFont = new Font("Arial", Font.BOLD, 48);

    }



    /**
     * This is the main drawing method for the UserInterface class that handles drawing everything in the class to the screen every frame.
     * This class also handles the different state the game can be in and draws the proper UI according to the game state.
     *
     * Possible game states:
     *          - Play
     *          - Pause
     *          - Dialogue
     *
     *
     * @param graphics2D        - Used to draw graphics to the screen
     */
    public void draw(Graphics2D graphics2D){

        // SET FONTS
        graphics2D.setColor(Color.white);
        graphics2D.setFont(screenFont);


        // TITLE SCREEN STATE
        if(gamePanel.getGameState() == gamePanel.titleScreenState){
            drawTitleScreen(graphics2D);
        }


        // PLAY STATE
        if(gamePanel.getGameState() == gamePanel.playState){


            // DEV OPTIONS
            if(gamePanel.isDevOptions()){
                playTime += (double) 1/60;
                graphics2D.drawString("Time: " + playTimeFormat.format(playTime), gamePanel.getTileSize()*11, 65);
                graphics2D.drawString("(X,Y): " + gamePanel.getPlayer().getWorldX()/gamePanel.getTileSize() + " " + gamePanel.getPlayer().getWorldY()/ gamePanel.getTileSize(), gamePanel.getTileSize()*1, 65);
            }



            // ITEM MESSAGE
            if(itemMessageOn == true){
                graphics2D.setFont(graphics2D.getFont().deriveFont(30F));
                graphics2D.drawString(itemMessage, gamePanel.getTileSize()/2, gamePanel.getTileSize()*5);
                messageTimer++;

                // timer for how long the item message stays on the screen (1.5 seconds)
                if(messageTimer > 90){
                    messageTimer = 0;
                    itemMessage = "";
                    itemMessageOn = false;
                }

            }
        }


        // PAUSE STATE
        if (gamePanel.getGameState() == gamePanel.pauseState) {
            drawPauseState(graphics2D);
        }


        // DIALOGUE STATE
        if(gamePanel.getGameState() == gamePanel.dialogueState) {
            drawDialogueScreen(graphics2D);


        }
    }


    public void drawTitleScreen(Graphics2D graphics2D) {


        // BACKGROUND COLOR
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0,0,gamePanel.getScreenWidth(), gamePanel.getScreenHeight());


        // GAME TITLE
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD,48F));
        String text = "Top-Down Demo Game";
        int x = getXValueForCenteredText(text, graphics2D);
        int y = gamePanel.getTileSize()*3;


        // SHADOW
        graphics2D.setColor(Color.gray);
        graphics2D.drawString(text, x+3, y+3);


        // MAIN TEXT
        graphics2D.setColor(Color.white);
        graphics2D.drawString(text, x, y);


        // PLAYER IMAGE
        x = gamePanel.getScreenWidth()/2 - (gamePanel.getTileSize()*2)/2;
        y += gamePanel.getTileSize()*2;
        graphics2D.drawImage(gamePanel.getPlayer().getDown1(), x, y, gamePanel.getTileSize()*2, gamePanel.getTileSize()*2, null);



        // MENU
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 24F));
        text = "New Game";
        x = getXValueForCenteredText(text, graphics2D);
        y += gamePanel.getTileSize()*3.5;
        graphics2D.drawString(text, x, y);
        if(mainMenuSelector == 0){
            graphics2D.drawString(">", (int) (x - (gamePanel.getTileSize()*0.75)), y);
        }

        text = "Load Game";
        x = getXValueForCenteredText(text, graphics2D);
        y += gamePanel.getTileSize()*0.85;
        graphics2D.drawString(text, x, y);
        if(mainMenuSelector == 1){
            graphics2D.drawString(">", (int) (x - (gamePanel.getTileSize()*0.75)), y);
        }

        text = "Quit";
        x = getXValueForCenteredText(text, graphics2D);
        y += gamePanel.getTileSize()*0.85;
        graphics2D.drawString(text, x, y);
        if(mainMenuSelector == 2){
            graphics2D.drawString(">", (int) (x - (gamePanel.getTileSize()*0.75)), y);
        }

    }


    /**
     * This method handles drawing the UI when the game state is in the paused state.
     *
     * @param graphics2D        - Used for drawing graphics to screen
     */
    public void drawPauseState(Graphics2D graphics2D){

        graphics2D.setFont(gamePauseFont);
        String pausedText = "PAUSED";
        int x;
        int y = gamePanel.getScreenHeight()/2;
        x = getXValueForCenteredText(pausedText, graphics2D);


        graphics2D.drawString(pausedText, x, y);
    }

    /**
     * This method handles drawing the UI to the screen when the game is in the dialogue state which occurs when the player is talking to an NPC.
     *
     * @param graphics2D        - Used to draw graphics to the screen
     */
    public void drawDialogueScreen(Graphics2D graphics2D){

        // WINDOW DIMENSIONS
        int x = gamePanel.getTileSize() * 3;                                            // three tiles from edge of screen
        int y = gamePanel.getTileSize() / 3;                                            // three tiles from edge of screen
        int width = gamePanel.getScreenWidth() - (gamePanel.getTileSize() * 4);
        int height = gamePanel.getTileSize() * 4;

        drawDialogueWindow(x, y, width, height, graphics2D);


        // TEXT
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN,24F));
        x += gamePanel.getTileSize();
        y += gamePanel.getTileSize();


        for(String line : displayDialogue.split("\n")) {
            graphics2D.drawString(line, x, y);
            y += 40;
        }



    }


    /**
     * This method creates the black and white window that represents the dialogue window whenever a player talks to an NPC.
     * Words then later get drawn on top of this
     *
     *
     * @param x                 - X for drawing rectangle
     * @param y                 - Y for drawing rectangle
     * @param width             - Width for drawing rectangle
     * @param height            - Height for drawing rectangle
     * @param graphics2D        - Used to draw to screen
     */
    public void drawDialogueWindow(int x, int y, int width, int height, Graphics2D graphics2D){

        // BLACK BOX
        Color newColor = new Color(0,0,0,120);                                          // last value for opacity of color

        graphics2D.setColor(newColor);
        graphics2D.fillRoundRect(x , y, width, height, 35,35);                               // last two digits for rounding corners


        // WHITE BORDER
        newColor = new Color(255,255,255);
        graphics2D.setColor(newColor);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect(x+5 , y+5, width-10, height-10, 25,25);        //notice how it is drawn around black rect
    }


    /**
     * This just creates a message if the player collides with a game object in the world.
     * This message then gets written on the screen in the draw method of this class
     *
     * @param itemName      - Custom message of what the player just collided with to be written to the screen
     */
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

    /**
     * This method is for determining the x-value when trying to draw strings to the screen with the drawString method.
     * Due to how the drawString method is implemented, in order to center the string along the x-axis,
     * you have to get the length of the string and subtract that from the width of the game screen.
     * In addition, since the font also impacts the size of the string, you must take that into account (which is what the funny stuff is for when calculating length).
     *
     * @param Text          - Text of the string being drawn to screen
     * @param graphics2D    - Used to draw graphics to screen
     *
     * @return              - X-value used in the params of the drawString method
     */
    public int getXValueForCenteredText(String Text, Graphics2D graphics2D){
        int length = (int) graphics2D.getFontMetrics().getStringBounds(Text, graphics2D).getWidth();
        int x = (gamePanel.getScreenWidth()/2) - (length/2);
        return x;
    }



    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public void setDisplayDialogue(String displayDialogue) {
        this.displayDialogue = displayDialogue;
    }

    public int getMainMenuSelector() {
        return mainMenuSelector;
    }

    public void setMainMenuSelector(int mainMenuSelector) {
        this.mainMenuSelector = mainMenuSelector;
    }
}
