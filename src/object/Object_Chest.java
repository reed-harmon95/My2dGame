package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Object_Chest extends SuperObject{

    public Object_Chest() {
        name = "Chest";

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/chest.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
