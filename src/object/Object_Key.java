package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Object_Key extends SuperObject{


    public Object_Key(GamePanel gamePanel) {
        name = "Key";

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/key.png"));
            image = utilityTool.scaleImage(image, gamePanel.getTileSize(), gamePanel.getTileSize());

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
