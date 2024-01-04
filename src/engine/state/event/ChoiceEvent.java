package engine.state.event;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static engine.GamePanel.WINDOW_HEIGHT;
import static engine.GamePanel.WINDOW_WIDTH;
import static engine.utility.Constants.CHOICE_FONT;
import static engine.utility.Constants.DIALOGUE_FONT;
import static engine.utility.Utility.wrapText;

public class ChoiceEvent extends Event {
    Event[] nextEvents ;
    String choiceMessage;
    String[] choices;
    ArrayList<String> choiceMessageParts;
    private int selectedChoice = 0;

    public ChoiceEvent(String eventName, String choiceMessage, String[] choices, EventManager eventManager) {
        super(eventName, eventManager);
        this.choiceMessage = choiceMessage;
        this.choiceMessageParts = new ArrayList<>();
        this.choices=choices;

        for (String line : choices) {
            String[] output = wrapText(line, DIALOGUE_FONT, WINDOW_WIDTH);
            choiceMessageParts.addAll(Arrays.asList(output));
        }
    }


    public Event[] getNextEvents() {
        return nextEvents;
    }

    public void setNextEvents(Event[] nextEvents) {
        this.nextEvents = nextEvents;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics graphics) {
        int yIndex = 0;

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        graphics.setFont(DIALOGUE_FONT);
        graphics.setColor(Color.WHITE);

        for(int i = 0; i < choiceMessageParts.size(); i++){
            yIndex = 50 + (i * 30);
            graphics.drawString(choiceMessageParts.get(i), 30, yIndex);
        }


        for (int i = 0; i < choices.length; i++) {
            graphics.setFont(CHOICE_FONT);
            if (i == selectedChoice) {
                graphics.setColor(Color.YELLOW);
            }
            graphics.drawString(choices[i], 30, yIndex + (i + 1) * 30);
            graphics.setColor(Color.WHITE);
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN){
            if(selectedChoice < choices.length - 1){
                selectedChoice++;
            }
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
            if(selectedChoice > 0){
                selectedChoice--;
            }
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
            eventManager.ActiveEvent = nextEvents[selectedChoice];
        }
    }

    @Override
    public void keyReleased(int keyCode) {

    }
}
