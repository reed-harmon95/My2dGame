package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Object_Key extends Entity {


    public Object_Key(GamePanel gamePanel) {
        super(gamePanel);

        name = "Key";
        File fileName = new File("res/images/objects/key.png");
        down1 = instantiateImages(fileName);


    }
}
