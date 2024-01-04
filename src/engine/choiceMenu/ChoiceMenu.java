package engine.choiceMenu;

import engine.entity.Enemy;
import engine.entity.Player;
import engine.state.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;

import static engine.utility.Constants.*;
import static engine.utility.Constants.BATTLE_ACTIONS;

public abstract class ChoiceMenu {
    Player player;
    Enemy enemy;
    protected int selectedIndex = 0;
    protected ChoiceMenuManager menuManager;
    protected ChoiceMenu(ChoiceMenuManager menuManager, Player player, Enemy enemy){
        this.menuManager = menuManager;
        this.player = player;
        this.enemy = enemy;

    }
    protected abstract int currentUsedMenuLength();
    public abstract void render(Graphics graphics);
    public abstract void keyPressed(KeyEvent keyEvent);
    protected void printSimpleHorizontalMenu(Graphics graphics, String[] items, Font font){
        graphics.setFont(font);
        graphics.setColor(Color.white);
        FontMetrics fontMetrics = graphics.getFontMetrics(font);
        int accumlatedWidth = 100;

        for (int i = 0; i < items.length; i++) {
            if (selectedIndex == i)
                graphics.setColor(Color.GREEN);
            else
                graphics.setColor(Color.white);

            graphics.drawString(items[i],
                    accumlatedWidth, CHOICE_MENU_HEIGHT);
            accumlatedWidth += fontMetrics.stringWidth(items[i]) + 40;
        }
    }
    protected void handleLeftRightPresses(KeyEvent keyEvent){
        if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(selectedIndex < currentUsedMenuLength() - 1)
                selectedIndex++;
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            if(selectedIndex > 0)
                selectedIndex--;
        }
    }
    protected void printSimpleVerticalMenu(Graphics graphics, String[] items, Font font, boolean select){
        graphics.setFont(font);
        graphics.setColor(Color.white);
        FontMetrics fontMetrics = graphics.getFontMetrics(font);
        int accumlatedWidth = 300;
        int accumlatedHeight = CHOICE_MENU_HEIGHT;

        for(int i = 0; i < items.length; i++){
            if(selectedIndex == i && select)
                graphics.setColor(Color.GREEN);
            else
                graphics.setColor(Color.white);

            graphics.drawString(items[i],
                    accumlatedWidth, accumlatedHeight);

            accumlatedHeight += fontMetrics.getHeight() + 5;
            if((i + 1) % 4 == 0){
                accumlatedWidth += 120;
                accumlatedHeight = CHOICE_MENU_HEIGHT;
            }
        }
    }

}
