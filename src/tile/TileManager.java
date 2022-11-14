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
        mapTileNumbers = new int[gamePanel.getMaxScreenColumns()][gamePanel.getMaxScreenRows()];

        getTileImage();
        loadIntegerMap("Enter absolute file path here");

    }


    public void getTileImage() {

        try{


            //instantiate tile array
            //set the images for specific tiles e.g. tree, grass, water, wall
            tileSetList[0] = new Tile();
            tileSetList[0].setTileNumber(0);
            tileSetList[0].setTileImage(ImageIO.read(getClass().getResourceAsStream("/images/tiles/grass.png")));

            tileSetList[1] = new Tile();
            tileSetList[0].setTileNumber(1);
            tileSetList[1].setTileImage(ImageIO.read(getClass().getResourceAsStream("/images/tiles/wall.png")));

            tileSetList[2] = new Tile();
            tileSetList[0].setTileNumber(2);
            tileSetList[2].setTileImage(ImageIO.read(getClass().getResourceAsStream("/images/tiles/water.png")));

            tileSetList[3] = new Tile();
            tileSetList[0].setTileNumber(3);
            tileSetList[3].setTileImage(ImageIO.read(getClass().getResourceAsStream("/images/tiles/tree.png")));

            tileSetList[4] = new Tile();
            tileSetList[0].setTileNumber(4);
            tileSetList[4].setTileImage(ImageIO.read(getClass().getResourceAsStream("/images/tiles/earth.png")));





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
            while(column < gamePanel.getMaxScreenColumns() && row < gamePanel.getMaxScreenRows()){


                //read one line at a time
                String line = reader.nextLine();
                System.out.println(line);

                //when you get the row/line
                //loop through the number of values in the line(columns)
                while (column < gamePanel.getMaxScreenColumns()) {


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
                if(column == gamePanel.getMaxScreenColumns()){
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
        int x = 0;                      //this keeps track of the pixel value of the current tile along the x-axis of the screen
        int y = 0;                      //this keeps track of the pixel value of the current tile along the y-axis of the screen


        //loop to create game screen
        //while the tile we are drawing is within the bounds of the screen size in terms of rows and columns
        while( currentColumn < gamePanel.getMaxScreenColumns() && currentRow < gamePanel.getMaxScreenRows()){


            //get the int value corresponding to the current tile
            int tileNumber = mapTileNumbers[currentColumn][currentRow];


            //draw a tile
            //increment column counter and x-value
            graphics2D.drawImage(tileSetList[tileNumber].getTileImage(), x, y, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
            currentColumn++;
            x += gamePanel.getTileSize();


            //once the entire first row is drawn, move onto the next row
            if(currentColumn == gamePanel.getMaxScreenColumns()){
                currentColumn = 0;
                x = 0;
                currentRow++;
                y += gamePanel.getTileSize();
            }
        }








    }
}
