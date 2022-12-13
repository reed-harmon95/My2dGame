package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Object_Boots extends SuperObject {

    public Object_Boots() {
        name = "Boots";

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/boots.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
