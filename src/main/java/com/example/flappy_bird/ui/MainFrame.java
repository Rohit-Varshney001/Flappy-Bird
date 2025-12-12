package main.java.com.example.flappy_bird.ui;

import javax.swing.*;

public class MainFrame extends JFrame {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 640;

    public MainFrame() {
        setTitle("Flappy Bird - Java");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        add(new GamePanel());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
