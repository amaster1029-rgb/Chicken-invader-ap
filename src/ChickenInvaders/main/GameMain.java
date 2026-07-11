package ChickenInvaders.main;

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
    static List<JLabel> chickens = new ArrayList<>();

    static boolean spacePressed = false;
    static ImageIcon shotIcon;

    static long lastShotTime = 0;
    static int playerLives = 3;

    public static void main(String[] args) {
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

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();

                ImageIcon originalBG = new ImageIcon("background/background.jpg");
                Image scaledBG = originalBG.getImage().getScaledInstance(700, 500, Image.SCALE_SMOOTH);
                JLabel backgroundLabel = new JLabel(new ImageIcon(scaledBG));
                backgroundLabel.setBounds(0, 0, 700, 500);

                ImageIcon originalImg = new ImageIcon("airplan/1.png");
                Image scaledImg = originalImg.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                ImageIcon airplaneIcon = new ImageIcon(scaledImg);
                JLabel airplaneLabel = new JLabel(airplaneIcon);
                airplaneLabel.setBounds(airplaneX, airplaneY, 60, 60);

                mainPanel.add(airplaneLabel);

                ImageIcon originalChicken = new ImageIcon("chicken/normal_chicken.png");
                Image scaledChicken = originalChicken.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
                ImageIcon chickenIcon = new ImageIcon(scaledChicken);

                int rows = 3;
                int cols = 6;
                int startX = 100;
                int startY = 30;
                int gapX = 80;
                int gapY = 60;

                for(int r = 0; r < rows; r++){
                    for(int c = 0; c < cols; c++){
                        JLabel chickenLabel = new JLabel(chickenIcon);

                        int cx = startX + (c * gapX);
                        int cy = startY + (r * gapY);

                        chickenLabel.setBounds(cx, cy, 45, 45);
                        mainPanel.add(chickenLabel, 0);
                        chickens.add(chickenLabel);
                    }
                }

                mainPanel.add(backgroundLabel);

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
                            }else {
                                bullet.setLocation(bullet.getX(), bulletY);
                                Rectangle bulletBounds = bullet.getBounds();

                                for(int j = 0; j < chickens.size(); j++){
                                    JLabel chicken = chickens.get(j);
                                    if(bulletBounds.intersects(chicken.getBounds())){
                                        mainPanel.remove(chicken);
                                        chickens.remove(j);

                                        mainPanel.remove(bullet);
                                        bullets.remove(i);
                                        i--;

                                        break;
                                    }
                                }
                            }
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
                        int speed = 5;

                        if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D){
                            if(airplaneX < mainPanel.getWidth() - 60)
                                airplaneX += speed;
                        }

                        if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A){
                            if(airplaneX > 0)
                                airplaneX -= speed;
                        }

                        if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S){
                            if(airplaneY < mainPanel.getHeight() - 60)
                                airplaneY += speed;
                        }

                        if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W){
                            if(airplaneY >= 0)
                                airplaneY -= speed;
                        }

                        if(keyCode == KeyEvent.VK_SPACE){
                            long currentTime = System.currentTimeMillis();

                            if(currentTime - lastShotTime >= 300) {
                                lastShotTime = currentTime;

                                JLabel bulletLabel = new JLabel(new ImageIcon(scaledShot));

                                int bulletX = airplaneX + 27;
                                int bulletY = airplaneY - 20;
                                bulletLabel.setBounds(bulletX, bulletY, 5, 20);

                                mainPanel.add(bulletLabel, 0);
                                bullets.add(bulletLabel);
                            }
                        }

                        airplaneLabel.setBounds(airplaneX, airplaneY, 60, 60);
                    }
                });

                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });

        howToPlayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();

                mainPanel.setBackground(new Color(0x9F1717));

                JLabel titleLabelControl = new JLabel("CONTROLS", SwingConstants.CENTER);
                titleLabelControl.setFont(new Font("Arial", Font.BOLD, 36));
                titleLabelControl.setForeground(Color.white);
                titleLabelControl.setBounds(150, 40, 400, 50);

                JLabel title1 = new JLabel("Move right ................... → / D", SwingConstants.CENTER);
                title1.setFont(new Font("Arial", Font.ITALIC, 20));
                title1.setForeground(Color.white);
                title1.setBounds(150, 110, 400, 50);

                JLabel title2 = new JLabel("Move left ..................... ← / A", SwingConstants.CENTER);
                title2.setFont(new Font("Arial", Font.ITALIC, 20));
                title2.setForeground(Color.white);
                title2.setBounds(150, 150, 400, 50);

                JLabel title3 = new JLabel("Move up ...................... ↑ / W", SwingConstants.CENTER);
                title3.setFont(new Font("Arial", Font.ITALIC, 20));
                title3.setForeground(Color.white);
                title3.setBounds(150, 190, 400, 50);

                JLabel title4 = new JLabel("Move down .................. ↓ / S", SwingConstants.CENTER);
                title4.setFont(new Font("Arial", Font.ITALIC, 20));
                title4.setForeground(Color.white);
                title4.setBounds(150, 230, 400, 50);

                JLabel title5 = new JLabel("Shot .......................... Space", SwingConstants.CENTER);
                title5.setFont(new Font("Arial", Font.ITALIC, 20));
                title5.setForeground(Color.white);
                title5.setBounds(150, 270, 400, 50);

                JLabel title6 = new JLabel("Pause ............................... P", SwingConstants.CENTER);
                title6.setFont(new Font("Arial", Font.ITALIC, 20));
                title6.setForeground(Color.white);
                title6.setBounds(150, 310, 400, 50);

                JLabel title7 = new JLabel("End the game ................ Esc", SwingConstants.CENTER);
                title7.setFont(new Font("Arial", Font.ITALIC, 20));
                title7.setForeground(Color.white);
                title7.setBounds(150, 350, 400, 50);

                JButton backToMenu = new JButton("Back to Menu");
                backToMenu.setBounds(275, 420, 150, 40);
                backToMenu.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mainPanel.removeAll();
                        mainPanel.setBackground(new Color(0x1A1A2E));

                        mainPanel.add(titleLabel);
                        mainPanel.add(newGameButton);
                        mainPanel.add(highScoreButton);
                        mainPanel.add(settingButton);
                        mainPanel.add(howToPlayButton);
                        mainPanel.add(exitButton);

                        mainPanel.revalidate();
                        mainPanel.repaint();
                    }
                });

                mainPanel.add(titleLabelControl);
                mainPanel.add(title1);
                mainPanel.add(title2);
                mainPanel.add(title3);
                mainPanel.add(title4);
                mainPanel.add(title5);
                mainPanel.add(title6);
                mainPanel.add(title7);
                mainPanel.add(backToMenu);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        mainPanel.add(titleLabel);
        mainPanel.add(newGameButton);
        mainPanel.add(highScoreButton);
        mainPanel.add(settingButton);
        mainPanel.add(howToPlayButton);
        mainPanel.add(exitButton);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
