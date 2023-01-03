package main;

import entity.Entity;
import entity.NPC_OldMan;
import object.*;


/**
 * The AssetManager class handles creating instances of game objects, NPCs, etc.
 * This is done by creating the instances of the assets and storing them within their respective arrays for use in the main program.
 */
public class AssetManager {



    private GamePanel gamePanel;
    private Entity[] objectsArray;
    private Entity[] npcArray;


    public AssetManager(GamePanel gamePanel, Entity[] objects, Entity[] npcArray){
        this.gamePanel = gamePanel;
        this.objectsArray = objects;
        this.npcArray = npcArray;
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
    public Entity[] setObject() {

        // KEYS
        objectsArray[0] = new Object_Key(gamePanel);
        objectsArray[0].setWorldX(23 * gamePanel.getTileSize());
        objectsArray[0].setWorldY(7 * gamePanel.getTileSize());

        objectsArray[1] = new Object_Key(gamePanel);
        objectsArray[1].setWorldX(23 * gamePanel.getTileSize());
        objectsArray[1].setWorldY(40 * gamePanel.getTileSize());

        objectsArray[2] = new Object_Key(gamePanel);
        objectsArray[2].setWorldX(38 * gamePanel.getTileSize());
        objectsArray[2].setWorldY(8 * gamePanel.getTileSize());


        // DOORS
        objectsArray[3] = new Object_Door(gamePanel);
        objectsArray[3].setWorldX(10 * gamePanel.getTileSize());
        objectsArray[3].setWorldY(11 * gamePanel.getTileSize());

        objectsArray[4] = new Object_Door(gamePanel);
        objectsArray[4].setWorldX(8 * gamePanel.getTileSize());
        objectsArray[4].setWorldY(28 * gamePanel.getTileSize());

        objectsArray[5] = new Object_Door(gamePanel);
        objectsArray[5].setWorldX(12 * gamePanel.getTileSize());
        objectsArray[5].setWorldY(22 * gamePanel.getTileSize());


        // CHEST
        objectsArray[6] = new Object_Chest(gamePanel);
        objectsArray[6].setWorldX(10 * gamePanel.getTileSize());
        objectsArray[6].setWorldY(7 * gamePanel.getTileSize());


        // BOOTS
        objectsArray[7] = new Object_Boots(gamePanel);
        objectsArray[7].setWorldX(24 * gamePanel.getTileSize());
        objectsArray[7].setWorldY(22 * gamePanel.getTileSize());

        return objectsArray;
    }


    public Entity[] setNPC() {
        npcArray[0] = new NPC_OldMan(gamePanel);
        npcArray[0].setWorldX(gamePanel.getTileSize()*38);
        npcArray[0].setWorldY(gamePanel.getTileSize()*12);

        npcArray[1] = new NPC_OldMan(gamePanel);
        npcArray[1].setWorldX(gamePanel.getTileSize()*17);
        npcArray[1].setWorldY(gamePanel.getTileSize()*21);

        npcArray[2] = new NPC_OldMan(gamePanel);
        npcArray[2].setWorldX(gamePanel.getTileSize()*15);
        npcArray[2].setWorldY(gamePanel.getTileSize()*21);

        return npcArray;
    }
}
