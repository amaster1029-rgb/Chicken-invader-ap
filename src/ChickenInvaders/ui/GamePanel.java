package ChickenInvaders.ui;

import ChickenInvaders.enemy.Enemy;
import ChickenInvaders.enemy.NormalEnemy;
import ChickenInvaders.main.GameMain;
import ChickenInvaders.main.SoundManager;
import ChickenInvaders.model.Plane;
import ChickenInvaders.model.Bullet;
import ChickenInvaders.enemy.Cell;

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

    private List<Cell> gridCells = new ArrayList<>();
    private double gridX = 100;
    private double gridY = 30;
    private int gridDirection = 1; //1 move to right and -1 move to left
    private double gridSpeedX = 1.0;
    private int gridStepY = 20;
    private ImageIcon normalChickenIcon;

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

        ImageIcon originalChicken = new ImageIcon("chicken/normal_chicken.png");
        normalChickenIcon = new ImageIcon(originalChicken.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));

        setupGrid(1);

        gameTimer = new Timer(16, e -> {

            moveGrid();
            //moving bullets
            for(int i = 0; i < bullets.size(); i++){
                Bullet bullet = bullets.get(i);
                bullet.move();

                boolean bulletDestroyed = false;

                //deleting bullets
                if(bullet.getY() < 0){
                    bullet.removeFromPanel(this);
                    bullets.remove(i);
                    i--;
                    continue;
                }

                Rectangle bulletBounds = bullet.getBounds();
                
                for(Cell cell : gridCells){
                    Enemy enemy = cell.getCurrentEnemy();

                    if(enemy != null){
                        if(bulletBounds.intersects(enemy.getBounds())){
                            bulletDestroyed = true;
                            enemy.takeDamage();

                            if(enemy.isDead()){
                                enemy.removeFromPanel(this);
                                cell.enemyKilled();

                                SoundManager.playExplosionSound("sound-effects/mixkit-epic-impact-afar-explosion-2782.wav");

                                //fil here with score increasing
                            }
                            break;
                        }
                    }
                }

                if(bulletDestroyed){
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

    private void setupGrid(int level){
        int rows = 5;
        int cols = 8;
        int gapX = 70;
        int gapY = 55;
        int initialCounter = 2;

        for(int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                int offsetX = c * gapX;
                int offsetY = r * gapY;

                Cell cell = new Cell(offsetX, offsetY, initialCounter);

                NormalEnemy chicken = new NormalEnemy((int)gridX + offsetX, (int)gridY + offsetY,
                        level, normalChickenIcon, this);

                cell.setCurrentEnemy(chicken);
                gridCells.add(cell);
            }
        }
    }

    private void moveGrid(){
        gridX += gridSpeedX * gridDirection;

        double gridWidth = (7 * 70) + 45;

        if(gridWidth + gridX >= getWidth()){
            gridX = getWidth() - gridWidth;
            gridDirection = -1;
            gridY += gridStepY;
        }
        else if(gridX <= 0){
            gridX = 0;
            gridDirection = 1;
            gridY += gridStepY;
        }

        for(Cell cell : gridCells){
            Enemy enemy = cell.getCurrentEnemy();
            if(enemy != null)
                enemy.setLocation((int)gridX + cell.getOffsetX(), (int)gridY + cell.getOffsetY());
        }
    }

    //background

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //draw background
        if(backgroundImg != null)
            g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);

        //draw lives
        if(playerPlane != null){
            g.setColor(new Color(0x11FF00));
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("LIVES: ", 20, 30);

            Image lifeIcon = new ImageIcon("airplan/1.png").getImage();
            for(int i = 0; i < playerPlane.getLives(); i++){
                g.drawImage(lifeIcon, 95 + (i * 40), 10, 25, 25, this);
            }
        }
    }
}
