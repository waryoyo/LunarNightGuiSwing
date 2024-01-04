package engine.state.event;

import engine.choiceMenu.ActionsMenu;
import engine.choiceMenu.ChoiceMenuManager;
import engine.choiceMenu.MessagePrintMenu;
import engine.entity.CharacterStates;
import engine.entity.Enemy;
import engine.entity.Player;
import engine.entity.move.MoveResult;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import static engine.GamePanel.WINDOW_HEIGHT;
import static engine.GamePanel.WINDOW_WIDTH;
import static engine.state.event.BattleEnumState.*;
import static engine.utility.Constants.MAIN_MENU_FONT;
import static engine.utility.Utility.getMoveResultMessage;

public class BattleEvent extends Event {

    private final BufferedImage backgroundImage;
    private final Player player;
    private final Enemy enemy;
    Event next;
    private ChoiceMenuManager menuManager;
    private int innerTurnCounter = 0, turnCounter = 1;

    public BattleEvent(String EVENT_NAME, EventManager eventManager, Player player, Enemy enemy) {
        super(EVENT_NAME, eventManager);

        URL res = this.getClass().getResource("/battleBackgroundSmall.png");
        this.player = player;
        this.enemy = enemy;
        this.menuManager = new ChoiceMenuManager();
        menuManager.pushBack(new ActionsMenu(menuManager, player, enemy));
        try {
            assert res != null;
            backgroundImage = ImageIO.read(res);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BattleEnumState checkBattleStatus() {
        if (player.getHp() == 0)
            return PLAYER_LOST;
        else if (enemy.getHp() == 0) {
            return PLAYER_WON;
        }
        return BATTLE_ONGOING;
    }


    private void EnemyTurn() {
        int choice;
        String message = "";

        Random random = new Random();
        choice = random.nextInt(enemy.moves.size());

        message += String.format("%s chose to use %s\n", enemy.getName(), enemy.moves.get(choice).getName());
        MoveResult moveResult = enemy.use(player, enemy.moves.get(choice));
        message += getMoveResultMessage(moveResult);
        enemy.currentState = CharacterStates.MOVING_TO_TARGET;

        menuManager.pushBack(new MessagePrintMenu(menuManager, player, message, enemy));
    }

    private void AdvanceTurn() {
        if (menuManager.isEmpty()) {
            innerTurnCounter++;
            if (innerTurnCounter % 2 == 0) {
                menuManager.pushBack(new ActionsMenu(menuManager, player, enemy));
            } else {
                EnemyTurn();
            }
        }


    }

    @Override
    public void update() {
        AdvanceTurn();
        player.update();
        enemy.update();

    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        graphics.drawImage(backgroundImage, 0, 0, null);
        graphics.setColor(Color.white);
        graphics.setFont(MAIN_MENU_FONT);
        graphics.drawString(String.format("Turn : %d", turnCounter), WINDOW_WIDTH / 2 - 50, 50);

        menuManager.render(graphics);

        player.render(graphics);
        enemy.render(graphics);

        handleBattleStatus(checkBattleStatus());
    }

    private void handleBattleStatus(BattleEnumState battleEnumState) {
        if (battleEnumState == PLAYER_WON) {
            eventManager.ActiveEvent = next;
        } else if (battleEnumState == PLAYER_LOST) {
            eventManager.showGameOver();
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        menuManager.keyPressed(keyEvent);
    }

    @Override
    public void keyReleased(int keyCode) {

    }
}
