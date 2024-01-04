package engine.choiceMenu;

import engine.entity.CharacterStates;
import engine.entity.Enemy;
import engine.entity.Player;

import java.awt.*;
import java.awt.event.KeyEvent;

import static engine.utility.Constants.*;
import static engine.utility.Constants.BATTLE_ACTIONS;
import static engine.utility.GraphicsUtility.drawPressEnter;

public class MessagePrintMenu extends ChoiceMenu {
    String message;
    public MessagePrintMenu(ChoiceMenuManager menuManager, Player player, String message, Enemy enemy) {
        super(menuManager, player, enemy);
        this.message = message;
    }

    @Override
    protected int currentUsedMenuLength() {
        return 0;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setFont(BATTLE_ACTIONS_FONT);
        graphics.setColor(Color.white);

        graphics.drawString(message,
                100, CHOICE_MENU_HEIGHT);

        drawPressEnter(graphics);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER && player.currentState == CharacterStates.IDLE){
            menuManager.clear();
        }
    }
}
