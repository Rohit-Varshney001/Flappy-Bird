package main.java.com.example.flappy_bird.game.object;


import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class    Player {

    private int x;
    private int y;
    private final int width = 40;
    private final int height = 40;
    private int velocityX = 2; // horizontal speed
    private double velocityY = 0;     // vertical speed
    private final double gravity = 0.4;   // gravity acceleration

    private Image[] sprites;
    private int spriteIndex = 0;
    private int animationTimer = 0;
    private Image crashSprite;
    private boolean dead = false;


    public Player(int x, int y) {
        this.x = x;
        this.y = y;

        sprites = new Image[] {
                new ImageIcon(Player.class.getResource("/images/bird1.png")).getImage(),
                new ImageIcon(Player.class.getResource("/images/bird2.png")).getImage()
        };

        crashSprite = new ImageIcon(Player.class.getResource("/images/bird_crash.png")).getImage();

    }

    public void update(boolean gameover) {
        if (gameover) return;

        if (!dead) {
            velocityY += gravity;
            y += velocityY;

            if (y + height >= 600) {
                y =  600 - height;
                velocityY = 0;
                dead = true;
            }

            // flap animation
            animationTimer++;
            if (animationTimer > 5) {
                spriteIndex = (spriteIndex + 1) % sprites.length;
                animationTimer = 0;
            }
        } else {
            // if dead, velocityY can be frozen or you can let bird fall
        }
    }

    public void draw(Graphics g) {
        if (dead) {
            g.drawImage(crashSprite, x, y, width, height, null);
        } else {
            g.drawImage(sprites[spriteIndex], x, y, width, height, null);
        }
    }


    public void jump() {
        velocityY = -8; // upward force
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    public void setDead(boolean dead) {
        this.dead = dead;
    }
    public boolean isDead() {
        return dead;
    }



}
