package main.java.com.example.flappy_bird.core;

public class GameLoop implements Runnable{
    private static final int FPS = 60;
    private static final double FRAME_TIME = 1_000_000_000.0 / FPS;

    private volatile boolean running = false;
    private final Updatable updatable;

    public GameLoop(Updatable updatable) {
        this.updatable = updatable;
    }

    public void start() {
        if (running) return;
        running = true;
        Thread loopThread = new Thread(this, "GameLoop-Thread");
        loopThread.start();
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / FRAME_TIME;
            lastTime = now;



            // Update at fixed FPS
            while (delta >= 1) {
                updatable.update();
                delta--;
            }

            // Request rendering
            updatable.render();

            try {
                Thread.sleep(1); // prevent CPU overload
            } catch (InterruptedException ignored) {}
        }
    }
}
