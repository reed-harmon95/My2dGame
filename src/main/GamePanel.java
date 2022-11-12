package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS
    final int originalTileSize = 16;                        //default tile size 16x16 pixels
    final int tileScale = 3;                                //scaling value to scale 16x16 images
    final int tileSize = originalTileSize * tileScale;      //size of tiles in game (16x16 scaled to 48x48)
    final int maxScreenColumns = 16;                        //max number of tiles for width
    final int maxScreenRows = 12;                           //max number of tiles for height
    final int screenWidth = tileSize * maxScreenColumns;    //overall screen width in pixels
    final int screenHeight = tileSize * maxScreenRows;      //overall screen height in pixels


    Thread gameThread;                                      //handles the gameplay loop

    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);                       //gives double buffer to help with performance
    }


    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();
    }



    @Override
    public void run() {



    }
}
