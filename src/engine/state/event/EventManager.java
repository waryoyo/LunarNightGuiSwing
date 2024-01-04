package engine.state.event;

import engine.entity.Enemy;
import engine.entity.Player;
import engine.entity.move.DamageMove;
import engine.state.GameOverState;
import engine.state.GameState;
import engine.state.GameStateManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static engine.utility.Constants.*;

public class EventManager implements GameState {
    private final ArrayList<Event> events;
    private final HashMap<String, DamageMove> moves;
    private final HashMap<String, Enemy> enemies;
    private final Player player;
    private final GameStateManager stateManager;
    public Event ActiveEvent;
    public EventManager(GameStateManager stateManager) {
        this.stateManager = stateManager;

        moves = new HashMap<>();
        initMoves();

        enemies = new HashMap<>();
        initEnemies();

        player = createPlayer();

        events = initEvents();
        ActiveEvent = events.get(0);

    }
    public void showGameOver(){
        stateManager.restToONE();
        stateManager.pushState(new GameOverState(stateManager));
    }
    private void initMoves(){

        moves.put("Punch", new DamageMove("Punch", 15, 0, 90));
        moves.put("Kick", new DamageMove("Kick", 25, 0, 75));
        moves.put("Slap", new DamageMove("Slap", 10, 0, 100));
        moves.put("Scratch", new DamageMove("Scratch", 12, 0, 95));
        moves.put("Bite", new DamageMove("Bite", 18, 0,  65));
        moves.put("Headbutt", new DamageMove("Headbutt", 20, 0, 85));
        moves.put("Body Slam", new DamageMove("Body Slam", 22, 0, 80));
        moves.put("Double Kick", new DamageMove("Double Kick", 18, 0, 90));
        moves.put("Tackle", new DamageMove("Tackle", 15, 0, 95));
        moves.put("Roundhouse Kick", new DamageMove("Roundhouse Kick", 30, 0, 70));

    }

    private Player createPlayer(){
        Player player = new Player("jack", 80, 20, 10, 10, 20,
                PLAYER_X_POSITION, PLAYER_Y_POSITION);
        player.moves.add(moves.get("Tackle"));
        player.moves.add(moves.get("Slap"));
        player.moves.add(moves.get("Bite"));
        player.moves.add(moves.get("Body Slam"));
        player.moves.add(moves.get("Double Kick"));


        return player;
    }
    private void initEnemies(){
        enemies.put("Fiend", new Enemy("Fiend", 80, 30, 15, 12, 10, 15));
        enemies.get("Fiend").moves.add(moves.get("Tackle"));
        enemies.get("Fiend").moves.add(moves.get("Slap"));
        enemies.get("Fiend").moves.add(moves.get("Bite"));


        enemies.put("Ghoul", new Enemy("Ghoul", 60, 30, 25, 8, 10, 25));
        enemies.get("Ghoul").moves.add(moves.get("Tackle"));
        enemies.get("Ghoul").moves.add(moves.get("Headbutt"));
        enemies.get("Ghoul").moves.add(moves.get("Bite"));


        enemies.put("Slime", new Enemy("Slime", 40, 10, 6, 8, 5, 7));
        enemies.get("Slime").moves.add(moves.get("Tackle"));


        enemies.put("Wisp", new Enemy("Wisp", 55, 12, 30, 5, 18, 30));
        enemies.get("Wisp").moves.add(moves.get("Bite"));
        enemies.get("Wisp").moves.add(moves.get("Tackle"));

    }
    private String[] loadScript(int id){
        List<String> listOfStrings = new ArrayList<>();

        try{
            URL res = this.getClass().getResource("/" + id + ".txt");
            BufferedReader bf = new BufferedReader(new FileReader(res.getPath()));

            String line = bf.readLine();

            while (line != null) {
                listOfStrings.add(line);
                line = bf.readLine();
            }
        }
        catch (IOException exception){
            System.out.print(exception.getMessage());
        };

        return listOfStrings.toArray(String[]::new);
    }

    private ArrayList<Event> initEvents(){

        ArrayList<Event> events = new ArrayList<>();

        /*
        HERE IS THE CREATION OF EVERY EVENT
        */

        DialogueEvent start = new DialogueEvent("Discovery", loadScript(1), this);
        events.add(start);

        DialogueEvent investigation = new DialogueEvent("Investigation", loadScript(2), this);
        events.add(investigation);

        ChoiceEvent emOrRes = new ChoiceEvent("Embrace or Resist",
                "The voices in your head tug at your sanity as you grapple with the newfound vampiric powers pulsating within.",
                new String[]{
                        "Embrace the vampiric abilities, feeling the surge of strength and agility coursing through your veins.",
                        "Resist the vampiric urges, relying on your traditional combat skills."
                }, this);
        events.add(emOrRes);

        BattleEvent battle = new BattleEvent("Fighting", this, player,
                enemies.get("Fiend").createGameEnemy(ENEMY1_X_POSITIONS[0], ENEMY1_Y_POSITIONS[0]));

        /*
        HERE IS THE CONNECTING OF THE EVENTS
         */

        start.next = investigation;
        investigation.next = emOrRes;
        emOrRes.nextEvents = new Event[]{battle, battle};

        return events;
    }
    public void start(){
        ActiveEvent = getHeadEvent();
    }

    public Event getHeadEvent(){
        return events.get(0);
    }

    @Override
    public void update() {
        ActiveEvent.update();
    }

    @Override
    public void render(Graphics graphics) {
        ActiveEvent.render(graphics);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        ActiveEvent.keyPressed(keyEvent);
    }

    @Override
    public void keyReleased(int keyCode) {

    }
}
