package engine.choiceMenu;

import engine.entity.Enemy;
import engine.entity.Player;

import java.awt.event.KeyEvent;

public class SurrenderMenu extends MessagePrintMenu{
    public SurrenderMenu(ChoiceMenuManager menuManager, Player player, String message, Enemy enemy) {
        super(menuManager, player, message, enemy);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
//        super.keyPressed(keyEvent);
        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
            player.surrender();
    }
}
