import engine.GamePanel;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main {

    public static void playMusic(String filePath) {
        try {
            File musicPath = new File(filePath);
            if(musicPath.exists()){
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        String fileName = "Final Fantasy 10 Battle Theme.wav";
        playMusic(fileName);
        JFrame window = new JFrame();
        Image image = new ImageIcon("resources/icons/OIG-9.png").getImage();
        window.setTitle("Rogue Realms 2.0");
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocation(300, 300);
        window.setIconImage(image);

        System.setProperty("awt.useSystemAAFontSettings", "on");
//        window.setAlwaysOnTop(true);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setVisible(true);



    }

}