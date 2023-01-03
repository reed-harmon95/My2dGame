package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Object_Chest extends Entity {

    public Object_Chest(GamePanel gamePanel) {
        super(gamePanel);

        name = "Chest";
        File fileName = new File("res/images/objects/chest.png");
        down1 = instantiateImages(fileName);



    }

}
