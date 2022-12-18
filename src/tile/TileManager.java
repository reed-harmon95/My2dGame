package tile;

import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
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


        // this will return a list of all files within the selected directory
        // it is advised to check the file extensions as this gets a list of all files
        File[] fileNames = new File("res/images/tiles").listFiles();


        // loop through the files in the directory containing the images
        // instantiate tile objects related to those file names
        // a switch case is used because each tile index is mapped to a unique int value in the pre-made map
        // giving a wrong index to a tile type would destroy the pre-made map
        for(int i = 0; i < fileNames.length; i++){

            System.out.println(fileNames[i].getPath());
            switch (fileNames[i].getName()){
                case "grass.png":
                    tileSetup(0, fileNames[i].getPath(), false);
                    break;
                case "wall.png":
                    tileSetup(1, fileNames[i].getPath(), true);
                    break;
                case "water.png":
                    tileSetup(2, fileNames[i].getPath(), true);
                    break;
                case "earth.png":
                    tileSetup(3, fileNames[i].getPath(),false);
                    break;
                case "tree.png":
                    tileSetup(4, fileNames[i].getPath(), true);
                    break;
                case "sand.png":
                    tileSetup(5, fileNames[i].getPath(), false);
                    break;
                default:
                    System.out.println("Error has occurred. File: " + fileNames[i].getName() + " does not have handler case. ");
            }
        }

    }


    public void tileSetup(int index, String imagePath, boolean collision){
        Utility utilityTool = new Utility();

        try {
            tileSetList[index] = new Tile();
            tileSetList[index].setTileNumber(index);
            tileSetList[index].setTileImage(ImageIO.read(new File(imagePath)));
            tileSetList[index].setTileImage(utilityTool.scaleImage(tileSetList[index].getTileImage(), gamePanel.getTileSize(), gamePanel.getTileSize()));
            tileSetList[index].setCollision(collision);
        } catch (IOException e) {
            System.out.println("Error in tileSetup: tile at index " + index);
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

                graphics2D.drawImage(tileSetList[tileNumber].getTileImage(), currScreenX, currScreenY, null);
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
