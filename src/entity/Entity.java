package entity;

import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class represents an entity which can be the player or NPC.
 * This class is abstract as it is mainly just a parent class to hold shared data between the player and NPC classes.
 *
 */
public abstract class Entity {

    protected int worldX, worldY;                                                        // coordinates of the entity in relation to the world map
    protected int speed;                                                                 // movement speed of entity;
    protected String name = "";



    //IMAGE AND ANIMATION DATA
    //NOTE** Change to array when I have time
    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;        //creates buffered images to hold walking animation images
    protected String direction = "down";                                                          //holds the direction the entity is facing in order to load the correct image array
    protected int spriteCounter = 0;
    protected int spriteNumber = 1;
    protected int actionCounter = 0;
    protected boolean player = false;


    // COLLISION
    public Rectangle collisionBox = new Rectangle(0,0,48,48);
    protected boolean collisionOn = false;
    protected int CollisionBoxDefaultX, CollisionBoxDefaultY;                           // these are used to reset the default x/y values of the collision box for an entity when interacting with an object
    public boolean isInvincible = false;
    protected int invincibleCounter = 0;
    protected int entityType;
    Utility utility;


    // DIALOGUE
    protected String dialogues[] = new String[20];
    protected int dialogueIndex = 0;


    // CHARACTER STATUS
    public int maxLife;
    public int currentLife;


    // OBJECTS
    protected BufferedImage image, image2, image3;
    protected boolean collision = false;


    protected GamePanel gamePanel;

    public Entity(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        this.utility = new Utility();
    }



    public BufferedImage instantiateImages(File fileName){

        Utility utilityTool = new Utility();
        BufferedImage image = null;

        try{


            image = ImageIO.read(new File(fileName.getPath()));
            image = utilityTool.scaleImage(image, gamePanel.getTileSize(), gamePanel.getTileSize());

        } catch(IOException e){
            e.printStackTrace();
        }

        return image;
    }


    public void setAction(){

    }

    public void setDialogue(){

    }



    public void speak(){
        facePlayer();
        gamePanel.getUserInterface().setDisplayDialogue(dialogues[dialogueIndex]);
        if(dialogues[dialogueIndex+1] !=null){
            dialogueIndex++;
        }
    }

    public void facePlayer(){
        switch(gamePanel.getPlayer().getDirection()){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    public void update(){


        setAction();


        // NPC COLLISION
        collisionOn = false;
        gamePanel.getCollisionHandler().checkTileCollision(this);
        gamePanel.getCollisionHandler().checkObjectCollision(this, false);
        gamePanel.getCollisionHandler().checkEntityCollision(this, gamePanel.npcArray);
        gamePanel.getCollisionHandler().checkEntityCollision(this, gamePanel.enemyArray);
        boolean hitPlayer = gamePanel.getCollisionHandler().checkNpcCollisionWithPlayer(this);

        if(this.entityType == 2 && hitPlayer){
            if(gamePanel.getPlayer().isInvincible == false){
                utility.calculateDamage(this.name, gamePanel);
            }
        }
        //if collision false, continue moving
        if(collisionOn == false){


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

    public void draw(Graphics2D graphics2D) {

        //get the screen's position on the world map (which surrounds the player's position)
        int currScreenX = worldX- gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX();
        int currScreenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();


        //this ensures that the only part of the world map being drawn to the screen is the area that is immediately surrounding the player
        //this slightly improves rendering efficiency since parts of the map the player isn't currently at are not being drawn every frame
        // ** IF YOU DO NOT UNDERSTAND. REMOVE THE +- gamePanel.getTileSize () FROM THE IF STATEMENT FOR VISUAL UNDERSTANDING **
        if(worldX + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getScreenX() &&
                worldX - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX() &&
                worldY  + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getScreenY() &&
                worldY  - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY()) {


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
            graphics2D.drawImage(image, currScreenX, currScreenY, null);


            // DEV OPTIONS
            
            // this displays the hitbox of the entity
            if(gamePanel.isDevOptions()){
                Color newColor = new Color(255,0,0);
                graphics2D.setColor(newColor);
                graphics2D.setStroke(new BasicStroke(3));
                //graphics2D.drawRoundRect(x+5 , y+5, width-10, height-10, 25,25);
                graphics2D.drawRect(currScreenX + this.collisionBox.x ,
                        currScreenY + this.collisionBox.y,
                        this.collisionBox.width , this.collisionBox.height);

                // this displays the entity's coords
                graphics2D.setColor(Color.white);
                graphics2D.setFont(new Font("Ariel", Font.BOLD, 12));
                graphics2D.drawString("(" + this.worldX/gamePanel.getTileSize() + ", " + this.worldY/gamePanel.getTileSize() + ")",
                        (currScreenX - gamePanel.getTileSize()/4), (currScreenY - gamePanel.getTileSize()/3));

                // this displays the entity's direction
                graphics2D.setColor(Color.white);
                graphics2D.drawString("Direction: " + this.direction,
                        (currScreenX - gamePanel.getTileSize()/4), (currScreenY - gamePanel.getTileSize()/9));
            }
        }
    }

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



    // GETTER AND SETTER
    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setWorldX(int x) {
        this.worldX = x;
    }

    public void setWorldY(int y) {
        this.worldY = y;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public String getDirection() {
        return direction;
    }

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

    public void setCollisionBoxX(int x) {
        this.collisionBox.x = x;
    }

    public void setCollisionBoxY(int y) {
        this.collisionBox.y = y;
    }

    public int getCollisionBoxDefaultX() {
        return CollisionBoxDefaultX;
    }

    public int getCollisionBoxDefaultY() {
        return CollisionBoxDefaultY;
    }

    public boolean isPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getDown1() {
        return down1;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public int getCurrentLife() {
        return currentLife;
    }

    public void setCurrentLife(int currentLife) {
        this.currentLife = currentLife;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage2() {
        return image2;
    }

    public void setImage2(BufferedImage image2) {
        this.image2 = image2;
    }

    public BufferedImage getImage3() {
        return image3;
    }

    public void setImage3(BufferedImage image3) {
        this.image3 = image3;
    }

    public boolean hasCollision() {
        return collision;
    }
}
