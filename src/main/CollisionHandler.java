package main;

import entity.Entity;
//import object.SuperObject;

public class CollisionHandler {

    private GamePanel gamePanel;


    public CollisionHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    /**
     * This method handles checking the player collision with the tiles of the game world.
     * The idea is that any time the player moves in a given direction,
     * this method checks the one or two tiles that the player wants to move into and determines if those tiles have collision or not.
     *
     * If there is collision, the player will be flagged for colliding with an object.
     * If there is no collision, they will not be flagged.
     * The actual player movement is handled in the update method of the Player class where this is called every frame the player is moving.
     *
     * @param entity        - This represents an entity that is moving within the game. They can be the player or an NPC
     */
    public void checkTileCollision(Entity entity){

        //use these to find col and row values
        int entityLeftX = entity.getWorldX() + entity.getCollisionBox().x;
        int entityRightX = entity.getWorldX() + entity.getCollisionBox().x + entity.getCollisionBox().width;
        int entityTopY = entity.getWorldY() + entity.getCollisionBox().y;
        int entityBottomY = entity.getWorldY() + entity.getCollisionBox().y + entity.getCollisionBox().height;


        //getting the specific tile(s) the collision box is on
        int entityLeftColumn = entityLeftX / gamePanel.getTileSize();
        int entityRightColumn = entityRightX / gamePanel.getTileSize();
        int entityTopRow = entityTopY / gamePanel.getTileSize();
        int entityBottomRow = entityBottomY / gamePanel.getTileSize();


        //this is to check if the entity iis colliding with the 1 or 2 tiles it is moving into
        int tile1, tile2;

        switch (entity.getDirection()){
            case "up":

                entityTopRow = (entityTopY - entity.getSpeed()) / gamePanel.getTileSize();

                //find the int value of the tiles the player is moving into
                tile1 = gamePanel.getTileManager().getMapTileNumbers()[entityLeftColumn][entityTopRow];
                tile2 = gamePanel.getTileManager().getMapTileNumbers()[entityRightColumn][entityTopRow];

                //if the int values of the tiles have collision
                //set the collision on entity
                if(gamePanel.getTileManager().getTileSetList()[tile1].isCollision() == true ||
                        gamePanel.getTileManager().getTileSetList()[tile2].isCollision() == true ) {

                    entity.setCollisionOn(true);

                    if(entity.isPlayer()){
                        System.out.println("Entity Colliding with " + gamePanel.getTileManager().getTileSetList()[tile1].getTileName());
                    }

                }
                break;

            case "down":


                entityBottomRow = (entityBottomY + entity.getSpeed()) / gamePanel.getTileSize();

                //find the int value of the tiles the player is moving into
                tile1 = gamePanel.getTileManager().getMapTileNumbers()[entityLeftColumn][entityBottomRow];
                tile2 = gamePanel.getTileManager().getMapTileNumbers()[entityRightColumn][entityBottomRow];

                //if the int values of the tiles have collision
                //set the collision on entity
                if(gamePanel.getTileManager().getTileSetList()[tile1].isCollision() == true ||
                        gamePanel.getTileManager().getTileSetList()[tile2].isCollision() == true) {

                    entity.setCollisionOn(true);


                    if(entity.isPlayer()){
                        System.out.println("Entity Colliding with " + gamePanel.getTileManager().getTileSetList()[tile1].getTileName());
                    }
                }
                break;

            case "left":


                entityLeftColumn = (entityLeftX - entity.getSpeed()) / gamePanel.getTileSize();

                //find the int value of the tiles the player is moving into
                tile1 = gamePanel.getTileManager().getMapTileNumbers()[entityLeftColumn][entityTopRow];
                tile2 = gamePanel.getTileManager().getMapTileNumbers()[entityLeftColumn][entityBottomRow];

                //if the int values of the tiles have collision
                //set the collision on entity
                if(gamePanel.getTileManager().getTileSetList()[tile1].isCollision() == true ||
                        gamePanel.getTileManager().getTileSetList()[tile2].isCollision() == true) {

                    entity.setCollisionOn(true);
                    if(entity.isPlayer()){
                        System.out.println("Entity Colliding with " + gamePanel.getTileManager().getTileSetList()[tile1].getTileName());
                    }
                }
                break;

            case "right":


                entityRightColumn = (entityRightX + entity.getSpeed()) / gamePanel.getTileSize();

                //find the int value of the tiles the player is moving into
                tile1 = gamePanel.getTileManager().getMapTileNumbers()[entityRightColumn][entityTopRow];
                tile2 = gamePanel.getTileManager().getMapTileNumbers()[entityRightColumn][entityBottomRow];

                //if the int values of the tiles have collision
                //set the collision on entity
                if(gamePanel.getTileManager().getTileSetList()[tile1].isCollision() == true ||
                        gamePanel.getTileManager().getTileSetList()[tile2].isCollision() == true) {

                    entity.setCollisionOn(true);
                    if(entity.isPlayer()){
                        System.out.println("Entity Colliding with " + gamePanel.getTileManager().getTileSetList()[tile1].getTileName());
                    }
                }
                break;
        }
    }


