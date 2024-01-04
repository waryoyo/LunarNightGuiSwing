package engine.entity.move;

import static java.lang.Math.max;
import engine.entity.Character;

public class DamageMove extends Move{
    public static final String TAG = "Damage";
    private int power, type, crit;

    public DamageMove(String name, int power, int type, int accuracy) {
        super(name, accuracy);
        this.power = power;
        this.type = type;
    }

    @Override
    protected MoveResult applyMoveEffect(Character user, Character target) {
        boolean dealtCrit = false;

        int baseDamage = (int) max( ((double) user.getStr() / 10) * getPower(), 1);
        int extraRandomDamage = (int) (Math.random() * (baseDamage/10) );
        int totalDamage = baseDamage + extraRandomDamage;


        int critChance = (int) (Math.random() * 101) + 1;
        if(critChance <= 5){
            dealtCrit = true;
            totalDamage = (int)(totalDamage * 1.5);
        }

        target.updateHp(totalDamage * -1);


        return new MoveResult(TAG, dealtCrit? MoveResultType.CRIT : MoveResultType.HIT, totalDamage);
    }

    @Override
    protected String getTag() {
        return TAG;
    }


    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCrit() {
        return crit;
    }

    public void setCrit(int crit) {
        this.crit = crit;
    }
}
