package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Object_Door extends SuperObject{


    public Object_Door() {
        name = "Door";
        collision = true;
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/door.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
