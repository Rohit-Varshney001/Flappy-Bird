package main.java.com.example.flappy_bird.game;

import javax.sound.sampled.*;
import java.net.URL;

public class SoundPlayer {

    public static void playSound(String resourcePath) {
        try {
            URL url = SoundPlayer.class.getResource(resourcePath);
            if (url == null) {
                System.err.println("Sound resource not found: " + resourcePath);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}