    /**
     * This method handles whether an entity collides with an object within the game world.
     * If an entity does, then the index of that object is returned so that it can be removed from the objects array within the GamePanel class.
     *
     *      Note - if the index is an arbitrarily high number e.g. 999, then that means there was no collision with an object
     *
     * @param entity        - An entity that is moving within the game world (Player or NPC)
     * @param isPlayer      - A boolean ot determine if the entity is the player
     *
     * @return              - An integer representing an index within the objects array in the GamePanel class
     */
    public int checkObjectCollision(Entity entity, boolean isPlayer) {

        int index = 999;

        for(int i = 0; i < gamePanel.objects.length; i++) {

            if(gamePanel.objects[i] != null){

                //get entity's collision box location
                entity.setCollisionBoxX(entity.getWorldX() + entity.getCollisionBox().x);
                entity.setCollisionBoxY(entity.getWorldY() + entity.getCollisionBox().y);

                //get objects solid area
                gamePanel.objects[i].setCollisionBoxX(gamePanel.objects[i].getWorldX() + gamePanel.objects[i].getCollisionBox().x);
                gamePanel.objects[i].setCollisionBoxY(gamePanel.objects[i].getWorldY() + gamePanel.objects[i].getCollisionBox().y);


                // Switch statement for each direction
                switch(entity.getDirection()) {
                    case "up":

                        entity.setCollisionBoxY(entity.getCollisionBox().y - entity.getSpeed());
                        if(entity.getCollisionBox().intersects(gamePanel.objects[i].getCollisionBox()) ){
                            if(entity.isPlayer()) {
                                System.out.println("Up collision with " + gamePanel.objects[i].getName());
                            }


                            if(gamePanel.objects[i].hasCollision() == true){
                                entity.setCollisionOn(true);
                            }
                            if(isPlayer == true){
                                index = i;
                            }
                        }
                        break;


                    case "down":

                        entity.setCollisionBoxY(entity.getCollisionBox().y + entity.getSpeed());
                        if(entity.getCollisionBox().intersects(gamePanel.objects[i].getCollisionBox()) ){
                            if(entity.isPlayer()) {
                                System.out.println("Down collision with " + gamePanel.objects[i].getName());
                            }


                            if(gamePanel.objects[i].hasCollision() == true){
                                entity.setCollisionOn(true);
                            }
                            if(isPlayer == true){
                                index = i;
                            }
                        }
                        break;


                    case "left":

                        entity.setCollisionBoxX(entity.getCollisionBox().x - entity.getSpeed());
                        if(entity.getCollisionBox().intersects(gamePanel.objects[i].getCollisionBox())){
                            if(entity.isPlayer()) {
                                System.out.println("Left collision with " + gamePanel.objects[i].getName());
                            }


                            if(gamePanel.objects[i].hasCollision() == true){
                                entity.setCollisionOn(true);
                            }
                            if(isPlayer == true){
                                index = i;
                            }
                        }
                        break;

                    case "right":

                        entity.setCollisionBoxX(entity.getCollisionBox().x + entity.getSpeed());
                        if(entity.getCollisionBox().intersects(gamePanel.objects[i].getCollisionBox())){
                            if(entity.isPlayer()) {
                                System.out.println("Right collision with " + gamePanel.objects[i].getName());
                            }


                            if(gamePanel.objects[i].hasCollision() == true){
                                entity.setCollisionOn(true);
                            }
                            if(isPlayer == true){
                                index = i;
                            }
                        }
                        break;
                }


                // Reset values collision boxes
                entity.setCollisionBoxX(entity.getCollisionBoxDefaultX());
                entity.setCollisionBoxY(entity.getCollisionBoxDefaultX());

                gamePanel.objects[i].setCollisionBoxX(gamePanel.objects[i].getCollisionBoxDefaultX());
                gamePanel.objects[i].setCollisionBoxY(gamePanel.objects[i].getCollisionBoxDefaultY());
            }
        }

        return index;
    }


