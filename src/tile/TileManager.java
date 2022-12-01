package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Scanner;


/**
 * This class is responsible for drawing the game map to the screen. It holds an array of tiles and utilizes a GamePanel
 * object to draw the array to the screen.
 */
public class TileManager {

    GamePanel gamePanel;
    Tile[] tileSetList;         //list of different tile options available
    int mapTileNumbers[][];     //map of the tile numbers in integer form



    public TileManager(GamePanel gamePanel){

        //instantiate an object of the GamePanel Class
        this.gamePanel = gamePanel;


        //Instantiate the list of different possible tiles to be used
        //Instantiate the integer form of the game map being used
        tileSetList = new Tile[10];
        mapTileNumbers = new int[gamePanel.getMaxWorldColumns()][gamePanel.getMaxWorldRows()];

        getTileImage();
        loadIntegerMap("/home/reed/Desktop/Projects/Java/My2dGame/res/images/maps/world01.txt");

    }


    public void getTileImage() {

        try{


            //instantiate tile array
            //set the images for specific tiles e.g. tree, grass, water, wall
            tileSetList[0] = new Tile();
            tileSetList[0].setTileNumber(0);
            tileSetList[0].setTileImage(ImageIO.read(getClass().getResourceAsStream("/images/tiles/grass.png")));

            tileSetList[1] = new Tile();
            tileSetList[1].setTileNumber(1);
            tileSetList[1].setTileImage(ImageIO.read(getClass().getResourceAsStream("/images/tiles/wall.png")));
            tileSetList[1].setTileName("wall");
            tileSetList[1].setCollision(true);

            tileSetList[2] = new Tile();
            tileSetList[2].setTileNumber(2);
            tileSetList[2].setTileImage(ImageIO.read(getClass().getResourceAsStream("/images/tiles/water.png")));
            tileSetList[2].setTileName("water");
            tileSetList[2].setCollision(true);

            tileSetList[3] = new Tile();
            tileSetList[3].setTileNumber(3);
            tileSetList[3].setTileImage(ImageIO.read(getClass().getResourceAsStream("/images/tiles/earth.png")));

            tileSetList[4] = new Tile();
            tileSetList[4].setTileNumber(4);
            tileSetList[4].setTileImage(ImageIO.read(getClass().getResourceAsStream("/images/tiles/tree.png")));
            tileSetList[4].setTileName("tree");
            tileSetList[4].setCollision(true);

            tileSetList[5] = new Tile();
            tileSetList[5].setTileNumber(5);
            tileSetList[5].setTileImage(ImageIO.read(getClass().getResourceAsStream("/images/tiles/sand.png")));





        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public void loadIntegerMap(String filePath)  {

        //NOTE ** MAKE SURE THE FILE PATH IS ABSOLUTE **

        System.out.println("loading map");
        try{
            File file = new File(filePath);
            Scanner reader = new Scanner(new FileReader(file));

            int column = 0;
            int row = 0;


            //loop through the text file containing the map data in integers
            while(column < gamePanel.getMaxWorldColumns() && row < gamePanel.getMaxWorldRows()){


                //read one line at a time
                String line = reader.nextLine();


                //NOTE ** THIS IS PRINTING THE MAP TO THE TERMINAL **
                System.out.println(line);


                //when you get the row/line
                //loop through the number of values in the line(columns)
                while (column < gamePanel.getMaxWorldColumns()) {


                    //get rid of the spaces separating the numbers in the row
                    String numbers[] = line.split(" ");


                    //put a single value into an integer
                    int num = Integer.parseInt(numbers[column]);


                    //place the integer into the appropriate spot in the array
                    //increment to the next value
                    mapTileNumbers[column][row] = num;
                    column++;
                }


                //once the row is complete
                //move onto the next row
                if(column == gamePanel.getMaxWorldColumns()){
                    column = 0;
                    row++;
                }
            }


            reader.close();
        } catch(Exception e) {
            System.out.println("Failure");
        }
    }


    /**
     * This method draws the background tiles to the screen. The important fields to understand in this method are the
     * currentColumn, currentRow, x, and y fields, as they assist in drawing the tiles to the screen.
     *
     * In terms of the currentColumn and currentRow fields, think of the entire game screen as being divided into individual tiles
     * as mentioned in the GamePanel class. These fields help keep the tiles drawn within the bounds of the GamePanel class's dimensions.
     * The dimensions of the GamePanel class are currently 16x12 tiles so these fields stay within those predefined bounds.
     *
     * In terms of the x and y fields, these keep track of the specific pixel values the tiles are being drawn at on the GamePanel screen.
     * the upper left is defined as X:0, Y:0 so that is where the first tile gets drawn.
     * The next tile would be drawn one tile size away in either the x or y direction.
     *
     *
     * @param graphics2D    - This is responsible for drawing the image eto the screen
     */
    public void draw(Graphics2D graphics2D){


        //Setup for drawing tiles across screen
        int currentColumn = 0;          //this keeps track of the current tile along the columns parameter of the screen size
        int currentRow = 0;             //this keeps track of the current tile along the rows parameter of the screen size



        //loop to create game screen
        //while the tile we are drawing is within the bounds of the screen size in terms of rows and columns
        while( currentColumn < gamePanel.getMaxWorldColumns() && currentRow < gamePanel.getMaxWorldRows()){


            //get the int value corresponding to the current tile
            int tileNumber = mapTileNumbers[currentColumn][currentRow];


            //get the current position on the world map
            int currWorldX = currentColumn * gamePanel.getTileSize();
            int currWorldY = currentRow * gamePanel.getTileSize();

            //get the screen's position on the world map (which surrounds the player's position)
            int currScreenX = currWorldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX();
            int currScreenY = currWorldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();


            //this ensures that the only part of the world map being drawn to the screen is the area that is immediately surrounding the player
            //this slightly improves rendering efficiency since parts of the map the player isn't currently at are not being drawn every frame
            // ** IF YOU DO NOT UNDERSTAND. REMOVE THE +- gamePanel.getTileSize () FROM THE IF STATEMENT FOR VISUAL UNDERSTANDING **
            if(currWorldX + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getScreenX() &&
                currWorldX - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX() &&
                currWorldY  + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getScreenY() &&
                currWorldY  - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY()) {

                graphics2D.drawImage(tileSetList[tileNumber].getTileImage(), currScreenX, currScreenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
            }


            //draw a tile
            //increment column counter and x-value

            currentColumn++;



            //once the entire first row is drawn, move onto the next row
            if(currentColumn == gamePanel.getMaxWorldColumns()){
                currentColumn = 0;

                currentRow++;

            }
        }
    }


    public int[][] getMapTileNumbers() {
        return mapTileNumbers;
    }


    public Tile[] getTileSetList() {
        return tileSetList;
    }
}
