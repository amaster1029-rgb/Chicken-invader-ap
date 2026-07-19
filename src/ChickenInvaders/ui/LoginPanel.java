package ChickenInvaders.ui;

import ChickenInvaders.database.DatabaseManager;
import ChickenInvaders.main.GameMain;
import ChickenInvaders.main.SoundManager;
import ChickenInvaders.model.User;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private GameMain gameMain;

    public LoginPanel(GameMain gameMain){
        this.gameMain = gameMain;
        setLayout(null);
        setBackground(new Color(0x4C039A));

        JLabel titleLabel = new JLabel("LOGIN", SwingUtilities.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.yellow);
        titleLabel.setBounds(150, 50, 400, 50);
        add(titleLabel);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 18));
        userLabel.setForeground(Color.white);
        userLabel.setBounds(200, 150, 100, 30);
        add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(310, 150, 180, 30);
        add(userField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 18));
        passLabel.setForeground(Color.white);
        passLabel.setBounds(200, 200, 100, 30);
        add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(310, 200, 180, 30);
        add(passField);

        JButton loginButton = new JButton("Sign In");
        loginButton.setBounds(200, 280, 130, 40);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if(username.isEmpty() || password.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter both fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = DatabaseManager.login(username, password);
            if(user != null){
                gameMain.setCurrentUser(user);

                SoundManager.isMusicEnabled = user.isBgMusic();

                if(!SoundManager.isMusicEnabled)
                    SoundManager.stopBGM();

                SoundManager.isShotEnabled = user.isShotMusic();
                SoundManager.isExplosionEnabled = user.isCrashMusic();
                SoundManager.isGameOverEnabled = user.isGameOverSound();

                gameMain.startGame();
            }
            else
                JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Login fail", JOptionPane.ERROR_MESSAGE);
        });

        add(loginButton);

        JButton goToRegisterButton = new JButton("Sign Up");
        goToRegisterButton.setBounds(360, 280, 130, 40);
        goToRegisterButton.setFont(new Font("Arial", Font.BOLD, 16));
        goToRegisterButton.addActionListener(e -> gameMain.showPanel("RegisterPanel"));
        add(goToRegisterButton);

        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBounds(250, 350, 190, 40);
        backButton.addActionListener(e -> gameMain.showPanel("MainMenu"));
        add(backButton);
    }
}
