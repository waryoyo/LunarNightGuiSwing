package engine.choiceMenu;

import engine.entity.Player;
import engine.state.GameState;
import engine.state.event.BattleEvent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Stack;
public class ChoiceMenuManager implements GameState {
    private Stack<ChoiceMenu> menuStack;
    public boolean menuView = true;
//    public BattleEvent event;

    public ChoiceMenuManager(){
        menuStack = new Stack<>();
    }
    public void pushBack(ChoiceMenu menu){
        menuStack.push(menu);
    }
    public void popBack(){
        menuStack.pop();
    }
    public boolean isEmpty(){
        return menuStack.isEmpty();
    }
    public void clear(){
        menuStack.clear();
    }
    public ChoiceMenu peek(){
        return menuStack.peek();
    }
    public void reset(){
        while(menuStack.size() > 1){
            menuStack.pop();
        }
    }
    @Override
    public void update(){

    }

    @Override
    public void render(Graphics graphics) {
        if(menuView)
            peek().render(graphics);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(menuView)
            peek().keyPressed(keyEvent);
    }

    @Override
    public void keyReleased(int keyCode) {

    }
}
