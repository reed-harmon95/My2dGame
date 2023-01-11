package enemy;

import entity.Entity;
import main.GamePanel;

import java.io.File;
import java.util.Random;

public class Enemy_GreenSlime extends Entity {

    public Enemy_GreenSlime(GamePanel gamePanel){
        super(gamePanel);


        // Basic Info
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        currentLife = maxLife;
        entityType = 2;


        // Collision Box
       setCollision();

        getImages();
    }

    public void  getImages(){

        File fileName = new File("res/images/enemy/greenslime_down_1.png");
        down1 = instantiateImages(fileName);

        fileName = new File("res/images/enemy/greenslime_down_2.png");
        down2 = instantiateImages(fileName);


        // Still use the same images for the other directions
        fileName = new File("res/images/enemy/greenslime_down_1.png");
        up1 = instantiateImages(fileName);
        fileName = new File("res/images/enemy/greenslime_down_2.png");
        up2 = instantiateImages(fileName);

        fileName = new File("res/images/enemy/greenslime_down_1.png");
        left1 = instantiateImages(fileName);
        fileName = new File("res/images/enemy/greenslime_down_2.png");
        left2 = instantiateImages(fileName);

        fileName = new File("res/images/enemy/greenslime_down_1.png");
        right1 = instantiateImages(fileName);
        fileName = new File("res/images/enemy/greenslime_down_2.png");
        right2 = instantiateImages(fileName);


    }


    @Override
    public void setAction() {
        actionCounter++;

        if(actionCounter == 120){
            Random randomNum = new Random();
            int decision = randomNum.nextInt(100)+1;

            if(decision <=20){
                direction = "up";
            } else if(decision >20 && decision <=40){
                direction = "down";
            } else if(decision > 40 && decision <=60) {
                direction = "left";
            } else if(decision > 60 && decision <=80){
                direction = "right";
            }

            actionCounter = 0;
        }
    }

    public void setCollision(){
        collisionBox.x = 3;
        collisionBox.y = 18;
        collisionBox.width = 42;
        collisionBox.height = 30;
        CollisionBoxDefaultX = collisionBox.x;
        CollisionBoxDefaultY = collisionBox.y;
    }
}
