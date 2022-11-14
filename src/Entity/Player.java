package Entity;

import main.GamePanel;
import main.PlayerController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This class represents the player that the user controls. It uses the playerController to move and this class houses
 * all data on the player.
 */
public class Player extends Entity {

    GamePanel gamePanel;
    PlayerController playerController;


    /**
     * This is the constructor for the Player class. It uses an instance of the GamePanel class to implement the update
     * and draw methods to handle all data and drawing of the player instead of doing all that within the GamePanel class itself.
     * It also uses the PlayerController class to handle the player movement from input via the keyboard.
     *
     * @param gamePanel             - Used to implement the update and draw methods for the player
     * @param playerController      - Used to handle the player input from the keyboard
     */
    public Player(GamePanel gamePanel, PlayerController playerController) {
        this.gamePanel = gamePanel;
        this.playerController = playerController;

        setDefaultValues();
        getPlayerImages();
    }


    /**
     * This is another constructor for the Player class. This just has extra fields being passed in as opposed to the default constructor.
     *
     * @param gamePanel             - Used to update player data and image drawing
     * @param playerController      - Used to handle player movement from keyboard input
     * @param x                     - Starting value on x-axis in terms of pixels
     * @param y                     - Starting value on y-axis in terms of pixels
     * @param speed                 - Movement speed per frame in terms of pixels
     * @param direction             - Starting direction
     */
    public Player(GamePanel gamePanel, PlayerController playerController, int x, int y, int speed, String direction) {
        this.gamePanel = gamePanel;
        this.playerController = playerController;

        setDefaultValues(x, y, speed, direction);
        getPlayerImages();
    }


    /**
     * This is the update method that handles the changes of the player data. It is called in the update method in the
     * GamePanel class and handles only data pertaining to the player.
     */
    public void update() {



        //This if statement is necessary to stop the animation when there is no keyboard input
        if(playerController.isUpPressed() || playerController.isDownPressed() ||
                playerController.isLeftPressed() || playerController.isRightPressed()){


            //Update the players position based on the keyboard input
            //NOTE** X:0, Y:0 is top left of screen
            //NOTE** Using else if statements does not allow for diagonal movement
            if (playerController.isUpPressed()){
                direction = "up";
                this.y -= this.speed;
            } else if (playerController.isDownPressed()){
                direction = "down";
                this.y += this.speed;
            } else if (playerController.isLeftPressed()) {
                direction = "left";
                this.x -= this.speed;
            } else if (playerController.isRightPressed()) {
                direction = "right";
                this.x += this.speed;
            }


            //This handles the animation sequence on the data side of things
            spriteAnimationCounter();
        }
    }



    /**
     * This method does the drawing of the player onto the game screen after the update. This is called by the GamePanel
     * class.
     * @param graphics2D        - used to draw the player
     */
    public void draw(Graphics2D graphics2D){

        //Reset image before attempting to draw again
        BufferedImage image = null;


        //Load image based on direction player is facing
        //Load correct image in animation sequence for said direction
        switch(direction){
            case "up":
                if(spriteNumber == 1){
                    image = up1;
                }
                if(spriteNumber == 2){
                    image = up2;
                }

                break;
            case "down":
                if(spriteNumber == 1){
                    image = down1;
                }
                if(spriteNumber == 2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNumber == 1){
                    image = left1;
                }
                if(spriteNumber == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNumber == 1){
                    image = right1;
                }
                if(spriteNumber == 2){
                    image = right2;
                }
                break;
        }



        //Finally, draw image to screen
        graphics2D.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }



    /**
     * This method loads the individual images into their respective images. Since the number of images is small
     * and the images are located in their own files, I opted to just manually assign them. In other cases it would be
     * optimal to use a loop to do this, especially if the images are all in one file.
     */
    public void getPlayerImages(){


        //put the images into their respective image arrays
        try {

            up1 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_right_2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * This handles the animation of the player sprite. This is done by setting a counter as a limiter for the animation speed
     * and cycling through the animation sequence. In this case it is switching between sprite number 1 and 2 in the animation sequence.
     *
     * The if statement if(spriteCounter > 12) is the limiter that handles the animation speed. It is important to note that this
     * method gets called every frame, so it gets called 60 times per second which is the current FPS cap.
     */
    public void spriteAnimationCounter(){
        spriteCounter++;
        if(spriteCounter > 12){
            if(spriteNumber == 1){
                spriteNumber = 2;
            } else if (spriteNumber == 2) {
                spriteNumber = 1;
            }


            spriteCounter = 0;
        }
    }



    /**
     * This method sets the player's starting position and speed as default if no values are passed into the method.
     * Note** The values X:0, Y:0 are in the upper left corner of the screen and the screen is set to 768x576.
     */
    @Override
    public void setDefaultValues(){
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }


    /**
     * This method sets the starting position and speed of player given the input values passed into the method.
     * Note** The values X:0, Y:0 are in the upper left corner of the screen and the screen is set to 768x576.
     *
     * @param x         - starting x-axis value
     * @param y         - starting y-axis value
     * @param speed     - number of pixels traversed per frame per key press.
     */
    @Override
    public void setDefaultValues(int x, int y, int speed, String direction){
        super.setDefaultValues(x, y, speed, direction);
    }

    @Override
    public int getX() {
        return super.getX();
    }

    @Override
    public int getY() {
        return super.getY();
    }

    @Override
    public int getSpeed() {
        return super.getSpeed();
    }

    @Override
    public void setX(int x) {
        super.setX(x);
    }

    @Override
    public void setY(int y) {
        super.setY(y);
    }
}
