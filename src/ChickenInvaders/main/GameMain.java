package ChickenInvaders.main;

import ChickenInvaders.database.DatabaseManager;
import ChickenInvaders.model.User;
import ChickenInvaders.ui.*;

import javax.swing.*;
import java.awt.*;

public class GameMain extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainContainer;
    private User currentUser;

    public GameMain(){
        setTitle("Chicken Invaders");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        MainMenu mainMenu = new MainMenu(this);
        SettingsPanel settingsPanel = new SettingsPanel(this);
        HowToPlayPanel howToPlayPanel = new HowToPlayPanel(this);

        mainContainer.add(mainMenu, "MainMenu");
        mainContainer.add(settingsPanel, "SettingMenu");
        mainContainer.add(howToPlayPanel, "HowToPlayMenu");

        add(mainContainer);

        showPanel("MainMenu");
        setVisible(true);

        DatabaseManager.initDatabase();

        LoginPanel loginPanel = new LoginPanel(this);
        mainContainer.add(loginPanel, "LoginPanel");

        RegisterPanel registerPanel = new RegisterPanel(this);
        mainContainer.add(registerPanel, "RegisterPanel");
    }

    public void showPanel(String panelName){
        cardLayout.show(mainContainer, panelName);
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public void setCurrentUser(User currentUser){
        this.currentUser = currentUser;
    }

    public void startGame(){
        GamePanel gamePanel = new GamePanel(this);
        mainContainer.add(gamePanel, "GamePanel");
        showPanel("GamePanel");
        gamePanel.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameMain());
    }
}
