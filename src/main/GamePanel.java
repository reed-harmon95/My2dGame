package main;

import Entity.Player;

import javax.swing.*;
import java.awt.*;


/**
 * This class handles the game screen. It contains the main gameplay loop that is run via a thread
 * and contains all necessary data to keep the game running.
 */
public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS
    final int originalTileSize = 16;                        //default tile size 16x16 pixels
    final int tileScale = 3;                                //scaling value to scale 16x16 images
    public final int tileSize = originalTileSize * tileScale;//size of tiles in game (16x16 scaled to 48x48), needed for player class
    final int maxScreenColumns = 16;                        //max number of tiles for width
    final int maxScreenRows = 12;                           //max number of tiles for height
    final int screenWidth = tileSize * maxScreenColumns;    //overall screen width in pixels
    final int screenHeight = tileSize * maxScreenRows;      //overall screen height in pixels


    // PLAYER DATA
    PlayerController playerController = new PlayerController();
    Player player = new Player(this, playerController);

    int fps = 60;
    Thread gameThread;                                      //handles the gameplay loop

    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);                       //gives double buffer to help with performance
        this.addKeyListener(playerController);              //adds player controller functionality to game screen
        this.setFocusable(true);                            //makes gamePanel focus on receiving player controller input

    }


    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();
    }


    //this is the run method for the gameThread Thread which hosts the main gameplay loop
    @Override
    public void run() {

        //Setup for the time of the game loop
        //tells how many times the cpu runs this loop per second (FPS)
        //Delta/Accumulator method
        double drawInterval = 1000000000 / (float) fps;             //1 second / 60 fps
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;


        //displaying fps
        long timer = 0;
        long drawCount = 0;


        //this is the main gameplay loop
        while(gameThread != null){

            //this sets up the timer to adjust how fast the cpu runs the game loop
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;



            if(delta >= 1){
                //update player, enemy, game data
                update();


                //painting that new data onto the screen via images
                repaint();


                delta--;
                drawCount++;
            }


            //every second display the fps
            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }


        }

    }


    //handles the data on the screen
    public void update() {

        //Update the players position based on the keyboard input
        player.update();

    }


    //handles putting images to the screen
    public void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);


        //set up instance of graphics 2d which includes some more functionality than regular graphics
        Graphics2D graphics2D = (Graphics2D) graphics;

        //drawing sample character(white rectangle)
        player.draw(graphics2D);


        //cleanup object once done drawing
        graphics2D.dispose();
    }
}
