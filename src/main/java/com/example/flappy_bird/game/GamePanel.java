package main.java.com.example.flappy_bird.game;

import main.java.com.example.flappy_bird.game.object.Pipe;
import main.java.com.example.flappy_bird.game.object.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class GamePanel extends JPanel {

    private Player player;
    private Timer timer;
    private List<Pipe> pipes = new ArrayList<>();
    private Random random = new Random();
    private int pipeSpawnTimer = 0;
    private boolean gameOver = false;
    private int score = 0;
    private final Image background;

    private Image pipeRedTop;
    private Image pipeRedBottom;
    private Image pipeGreenTop;
    private Image pipeGreenBottom;
    private boolean spawnRedNext = true; // to alternate

    private Image land1;
    private Image land2;
    private int landX1 = 0;
    private int landX2;
    private int landY = 550; // vertical position of land
    private int landSpeed = 4; // same as pipe speed




    public GamePanel() {
//        new ImageIcon("C:/Users/Lenovo/Documents/flappy_bird/flappy_bird/src/main/resources/images/bird1.png").getImage(),

        land1 = new ImageIcon("C:/Users/Lenovo/Documents/flappy_bird/flappy_bird/src/main/resources/images/land_1.png").getImage();
        land2 = new ImageIcon("C:/Users/Lenovo/Documents/flappy_bird/flappy_bird/src/main/resources/images/land_2.png").getImage();

        landX2 = land1.getWidth(null);

        setPreferredSize(new Dimension(400, 600));
        setBackground(Color.BLACK);

        player = new Player(50, 300);

        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Restart if game over
                if (gameOver && (e.getKeyCode() == KeyEvent.VK_R || e.getKeyCode() == KeyEvent.VK_SPACE)) {
                    restartGame();
                    return;
                }
                // Jump if not game over
                if (!gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
                    SoundPlayer.playSound("C:/Users/Lenovo/Documents/flappy_bird/flappy_bird/src/main/resources/sounds/sfx_wing.wav");
                    player.jump();
                }
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (gameOver) {
                    int mx = e.getX();
                    int my = e.getY();

                    // Button bounds
                    Rectangle restartBtn = new Rectangle(120, 360, 160, 50);

                    if (restartBtn.contains(mx, my)) {
                        restartGame();
                    }
                }
            }
        });


        timer = new Timer(16, e -> gameLoop()); // ~60 FPS
        timer.start();
        background = new ImageIcon("C:/Users/Lenovo/Documents/flappy_bird/flappy_bird/src/main/resources/images/background.png").getImage();

        pipeRedTop = new ImageIcon("C:/Users/Lenovo/Documents/flappy_bird/flappy_bird/src/main/resources/images/pipe_red.png").getImage();
        pipeRedBottom = new ImageIcon("C:/Users/Lenovo/Documents/flappy_bird/flappy_bird/src/main/resources/images/pipe_red.png").getImage();


        pipeGreenTop = new ImageIcon("C:/Users/Lenovo/Documents/flappy_bird/flappy_bird/src/main/resources/images/pipe_green.png").getImage();
        pipeGreenBottom = new ImageIcon("C:/Users/Lenovo/Documents/flappy_bird/flappy_bird/src/main/resources/images/pipe_green.png").getImage();

        pipeRedTop = rotateImage(pipeRedTop, 180);
        pipeGreenTop = rotateImage(pipeGreenTop, 180);

    }

    private void gameLoop() {
        if (!gameOver) {
            player.update(gameOver);
            updatePipes();
            updateLand(); // move the land
            checkCollision();
            if (player.isDead()) {
                gameOver = true;
            }

        }
        repaint();
//        player.update(gameOver);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        // Draw Background
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

        // Draw player
        player.draw(g);

        // Draw Land
        g.drawImage(land1, landX1, landY, null);
        g.drawImage(land2, landX2, landY, null);

        // Draw pipes
        for (Pipe pipe : pipes) {
            pipe.draw(g);
        }
        // Draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Score: " + score, 10, 30);

        if (gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.drawString("GAME OVER", 100, 250);

            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Press SPACE or R to Restart", 60, 320);

            // Optional clickable button (rectangle)
            g.setColor(new Color(255, 255, 255, 150));
            g.fillRoundRect(120, 360, 160, 50, 20, 20);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 22));
            g.drawString("RESTART", 150, 394);
        }

    }
    private void updatePipes() {
        if (gameOver) return;

        pipeSpawnTimer++;

        if (pipeSpawnTimer > 90) {
            int topHeight = 100 + random.nextInt(300);

            Image topImg = spawnRedNext ? pipeRedTop : pipeGreenTop;
            Image bottomImg = spawnRedNext ? pipeRedBottom : pipeGreenBottom;

            pipes.add(new Pipe(400, topHeight, topImg, bottomImg));
            spawnRedNext = !spawnRedNext; // toggle for next pipe
            pipeSpawnTimer = 0;
        }

        List<Pipe> toRemove = new ArrayList<>();
        for (Pipe pipe : pipes) {
            pipe.update();
            if (!pipe.isScored() && pipe.getX() + pipe.getWidth() < 0) {
                score++;
                pipe.setScored(true);
                SoundPlayer.playSound("C:/Users/Lenovo/Documents/flappy_bird/flappy_bird/src/main/resources/sounds/sfx_point.wav");

            }
        }
        pipes.removeAll(toRemove);

    }
    private void checkCollision() {
        Rectangle playerBounds = player.getBounds();

        // Ground collision
        if (playerBounds.y + playerBounds.height > 600) {
            gameOver = true;
            return;
        }

        // Ceiling collision (optional)
        if (playerBounds.y < 0) {
            gameOver = true;
            return;
        }

        // Pipe collision
        for (Pipe pipe : pipes) {
            Rectangle topPipe = new Rectangle(
                    pipe.getX(),
                    0,
                    pipe.getWidth(),
                    pipe.getTopHeight()
            );

            Rectangle bottomPipe = new Rectangle(
                    pipe.getX(),
                    600 - pipe.getBottomHeight(),
                    pipe.getWidth(),
                    pipe.getBottomHeight()
            );

            if (playerBounds.intersects(topPipe) || playerBounds.intersects(bottomPipe) ||
                    playerBounds.y + playerBounds.height > 600 || playerBounds.y < 0) {
                SoundPlayer.playSound("C:/Users/Lenovo/Documents/flappy_bird/flappy_bird/src/main/resources/sounds/sfx_hit.wav");
                gameOver = true;
                player.setDead(true);  // add setter in Player.java
                return;
            }
        }
    }
    private void updateLand() {
        landX1 -= landSpeed;
        landX2 -= landSpeed;

        // loop images
        if (landX1 + land1.getWidth(null) <= 0) {
            landX1 = landX2 + land2.getWidth(null);
        }
        if (landX2 + land2.getWidth(null) <= 0) {
            landX2 = landX1 + land1.getWidth(null);
        }
    }
    private Image rotateImage(Image img, double angle) {
        int w = img.getWidth(null);
        int h = img.getHeight(null);

        BufferedImage rotated = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        g2d.rotate(Math.toRadians(angle), w / 2.0, h / 2.0);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        return rotated;
    }
    private void restartGame() {
        // Reset player
        player = new Player(50, 300);

        // Reset pipes
        pipes.clear();

        // Reset score & state
        score = 0;
        gameOver = false;
        pipeSpawnTimer = 0;

        // Give small swooshing sound (optional)
        SoundPlayer.playSound("C:/Users/Lenovo/Documents/flappy_bird/flappy_bird/src/main/resources/sounds/sfx_swooshing.wav");

    }



}
