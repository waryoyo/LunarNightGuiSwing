package engine.utility;

import engine.entity.move.DamageMove;
import engine.entity.move.MoveResult;
import engine.entity.move.MoveResultType;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Utility {
    private Utility(){}

    public static String[] wrapText(String text, Font font, int maxWidth) {
        Canvas c = new Canvas();
        FontMetrics fontMetrics = c.getFontMetrics(font);

        String[] words = text.split("\\s+");
        StringBuilder line = new StringBuilder();
        java.util.List<String> wrappedLines = new java.util.ArrayList<>();

        for (String word : words) {
            if (fontMetrics.stringWidth(line + word) < maxWidth - 60) {
                line.append(word).append(" ");
            } else {
                wrappedLines.add(line.toString().trim());
                line = new StringBuilder(word + " ");
            }
        }

        if (!line.isEmpty()) {
            wrappedLines.add(line.toString().trim());
        }

        return wrappedLines.toArray(new String[0]);
    }

    public static BufferedImage scaleImage(BufferedImage bufferedImage, double factor){

        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        // Create a new image of the proper size
        int w2 = (int) (w * factor);
        int h2 = (int) (h * factor);
        BufferedImage after = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_ARGB);
        AffineTransform scaleInstance = AffineTransform.getScaleInstance(factor, factor);
        AffineTransformOp scaleOp
                = new AffineTransformOp(scaleInstance, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        scaleOp.filter(bufferedImage, after);
        return after;
    }
    public static BufferedImage flipImage(BufferedImage bufferedImage){
        int width = bufferedImage.getWidth();

        AffineTransform transform = AffineTransform.getScaleInstance(-1, 1);
        transform.translate(-width, 0);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(bufferedImage, null);

    }
    public static String getMoveResultMessage(MoveResult moveResult) {
        String message;
        if(Objects.equals(moveResult.moveType(), DamageMove.TAG)){
            if (moveResult.type() == MoveResultType.MISS)
                message = "it was a total miss";
            else if(moveResult.type() == MoveResultType.CRIT)
                message = String.format("it was a critical hit!! dealing %d points of damage!\n", moveResult.damageDealt());
            else
                message = String.format("it dealt %d points of damage!\n", moveResult.damageDealt());
        } else {
            if (moveResult.type() == MoveResultType.MISS)
                message = "it was a total miss!";
            else
                message = "the effect has been applied successfully!";
        }
        return message;
    }

}
