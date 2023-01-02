package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Object_Heart extends SuperObject {

    public Object_Heart(GamePanel gamePanel) {
        name = "Heart";

        try{

            // get the three different types of heart images
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/heart_full.png"));
            image = utilityTool.scaleImage(image, gamePanel.getTileSize(), gamePanel.getTileSize());

            image2 = ImageIO.read(getClass().getResourceAsStream("/images/objects/heart_half.png"));
            image2 = utilityTool.scaleImage(image2, gamePanel.getTileSize(), gamePanel.getTileSize());

            image3 = ImageIO.read(getClass().getResourceAsStream("/images/objects/heart_blank.png"));
            image3 = utilityTool.scaleImage(image3, gamePanel.getTileSize(), gamePanel.getTileSize());

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
