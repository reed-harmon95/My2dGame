package Entity;

import main.GamePanel;
import main.PlayerController;

import java.awt.*;

/**
 * This class represents the player that the user controls. It uses the playerController to move and this class houses
 * all data on the player.
 */
public class Player extends Entity {

    GamePanel gamePanel;
    PlayerController playerController;


    public Player(GamePanel gamePanel, PlayerController playerController) {
        this.gamePanel = gamePanel;
        this.playerController = playerController;

        setDefaultValues();
    }

    public Player(GamePanel gamePanel, PlayerController playerController, int x, int y, int speed) {
        this.gamePanel = gamePanel;
        this.playerController = playerController;

        setDefaultValues(x, y, speed);
    }


    public void update() {

        //Update the players position based on the keyboard input
        //NOTE** X:0, Y:0 is top left of screen
        //NOTE** Using else if statements does not allow for diagonal movement
        if (playerController.isUpPressed()){
            this.y -= this.speed;
        } else if (playerController.isDownPressed()){
            this.y += this.speed;
        } else if (playerController.isLeftPressed()) {
            this.x -= this.speed;
        } else if (playerController.isRightPressed()) {
            this.x += this.speed;
        }
    }

    public void draw(Graphics2D graphics2D){

        //drawing sample character(white rectangle)
        graphics2D.setColor(Color.white);
        graphics2D.fillRect(this.x, this.y, gamePanel.tileSize, gamePanel.tileSize);
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
    public void setDefaultValues(int x, int y, int speed){
        super.setDefaultValues(x, y, speed);
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
