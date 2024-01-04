package engine.state;

import java.awt.*;
import java.awt.event.KeyEvent;

import static engine.GamePanel.WINDOW_HEIGHT;
import static engine.GamePanel.WINDOW_WIDTH;
import static engine.utility.Constants.GAME_OVER_FONT;

public class GameOverState implements GameState{
    private final GameStateManager gameStateManager;
    public GameOverState(GameStateManager stateManager){
        gameStateManager = stateManager;
    }
    @Override
    public void update() {

    }

    @Override
    public void render(Graphics graphics) {

        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        graphics.setColor(Color.red);
        graphics.setFont(GAME_OVER_FONT);
        FontMetrics fontMetrics = graphics.getFontMetrics();

        graphics.drawString("GAME OVER", WINDOW_WIDTH/2 - fontMetrics.stringWidth("GAMER OVER")/2, 200);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
            gameStateManager.popState();
    }

    @Override
    public void keyReleased(int keyCode) {

    }
}
