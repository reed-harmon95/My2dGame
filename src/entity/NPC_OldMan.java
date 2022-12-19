package entity;

import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class NPC_OldMan extends Entity{



    public NPC_OldMan(GamePanel gamePanel) {
        super(gamePanel);

        player = false;
        direction = "down";
        speed = 1;
        name = "Old Man";

        getPlayerImages();
    }


    public void getPlayerImages(){

        File[] fileNames = new File("res/images/npc").listFiles();

        for(int i = 0; i < fileNames.length; i++){


            switch (fileNames[i].getName()) {


                // UP
                case "oldman_up_1.png":
                    up1 = instantiateImages(fileNames[i]);
                    break;
                case "oldman_up_2.png":
                    up2 = instantiateImages(fileNames[i]);
                    break;


                // DOWN
                case "oldman_down_1.png":
                    down1 = instantiateImages(fileNames[i]);
                    break;
                case "oldman_down_2.png":
                    down2 = instantiateImages(fileNames[i]);
                    break;


                // LEFT
                case "oldman_left_1.png":
                    left1 = instantiateImages(fileNames[i]);
                    break;
                case "oldman_left_2.png":
                    left2 = instantiateImages(fileNames[i]);
                    break;


                // RIGHT
                case "oldman_right_1.png":
                    right1 = instantiateImages(fileNames[i]);
                    break;
                case "oldman_right_2.png":
                    right2 = instantiateImages(fileNames[i]);
                    break;
            }
        }

    }


    @Override
    public void setAction() {

        actionCounter++;

        if(actionCounter == 120){
            Random randomNum = new Random();
            int decision = randomNum.nextInt(100)+1;

            if(decision <=20){
                direction = "up";
            } else if(decision >20 && decision <=40){
                direction = "down";
            } else if(decision > 40 && decision <=60) {
                direction = "left";
            } else if(decision > 60 && decision <=80){
                direction = "right";
            }

            actionCounter = 0;
        }


    }
}
