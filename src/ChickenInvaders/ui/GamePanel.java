package ChickenInvaders.ui;

import ChickenInvaders.main.GameMain;
import ChickenInvaders.main.SoundManager;
import ChickenInvaders.model.Plane;
import ChickenInvaders.model.Bullet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;

public class GamePanel extends JPanel{
    private Image backgroundImg;
    private Timer gameTimer;
    private Plane playerPlane;

    private List<Bullet> bullets = new ArrayList<>();
    private ImageIcon shotIcon;
    private long lastShotTime = 0;

    public GamePanel(GameMain gameMain){
        setLayout(null);
        setBounds(0, 0, 700, 500);

        //load background image
        ImageIcon originalBG = new ImageIcon("background/background.jpg");
        backgroundImg = originalBG.getImage();

        //adding plane
        ImageIcon originalImg = new ImageIcon("airplan/1.png");
        Image scaledImg = originalImg.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        playerPlane = new Plane(360, 450, new ImageIcon(scaledImg), this);

        //load shot image
        ImageIcon originalShot = new ImageIcon("airplan/shot.png");
        Image scaledShot = originalShot.getImage().getScaledInstance(5, 20, Image.SCALE_SMOOTH);
        shotIcon = new ImageIcon(scaledShot);

        gameTimer = new Timer(16, e -> {
            //moving bullets
            for(int i = 0; i < bullets.size(); i++){
                Bullet bullet = bullets.get(i);
                bullet.move();

                //deleting bullets
                if(bullet.getY() < 0){
                    bullet.removeFromPanel(this);
                    bullets.remove(i);
                    i--;
                }
            }

            repaint();
        });

        gameTimer.start();

        setFocusable(true);

        //keys
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                //move right
                if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D)
                    playerPlane.moveRight(getWidth());
                //move left
                if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A)
                    playerPlane.moveLeft();
                //move down
                if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S)
                    playerPlane.moveDown(getHeight());
                //move up
                if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W)
                    playerPlane.moveUp();
                //shooting
                if(keyCode == KeyEvent.VK_SPACE){
                    SoundManager.playShotSound("sound-effects/mixkit-short-laser-gun-shot-1670.wav");
                    long currentTime = System.currentTimeMillis();

                    //checking for 300ms delay
                    if(currentTime - lastShotTime >= 300){
                        lastShotTime = currentTime;

                        //add new bullet
                        Bullet newBullet = new Bullet(playerPlane.getX(), playerPlane.getY(), shotIcon,
                                GamePanel.this);
                        bullets.add(newBullet);
                    }
                }

                //quiting the game
                if(keyCode == KeyEvent.VK_ESCAPE){
                    gameTimer.stop();

                    //deleting all bullets
                    for(Bullet b : bullets){
                        b.removeFromPanel(GamePanel.this);
                    }
                    bullets.clear();

                    gameMain.showPanel("MainMenu");
                }
            }
        });
    }

    //background

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(backgroundImg != null)
            g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
    }
}
