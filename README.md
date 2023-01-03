# My2dGame

This is just a simple 2d game demo written in java-11.

Credit to this tutorial: https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq


Currently implemented:

    11/12:

             - Blank screen
             - Player controller for keyboard input
             - Player object and entity object to hold data for player and entities

    
    11/13:

             - Added player sprite and walking animation


    11/14:

             - Added tiles and tile manager to draw the background tiles to the screen
             - Implemented camera to allow for maps larger than screen size


    12/5:
            
            - Added collision at some point, forgot when
            - Created objects that the player can interact with inside the world
            - Added some documentation inside various methods of the GamePanel class for clarification 


    12/12:

            - Added object collision so the player can interact with the object
            - Added basic key inventory that keeps track of the number of keys a player has picked up in the game


    12/13:

            - Added boots object which doubles player's movement speed when picked up
            
            
    12/14:
    
            - Added background music and sound effects. Works in Linux. Mute functionality is broken
            
            
    12/16:
    
            - Added UI component to track relevant game data    
            - Added game completed fucntionality
            
            
    12/17:
    
            - Small tweak to the rendering algorithm to slightly improve performance
            - Added NPCs with collision and movement
            - Added game state for pause functionality
            - Updated images and map system. Maps utilize two-digit integers instead of single-digit
       
       
    12/20:
    
            - Added dialogue system to display NPC dialogue to screen when player collides with NPC and hits ENTER button
            - Added some helpful things to look at when fixing issues during runtime. Hit SPACEBAR to display
            - Helpful options include:
                            - Displaying player and visible entity collision boxes
                            - Displaying player and NPC coordinates on map
                            - Displaying time it takes to draw frame to screen in nanoseconds
    
    
    12/21:
    
            - Added main menu screen that loads before the game starts
                    - Main menu screen "load game" option does not currently work as load functionality is not currently implemented
                    
                                     
    1/2:
    
            - Added events to the game so that player can trigger random events when entering a tile
                    - Events added:
                                    - Damage Pit
                                    - Healing Pool
                                    - Teleport

    1/3:
    
            - Reworked the rendering algorithm to allow for the player to appear behind entities when standing directly behind them(in the case the entity's collision box is smaller than the image representing the entity)
            - Moved all classes inheriting the SuperObject class to inheriting the Entity class

