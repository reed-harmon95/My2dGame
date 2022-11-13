package Entity;

/**
 * This class represents an entity which can be the player or NPC. This class is abstract, so it doesn't accidentally
 * get instantiated in the rest of the program. It should only be inherited by other classes.
 *
 */
public abstract class Entity {

    protected int x, y;     //starting coordinates of the entity
    protected int speed;    //movement speed of entity;


    public void setDefaultValues(){

    }

    public void setDefaultValues(int x, int y, int speed){
        this.x = x;
        this.y = y;
        this.speed = speed;
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
