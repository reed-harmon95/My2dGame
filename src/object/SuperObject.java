package object;

import main.GamePanel;
import main.Utility;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * This is a parent class to all objects used within the game world.
 */
public abstract class SuperObject {

    // OBJECT DATA FOR DRAWING TO SCREEN
    protected BufferedImage image, image2, image3;
    protected String name;
    protected int worldX, worldY;
    protected Utility utilityTool = new Utility();



    // OBJECT DATA FOR COLLISION/INTERACTING WITH PLAYER
    protected Rectangle collisionBox = new Rectangle(0,0,48,48);
    protected int collisionBoxDefaultX = 0;
    protected int collisionBoxDefaultY = 0;

    protected boolean collision = false;


    public void draw(Graphics2D graphics2D, GamePanel gamePanel){



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

            graphics2D.drawImage(image, currScreenX, currScreenY, null);
        }
    }



    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getImage2() {
        return image2;
    }

    public BufferedImage getImage3() {
        return image3;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public void setCollisionBoxX(int x) {
        this.collisionBox.x = x;
    }

    public void setCollisionBoxY(int y) {
        this.collisionBox.y = y;
    }


    public int getCollisionBoxDefaultX() {
        return collisionBoxDefaultX;
    }

    public int getCollisionBoxDefaultY() {
        return collisionBoxDefaultY;
    }
}
