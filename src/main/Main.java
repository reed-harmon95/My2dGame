package main;

import javax.swing.*;


/**
 * This is the driver class where the game starts running. It creates a JFrame and then creates an instance of the
 * GamePanel class to start an instance of the game.
 */
public class Main {
    public static void main(String[] args) {

        //creating the window for the game to be played
        JFrame window = new JFrame();
        GamePanel gamePanel = new GamePanel();


        //setting up basic screen functionalities
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(gamePanel);                  //add the game panel to the window
        window.pack();                          //sizes the window to the size of the gamePanel
        window.setResizable(false);
        window.setTitle("2D Game");
        window.setLocationRelativeTo(null);     //centers the game screen on the computer monitor
        window.setVisible(true);


        //load up the game and start running the thread
        gamePanel.initialSetup();
        gamePanel.startGameThread();
    }
}