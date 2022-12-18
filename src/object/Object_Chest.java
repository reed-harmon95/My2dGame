package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Object_Chest extends SuperObject{

    public Object_Chest(GamePanel gamePanel) {
        name = "Chest";

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/chest.png"));
            image = utilityTool.scaleImage(image, gamePanel.getTileSize(), gamePanel.getTileSize());

        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
