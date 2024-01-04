package engine.entity;

import engine.entity.move.Move;
import engine.entity.move.MoveResult;

import java.awt.*;
import java.util.ArrayList;

public abstract class Character extends GameComponent {
    public final ArrayList<Move> moves;
    public final ArrayList<Item> items;
    private String name;
    private int maxHP, maxMP;
    private int hp, mp;
    private int str, def, speed;
    private int lvl;
    private int battleStr, battleDef, battleSpeed;

    public Character(String name, int maxHP, int maxMP, int str, int def, int speed, int x, int y) {
        super(x, y);
        this.name = name;
        this.maxHP = maxHP;
        this.maxMP = maxMP;
        this.str = str;
        this.def = def;
        this.hp = maxHP;
        this.mp = maxMP;
        this.speed = speed;
        this.lvl = 1;
        this.moves = new ArrayList<>();
        this.battleDef = def;
        this.battleStr = str;
        this.battleSpeed = speed;
        this.items = new ArrayList<>();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getMaxMP() {
        return maxMP;
    }

    public void setMaxMP(int maxMP) {
        this.maxMP = maxMP;
    }

    public int getHp() {
        return hp;
    }

    public int getBattleStr() {
        return battleStr;
    }

    public void setBattleStr(int battleStr) {
        this.battleStr = battleStr;
    }

    public int getBattleDef() {
        return battleDef;
    }

    public void setBattleDef(int battleDef) {
        this.battleDef = battleDef;
    }

    public int getBattleSpeed() {
        return battleSpeed;
    }

    public void setBattleSpeed(int battleSpeed) {
        this.battleSpeed = battleSpeed;
    }

    public void setHp(int hp) {
        this.hp = hp;
        if (this.hp < 0)
            this.hp = 0;
        else if (this.hp > this.maxHP)
            this.hp = maxHP;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
        if (this.mp < 0)
            this.mp = 0;
        else if (this.mp > this.maxMP)
            this.mp = maxMP;
    }

    public int getStr() {
        return str;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public void restore() {
        this.hp = this.maxHP;
        this.mp = this.maxMP;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public void updateHp(int value) {
        setHp(this.hp + value);

    }

    public void updateMp(int value) {

        setMp(this.mp + value);
    }

    public MoveResult use(Character target, Move move) {

        return move.use(this, target);
    }

    public int use(Character target, Item item) {
        item.use(this);
        items.remove(item);
        return 0;
    }
    protected void displayHealthBar(Graphics graphics, int x, int y) {
        graphics.setColor(Color.black);
        graphics.fillRect(x, y, 120, 20);
        int filledHp = (int) ((double)(getHp() * 120) / getMaxHP());
        graphics.setColor(Color.green);
        graphics.fillRect(x, y, filledHp, 20);

    }
    public void updateStr(int value) {
        setStr(this.str + value);
    }

    public void updateSpd(int value) {
        setSpeed(this.speed + value);
    }

    public void updateDef(int value) {
        setDef(this.def + value);
    }

    public void updateBattleStr(int value) {
        setBattleStr(this.battleStr + value);
    }

    public void updatebattleDef(int value) {
        setBattleDef(this.battleDef + value);
    }

    public void updateBattleSpeed(int value) {
        setBattleSpeed(this.battleSpeed + value);
    }

    public void enterBattleState() {
        this.battleStr = str;
        this.battleDef = def;
        this.battleSpeed = speed;
    }

    public void addItem(Item item) {
        items.add(item);
    }

}
