package main;

import object.*;

public class AssetManager {



    GamePanel gamePanel;
    SuperObject[] objectsArray;


    public AssetManager(GamePanel gamePanel, SuperObject[] objects){
        this.gamePanel = gamePanel;
        this.objectsArray = objects;
    }


    /**
     * This handles setting up the objects that the player can interact with inside the game world.
     * These have to be coded manually since the game world is not procedurally generated.
     * Although I guess I could create an algorithm to load them randomly on tiles the player can walk on...
     *
     * This currently loads 3 keys for 3 doors, and 1 chest
     *
     *
     * @return      - This returns the newly updated list of the game objects within the game
     */
    public SuperObject[] setObject() {

        // KEYS
        objectsArray[0] = new Object_Key();
        objectsArray[0].setWorldX(23 * gamePanel.getTileSize());
        objectsArray[0].setWorldY(7 * gamePanel.getTileSize());

        objectsArray[1] = new Object_Key();
        objectsArray[1].setWorldX(23 * gamePanel.getTileSize());
        objectsArray[1].setWorldY(40 * gamePanel.getTileSize());

        objectsArray[2] = new Object_Key();
        objectsArray[2].setWorldX(38 * gamePanel.getTileSize());
        objectsArray[2].setWorldY(8 * gamePanel.getTileSize());


        // DOORS
        objectsArray[3] = new Object_Door();
        objectsArray[3].setWorldX(10 * gamePanel.getTileSize());
        objectsArray[3].setWorldY(11 * gamePanel.getTileSize());

        objectsArray[4] = new Object_Door();
        objectsArray[4].setWorldX(8 * gamePanel.getTileSize());
        objectsArray[4].setWorldY(28 * gamePanel.getTileSize());

        objectsArray[5] = new Object_Door();
        objectsArray[5].setWorldX(12 * gamePanel.getTileSize());
        objectsArray[5].setWorldY(22 * gamePanel.getTileSize());


        // CHEST
        objectsArray[6] = new Object_Chest();
        objectsArray[6].setWorldX(10 * gamePanel.getTileSize());
        objectsArray[6].setWorldY(7 * gamePanel.getTileSize());


        // BOOTS
        objectsArray[7] = new Object_Boots();
        objectsArray[7].setWorldX(24 * gamePanel.getTileSize());
        objectsArray[7].setWorldY(22 * gamePanel.getTileSize());

        return objectsArray;
    }
}
