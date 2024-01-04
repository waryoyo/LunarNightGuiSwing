package engine.utility;

import java.awt.*;

import static engine.GamePanel.WINDOW_HEIGHT;
import static engine.GamePanel.WINDOW_WIDTH;
import static engine.utility.Constants.*;

public class GraphicsUtility {
    private GraphicsUtility(){}

    public static void drawPressEnter(Graphics graphics){
        graphics.setColor(Color.red);
        graphics.setFont(DIALOGUE_FONT);

        FontMetrics fontMetrics = graphics.getFontMetrics();
        int right = WINDOW_WIDTH;
        int bottom = WINDOW_HEIGHT;

        graphics.drawString(PRESS_ENTER,
                right - fontMetrics.stringWidth(PRESS_ENTER) - UNIVERSAL_MARGIN,
                bottom - fontMetrics.getHeight());

    }
}
