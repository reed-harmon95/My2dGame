package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class represents an entity which can be the player or NPC. This class is abstract, so it doesn't accidentally
 * get instantiated in the rest of the program. It should only be inherited by other classes.
 *
 */
public class Entity {

    protected int worldX, worldY;                                                        //starting coordinates of the entity in relation to the world map
    protected int speed;                                                                 //movement speed of entity;


    //IMAGE AND ANIMATION DATA
    //NOTE** Change to array when I have time
    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;        //creates buffered images to hold walking animation images
    protected String direction;                                                          //holds the direction the entity is facing in order to load the correct image array
    protected int spriteCounter = 0;
    protected int spriteNumber = 1;


    // COLLISION
    protected Rectangle collisionBox;
    protected boolean collisionOn = false;
    protected int CollisionBoxDefaultX, CollisionBoxDefaultY;                           // these are used to reset the default x/y values of the collision box for an entity when interacting with an object

    public void setDefaultValues(){

    }

    public void setDefaultValues(int x, int y, int speed, String direction){
        this.worldX = x;
        this.worldY = y;
        this.speed = speed;
        this.direction = direction;
    }

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
}
