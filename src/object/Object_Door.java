package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Object_Door extends Entity {


    public Object_Door(GamePanel gamePanel) {
        super(gamePanel);
        name = "Door";
        collision = true;

        File fileName = new File("res/images/objects/door.png");
        down1 = instantiateImages(fileName);


        collisionBox.x = 0;
        collisionBox.y = 16;
        collisionBox.width = 48;
        collisionBox.height = 32;
        CollisionBoxDefaultX = collisionBox.x;
        CollisionBoxDefaultY = collisionBox.y;

    }
}
