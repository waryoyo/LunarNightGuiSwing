package engine.entity;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static engine.entity.CharacterStates.*;
import static engine.utility.Constants.*;
import static engine.utility.Utility.flipImage;
import static engine.utility.Utility.scaleImage;
import static java.lang.Math.ceil;

public class Player extends Character {
    private int totalXP, XPTillLvl;
    private int currency = 0;
    private final BufferedImage[] playerIdleSprites;
    private final BufferedImage[] playerRunSprites;
    private final BufferedImage[] playerAttackSprites;

    public Player(String name, int maxHP, int maxMP, int str, int def, int speed, int x, int y) {
        super(name, maxHP, maxMP, str, def, speed, x, y);
        this.totalXP = CalculateXPTillLvl(this.getLvl());
        this.XPTillLvl = CalculateXPTillLvl(this.getLvl() + 1);

        playerIdleSprites = loadSprites(Objects.requireNonNull(this.getClass().getResource("/player/idle")).getPath(), "idle");
        playerRunSprites = loadSprites(Objects.requireNonNull(this.getClass().getResource("/player/run")).getPath(), "run");
        playerAttackSprites = loadSprites(Objects.requireNonNull(this.getClass().getResource("/player/attack")).getPath(), "attack");

    }

    private int CalculateXPTillLvl(int lvl) {
        final double XPCONST1 = 0.3;
        final double XPCONST2 = 2.0;
        return (int) ((lvl / XPCONST1) * XPCONST2) + this.getTotalXP();
    }

    private void levelUp() {
        this.setLvl(this.getLvl() + 1);
        this.currency += 100;
    }

    public int getTotalXP() {
        return totalXP;
    }

    public int getXPTillLvl() {
        return XPTillLvl;
    }

    public void setTotalXP(int totalXP) {
        this.totalXP = totalXP;
        while (this.totalXP >= this.XPTillLvl) {
            levelUp();
            System.out.println("PLAYER LEVELED UP!! he is now level " + this.getLvl());
            this.XPTillLvl = CalculateXPTillLvl(this.getLvl() + 1);
            updateStats();
            this.restore();
        }
    }

    public void updateStats() {
        this.setMaxHP((int) (this.getMaxHP() * (1.25)));
        this.setMaxMP((int) (this.getMaxMP() * (1.25)));
        this.setStr(this.getStr() + 1);
        this.setDef(this.getDef() + 1);

    }

    public void updateTotalXP(int value) {
        setTotalXP(this.totalXP + value);
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    @Override
    public void update() {
        if(currentState == MOVING_TO_TARGET){
            if(currentX >= ENEMY1_X_POSITIONS[0] - PLAYER_ENEMY_MARGIN){
                currentX = ENEMY1_X_POSITIONS[0] - PLAYER_ENEMY_MARGIN;
                currentState = ATTACKING;
                restAnimation();
            }
            else
                move(4);

        } else if (currentState == MOVING_BACK) {
            if(currentX <= originX){
                currentX = originX;
                currentState = IDLE;
                restAnimation();
            }
            else
                move(-4);

        } else if (currentState == SURRENDERING) {
            if(currentX <= -150)
                setHp(-1);

            else
                move(-3);

        }
    }
    public void surrender(){
        currentState = SURRENDERING;
    }
    @Override
    public void render(Graphics graphics) {
        switch (currentState) {
            case IDLE -> {

                displayHealthBar(graphics, currentX + 10, currentY - 20);
                BufferedImage idlePlayer = playerIdleSprites[animationFrame];
                idlePlayer = scaleImage(idlePlayer, 3);
                graphics.drawImage(idlePlayer, currentX, currentY, null);

                if (animationFrame >= playerIdleSprites.length - 1)
                    animationFrame = 0;
                else {
                    timer++;
                    if (timer > 7) {
                        animationFrame++;
                        timer = 0;
                    }
                }
            }
            case MOVING_TO_TARGET -> {
                BufferedImage runPlayer = playerRunSprites[animationFrame];
                runPlayer = scaleImage(runPlayer, 3);
                graphics.drawImage(runPlayer, currentX, currentY, null);

                if (animationFrame >= playerRunSprites.length - 1)
                    animationFrame = 0;
                else {
                    timer++;
                    if (timer > 7) {
                        animationFrame++;
                        timer = 0;
                    }
                }
            }
            case ATTACKING -> {
                BufferedImage attackPlayer = playerAttackSprites[animationFrame];
                attackPlayer = scaleImage(attackPlayer, 3);
                graphics.drawImage(attackPlayer, currentX, currentY, null);

                if (animationFrame >= playerAttackSprites.length - 1) {
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
                BufferedImage runPlayer = playerRunSprites[animationFrame];
                runPlayer = scaleImage(runPlayer, 3);
                runPlayer = flipImage(runPlayer);
                graphics.drawImage(runPlayer, currentX, currentY, null);

                if (animationFrame >= playerRunSprites.length - 1) {
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
            case SURRENDERING -> {
                BufferedImage runPlayer = playerRunSprites[animationFrame];
                runPlayer = scaleImage(runPlayer, 3);
                runPlayer = flipImage(runPlayer);
                graphics.drawImage(runPlayer, currentX, currentY, null);

                if (animationFrame >= playerRunSprites.length - 1) {
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
