package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Object_Boots extends SuperObject {

    public Object_Boots(GamePanel gamePanel) {
        name = "Boots";

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/boots.png"));
            image = utilityTool.scaleImage(image, gamePanel.getTileSize(), gamePanel.getTileSize());

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
