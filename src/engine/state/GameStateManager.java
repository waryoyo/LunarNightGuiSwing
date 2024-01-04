package engine.state;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class GameStateManager{
    Stack<GameState> states;
    public GameStateManager(){
        states = new Stack<>();
    }
    public void pushState(GameState state){
        states.add(state);
    }
    public void restToONE(){
        while(states.size() > 1)
            states.pop();

    }
    public void popState(){
        states.pop();
    }
    public void update(){
        states.peek().update();
    }
    public void render(Graphics graphics){
        states.peek().render(graphics);
    }
    public void keyPressed(KeyEvent keyEvent) {
        states.peek().keyPressed(keyEvent);
    }
    public void keyReleased(int keyCode) {
        states.peek().keyReleased(keyCode);
    }
}
