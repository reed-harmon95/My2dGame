package main;

import entity.Entity;

public class CollisionHandler {

    private GamePanel gamePanel;


    public CollisionHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


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
                        gamePanel.getTileManager().getTileSetList()[tile2].isCollision() == true) {

                    entity.setCollisionOn(true);
                    System.out.println("Entity Colliding with " + gamePanel.getTileManager().getTileSetList()[tile1].getTileName());
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
                    System.out.println("Entity Colliding with " + gamePanel.getTileManager().getTileSetList()[tile1].getTileName());
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
                    System.out.println("Entity Colliding with " + gamePanel.getTileManager().getTileSetList()[tile1].getTileName());
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
                    System.out.println("Entity Colliding with " + gamePanel.getTileManager().getTileSetList()[tile1].getTileName());
                }
                break;

        }
    }
}
