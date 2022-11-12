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


        //check which key was pressed (W,A,S,D)
        //flag the appropriate bool corresponding to the key press
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
}
