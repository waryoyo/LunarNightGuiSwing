package engine;

import engine.state.GameOverState;
import engine.state.GameStateManager;
import engine.state.MainMenuState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, ActionListener, KeyListener {
    private Thread gameThread;
    private boolean running = false;
    private final GameStateManager stateManager;
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 600;

    public GamePanel(){
        Dimension windowDimensions = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);

        setPreferredSize(windowDimensions);
        setFocusable(true);
        requestFocusInWindow();
        setSize(windowDimensions);
        addKeyListener(this);

        stateManager = new GameStateManager();
        stateManager.pushState(new MainMenuState(stateManager));
        start();
    }

    public void start(){

        if(running) return;

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stop(){

        if(!running) return;

        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void run() {
        running = true;
        while(running){
            update();
            repaint();

            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
//        boolean renderScreen;
//
//        double thisTime;
//        double lastTime = System.nanoTime() / 1e9d;
//        double missedTime = 0;
//
//        while(running) {
//            renderScreen = false;
//            thisTime = System.nanoTime() / 1e9d;
//            missedTime += thisTime - lastTime;
//            lastTime = thisTime;
//
//            // for missed frames
//            double maxFps = 60;
//            double fpsDelay = 1 / maxFps;
//            while(missedTime >= fpsDelay) {
//                missedTime -= fpsDelay;
//                renderScreen = true;
//                update();
//            }
//            if(renderScreen) {
//                repaint();
//            }
//            else{
//                Thread.yield();
//
//            }
//        }

    }
    private void update(){
        stateManager.update();
    }
    private void render(Graphics graphics){
        stateManager.render(graphics);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        stateManager.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
