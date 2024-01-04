package engine.entity;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static engine.entity.CharacterStates.*;
import static engine.entity.CharacterStates.IDLE;
import static engine.utility.Constants.*;
import static engine.utility.Utility.flipImage;
import static engine.utility.Utility.scaleImage;

public class Enemy extends Character implements Cloneable {
    private int xpValue;
    private int currencyValue;
    private String[] enemyTaunts = new String[]{};
    private BufferedImage[] enemyIdleSprites, enemyRunSprites, enemyAttackSprites;
    public String[] getEnemyTaunts() {
        return enemyTaunts;
    }

    public void setEnemyTaunts(String[] enemyTaunts) {
        this.enemyTaunts = enemyTaunts;
    }

    public Enemy(String name, int maxHP, int maxMP, int str, int def, int speed, int xpValue) {
        this(name, maxHP, maxMP, str, def, speed, xpValue, 0, 0);
    }
    public Enemy(String name, int maxHP, int maxMP, int str, int def, int speed, int xpValue, int x, int y) {
        super(name, maxHP, maxMP, str, def, speed, x, y);
        this.xpValue = xpValue;

        enemyIdleSprites = loadSprites(Objects.requireNonNull(
                this.getClass().getResource("/enemy/werewolf/idle")).getPath(),
                "idle");
        enemyRunSprites = loadSprites(Objects.requireNonNull(
                        this.getClass().getResource("/enemy/werewolf/walk")).getPath(),
                "walk");
        enemyAttackSprites = loadSprites(Objects.requireNonNull(
                        this.getClass().getResource("/enemy/werewolf/attack")).getPath(),
                "attack");
    }

    public int getXpValue() {
        return xpValue;
    }

    public void setXpValue(int xpValue) {
        this.xpValue = xpValue;
    }

    public int getCurrencyvalue() {
        return currencyValue;
    }

    public void setCurrencyvalue(int currencyvalue) {
        this.currencyValue = currencyvalue;
    }
    public Enemy createGameEnemy(int x, int y){
        Enemy newEnemy = this.clone();
        newEnemy.originX = x;
        newEnemy.currentX = x;

        newEnemy.originY = y;
        newEnemy.currentY = y;

        return newEnemy;
    }
    @Override
    public Enemy clone() {
        try {
            Enemy clone = (Enemy) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public void update() {
        if(currentState == MOVING_TO_TARGET){
            if(currentX <= PLAYER_X_POSITION + PLAYER_ENEMY_MARGIN){
                currentX = PLAYER_X_POSITION + PLAYER_ENEMY_MARGIN;
                currentState = ATTACKING;
                restAnimation();
            }
            else
                move(-4);

        } else if (currentState == MOVING_BACK) {
            if(currentX >= originX){
                currentX = originX;
                currentState = IDLE;
                restAnimation();
            }
            else
                move(4);
        }
    }

    @Override
    public void render(Graphics graphics) {

        switch (currentState) {
            case IDLE -> {
                BufferedImage idleEnemy = enemyIdleSprites[animationFrame];
                idleEnemy = scaleImage(idleEnemy, 1.3);
                idleEnemy = flipImage(idleEnemy);
                displayHealthBar(graphics, currentX + 5, currentY - 30);
                graphics.drawImage(idleEnemy, currentX, currentY, null);

                if(animationFrame >= enemyIdleSprites.length - 1)
                    animationFrame = 0;
                else{
                    timer++;
                    if(timer > 7){
                        animationFrame++;
                        timer = 0;
                    }
                }
            }
            case MOVING_TO_TARGET -> {
                BufferedImage idleEnemy = enemyRunSprites[animationFrame];
                idleEnemy = scaleImage(idleEnemy, 1.3);
                idleEnemy = flipImage(idleEnemy);
                graphics.drawImage(idleEnemy, currentX, currentY, null);

                if(animationFrame >= enemyRunSprites.length - 1)
                    animationFrame = 0;
                else{
                    timer++;
                    if(timer > 7){
                        animationFrame++;
                        timer = 0;
                    }
                }
            }
            case ATTACKING -> {
                BufferedImage attackEnemy = enemyAttackSprites[animationFrame];
                attackEnemy = scaleImage(attackEnemy, 1.3);
                attackEnemy = flipImage(attackEnemy);
                graphics.drawImage(attackEnemy, currentX, currentY, null);

                if (animationFrame >= enemyAttackSprites.length - 1) {
                    currentState = MOVING_BACK;
                    restAnimation();
                } else {
                    timer++;
                    if (timer > 7) {
                        animationFrame++;
                        timer = 0;
                    }
                }
            }
            case MOVING_BACK -> {
                BufferedImage runPlayer = enemyRunSprites[animationFrame];
                runPlayer = scaleImage(runPlayer, 1.3);
                graphics.drawImage(runPlayer, currentX, currentY, null);

                if (animationFrame >= enemyRunSprites.length - 1) {
                    currentState = MOVING_BACK;
                    restAnimation();
                } else {
                    timer++;
                    if (timer > 7) {
                        animationFrame++;
                        timer = 0;
                    }
                }
            }
        }

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(int keyCode) {

    }
}
