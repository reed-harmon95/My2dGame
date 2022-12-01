package tile;


import java.awt.image.BufferedImage;

/**
 * This class represents the tiles of the game map.
 */
public class Tile {

    private BufferedImage tileImage;
    public boolean collision = false;
    private int tileNumber;

    private String tileName;

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

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public String getTileName() {
        return tileName;
    }

    public void setTileName(String tileName) {
        this.tileName = tileName;
    }
}
