package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];


    // VOLUME CONTROL SETTINGS
    float currentVolume = 0;
    float previousVolume = 0;
    FloatControl floatControl;

    boolean isMuted = false;



    public Sound () {

        // Set the indices to the sound array which holds all the file paths to the wav files


        File tempFile = new File("res/sound/background/BackgroundMusic.wav");
        File tempFile1 = new File("res/sound/effects/coin.wav");
        File tempFile2 = new File("res/sound/effects/fanfare.wav");
        File tempFile3 = new File("res/sound/effects/powerup.wav");
        File tempFile4 = new File("res/sound/effects/unlock.wav");
        try {
            soundURL[0] = tempFile.toURI().toURL();
            soundURL[1] = tempFile1.toURI().toURL();
            soundURL[2] = tempFile2.toURI().toURL();
            soundURL[3] = tempFile3.toURI().toURL();
            soundURL[4] = tempFile4.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }



    }


    public void setFile(int index) {

        // Sets up the clips to be ready to be played
        try {

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL[index]);

            clip = AudioSystem.getClip();
            clip.open(audioStream);

            floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);



        } catch(Exception e) {
            System.out.println("Error when playing sound file: " + soundURL[index].getClass().getName());
        }
    }


    public void play() {

        clip.setFramePosition(0);
        clip.start();
    }

    public void loop() {

        //loop clip forever
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }


    public void stop() {

        clip.stop();
    }

    public void muteVolume() {

        //going from false to true
        if(isMuted == false){
            previousVolume = currentVolume;
            currentVolume = -80.0f;
            floatControl.setValue(currentVolume);
            isMuted = true;
        }else if(isMuted == true) {
            currentVolume = previousVolume;
            floatControl.setValue(currentVolume);
            isMuted = false;
        }
    }
}
