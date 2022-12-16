package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;


/**
 * This class handles the game screen. It contains the main gameplay loop that is run via a thread
 * and contains all necessary data to keep the game running.
 */
public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS ** Getters for certain fields at bottom **
    private final int originalTileSize = 16;                        //default tile size 16x16 pixels
    private final int tileScale = 3;                                //scaling value to scale 16x16 images
    private final int tileSize = originalTileSize * tileScale;      //size of tiles in game (16x16 scaled to 48x48), needed for player class
    private final int maxScreenColumns = 16;                        //max number of tiles for width
    private final int maxScreenRows = 12;                           //max number of tiles for height
    private final int screenWidth = tileSize * maxScreenColumns;    //overall screen width in pixels
    private final int screenHeight = tileSize * maxScreenRows;      //overall screen height in pixels


    // WORLD SETTINGS
    private final int maxWorldColumns = 50;                         //max number of columns a world can have
    private final int maxWorldRows = 50;                            //max number of rows a world can have
    private final int worldWidth = tileSize * maxWorldColumns;      //max width of the world in terms of pixels
    private final int worldHeight = tileSize * maxWorldRows;        //max height of the world in terms of pixels


    // PLAYER DATA
    PlayerController playerController = new PlayerController();
    Player player = new Player(this, playerController);
    CollisionHandler collisionHandler = new CollisionHandler(this);


    // BACKGROUND TILES
    TileManager tileManager = new TileManager(this);


    // GAME OBJECTS
    public SuperObject objects[] = new SuperObject[10];
    AssetManager assetManager = new AssetManager(this, objects);


    // SOUND
    Sound soundEffect = new Sound();
    Sound music = new Sound();
    boolean isPlayingBackgroundMusic = false;


    // USER INTERFACE
    UserInterface userInterface = new UserInterface(this);



    int fps = 60;
    Thread gameThread;                                              //handles the gameplay loop



    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);                       //gives double buffer to help with performance
        this.addKeyListener(playerController);              //adds player controller functionality to game screen
        this.setFocusable(true);                            //makes gamePanel focus on receiving player controller input

    }


    /**
     * This method handles initial setup of data/assets prior to the game running.
     */
    public void initialSetup(){


        // Set objects to game map
        objects = assetManager.setObject();


        // Play background music
        playBackgroundMusic(0);
        isPlayingBackgroundMusic = true;
    }


    /**
     * This method is used by the GamePanel class to start a thread to run the main gameplay loop.
     */
    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();
    }


    /**
     * This run method handles the main gameplay loop of the game.
     * It utilizes the delta time/accumulator method to handle the frames per second.
     * Each frame, the update and paint methods are called to update necessary game data and appropriately paint that data to the screen.
     */
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



            if(playerController.isMuted() == true && isPlayingBackgroundMusic == true){
                music.muteVolume();
            } else if(playerController.isMuted() == false && isPlayingBackgroundMusic == false) {
                playBackgroundMusic(0);
            }
        }

    }


    /**
     * This is the update method that gets called in every frame during the duration of the game instance.
     * This method handles updating the data of various objects that interact within the game.
     * This updated data then gets used by the paint method to properly draw the data to the screen during each frame.
     */
    public void update() {

        //Update the players position based on the keyboard input
        player.update();

    }


    /**
     * This is the paint method that is responsible for drawing all the data to the screen.
     * It is run every frame after the update method to ensure that all data updated during this frame are appropriately drawn to the screen.
     * This method utilizes the Swing toolkit Graphics class to accomplish drawing images to the screen.
     *
     *
     * @param graphics      - Instance of the Graphics class used to draw data in the game to the screen
     */
    public void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);


        //set up instance of graphics 2d which includes some more functionality than regular graphics
        Graphics2D graphics2D = (Graphics2D) graphics;


        //drawing background tiles(map) to screen
        //NOTE** This should be done before drawing the player character so the player character is on top of the tiles
        tileManager.draw(graphics2D);


        //drawing object to screen
        //loop through the objects array
        //if the object in the array is instantiated, draw it ot the screen
        for(int i = 0; i < objects.length; i++){
            if(objects[i] != null){
                objects[i].draw(graphics2D, this);
            }
        }


        //drawing player character to screen
        player.draw(graphics2D);


        // DRAW UI
        userInterface.draw(graphics2D);

        //cleanup object once done drawing
        graphics2D.dispose();
    }


    public void playBackgroundMusic(int index) {


        music.setFile(index);
        music.play();
        music.loop();
    }

    public void stopBackgroundMusic(int index){
        music.setFile(index);
        music.stop();
    }

    public void playSoundEffect(int index){


        soundEffect.setFile(index);
        soundEffect.play();
    }


    /**
     * These are the Getters/Setters for various fields in this class that get utilized in other classes throughout this program.
     *
     *      * Note -    I should probably clean this up a bit,
     *                  but this is my first attempt at game development,
     *                  it shall be a problem for future me.
     *
     *                      - Reed Harmon 2022
     */
    public int getTileSize() {
        return tileSize;
    }

    public int getMaxScreenColumns() {
        return maxScreenColumns;
    }

    public int getMaxScreenRows() {
        return maxScreenRows;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getMaxWorldColumns() {
        return maxWorldColumns;
    }

    public int getMaxWorldRows() {
        return maxWorldRows;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public Player getPlayer() {
        return player;
    }

    public CollisionHandler getCollisionHandler() {
        return collisionHandler;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public UserInterface getUserInterface() {
        return userInterface;
    }
}
