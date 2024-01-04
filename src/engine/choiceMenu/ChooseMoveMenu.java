package engine.choiceMenu;

import engine.entity.CharacterStates;
import engine.entity.Enemy;
import engine.entity.Player;
import engine.entity.move.DamageMove;
import engine.entity.move.Move;
import engine.entity.move.MoveResult;
import engine.entity.move.MoveResultType;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.Timer;

import static engine.utility.Constants.BATTLE_MOVES_FONT;
import static engine.utility.Utility.getMoveResultMessage;

public class ChooseMoveMenu extends ChoiceMenu {
    Timer timer;
    protected ChooseMoveMenu(ChoiceMenuManager menuManager, Player player, Enemy enemy) {
        super(menuManager, player, enemy);
    }


    @Override
    public void render(Graphics graphics) {
        printSimpleVerticalMenu(graphics, player.moves.stream().map(Move::getName).toArray(String[]::new), BATTLE_MOVES_FONT, true);
    }

    @Override
    protected int currentUsedMenuLength() {
        return player.moves.size();
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
            player.currentState = CharacterStates.MOVING_TO_TARGET;
            MoveResult moveResult = player.use(enemy, player.moves.get(selectedIndex));
            String message = getMoveResultMessage(moveResult);
            menuManager.pushBack(
                    new MessagePrintMenu(menuManager, player,
                            message, enemy));
        }
        else {
            handleLeftRightPresses(keyEvent);
        }
    }

}
