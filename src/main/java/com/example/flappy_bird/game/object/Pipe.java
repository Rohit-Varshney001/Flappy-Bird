package main.java.com.example.flappy_bird.game.object;
import java.awt.*;

public class Pipe {

    private int x;
    private int width = 60;

    private int topHeight;
    private int bottomHeight;

    private int gap = 150;      // space for player to pass
    private int speed = 3;
    private boolean scored = false;

    private Image topImage;
    private Image bottomImage;


    public Pipe(int startX, int topHeight, Image topImage, Image bottomImage) {
        this.x = startX;
        this.topHeight = topHeight;
        this.bottomHeight = 600 - (topHeight + gap); // panel height = 600
        this.width = 50;

        this.topImage = topImage;
        this.bottomImage = bottomImage;

    }

    public void update() {
        x -= speed;
    }

    public void draw(Graphics g) {
        g.drawImage(topImage, x, 0, width, topHeight, null);
        g.drawImage(bottomImage, x, 600 - bottomHeight, width, bottomHeight, null);
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }
    public int getTopHeight() {
        return topHeight;
    }

    public int getBottomHeight() {
        return bottomHeight;
    }

    public boolean isScored() {
        return scored;
    }

    public void setScored(boolean scored) {
        this.scored = scored;
    }

}
