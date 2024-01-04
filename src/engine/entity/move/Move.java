package engine.entity.move;
import engine.entity.Character;
public abstract class Move {
    private String name;
    private int accuracy;

    public Move(String name, int accuracy) {
        this.name = name;
        this.accuracy = accuracy;
    }

    public String getName() {
        return name;
    }

    public MoveResult use(Character user, Character target) {

        int hitChance = (int) (Math.random() * 101) + 1;
        if(hitChance > accuracy)
            return new MoveResult(getTag(), MoveResultType.MISS, 0);;

        return applyMoveEffect(user, target);
    }
    protected abstract MoveResult applyMoveEffect(Character user, Character target);
    protected abstract String getTag();
    public void setName(String name) {
        this.name = name;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

}
