package engine.state;

import java.awt.*;
import java.awt.event.KeyEvent;

public interface GameState {
    void update();
    void render(Graphics graphics);
    void keyPressed(KeyEvent keyEvent);
    void keyReleased(int keyCode);
}
