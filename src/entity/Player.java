package entity;

import main.GamePanel;
import main.PlayerController;
import main.Utility;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * This class represents the player that the user controls. It uses the playerController to move and this class houses
 * all data on the player.
 */
public class Player extends Entity {


    private PlayerController playerController;


    // USED FOR CAMERA
    private final int screenX;                      //this holds the screen width along the x-axis
    private final int screenY;                      //this holds the screen height along the y-axis


    // OBJECT INTERACTION
    private int numberOfKeys = 0;




    /**
     * This is the constructor for the Player class. It uses an instance of the GamePanel class to implement the update
     * and draw methods to handle all data and drawing of the player instead of doing all that within the GamePanel class itself.
     * It also uses the PlayerController class to handle the player movement from input via the keyboard.
     *
     * @param gamePanel             - Used to implement the update and draw methods for the player
     * @param playerController      - Used to handle the player input from the keyboard
     */
    public Player(GamePanel gamePanel, PlayerController playerController) {
        super(gamePanel);
        this.playerController = playerController;
        player = true;


        //this is for setting the camera to focus around the middle of the screen
        //small offset since the coordinates would otherwise point to the top left corner of the middle tile on the screen
        screenX = (gamePanel.getScreenWidth() / 2)  - (gamePanel.getTileSize()/2);
        screenY = (gamePanel.getScreenHeight() / 2) - (gamePanel.getTileSize()/2);


        //collision
        //these are only hard coded since I didn't feel like doing the math to get these answers
        //one tile is 48x48, so making the collision box a bit smaller is recommended
        collisionBox = new Rectangle(8,16,32,32);
        
        super.CollisionBoxDefaultX = collisionBox.x;
        super.CollisionBoxDefaultY = collisionBox.y;



        setDefaultValues();
        getPlayerImages();
    }


    /**
     * This method sets the player's starting position and speed as default if no values are passed into the method.
     * Note** The values X:0, Y:0 are in the upper left corner of the screen and the screen is set to 768x576.
     */
    public void setDefaultValues(){
        worldX = gamePanel.getTileSize() * 23;
        worldY = gamePanel.getTileSize() * 21;
        speed = 4;
        direction = "down";
        maxLife = 6;
        currentLife = maxLife;
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

            } else if (playerController.isDownPressed()){
                direction = "down";

            } else if (playerController.isLeftPressed()) {
                direction = "left";

            } else if (playerController.isRightPressed()) {
                direction = "right";

            }

            //check tile collision
            collisionOn = false;
            gamePanel.getCollisionHandler().checkTileCollision(this);


            // check object collision
            int objectIndex = gamePanel.getCollisionHandler().checkObjectCollision(this, true);
            pickUpObject(objectIndex);


            // check npc collision
            int npcIndex = gamePanel.getCollisionHandler().checkEntityCollision(this, gamePanel.npcArray);
            interactNPC(npcIndex);

            // check enemy collision
            int enemyIndex = gamePanel.getCollisionHandler().checkEntityCollision(this, gamePanel.enemyArray);
            interactEnemy(enemyIndex);



            // check event collision
            gamePanel.getEventManager().checkEvent();
            playerController.setEnteredPressed(false);


            //if collision false, continue moving
            if(collisionOn == false && playerController.isEnteredPressed() == false){

                switch (direction){
                    case "up":
                        this.worldY -= this.speed;
                        break;

                    case "down":
                        this.worldY += this.speed;
                        break;

                    case "left":
                        this.worldX -= this.speed;
                        break;

                    case "right":
                        this.worldX += this.speed;
                        break;
                }
            }





