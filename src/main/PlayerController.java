package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class handles the key inputs from the keyboard and translates that to player movement which then gets drawn
 * onto the screen via the GamePanel class.
 */
public class PlayerController implements KeyListener {


    //These booleans handle the player movement. They get utilized by the GamePanel class to update the player data
    //within the gameplay loop
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean isMuted = false;
    private boolean enteredPressed = false;
    private boolean spacePressed = false;


    // Debug
    private boolean checkDrawTime = false;


    // GAME STATE
    GamePanel gamePanel;                                        // needed to access the game state

    public PlayerController (GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }


    //not used currently, just necessary for implementing the KeyListener class
    //this would just handle events in the case a unicode char representing a key is sent from the keyboard
    @Override
    public void keyTyped(KeyEvent keyEvent) {


    }


    //this handles the events in the case a key on the keyboard is pressed down by the user
    @Override
    public void keyPressed(KeyEvent keyEvent) {

        //get the key code from the keyboard
        int code = keyEvent.getKeyCode();


        // TITLE STATE
        // CONTROLS
        if(gamePanel.getGameState() == gamePanel.titleScreenState){
            if(code == KeyEvent.VK_W){
                if(gamePanel.getUserInterface().getMainMenuSelector() == 0){
                    gamePanel.getUserInterface().setMainMenuSelector(2);
                }
                else {
                    gamePanel.getUserInterface().setMainMenuSelector(gamePanel.getUserInterface().getMainMenuSelector()-1);
                }
            }
            if(code == KeyEvent.VK_S){
                if(gamePanel.getUserInterface().getMainMenuSelector() == 2){
                    gamePanel.getUserInterface().setMainMenuSelector(0);
                }
                else {
                    gamePanel.getUserInterface().setMainMenuSelector(gamePanel.getUserInterface().getMainMenuSelector()+1);
                }

            }
            if(code == KeyEvent.VK_ENTER){
                switch(gamePanel.getUserInterface().getMainMenuSelector()){

                    // select new game
                    case 0:
                        gamePanel.setGameState(gamePanel.playState);
                        gamePanel.playBackgroundMusic(0);
                        break;

                    // select load game
                    case 1:
                        System.out.println("Error: Cannot Load Game");
                        break;

                    // select quit
                    case 2:
                        System.exit(0);
                        break;
                }
            }
        }




        // PLAY STATE
        // CONTROLS
        if(gamePanel.getGameState() == gamePanel.playState){

            // MOVEMENT
            if(code == KeyEvent.VK_W){
                upPressed = true;
            }
            if(code == KeyEvent.VK_A){
                leftPressed = true;
            }
            if(code == KeyEvent.VK_S){
                downPressed = true;
            }
            if(code == KeyEvent.VK_D){
                rightPressed = true;
            }
            if(code == KeyEvent.VK_ENTER){
                enteredPressed = true;
            }


            if(code == KeyEvent.VK_M && isMuted == false){
                isMuted = true;
                System.out.println("Muted: " + isMuted);
            } else if (code == KeyEvent.VK_M && isMuted == true) {
                isMuted = false;
                System.out.println("Muted: " + isMuted);
            }



            // DEV OPTIONS
            if(code == KeyEvent.VK_SPACE && gamePanel.isDevOptions() == false){
                gamePanel.setDevOptions(true);
                checkDrawTime = true;
            } else if (code == KeyEvent.VK_SPACE && gamePanel.isDevOptions() == true) {
                gamePanel.setDevOptions(false);
                checkDrawTime = false;
            }


            // TOGGLE PAUSE
            if(code == KeyEvent.VK_P){

                gamePanel.setGameState(gamePanel.pauseState);
            }
        }



        // PAUSE STATE
        // CONTROLS
        else if(gamePanel.getGameState() == gamePanel.pauseState){


            // TOGGLE PAUSE
            if(code == KeyEvent.VK_P){

                gamePanel.setGameState(gamePanel.playState);
            }
        }



        // DIALOGUE STATE
        // CONTROLS
       else  if(gamePanel.getGameState() == gamePanel.dialogueState){
            if(code == KeyEvent.VK_ENTER){
                gamePanel.setGameState(gamePanel.playState);
            }
        }




    }


    //this handles the events in the case a key that was formerly pressed down by the user is no longer being pressed
    @Override
    public void keyReleased(KeyEvent keyEvent) {

        //get the key code from the keyboard
        int code = keyEvent.getKeyCode();


        //check which key was released (W,A,S,D)
        //flag the appropriate bool corresponding to the key release
        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }

    }


    /** GETTER METHODS */
    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void setMuted(boolean muted) {
        isMuted = muted;
    }

    public boolean isCheckDrawTime() {
        return checkDrawTime;
    }

    public boolean isEnteredPressed() {
        return enteredPressed;
    }

    public void setEnteredPressed(boolean enteredPressed) {
        this.enteredPressed = enteredPressed;
    }
}
