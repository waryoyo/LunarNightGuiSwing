package engine.utility;

import java.awt.*;

import static engine.utility.Constants.UNIVERSAL_MARGIN;

public class SlowPrintWrite {
    int xIndex = 0;
    int yIndex = 0;
    boolean printing = false;

    public void slowPrint(String lines, int x, int y, int end, Graphics graphics){

    }

    public void slowPrintArr(String[] lines, int x, int y, int end, Graphics graphics){
        if(yIndex+1 > lines[xIndex].length()){
            if(xIndex != lines.length-1){
                xIndex++;
                yIndex = 0;
            }
            else{
                yIndex = lines[xIndex].length() - 1;
                printing = false;
            }
        }
        for(int i = 0; i <= xIndex; i++){
            if(i == xIndex)
                graphics.drawString(lines[i].substring(0, yIndex+1), UNIVERSAL_MARGIN,
                        50 + (i*UNIVERSAL_MARGIN));
            else
                graphics.drawString(lines[i], UNIVERSAL_MARGIN,
                        50 + (i*UNIVERSAL_MARGIN));

        }
    }

}
