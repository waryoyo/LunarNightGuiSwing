package engine.state;

import engine.state.event.EventManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static engine.GamePanel.WINDOW_HEIGHT;
import static engine.GamePanel.WINDOW_WIDTH;
import static engine.utility.Constants.MAIN_MENU_FONT;

public class MainMenuState implements GameState {

    private final int STARTGAME = 0;
    private final int OPTIONS = 1;
    private final int EXIT = 2;
    private final GameStateManager manager;
    private final String[] options = {"Start Game", "Options", "Exit"};
    private int selected = 0;

    public MainMenuState(GameStateManager manager) {
        this.manager = manager;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        graphics.setFont(MAIN_MENU_FONT);
        for (int i = 0; i < options.length; i++) {
            graphics.setColor(Color.white);

            if (i == selected)
                graphics.setColor(Color.GREEN);


            graphics.drawString(options[i], 30, 40 + (i * 50));
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
            if (selected < options.length - 1) {
                selected++;
            }
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
            if (selected > 0) {
                selected--;
            }
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            switch (selected) {
                case STARTGAME:
                    manager.pushState(new EventManager(manager));
                    break;
                case OPTIONS:
                    break;
                case EXIT:
                    exitConfirmation();
                    break;
            }
        }

    }

    private void exitConfirmation() {
        ImageIcon image = new ImageIcon("resources/icons/OIG-8.png");
        JDialog dialog = new JDialog();
        dialog.setSize(300, 150);
        dialog.setTitle("Exit Confirmation");
        dialog.setLayout(new BorderLayout());
        dialog.setIconImage(image.getImage());
        dialog.getContentPane().setBackground(Color.BLACK);

        JLabel label1 = new JLabel("You are about to leave the realms behind.");
        JLabel label2 = new JLabel("Are you sure you want to exit?");

        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setHorizontalAlignment(SwingConstants.CENTER);

        label1.setForeground(Color.WHITE);
        label1.setSize(10,20);

        label2.setForeground(Color.WHITE);
        label2.setSize(10,20);

        dialog.add(label1, BorderLayout.NORTH);
        dialog.add(label2, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("No");

        yesButton.setBackground(Color.BLACK);
        noButton.setBackground(Color.BLACK);
        yesButton.setForeground(Color.GREEN);
        noButton.setForeground(Color.RED);

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        yesButton.addActionListener(e -> {
            System.exit(0);
        });

        noButton.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    @Override
    public void keyReleased(int keyCode) {

    }
}