            //This handles the animation sequence on the data side of things
            spriteAnimationCounter();
        }


        // Invincibility Counter
        if(isInvincible){
            invincibleCounter++;

            if(invincibleCounter > 60){
                invincibleCounter = 0;
                isInvincible = false;
            }
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

        // visual effect for invincibility
        if(isInvincible){
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }

        //Finally, draw image to screen
        graphics2D.drawImage(image, screenX, screenY, null);

        // reset to normal
        if(!isInvincible){
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }



    }


    /**
     * This method handles the object interaction with the player in the instance that a player collides with an object that can be picked up.
     *
     * @param index     - index of the object being picked up in the objects array
     */
    public void pickUpObject(int index){

        // 999 refers to the case that the player has not interacted with an object
        if(index != 999){

            // figure out which object the player is interacting with
            String objectName = gamePanel.objects[index].getName();


            //decide how to interact with the object
            switch (objectName){
                case "Key":
                    numberOfKeys++;

                    // play sound effect
                    gamePanel.playSoundEffect(1);


                    // remove the object from the array
                    gamePanel.objects[index] = null;

                    //notification on ui
                    gamePanel.getUserInterface().displayItemMessage(objectName);

                    break;
                case "Door":

                    //if the player has a key
                    if(numberOfKeys > 0){
                        numberOfKeys--;

                        // play sound effect
                        gamePanel.playSoundEffect(4);


                        // remove the object from the array
                        gamePanel.objects[index] = null;

                        //notification on ui
                        gamePanel.getUserInterface().displayItemMessage(objectName);
                    } else if(numberOfKeys <= 0){
                        gamePanel.getUserInterface().displayLockedMessage(objectName);
                    }

                    break;
                case "Chest":

                    gamePanel.getUserInterface().setGameFinished(true);
                    gamePanel.stopBackgroundMusic(0);
                    gamePanel.playSoundEffect(2);
                    break;
                case "Boots":

                    // Increase movement speed
                    speed += 1.5;

                    // play sound effect
                    gamePanel.playSoundEffect(3);


                    gamePanel.objects[index] = null;

                    //notification on ui
                    gamePanel.getUserInterface().displayItemMessage(objectName);
                    break;
            }

        }
    }


    /**
     * This method handles NPC interaction with the player
     *
     * @param index     - Index of the NPC being interacted with in the NPC array
     */
    public void interactNPC(int index){


        if(index != 999){

            if(gamePanel.getPlayerController().isEnteredPressed() == true){
                gamePanel.setGameState(gamePanel.dialogueState);
                gamePanel.npcArray[index].speak();
            }
        }
    }


    public void interactEnemy(int index){

        if(index != 999){

            String enemyName = gamePanel.enemyArray[index].getName();

            if(isInvincible == false){
                utility.calculateDamage(enemyName, gamePanel);
            }
        }
    }


    public void knockBack(){
        switch (this.direction){
            case "up":
                if(checkKnockBackOB()){
                    worldY += gamePanel.getTileSize();
                }

                break;
            case "down":
                if(checkKnockBackOB()){
                    worldY -= gamePanel.getTileSize();
                }

                break;
            case "left":
                if(checkKnockBackOB()){
                    worldX += gamePanel.getTileSize();
                }

                break;
            case "right":
                if(checkKnockBackOB()){
                    worldX -= gamePanel.getTileSize();
                }

                break;

        }
    }


    /** FIX THIS **/
    public boolean checkKnockBackOB(){

        // use these to find col and row values
        int entityLeftX = this.getWorldX() + this.getCollisionBox().x;
        int entityRightX = this.getWorldX() + this.getCollisionBox().x + this.getCollisionBox().width;
        int entityTopY = this.getWorldY() + this.getCollisionBox().y;
        int entityBottomY = this.getWorldY() + this.getCollisionBox().y + this.getCollisionBox().height;


        // getting the specific tile(s) the collision box is on
        int entityLeftColumn = entityLeftX / gamePanel.getTileSize();
        int entityRightColumn = entityRightX / gamePanel.getTileSize();
        int entityTopRow = entityTopY / gamePanel.getTileSize();
        int entityBottomRow = entityBottomY / gamePanel.getTileSize();


        // this is to check if the entity is colliding with the 1 or 2 tiles it is moving into
        int tile1, tile2;

        switch (this.getDirection()){
            case "up":

                entityTopRow = (entityTopY - this.getSpeed()) / gamePanel.getTileSize();

                //find the int value of the tiles the player is launched into
                tile1 = gamePanel.getTileManager().getMapTileNumbers()[entityLeftColumn][entityTopRow + 1];
                tile2 = gamePanel.getTileManager().getMapTileNumbers()[entityRightColumn][entityTopRow + 1];

                // if the int values of the tiles have collision
                // do not do knock back
                if(gamePanel.getTileManager().getTileSetList()[tile1].isCollision() == true ||
                        gamePanel.getTileManager().getTileSetList()[tile2].isCollision() == true ) {
                    return false;
                }
                break;

            case "down":


                entityBottomRow = (entityBottomY + this.getSpeed()) / gamePanel.getTileSize();

                //find the int value of the tiles the player is moving into
                tile1 = gamePanel.getTileManager().getMapTileNumbers()[entityLeftColumn][entityBottomRow - 1];
                tile2 = gamePanel.getTileManager().getMapTileNumbers()[entityRightColumn][entityBottomRow - 1];

                //if the int values of the tiles have collision
                //set the collision on entity
                if(gamePanel.getTileManager().getTileSetList()[tile1].isCollision() == true ||
                        gamePanel.getTileManager().getTileSetList()[tile2].isCollision() == true) {
                    return false;
                }
                break;

            case "left":


                entityLeftColumn = (entityLeftX - this.getSpeed()) / gamePanel.getTileSize();

                //find the int value of the tiles the player is moving into
                tile1 = gamePanel.getTileManager().getMapTileNumbers()[entityLeftColumn + 1][entityTopRow];
                tile2 = gamePanel.getTileManager().getMapTileNumbers()[entityLeftColumn + 1][entityBottomRow];

                //if the int values of the tiles have collision
                //set the collision on entity
                if(gamePanel.getTileManager().getTileSetList()[tile1].isCollision() == true ||
                        gamePanel.getTileManager().getTileSetList()[tile2].isCollision() == true) {
                    return false;
                }
                break;

            case "right":


                entityRightColumn = (entityRightX + this.getSpeed()) / gamePanel.getTileSize();

                //find the int value of the tiles the player is moving into
                tile1 = gamePanel.getTileManager().getMapTileNumbers()[entityRightColumn - 1][entityTopRow];
                tile2 = gamePanel.getTileManager().getMapTileNumbers()[entityRightColumn - 1][entityBottomRow];

                //if the int values of the tiles have collision
                //set the collision on entity
                if(gamePanel.getTileManager().getTileSetList()[tile1].isCollision() == true ||
                        gamePanel.getTileManager().getTileSetList()[tile2].isCollision() == true) {
                    return false;
                }
                break;
        }

        return true;
    }


    /**
     * This method loads the individual images into their respective images. Since the number of images is small
     * and the images are located in their own files, I opted to just manually assign them. In other cases it would be
     * optimal to use a loop to do this, especially if the images are all in one file.
     */
    public void getPlayerImages(){

        File[] fileNames = new File("res/images/player").listFiles();


        for(int i = 0; i < fileNames.length; i++){


            switch (fileNames[i].getName()) {


               // UP
                case "boy_up_1.png":
                    up1 = instantiateImages(fileNames[i]);
                    break;
                case "boy_up_2.png":
                    up2 = instantiateImages(fileNames[i]);
                    break;


                // DOWN
                case "boy_down_1.png":
                    down1 = instantiateImages(fileNames[i]);
                    break;
                case "boy_down_2.png":
                    down2 = instantiateImages(fileNames[i]);
                    break;


                // LEFT
                case "boy_left_1.png":
                    left1 = instantiateImages(fileNames[i]);
                    break;
                case "boy_left_2.png":
                    left2 = instantiateImages(fileNames[i]);
                    break;


                // RIGHT
                case "boy_right_1.png":
                    right1 = instantiateImages(fileNames[i]);
                    break;
                case "boy_right_2.png":
                    right2 = instantiateImages(fileNames[i]);
                    break;
            }
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



    // GETTERS AND SETTERS
    @Override
    public int getWorldX() {
        return super.getWorldX();
    }

    @Override
    public int getWorldY() {
        return super.getWorldY();
    }

    @Override
    public int getSpeed() {
        return super.getSpeed();
    }

    @Override
    public void setWorldX(int x) {
        super.setWorldX(x);
    }

    @Override
    public void setWorldY(int y) {
        super.setWorldY(y);
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public int getNumberOfKeys() {
        return numberOfKeys;
    }








}
