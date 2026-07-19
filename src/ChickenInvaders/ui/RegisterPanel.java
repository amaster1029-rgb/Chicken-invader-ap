package ChickenInvaders.ui;

import ChickenInvaders.database.DatabaseManager;
import ChickenInvaders.main.GameMain;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {
    private GameMain gameMain;

    public RegisterPanel(GameMain gameMain){
        this.gameMain = gameMain;
        setLayout(null);
        setBackground(new Color(0x4C039A));

        JLabel titleLabel = new JLabel("Register", SwingUtilities.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.green);
        titleLabel.setBounds(150, 50, 400, 50);
        add(titleLabel);

        JLabel userLabel = new JLabel("New Username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        userLabel.setForeground(Color.white);
        userLabel.setBounds(160, 150, 140, 30);
        add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(310, 150, 180, 30);
        add(userField);

        JLabel passLabel = new JLabel("New Password");
        passLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passLabel.setForeground(Color.white);
        passLabel.setBounds(160, 200, 140, 30);
        add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(310, 200, 180, 30);
        add(passField);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(280, 280, 130, 40);
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = DatabaseManager.registerUser(username, password);
            if(success){
                JOptionPane.showMessageDialog(this, "Registered successfully! Please login", "Success", JOptionPane.INFORMATION_MESSAGE);
                gameMain.showPanel("LoginPanel");
            }
            else
                JOptionPane.showMessageDialog(this, "Username already exists", "Register failed", JOptionPane.ERROR_MESSAGE);


        });
        add(registerButton);

        JButton backButton = new JButton("Back to Login");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBounds(250, 350, 190, 40);
        backButton.addActionListener(e -> gameMain.showPanel("LoginPanel"));
        add(backButton);
    }
}
