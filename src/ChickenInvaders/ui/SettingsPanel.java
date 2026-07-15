package ChickenInvaders.ui;

import ChickenInvaders.main.GameMain;
import java.awt.*;
import javax.swing.*;

public class SettingsPanel extends JPanel{

    public SettingsPanel(GameMain gameMain){
        setLayout(null);
        setBackground(new Color(0x1A1A2E));

        JLabel titleLabel = new JLabel("Settings", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.white);
        titleLabel.setBounds(150, 40, 400, 50);

        JCheckBox musicCheck = new JCheckBox("Background Music", true);
        musicCheck.setBounds(250, 120, 300, 40);
        musicCheck.setOpaque(false);
        musicCheck.setForeground(Color.white);

        JButton backButton = new JButton("Save & Back");
        backButton.setBounds(270, 400, 150, 40);

        backButton.addActionListener(e -> gameMain.showPanel("MainMenu"));

        add(titleLabel);
        add(musicCheck);
        add(backButton);
    }
}
