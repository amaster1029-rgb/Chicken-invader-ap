package ChickenInvaders.ui;

import ChickenInvaders.main.GameMain;
import ChickenInvaders.main.SoundManager;

import java.awt.*;
import javax.swing.*;

public class SettingsPanel extends JPanel{

    public SettingsPanel(GameMain gameMain){
        setLayout(null);
        setBackground(new Color(0x0FA368));

        JLabel titleLabel = new JLabel("Settings", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.white);
        titleLabel.setBounds(150, 40, 400, 50);

        JCheckBox musicCheck = new JCheckBox("Background Music", SoundManager.isMusicEnabled);
        musicCheck.setBounds(280, 160, 300, 40);
        setupCheckBoxStyle(musicCheck);

        JCheckBox shotCheck = new JCheckBox("Shot sound", SoundManager.isShotEnabled);
        shotCheck.setBounds(280, 200, 300, 40);
        setupCheckBoxStyle(shotCheck);

        JCheckBox explosionCheck = new JCheckBox("Crash / Explosion Sound", SoundManager.isExplosionEnabled);
        explosionCheck.setBounds(280, 240, 300, 40);
        setupCheckBoxStyle(explosionCheck);

        JCheckBox gameOverCheck = new JCheckBox("Game Over / Win Sound", SoundManager.isGameOverEnabled);
        gameOverCheck.setBounds(280, 280, 300, 40);
        setupCheckBoxStyle(gameOverCheck);

        JButton backButton = new JButton("Save & Back");
        backButton.setBounds(280, 400, 150, 40);

        backButton.addActionListener(e -> gameMain.showPanel("MainMenu"));

        add(titleLabel);
        add(musicCheck);
        add(shotCheck);
        add(explosionCheck);
        add(gameOverCheck);
        add(backButton);
    }

    public void setupCheckBoxStyle(JCheckBox checkBox){
        checkBox.setOpaque(false);
        checkBox.setForeground(Color.white);
        checkBox.setFont(new Font("Arial", Font.PLAIN, 20));
        checkBox.setFocusPainted(false);
    }
}
