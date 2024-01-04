package engine.choiceMenu;

import engine.entity.Enemy;
import engine.entity.Item;
import engine.entity.Player;

import java.awt.*;
import java.awt.event.KeyEvent;

import static engine.utility.Constants.BATTLE_MOVES_FONT;
import static engine.utility.Constants.CHOICE_MENU_HEIGHT;

public class ItemMenu extends ChoiceMenu{
    protected ItemMenu(ChoiceMenuManager menuManager, Player player, Enemy enemy) {
        super(menuManager, player, enemy);
    }

    @Override
    protected int currentUsedMenuLength() {
        return player.items.size();
    }

    @Override
    public void render(Graphics graphics) {

        if(currentUsedMenuLength() == 0){
            graphics.setFont(BATTLE_MOVES_FONT);
            graphics.setColor(Color.white);
            graphics.drawString("No items", 100, CHOICE_MENU_HEIGHT);
        }
        else{
            printSimpleVerticalMenu(graphics,
                    player.items.stream().map(Item::getItemName).toArray(String[]::new),
                    BATTLE_MOVES_FONT, true);
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
            if(currentUsedMenuLength() <= 0){
                menuManager.popBack();
            }
            else {
                Item selectedItem = player.items.get(selectedIndex);

                String message = String.format("Player used %s", selectedItem.getItemName());
                player.use(player, selectedItem);
                menuManager.pushBack(new MessagePrintMenu(menuManager, player, message, enemy));
            }
        }else{
            handleLeftRightPresses(keyEvent);
        }

    }
}
