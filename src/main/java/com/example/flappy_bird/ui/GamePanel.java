package main.java.com.example.flappy_bird.ui;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public GamePanel() {
        setPreferredSize(new Dimension(MainFrame.WIDTH, MainFrame.HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocus();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.CYAN);
        g.fillRect(200, 300, 50, 50);
    }
}