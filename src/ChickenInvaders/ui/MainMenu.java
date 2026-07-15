package ChickenInvaders.ui;

import ChickenInvaders.main.GameMain;
import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel{

    public MainMenu(GameMain gameMain){
        setLayout(null);
        setBackground(new Color(0x1A1A2E));

        JLabel titleLabel = new JLabel("CHICKEN INVADERS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.white);
        titleLabel.setBounds(150, 50, 400, 50);

        JButton newGameButton = new JButton("New Game");
        newGameButton.setBounds(275, 180, 150, 40);

        JButton highScoreButton = new JButton("High Score");
        highScoreButton.setBounds(275, 240, 150, 40);

        JButton settingButton = new JButton("Setting");
        settingButton.setBounds(275, 300, 150, 40);

        JButton howToPlayButton = new JButton("How to Play");
        howToPlayButton.setBounds(275, 360, 150, 40);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(275, 420, 150, 40);

        newGameButton.addActionListener(e -> gameMain.startGame());
        settingButton.addActionListener(e -> gameMain.showPanel("SettingMenu"));
        howToPlayButton.addActionListener(e -> gameMain.showPanel("HowToPlayMenu"));
        exitButton.addActionListener(e -> System.exit(0));

        add(titleLabel);
        add(newGameButton);
        add(highScoreButton);
        add(settingButton);
        add(howToPlayButton);
        add(exitButton);
    }
}
