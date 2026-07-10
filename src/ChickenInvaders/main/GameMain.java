package ChickenInvaders.main;

import ChickenInvaders.database.DatabaseManager;
import ChickenInvaders.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameMain extends JFrame {
    static int airplaneX = 325;
    static int airplaneY = 380;
    static List<JLabel> bullets = new ArrayList<>();

    static boolean spacePressed = false;
    static ImageIcon shotIcon;

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

                mainPanel.setBackground(Color.black);

                ImageIcon originalImg = new ImageIcon("airplan/1.png");
                Image scaledImg = originalImg.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                ImageIcon airplaneIcon = new ImageIcon(scaledImg);
                JLabel airplaneLabel = new JLabel(airplaneIcon);
                airplaneLabel.setBounds(airplaneX, airplaneY, 60, 60);
                mainPanel.add(airplaneLabel);

                ImageIcon originalShot = new ImageIcon("airplan/shot.png");
                Image scaledShot = originalShot.getImage().getScaledInstance(5, 20, Image.SCALE_SMOOTH);
                shotIcon = new ImageIcon(scaledShot);

                Timer gameTimer = new Timer(16, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for(int i = 0; i < bullets.size(); i++){
                            JLabel bullet = bullets.get(i);
                            int bulletY = bullet.getY();
                            bulletY -= 10;

                            if(bulletY < 0){
                                mainPanel.remove(bullet);
                                bullets.remove(i);
                                i--;
                            }else
                                bullet.setLocation(bullet.getX(), bulletY);
                        }
                        mainPanel.repaint();
                    }
                });
                gameTimer.start();

                frame.setFocusable(true);
                frame.requestFocusInWindow();

                frame.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent ke) {
                        int keyCode = ke.getKeyCode();
                        int speed = 15;

                        if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D){
                            if(airplaneX < mainPanel.getWidth() - 60)
                                airplaneX += speed;
                        }

                        if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A){
                            if(airplaneX > 0)
                                airplaneX -= speed;
                        }

                        if(keyCode == KeyEvent.VK_SPACE && !spacePressed){
                            spacePressed = true;
                            JLabel bulletLabel = new JLabel(new ImageIcon(scaledShot));

                            int bulletX = airplaneX + 27;
                            int bulletY = airplaneY - 20;
                            bulletLabel.setBounds(bulletX, bulletY, 5, 20);

                            mainPanel.add(bulletLabel);
                            bullets.add(bulletLabel);
                        }

                        airplaneLabel.setBounds(airplaneX, airplaneY, 60, 60);
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                        if(e.getKeyCode() == KeyEvent.VK_SPACE)
                            spacePressed = false;
                    }
                });

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
