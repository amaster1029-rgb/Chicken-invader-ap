package ChickenInvaders.ui;

import ChickenInvaders.database.DatabaseManager;
import ChickenInvaders.main.GameMain;
import ChickenInvaders.main.SoundManager;
import ChickenInvaders.model.User;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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

        backButton.addActionListener(e -> {
            SoundManager.isMusicEnabled = musicCheck.isSelected();
            SoundManager.isExplosionEnabled = explosionCheck.isSelected();
            SoundManager.isShotEnabled = shotCheck.isSelected();
            SoundManager.isGameOverEnabled = gameOverCheck.isSelected();

            if(!SoundManager.isMusicEnabled)
                SoundManager.stopBGM();
            else
                SoundManager.resumeBGM();

            User currentUser = gameMain.getCurrentUser();
            if(currentUser != null){
                currentUser.setBgMusic(musicCheck.isSelected());
                currentUser.setCrashMusic(explosionCheck.isSelected());
                currentUser.setShotMusic(shotCheck.isSelected());
                currentUser.setGameOverSound(gameOverCheck.isSelected());

                DatabaseManager.updateSoundSetting(
                        currentUser.getUsername(),
                        musicCheck.isSelected(),
                        shotCheck.isSelected(),
                        explosionCheck.isSelected(),
                        gameOverCheck.isSelected()
                );
            }

            gameMain.showPanel("MainMenu");
        });

        add(titleLabel);
        add(musicCheck);
        add(shotCheck);
        add(explosionCheck);
        add(gameOverCheck);
        add(backButton);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                musicCheck.setSelected(SoundManager.isMusicEnabled);
                shotCheck.setSelected(SoundManager.isShotEnabled);
                explosionCheck.setSelected(SoundManager.isExplosionEnabled);
                gameOverCheck.setSelected(SoundManager.isGameOverEnabled);
            }
        });
    }

    public void setupCheckBoxStyle(JCheckBox checkBox){
        checkBox.setOpaque(false);
        checkBox.setForeground(Color.white);
        checkBox.setFont(new Font("Arial", Font.PLAIN, 20));
        checkBox.setFocusPainted(false);
    }
}
