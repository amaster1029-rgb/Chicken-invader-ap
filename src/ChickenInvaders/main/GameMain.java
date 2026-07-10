package ChickenInvaders.main;

import ChickenInvaders.database.DatabaseManager;
import ChickenInvaders.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.PublicKey;

public class GameMain extends JFrame {
    static void main(String[] args) {
        JFrame frame = new JFrame("Chicken Invaders");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(40, 35, 700, 500);
        mainPanel.setBackground(new Color(0x1A1A2E));
        mainPanel.setLayout(null);

        JLabel titleLabel = new JLabel("CHICKEN INVADERS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.white);
        titleLabel.setBounds(150, 50, 400, 50);

        JButton startButton = new JButton("Start Game");
        startButton.setBounds(275, 180, 150, 40);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(275, 240, 150, 40);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();

                JLabel gameStatusLabel = new JLabel("Game is running", SwingConstants.CENTER);
                gameStatusLabel.setFont(new Font("Arial", Font.PLAIN, 24));
                gameStatusLabel.setForeground(Color.GREEN);
                gameStatusLabel.setBounds(150, 200, 400, 50);

                mainPanel.add(gameStatusLabel);
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        mainPanel.add(titleLabel);
        mainPanel.add(startButton);
        mainPanel.add(exitButton);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
