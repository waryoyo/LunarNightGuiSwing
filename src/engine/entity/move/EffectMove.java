package engine.entity.move;
import engine.entity.Character;

public class EffectMove extends Move{
    public static final String TAG = "Effect";

    public EffectMove(String name, int accuracy) {
        super(name, accuracy);
    }

    @Override
    protected MoveResult applyMoveEffect(Character user, Character target) {
        return null;
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
