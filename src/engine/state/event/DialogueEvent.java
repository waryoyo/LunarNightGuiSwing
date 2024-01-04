package engine.state.event;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import static engine.GamePanel.WINDOW_HEIGHT;
import static engine.GamePanel.WINDOW_WIDTH;
import static engine.utility.Constants.DIALOGUE_FONT;
import static engine.utility.Constants.UNIVERSAL_MARGIN;
import static engine.utility.GraphicsUtility.drawPressEnter;
import static engine.utility.Utility.wrapText;

public class DialogueEvent extends Event {
    String[] lines;
    ArrayList<String> printableLines;
    Event next;
    boolean printing;
    int xIndex = 0, yIndex = 0;
    public DialogueEvent(String eventName, String[] lines, EventManager eventManager) {
        super(eventName, eventManager);
        this.lines = lines;
        printableLines = new ArrayList<>(lines.length);

        for (String line : lines) {
            String[] output = wrapText(line, DIALOGUE_FONT, WINDOW_WIDTH);
            printableLines.addAll(Arrays.asList(output));
        }
    }


    @Override
    public void update() {

    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        graphics.setFont(DIALOGUE_FONT);
        graphics.setColor(Color.WHITE);
        printing = true;

        if(yIndex+1 > printableLines.get(xIndex).length()){
            if(xIndex != printableLines.size()-1){
                xIndex++;
                yIndex = 0;
            }
            else{
                yIndex = printableLines.get(xIndex).length() - 1;
                printing = false;
            }
        }
        for(int i = 0; i <= xIndex; i++){
            if(i == xIndex)
                graphics.drawString(printableLines.get(i).substring(0, yIndex+1), UNIVERSAL_MARGIN,
                        50 + (i*UNIVERSAL_MARGIN));
            else
                graphics.drawString(printableLines.get(i), UNIVERSAL_MARGIN,
                        50 + (i*UNIVERSAL_MARGIN));

        }

        if (!printing){
            drawPressEnter(graphics);
        }

        yIndex++;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(!printing && keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
            eventManager.ActiveEvent = next;
    }

    @Override
    public void keyReleased(int keyCode) {

    }
}
