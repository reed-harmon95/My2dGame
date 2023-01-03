package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Object_Boots extends Entity {

    public Object_Boots(GamePanel gamePanel) {
        super(gamePanel);

        name = "Boots";
        File fileName = new File("res/images/objects/boots.png");
        down1 = instantiateImages(fileName);
    }
}
