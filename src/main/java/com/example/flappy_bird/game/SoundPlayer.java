package main.java.com.example.flappy_bird.game;

import javax.sound.sampled.*;
import java.io.File;

public class SoundPlayer {

    public static void playSound(String filePath) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}