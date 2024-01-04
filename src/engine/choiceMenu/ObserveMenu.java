package engine.choiceMenu;

import engine.entity.Enemy;
import engine.entity.Player;
import engine.entity.Character;

import java.awt.*;
import java.awt.event.KeyEvent;

import static engine.utility.Constants.*;
import static engine.utility.Constants.BATTLE_ACTIONS_FONT;

public class ObserveMenu extends ChoiceMenu {
    private final int SELECTING_TARGET = 0;
    private final int SHOWING_STATS = 1;
    private int currentMenuState = SELECTING_TARGET;
    private final String[] observeOptions;

    protected ObserveMenu(ChoiceMenuManager menuManager, Player player, Enemy enemy) {
        super(menuManager, player, enemy);
        observeOptions = new String[]{"My stats", enemy.getName() + "'s stats"};
    }
    private void printTargetsMenu(Graphics graphics){
        printSimpleHorizontalMenu(graphics, observeOptions, DIALOGUE_FONT);
    }
    private void printObserve(Graphics graphics){
        printSimpleVerticalMenu(graphics, getCharacterStats(selectedIndex == 0? player : enemy), BATTLE_MOVES_FONT, false);
    }
    @Override
    protected int currentUsedMenuLength() {
        return observeOptions.length;
    }

    @Override
    public void render(Graphics graphics) {
        if(currentMenuState == SELECTING_TARGET)
            printTargetsMenu(graphics);
        else
            printObserve(graphics);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
            if(currentMenuState == SELECTING_TARGET)
                currentMenuState = SHOWING_STATS;


            else
                menuManager.reset();

        }
        else {
            if(currentMenuState == SELECTING_TARGET)
                handleLeftRightPresses(keyEvent);
        }
    }
    private String[] getCharacterStats(Character character) {
        String[] stats = new String[6];

        stats[0] = String.format("Name : %s", character.getName());
        stats[1] = String.format("hp : %d/%d", character.getHp(), character.getMaxHP());
        stats[2] = String.format("sp : %d/%d", character.getMp(), character.getMaxMP());
        stats[3] = String.format("str : %d", character.getBattleStr());
        stats[4] = String.format("def : %d", character.getBattleDef());
        stats[5] = String.format("spd : %d", character.getBattleSpeed());

        return stats;
    }
}
