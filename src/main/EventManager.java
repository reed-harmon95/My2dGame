package main;

import java.awt.*;

public class EventManager {

    GamePanel gamePanel;
    Rectangle eventCollisionBox;
    private int eventCollisionDefaultX, eventCollisionDefaultY;

    public EventManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        eventCollisionBox = new Rectangle();
        eventCollisionBox.x = 23;
        eventCollisionBox.y = 23;
        eventCollisionBox.width = 2;
        eventCollisionBox.height = 2;

        eventCollisionDefaultX = eventCollisionBox.x;
        eventCollisionDefaultY = eventCollisionBox.y;


    }


    public void checkEvent(){


        if(triggerEvent(25, 16, "right") == true){
            // event happens
            damagePit(gamePanel.dialogueState);
        }
        if(triggerEvent(22, 11, "up") == true){
            // event happens
            healingPool(gamePanel.dialogueState);
        }
        if(triggerEvent(20, 18, "any") == true){
            // event happens
            teleport(gamePanel.dialogueState);
        }

    }


    public boolean triggerEvent (int eventCol, int eventRow, String requiredDirection){

        boolean triggeredEvent = false;


        // Ayyo I have no idea why I am off by 1 for the rows and columns, but just roll with it
        // Realistically, it is probably due to the fact that counting starts at 0,0 for rows and columns
        eventCol++;
        eventRow++;


        // get player collision coords
        gamePanel.getPlayer().collisionBox.x = gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().collisionBox.x;
        gamePanel.getPlayer().collisionBox.y = gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().collisionBox.y;

        // get event collision coords
        eventCollisionBox.x = (eventCol * gamePanel.getTileSize()) + eventCollisionBox.x;
        eventCollisionBox.y = (eventRow * gamePanel.getTileSize()) + eventCollisionBox.y;


        // check collision
        if(gamePanel.getPlayer().collisionBox.intersects(eventCollisionBox)){
            if(gamePanel.getPlayer().getDirection().contentEquals(requiredDirection) || requiredDirection.contentEquals("any")){
                triggeredEvent = true;
            }
        }


        // RESET VALUES
        gamePanel.getPlayer().setCollisionBoxX(gamePanel.getPlayer().getCollisionBoxDefaultX());
        gamePanel.getPlayer().setCollisionBoxY(gamePanel.getPlayer().getCollisionBoxDefaultY());
        eventCollisionBox.x = eventCollisionDefaultX;
        eventCollisionBox.y = eventCollisionDefaultY;

        return triggeredEvent;
    }


    public void damagePit( int gameState){

        // The idea is to change to dialogue state to display the text to the screen when the user falls into the pit
        gamePanel.setGameState(gameState);
        gamePanel.getUserInterface().setDisplayDialogue("You fall into a pit");
        gamePanel.getPlayer().setCurrentLife(gamePanel.getPlayer().getCurrentLife() - 1);
    }


    public void healingPool(int gameState) {


        if(gamePanel.getPlayerController().isEnteredPressed() == true){
            gamePanel.setGameState(gameState);
            gamePanel.userInterface.setDisplayDialogue("You have recovered some health");
            if(gamePanel.getPlayer().getCurrentLife() < gamePanel.getPlayer().getMaxLife()){
                gamePanel.getPlayer().setCurrentLife(gamePanel.getPlayer().getCurrentLife() + 1);
            }
        }
    }


    public void teleport( int gameState){

        // The idea is to change to dialogue state to display the text to the screen when the user falls into the pit
        gamePanel.setGameState(gameState);
        gamePanel.getUserInterface().setDisplayDialogue("Teleport!");
        gamePanel.getPlayer().setWorldX(gamePanel.getTileSize() * 34);          // x: 33
        gamePanel.getPlayer().setWorldY(gamePanel.getTileSize() * 7);           // y: 6
    }



    public Rectangle getEventCollisionBox() {
        return eventCollisionBox;
    }


    public int getEventCollisionDefaultX() {
        return eventCollisionDefaultX;
    }

    public void setEventCollisionDefaultX(int eventCollisionDefaultX) {
        this.eventCollisionDefaultX = eventCollisionDefaultX;
    }

    public int getEventCollisionDefaultY() {
        return eventCollisionDefaultY;
    }

    public void setEventCollisionDefaultY(int eventCollisionDefaultY) {
        this.eventCollisionDefaultY = eventCollisionDefaultY;
    }
}
