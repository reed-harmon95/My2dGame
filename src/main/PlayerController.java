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
        if(code == KeyEvent.VK_M && isMuted == false){
            isMuted = true;
            System.out.println("Muted: " + isMuted);
        } else if (code == KeyEvent.VK_M && isMuted == true) {
            isMuted = false;
            System.out.println("Muted: " + isMuted);
        }


        // GAME STATE
        if(code == KeyEvent.VK_P){

            // if playing, set to pause and vice versa
            if(gamePanel.getGameState() == gamePanel.playState){
                gamePanel.setGameState(gamePanel.pauseState);
            } else if(gamePanel.getGameState() == gamePanel.pauseState){
                gamePanel.setGameState(gamePanel.playState);
            }
        }


        // OPTIONAL: CHECK DRAW TIME
        if(code == KeyEvent.VK_T){
            if(checkDrawTime == true){
                checkDrawTime = false;
            } else if(checkDrawTime == false){
                checkDrawTime = true;
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
}
