package enemy;

import entity.Entity;
import main.GamePanel;

import java.io.File;
import java.util.Random;

public class Enemy_BlueSlime extends Enemy_GreenSlime {


    public Enemy_BlueSlime(GamePanel gamePanel){
        super(gamePanel);


        // Basic Info
        name = "Blue Slime";
        maxLife = 6;
        currentLife = maxLife;
        entityType = 2;


        setCollision();
        getImages();
    }

    public void getImages(){

        File fileName = new File("res/images/enemy/blueslime_down_1.png");
        down1 = instantiateImages(fileName);

        fileName = new File("res/images/enemy/blueslime_down_2.png");
        down2 = instantiateImages(fileName);

        fileName = new File("res/images/enemy/blueslime_down_1.png");
        up1 = instantiateImages(fileName);
        fileName = new File("res/images/enemy/blueslime_down_2.png");
        up2 = instantiateImages(fileName);

        fileName = new File("res/images/enemy/blueslime_down_1.png");
        left1 = instantiateImages(fileName);
        fileName = new File("res/images/enemy/blueslime_down_2.png");
        left2 = instantiateImages(fileName);

        fileName = new File("res/images/enemy/blueslime_down_1.png");
        right1 = instantiateImages(fileName);
        fileName = new File("res/images/enemy/blueslime_down_2.png");
        right2 = instantiateImages(fileName);
    }

    @Override
    public void setAction() {
        super.setAction();
    }

    @Override
    public void setCollision(){
        super.setCollision();

        collisionBox.y = -20;
        collisionBox.height = gamePanel.getTileSize();
    }
}