    /**
     * This method checks the collision between an entity (entity) and a list of entities (target),
     * where the entity can be either the player or NPC and the target is the list of current NPCs on the map.
     *
     * This is done by checking the currently held move of the entity and checking to see if that will be in the location of the list of targets.
     *
     *
     * @param entity        - Entity being checked (most likely player)
     * @param target        - List of NPCs
     * @return              - Index of NPC in NPC array corresponding with the collision, 999 if no collision
     */
    public int checkEntityCollision(Entity entity, Entity[] target){
        int index = 999;

        for(int i = 0; i < target.length; i++) {

            if(target[i] != null){

                //get entity's collision box location
                entity.setCollisionBoxX(entity.getWorldX() + entity.getCollisionBox().x);
                entity.setCollisionBoxY(entity.getWorldY() + entity.getCollisionBox().y);

                //get objects solid area
                target[i].setCollisionBoxX(target[i].getWorldX() + target[i].getCollisionBox().x);
                target[i].setCollisionBoxY(target[i].getWorldY() + target[i].getCollisionBox().y);


                // Switch statement for each direction
                switch(entity.getDirection()) {
                    case "up":

                        entity.setCollisionBoxY(entity.getCollisionBox().y - entity.getSpeed());
                        break;


                    case "down":

                        entity.setCollisionBoxY(entity.getCollisionBox().y + entity.getSpeed());
                        break;


                    case "left":

                        entity.setCollisionBoxX(entity.getCollisionBox().x - entity.getSpeed());
                        break;

                    case "right":

                        entity.setCollisionBoxX(entity.getCollisionBox().x + entity.getSpeed());
                        break;
                }

                if(entity.getCollisionBox().intersects(target[i].getCollisionBox()) ){
                    if(target[i] != entity) {
                        entity.setCollisionOn(true);
                        index = i;
                    }
                }

                // Reset values collision boxes
                entity.setCollisionBoxX(entity.getCollisionBoxDefaultX());
                entity.setCollisionBoxY(entity.getCollisionBoxDefaultX());

                target[i].setCollisionBoxX(target[i].getCollisionBoxDefaultX());
                target[i].setCollisionBoxY(target[i].getCollisionBoxDefaultY());
            }
        }

        return index;
    }




    /**
     * This method handles the collision in the case that an NPC walks into the player.
     * Basically, it just checks that the NPC's currently set move is not going to put it in the same tile that the player is currently in.
     *
     * @param entity        - Entity that is being checked for movement
     */
    public boolean checkNpcCollisionWithPlayer(Entity entity){

        boolean hitPlayer = false;

        //get entity's collision box location
        entity.setCollisionBoxX(entity.getWorldX() + entity.getCollisionBox().x);
        entity.setCollisionBoxY(entity.getWorldY() + entity.getCollisionBox().y);

        //get objects solid area
        gamePanel.getPlayer().setCollisionBoxX(gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getCollisionBox().x);
        gamePanel.getPlayer().setCollisionBoxY(gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getCollisionBox().y);


        // Switch statement for each direction
        switch(entity.getDirection()) {
            case "up":

                entity.setCollisionBoxY(entity.getCollisionBox().y - entity.getSpeed());
                break;


            case "down":

                entity.setCollisionBoxY(entity.getCollisionBox().y + entity.getSpeed());
                break;


            case "left":

                entity.setCollisionBoxX(entity.getCollisionBox().x - entity.getSpeed());
                break;

            case "right":

                entity.setCollisionBoxX(entity.getCollisionBox().x + entity.getSpeed());
                break;
        }

        if(entity.getCollisionBox().intersects(gamePanel.getPlayer().getCollisionBox()) ){
            entity.setCollisionOn(true);
            hitPlayer = true;
        }


        // Reset values collision boxes
        entity.setCollisionBoxX(entity.getCollisionBoxDefaultX());
        entity.setCollisionBoxY(entity.getCollisionBoxDefaultX());

        gamePanel.getPlayer().setCollisionBoxX(gamePanel.getPlayer().getCollisionBoxDefaultX());
        gamePanel.getPlayer().setCollisionBoxY(gamePanel.getPlayer().getCollisionBoxDefaultY());

        return hitPlayer;
    }

}
