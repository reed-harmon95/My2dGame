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


    public void calculateDamage(String name, GamePanel gamePanel){
        switch(name){

            case "Green Slime":
                if(gamePanel.getPlayer().currentLife > 1){
                    gamePanel.getPlayer().currentLife -= 1;
                    gamePanel.getPlayer().isInvincible = true;
                }

                break;
            case "Blue Slime":

                if(gamePanel.getPlayer().currentLife > 2){
                    gamePanel.getPlayer().currentLife -= 2;
                    gamePanel.getPlayer().isInvincible = true;
                }
                gamePanel.getPlayer().knockBack();
                break;
        }
    }


}
