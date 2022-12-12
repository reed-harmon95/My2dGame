package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Object_Key extends SuperObject{


    public Object_Key() {
        name = "Key";

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/key.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
