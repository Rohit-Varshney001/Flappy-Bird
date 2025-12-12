package main.java.com.example.flappy_bird.game;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        setTitle("Flappy Bird - Java");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new GamePanel());
        pack();

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}