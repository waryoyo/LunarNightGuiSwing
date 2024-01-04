package engine.entity;

import engine.state.GameState;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public abstract class GameComponent implements GameState {
    public CharacterStates currentState;
    protected int animationFrame = 0, timer = 0;
    protected int currentX, currentY;
    protected int originX, originY;

    protected GameComponent(int x, int y){
        currentState = CharacterStates.IDLE;
        originX = x;
        currentX = x;

        originY = y;
        currentY = y;

    }

//    public abstract void update();
//    public abstract void render(Graphics graphics);
    protected BufferedImage[] loadSprites(String folderPath, String resourceName){
        int count = 0;
        folderPath = folderPath.substring(1);
        BufferedImage[] sprites;

        try (Stream<Path> files = Files.list(Paths.get(folderPath))) {
            count = (int) files.count();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
            sprites = new BufferedImage[count];
        try {
            for(int i = 1; i <= count; i++){
                String fileName = folderPath + "/" + resourceName + i + ".png";
                sprites[i-1] = ImageIO.read(new File(fileName));
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return sprites;
    }
    protected void move(int xAmount){
        currentX += xAmount;
    }
    protected void move(int xAmount, int yAmount){
        currentX += xAmount;
        currentY += yAmount;
    }
    public void restAnimation(){
        timer = 0;
        animationFrame = 0;
    }
}
