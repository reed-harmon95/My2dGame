package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Utility {


    // doesn't really need anything for now
    // it is just a class that holds various methods
    public Utility(){

    }


    public BufferedImage scaleImage(BufferedImage originalImage, int width, int height) {

        BufferedImage scaledImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, width, height, null);
        graphics2D.dispose();

        return scaledImage;
    }




}
