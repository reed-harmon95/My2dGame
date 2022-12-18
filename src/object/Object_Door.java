package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Object_Door extends SuperObject{


    public Object_Door(GamePanel gamePanel) {
        name = "Door";
        collision = true;
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/door.png"));
            image = utilityTool.scaleImage(image, gamePanel.getTileSize(), gamePanel.getTileSize());

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
