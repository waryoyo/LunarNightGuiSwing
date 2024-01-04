package engine.choiceMenu;

import engine.entity.Enemy;
import engine.entity.Player;

import java.awt.*;
import java.awt.event.KeyEvent;

import static engine.choiceMenu.BattleMenuStates.CHOOSE_MOVE;
import static engine.utility.Constants.*;

public class ActionsMenu extends ChoiceMenu {
    public ActionsMenu(ChoiceMenuManager menuManager, Player player, Enemy enemy) {
        super(menuManager, player, enemy);
    }

    @Override
    protected int currentUsedMenuLength() {
        return BATTLE_ACTIONS.length;
    }

    @Override
    public void render(Graphics graphics) {
        printSimpleHorizontalMenu(graphics, BATTLE_ACTIONS, BATTLE_ACTIONS_FONT);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
            switch (selectedIndex){
                case 0 -> {
                    menuManager.pushBack(new ChooseMoveMenu(menuManager, player, enemy));
                }
                case 1 ->{
                    menuManager.pushBack(new ObserveMenu(menuManager, player, enemy));
                }
                case 2 ->{
                    menuManager.pushBack(new SurrenderMenu(menuManager, player, "Jack has chosen the path of least resistance.", enemy));
                }
                case 3 ->{
                    menuManager.pushBack(new ItemMenu(menuManager, player, enemy));
                }
            }
        }
        else {
            handleLeftRightPresses(keyEvent);
        }
    }

}
