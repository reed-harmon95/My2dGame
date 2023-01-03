package main;

import java.awt.*;

public class EventManager {

    GamePanel gamePanel;
    EventBox eventCollisionBox[][];


    // Resets event once player leaves location
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;


    public EventManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        eventCollisionBox = new EventBox[gamePanel.getMaxWorldColumns()][gamePanel.getMaxWorldRows()];


        int column = 0;
        int row = 0;

        while(column < gamePanel.getMaxWorldColumns() && row < gamePanel.getMaxWorldRows()){

            eventCollisionBox[column][row] = new EventBox();
            eventCollisionBox[column][row].x = 23;
            eventCollisionBox[column][row].y = 23;
            eventCollisionBox[column][row].width = 2;
            eventCollisionBox[column][row].height = 2;

            eventCollisionBox[column][row].eventBoxDefaultX = eventCollisionBox[column][row].x;
            eventCollisionBox[column][row].eventBoxDefaultY = eventCollisionBox[column][row].y;


            column++;
            if(column == gamePanel.getMaxWorldColumns()){
                column = 0;
                row++;
            }
        }


    }


    public void checkEvent(){


        // Checking player distance to reset last event (if you want the event to run multiple times)
        int xDistance = Math.abs(gamePanel.getPlayer().getWorldX() - previousEventX);
        int yDistance = Math.abs(gamePanel.getPlayer().getWorldY() - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gamePanel.getTileSize()){
            canTouchEvent = true;
        }




        if(canTouchEvent == true) {

            if(triggerEvent(25, 16, "right") == true){
                // event happens
                damagePit(25, 16, gamePanel.dialogueState);
            }
            if(triggerEvent(24, 18, "any") == true){
                // event happens
                damagePit(25, 16, gamePanel.dialogueState);
            }
            if(triggerEvent(22, 11, "up") == true){
                // event happens
                healingPool(22, 11, gamePanel.dialogueState);
            }
            if(triggerEvent(20, 18, "any") == true){
                // event happens
                teleport(gamePanel.dialogueState);
            }
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
        eventCollisionBox[eventCol][eventRow].x = (eventCol * gamePanel.getTileSize()) + eventCollisionBox[eventCol][eventRow].x;
        eventCollisionBox[eventCol][eventRow].y = (eventRow * gamePanel.getTileSize()) + eventCollisionBox[eventCol][eventRow].y;


        // check collision
        if(gamePanel.getPlayer().collisionBox.intersects(eventCollisionBox[eventCol][eventRow])
                && eventCollisionBox[eventCol][eventRow].eventDone == false){
            if(gamePanel.getPlayer().getDirection().contentEquals(requiredDirection) || requiredDirection.contentEquals("any")){
                triggeredEvent = true;

                previousEventX = gamePanel.getPlayer().getWorldX();
                previousEventY = gamePanel.getPlayer().getWorldY();
            }
        }


        // RESET VALUES
        gamePanel.getPlayer().setCollisionBoxX(gamePanel.getPlayer().getCollisionBoxDefaultX());
        gamePanel.getPlayer().setCollisionBoxY(gamePanel.getPlayer().getCollisionBoxDefaultY());
        eventCollisionBox[eventCol][eventRow].x = eventCollisionBox[eventCol][eventRow].eventBoxDefaultX;
        eventCollisionBox[eventCol][eventRow].y = eventCollisionBox[eventCol][eventRow].eventBoxDefaultY;

        return triggeredEvent;
    }


    public void damagePit( int column, int row, int gameState){

        // The idea is to change to dialogue state to display the text to the screen when the user falls into the pit
        gamePanel.setGameState(gameState);
        gamePanel.getUserInterface().setDisplayDialogue("You fall into a pit");
        gamePanel.getPlayer().setCurrentLife(gamePanel.getPlayer().getCurrentLife() - 1);
        eventCollisionBox[column][row].eventDone = true;

        canTouchEvent = false;
    }


    public void healingPool(int column, int row, int gameState) {


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


}
