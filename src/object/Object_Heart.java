package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Object_Heart extends Entity {

    public Object_Heart(GamePanel gamePanel) {
       super(gamePanel);
        name = "Heart";


        File fileName = new File("res/images/objects/heart_full.png");
        image = instantiateImages(fileName);

        fileName = new File("res/images/objects/heart_half.png");
        image2 = instantiateImages(fileName);

        fileName = new File("res/images/objects/heart_blank.png");
        image3 = instantiateImages(fileName);

    }
}
