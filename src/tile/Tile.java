package tile;


import java.awt.image.BufferedImage;

/**
 * This class represents the tiles of the game map.
 */
public class Tile {

    private BufferedImage tileImage;
    public boolean collision = false;
    private int tileNumber;

    public BufferedImage getTileImage() {
        return tileImage;
    }

    public void setTileImage(BufferedImage tileImage) {
        this.tileImage = tileImage;
    }

    public void setTileNumber(int tileNumber) {
        this.tileNumber = tileNumber;
    }

    public int getTileNumber() {
        return tileNumber;
    }
}
