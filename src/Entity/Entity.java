package Entity;

import java.awt.image.BufferedImage;

/**
 * This class represents an entity which can be the player or NPC. This class is abstract, so it doesn't accidentally
 * get instantiated in the rest of the program. It should only be inherited by other classes.
 *
 */
public abstract class Entity {

    protected int x, y;                                     //starting coordinates of the entity
    protected int speed;                                    //movement speed of entity;


    //IMAGE AND ANIMATION DATA
    //NOTE** Change to array when I have time
    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;        //creates buffered images to hold walking animation images
    protected String direction;                             //holds the direction the entity is facing in order to load the correct image array
    protected int spriteCounter = 0;
    protected int spriteNumber = 1;

    public void setDefaultValues(){

    }

    public void setDefaultValues(int x, int y, int speed, String direction){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
