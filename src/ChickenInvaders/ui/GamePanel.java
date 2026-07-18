package ChickenInvaders.ui;

import ChickenInvaders.enemy.*;
import ChickenInvaders.main.GameMain;
import ChickenInvaders.main.SoundManager;
import ChickenInvaders.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;

public class GamePanel extends JPanel{
    private GameMain gameMain;

    private Image backgroundImg1;
    private Image backgroundImg2;
    private Timer gameTimer;
    private Plane playerPlane;
    private List<Bullet> bullets = new ArrayList<>();
    private ImageIcon shotIcon;
    private long lastShotTime = 0;

    //cell
    private List<Cell> gridCells = new ArrayList<>();
    private double gridX = 100;
    private double gridY = 30;
    private int gridDirection = 1; //1 move to right and -1 move to left
    private double gridSpeedX = 0.5;
    private int gridStepY = 20;
    private ImageIcon normalChickenIcon;

    //egg
    private List<Egg> eggs = new ArrayList<>();
    private ImageIcon eggIcon;
    private int eggSpawnTimer = 0;
    private int eggSpawnRate = 180;

    //score
    private int score = 0;

    //explosion
    private List<Explosion> explosions = new ArrayList<>();

    //boss explosion
    private Image bossExplosionImg;
    private int bossExplosionTimer = 0;
    private int bossExpX = 0;
    private int bossExpY = 0;

    //power up
    private int currentLevel = 1;
    private List<PowerUp> powerUps = new ArrayList<>();
    private int rapidFireTimer = 0;
    private int shieldTimer = 0;
    private int freezeTimer = 0;
    private ImageIcon addFireIcon, rapidFireIcon, extraLifeIcon, shieldIcon, freezeIcon;

    private Image snowflakeImg;

    private boolean isPaused = false;

    private ImageIcon fastChickenIcon;
    private ImageIcon zigzagChickenIcon;
    private ImageIcon boss1Icon;

    private int levelTransitionTimer = 0;

    public GamePanel(GameMain gameMain){
        this.gameMain = gameMain;
        setLayout(null);
        setBounds(0, 0, 700, 500);

        //load background image
        ImageIcon originalBG = new ImageIcon("background/background.jpg");
        backgroundImg1 = originalBG.getImage();

        ImageIcon originalBG2 = new ImageIcon("background/background2.jpg");
        backgroundImg2 = originalBG2.getImage();

        //adding plane
        ImageIcon originalImg = new ImageIcon("airplan/1.png");
        Image scaledImg = originalImg.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        playerPlane = new Plane(360, 450, new ImageIcon(scaledImg), this);

        //load shot image
        ImageIcon originalShot = new ImageIcon("airplan/shot.png");
        Image scaledShot = originalShot.getImage().getScaledInstance(5, 20, Image.SCALE_SMOOTH);
        shotIcon = new ImageIcon(scaledShot);

        //load chickens
        ImageIcon originalChicken = new ImageIcon("chicken/normal_chicken.png");
        normalChickenIcon = new ImageIcon(originalChicken.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));

        ImageIcon originalFastChicken = new ImageIcon("chicken/fast_chicken.png");
        fastChickenIcon = new ImageIcon(originalFastChicken.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));

        ImageIcon originalZigzagChicken = new ImageIcon("chicken/zigzag_chicken.png");
        zigzagChickenIcon = new ImageIcon(originalZigzagChicken.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));

        ImageIcon originalBoss1 = new ImageIcon("chicken/boss1.png");
        boss1Icon = new ImageIcon(originalBoss1.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));

        setupGrid(1);

        //load egg imag
        ImageIcon originalEgg = new ImageIcon("chicken/egg.png");
        eggIcon = new ImageIcon(originalEgg.getImage().getScaledInstance(15, 20, Image.SCALE_SMOOTH));

        //load power up imag
        ImageIcon originalAddFire = new ImageIcon("powerup1/add_shot.png");
        addFireIcon = new ImageIcon(originalAddFire.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

        ImageIcon originalRapidFire = new ImageIcon("powerup1/fast_shot.png");
        rapidFireIcon = new ImageIcon(originalRapidFire.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

        ImageIcon originalExtraLife = new ImageIcon("powerup1/heal.png");
        extraLifeIcon = new ImageIcon(originalExtraLife.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

        ImageIcon originalShield = new ImageIcon("powerup1/sheild.png");
        shieldIcon = new ImageIcon(originalShield.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

        ImageIcon originalFreeze = new ImageIcon("powerup1/freeze.png");
        freezeIcon = new ImageIcon(originalFreeze.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

        //load snowflake image
        ImageIcon originalSnow = new ImageIcon("chicken/snowflake.png");
        snowflakeImg = originalSnow.getImage();

        //load boss explosion image
        ImageIcon originalBossExp = new ImageIcon("airplan/Explosion.png");
        bossExplosionImg = originalBossExp.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);

        gameTimer = new Timer(16, e -> {

            if(isPaused){
                repaint();
                return;
            }

            if(levelTransitionTimer > 0){
                levelTransitionTimer--;
                if(levelTransitionTimer == 0){
                    System.out.println("Level " + currentLevel + " cleared");
                    currentLevel++;
                    gridX = 100;
                    gridY = 30;
                    gridDirection = 1;
                    gridCells.clear();
                    setupGrid(currentLevel);
                }
            }

            if(freezeTimer > 0)
                freezeTimer--;

            if(freezeTimer <= 0)
                moveGrid();

            if(shieldTimer > 0){
                shieldTimer--;
                if(shieldTimer <= 0)
                    playerPlane.setShielded(false);
            }

            if(rapidFireTimer > 0)
                rapidFireTimer--;

            if(bossExplosionTimer > 0)
                bossExplosionTimer--;

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

                                if(enemy instanceof BossLevel4 || enemy instanceof BossLevel8){
                                    bossExpX = enemy.getX() - 25;
                                    bossExpY = enemy.getY() - 25;
                                    bossExplosionTimer = 60;
                                }else
                                    explosions.add(new Explosion(enemy.getX() + 22, enemy.getY() + 22, Color.orange));

                                score += (enemy instanceof BossLevel4 || enemy instanceof BossLevel8) ? 500 : 10;

                                if(Math.random() < 0.20){
                                    PowerUp.Type[] types = PowerUp.Type.values();
                                    PowerUp.Type randomType = types[(int)(Math.random() * types.length)];

                                    ImageIcon selectedIcon = addFireIcon;
                                    if(randomType == PowerUp.Type.rapidFire)
                                        selectedIcon = rapidFireIcon;
                                    if(randomType == PowerUp.Type.extraLife)
                                        selectedIcon = extraLifeIcon;
                                    if(randomType == PowerUp.Type.shield)
                                        selectedIcon = shieldIcon;
                                    if(randomType == PowerUp.Type.freeze)
                                        selectedIcon = freezeIcon;

                                    powerUps.add(new PowerUp(enemy.getX(), enemy.getY(), selectedIcon, randomType, GamePanel.this));
                                }
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

            if(freezeTimer <= 0) {
                eggSpawnTimer++;
                if (eggSpawnTimer >= eggSpawnRate) {
                    eggSpawnTimer = 0;

                    List<Enemy> activeEnemies = new ArrayList<>();
                    for (Cell cell : gridCells) {
                        Enemy ene = cell.getCurrentEnemy();
                        if (ene != null && !ene.isArriving())
                            activeEnemies.add(ene);
                    }

                    if (!activeEnemies.isEmpty()) {
                        int randomIndex = (int) (Math.random() * activeEnemies.size());
                        Enemy shooter = activeEnemies.get(randomIndex);

                        boolean isBoss = (shooter instanceof BossLevel4 || shooter instanceof BossLevel8);
                        int cx = shooter.getX() + (isBoss ? 50 : 15);
                        int cy = shooter.getY() + (isBoss ? 100 : 40);

                        if(currentLevel == 4 && isBoss){
                            eggs.add(new Egg(cx, cy, 0, -4, eggIcon, GamePanel.this)); // up
                            eggs.add(new Egg(cx, cy, 0, 4, eggIcon, GamePanel.this)); // down
                            eggs.add(new Egg(cx, cy, -4, 0, eggIcon, GamePanel.this)); //left
                            eggs.add(new Egg(cx, cy, 4, 0, eggIcon, GamePanel.this)); //right
                        }
                        else if(currentLevel == 8 && isBoss){

                        }
                        else
                            eggs.add(new Egg(cx, cy, 0, 4, eggIcon, GamePanel.this));
                    }
                }
            }

            //colision with airplane
            for(int i = 0; i < eggs.size(); i++){
                Egg egg = eggs.get(i);
                if(freezeTimer <= 0)
                    egg.move();

                //egg get out of the screen
                if(egg.getY() > getHeight()){
                    egg.removeFromPanel(GamePanel.this);
                    eggs.remove(i);
                    i--;
                    continue;
                }

                if (playerPlane != null && egg.getBounds().intersects(playerPlane.getBounds())){
                    if(!playerPlane.isShielded()) {
                        playerPlane.takeDamage();
                        SoundManager.playExplosionSound("sound-effects/mixkit-epic-impact-afar-explosion-2782.wav");
                        explosions.add(new Explosion(playerPlane.getX() + 30, playerPlane.getY() + 30, Color.orange));
                    }

                    egg.removeFromPanel(GamePanel.this);
                    eggs.remove(i);
                    i--;

                    //losing check
                    if(playerPlane.isDead()){
                        triggerGameOver();
                        return;
                    }
                }
            }

            //explosion
            for(int i = 0; i < explosions.size(); i++){
                Explosion exp = explosions.get(i);
                exp.update();
                if(exp.isVanished()){
                    explosions.remove(i);
                    i--;
                }
            }

            //power ups
            for(int i = 0; i < powerUps.size(); i++){
                PowerUp p = powerUps.get(i);
                p.move();

                if(p.getY() > getHeight()){
                    p.removeFromPanel(GamePanel.this);
                    powerUps.remove(i);
                    i--;
                    continue;
                }

                if(playerPlane != null && p.getBounds().intersects(playerPlane.getBounds())){
                    switch (p.getType()){

                        case addFire -> {
                            int wLevel = playerPlane.getWeaponLevel();
                            if (currentLevel <= 4 && wLevel < 3)
                                playerPlane.upgradeWeapon();
                            else if (currentLevel > 4 && wLevel < 5)
                                playerPlane.upgradeWeapon();
                        }
                        case extraLife -> playerPlane.addLife();
                        case rapidFire -> rapidFireTimer = 480; //8s
                        case freeze -> freezeTimer = 180; //3s
                        case shield -> {
                            shieldTimer = 600;
                            playerPlane.setShielded(true); //10s
                        }
                    }

                    p.removeFromPanel(GamePanel.this);
                    powerUps.remove(i);
                    i--;
                }
            }

            //checking if chickens reached the end of the screen
            for(Cell cell : gridCells) {
                Enemy enemy = cell.getCurrentEnemy();

                if (enemy != null && !enemy.isArriving() && !cell.isCleared()) {

                    //collision with airplane
                    if (playerPlane != null && enemy.getBounds().intersects(playerPlane.getBounds())) {
                        if(!playerPlane.isShielded()) {
                            playerPlane.takeDamage();
                            SoundManager.playExplosionSound("sound-effects/mixkit-epic-impact-afar-explosion-2782.wav");
                            explosions.add(new Explosion(playerPlane.getX() + 30, playerPlane.getY() + 30, Color.orange));

                            shieldTimer = 120;
                            playerPlane.setShielded(true);
                        }

                        if (playerPlane.isDead()) {
                            triggerGameOver();
                            return;
                        }
                    }
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

                if(keyCode == KeyEvent.VK_P) {
                    isPaused = !isPaused;
                    return;
                }

                if(isPaused)
                    return;

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

                    int currentCooldown = (rapidFireTimer > 0) ? 100 : 300;

                    //checking for 300ms delay
                    if(currentTime - lastShotTime >= currentCooldown){
                        SoundManager.playShotSound("sound-effects/mixkit-short-laser-gun-shot-1670.wav");
                        lastShotTime = currentTime;

                        int wLevel = playerPlane.getWeaponLevel();
                        int px = playerPlane.getX();
                        int py = playerPlane.getY();

                        if (wLevel == 1) {
                            bullets.add(new Bullet(px, py, shotIcon, GamePanel.this));
                        } else if (wLevel == 2) {
                            bullets.add(new Bullet(px + 12, py + 10, shotIcon, GamePanel.this));
                            bullets.add(new Bullet(px - 12, py + 10, shotIcon, GamePanel.this));
                        } else if (wLevel == 3) {
                            bullets.add(new Bullet(px - 15, py + 15, shotIcon, GamePanel.this));
                            bullets.add(new Bullet(px, py - 5, shotIcon, GamePanel.this));
                            bullets.add(new Bullet(px + 15, py + 15, shotIcon, GamePanel.this));
                        }else if (wLevel == 4) {
                            bullets.add(new Bullet(px - 15, py + 10, shotIcon, GamePanel.this));
                            bullets.add(new Bullet(px - 5, py + 10, shotIcon, GamePanel.this));
                            bullets.add(new Bullet(px + 5, py + 10, shotIcon, GamePanel.this));
                            bullets.add(new Bullet(px + 15, py + 10, shotIcon, GamePanel.this));
                        } else if (wLevel >= 5) {
                            bullets.add(new Bullet(px - 20, py + 10, shotIcon, GamePanel.this));
                            bullets.add(new Bullet(px - 10, py + 10, shotIcon, GamePanel.this));
                            bullets.add(new Bullet(px, py + 15, shotIcon, GamePanel.this));
                            bullets.add(new Bullet(px + 10, py + 10, shotIcon, GamePanel.this));
                            bullets.add(new Bullet(px + 20, py + 10, shotIcon, GamePanel.this));
                        }

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
        gridCells.clear();

        //boss level 4 & 8
        if(level == 4 || level == 8){
            gridSpeedX = (level == 4) ? 1.5 : 2.0;
            gridStepY = 0;
            eggSpawnRate = (level == 4) ? (int)(1.5 * 60) : (int)(1.0 * 60);

            Cell bossCell = new Cell(0, 0, 0);
            bossCell.setEnemyType(4);

            int startX = (getWidth() - 150) / 2;
            int startY = 80;

            gridX = startX;
            gridY = startY;

            int bossHp = (level == 4) ? 50 : 100;
            Enemy boss = creatEnemyByType(4, startX, startY);
            boss.setHealth(bossHp);

            bossCell.setCurrentEnemy(boss);
            gridCells.add(bossCell);
            return;
        }

        int rows = 5;
        int cols = 8;
        int respawnCount = 2;

        switch (level){
            case 1:
                respawnCount = 2;
                gridSpeedX = 1.0;
                gridStepY = 20;
                eggSpawnRate = (int)(3.0 * 60);
                break;
            case 2:
                respawnCount = 2;
                gridSpeedX = 1.5;
                gridStepY = 20;
                eggSpawnRate = (int)(2.0 * 60);
                break;
            case 3:
                respawnCount = 3;
                gridSpeedX = 2.0;
                gridStepY = 25;
                eggSpawnRate = (int)(1.5 * 60);
                break;
            case 5:
                respawnCount = 3;
                gridSpeedX = 2.5;
                gridStepY = 25;
                eggSpawnRate = (int)(1.0 * 60);
                break;
            case 6:
                respawnCount = 4;
                gridSpeedX = 3.0;
                gridStepY = 30;
                eggSpawnRate = (int)(0.8 * 60);
                break;
            case 7:
                respawnCount = 4;
                gridSpeedX = 3.5;
                gridStepY = 30;
                eggSpawnRate = (int)(0.7 * 60);
        }

        for(int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                int offsetX = c * 60;
                int offsetY = r * 45;

                Cell cell = new Cell(offsetX, offsetY, respawnCount);

                int startX = (int)gridX + offsetX;
                int startY = (int)gridY + offsetY;

                int type = getRandomEnemyTypeForLevel(level);
                cell.setEnemyType(type);

                Enemy enemy = creatEnemyByType(type, startX, startY);

                cell.setCurrentEnemy(enemy);
                gridCells.add(cell);
            }
        }
    }

    private void moveGrid(){
        int minOffsetX = Integer.MAX_VALUE;
        int maxOffsetX = Integer.MIN_VALUE;
        int maxOffsetY = Integer.MIN_VALUE;
        boolean hasActiveCell = false;

        for(Cell cell : gridCells){
            if(!cell.isCleared()){
                if(cell.getOffsetX() < minOffsetX)
                    minOffsetX = cell.getOffsetX();
                if(cell.getOffsetX() > maxOffsetX)
                    maxOffsetX = cell.getOffsetX();
                if(cell.getOffsetY() > maxOffsetY)
                    maxOffsetY = cell.getOffsetY();
                hasActiveCell = true;
            }
        }

        if(!hasActiveCell){
            if(levelTransitionTimer == 0)
                levelTransitionTimer = 180;
            return;
        }

        gridX += gridSpeedX * gridDirection;

        int enemyWidth = (currentLevel == 4 || currentLevel == 8) ? 150 : 45;
        int enemyHeight = (currentLevel == 4 || currentLevel == 8) ? 150 : 45;

        //right collision
        if(gridX + maxOffsetX + enemyWidth >= getWidth()){
            gridX = getWidth() - (maxOffsetX + enemyWidth);
            gridDirection = -1;
            gridY += gridStepY;
        } else if (gridX + minOffsetX <= 0) {
            gridX = -minOffsetX;
            gridDirection = 1;
            gridY += gridStepY;
        }

        //collision with the end of the screen
        if(gridY + maxOffsetY + enemyHeight >= getHeight()){
            System.out.println("Enemy reached bottom");
            triggerGameOver();
            return;
        }

        for(Cell cell : gridCells){
            Enemy enemy = cell.getCurrentEnemy();

            int targetX = (int)gridX + cell.getOffsetX();
            int targetY = (int)gridY + cell.getOffsetY();

            if(enemy == null && cell.getRespawnCounter() > 0){
                int startX = (Math.random() > 0.5) ? 0 : getWidth();
                int startY = -50;

                int type = cell.getEnemyType();

                Enemy newChicken = creatEnemyByType(type, startX, startY);
                newChicken.setArriving(true);

                cell.setCurrentEnemy(newChicken);
            }
            else if(enemy != null){
                if(enemy.isArriving())
                    enemy.flyTowards(targetX, targetY);
                else
                    enemy.setLocation(targetX, targetY);
            }
        }
    }

    //background

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //draw background
        if(backgroundImg1 != null && currentLevel <= 4)
            g.drawImage(backgroundImg1, 0, 0, getWidth(), getHeight(), this);
        else if (backgroundImg2 != null && currentLevel > 4)
            g.drawImage(backgroundImg2, 0, 0, getWidth(), getHeight(), this);

        //draw snow effects
        if(freezeTimer > 0){
            Graphics2D g2d = (Graphics2D) g;
            Composite originalComposite = g2d.getComposite();

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
            g2d.setColor(new Color(60, 193, 205, 255));
            g2d.fillRect(0, 0, getWidth(), getHeight());


            if(snowflakeImg != null){
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));

                int snowSize = 450;
                int snowX = (getWidth() - snowSize) / 2;
                int snowY = (getHeight() - snowSize) / 2;

                g2d.drawImage(snowflakeImg, snowX, snowY, snowSize, snowSize, this);
            }

            g2d.setComposite(originalComposite);
        }

        //draw lives
        if(playerPlane != null){
            g.setColor(new Color(0x11FF00));
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("LIVES: ", 20, 30);

            Image lifeIcon = new ImageIcon("airplan/1.png").getImage();
            for(int i = 0; i < playerPlane.getLives(); i++){
                g.drawImage(lifeIcon, 95 + (i * 40), 10, 25, 25, this);
            }

            //draw user name
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            //g.drawString("PLAYER: " + username, 20, 20);

            //draw score
            g.setColor(Color.white);
            g.drawString("SCORE: " + score, getWidth() - 150, 30);

            //temprory power up marks
            int iconSize = 40;
            int powerUpX = getWidth() - 120;
            int powerUpY = getHeight() - 60;

            g.setFont(new Font("Arial", Font.BOLD, 18));

            if(shieldTimer > 0 && shieldIcon != null){
                g.drawImage(shieldIcon.getImage(), powerUpX, powerUpY, iconSize, iconSize, this);
                g.setColor(Color.cyan);
                g.drawString((shieldTimer / 60) + "s", powerUpX + iconSize + 10, powerUpY + 28);
                powerUpY -= 50;
            }

            if(rapidFireTimer > 0 && rapidFireIcon != null){
                g.drawImage(rapidFireIcon.getImage(), powerUpX, powerUpY, iconSize, iconSize, this);
                g.setColor(Color.red);
                g.drawString((rapidFireTimer / 60) + "s", powerUpX + iconSize +10, powerUpY + 28);
                powerUpY -= 50;
            }

            if(freezeTimer > 0 && freezeIcon != null){
                g.drawImage(freezeIcon.getImage(), powerUpX, powerUpY, iconSize, iconSize, this);
                g.setColor(new Color(100, 200, 255));
                g.drawString((freezeTimer / 60) + "s", powerUpX + iconSize + 10, powerUpY + 28);
                powerUpY -= 50;
            }

            int weaponIconSize = 40;
            int weaponX = 20;
            int weaponY = getHeight() - 60;

            if(addFireIcon != null)
                g.drawImage(addFireIcon.getImage(), weaponX, weaponY, weaponIconSize, weaponIconSize, this);
            g.setColor(Color.orange);
            g.setFont(new Font("Arial", Font.BOLD, 22));
            g.drawString("x " + playerPlane.getWeaponLevel(), weaponX + weaponIconSize + 10, weaponY + 28);

            //draw explosion
            for (Explosion exp : explosions){
                exp.draw(g);
            }

            //draw shield
            if(playerPlane.isShielded()){
                Graphics2D g2d = (Graphics2D) g;

                Composite originalComposite = g2d.getComposite();
                Stroke originalStroke = g2d.getStroke();

                int planeSize = 60;
                int centerX = playerPlane.getX() + planeSize / 2;
                int centerY = playerPlane.getY() + planeSize / 2;

                int shieldRadius = 45;
                int diameter = shieldRadius * 2;

                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                g2d.setColor(Color.cyan);
                g2d.fillOval(centerX - shieldRadius, centerY - shieldRadius, diameter, diameter);

                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
                g2d.setStroke(new BasicStroke(3));
                g2d.drawOval(centerX - shieldRadius, centerY - shieldRadius, diameter, diameter);

                g2d.setComposite(originalComposite);
                g2d.setStroke(originalStroke);
            }
        }

        if(currentLevel == 4 || currentLevel == 8){
            for(Cell cell : gridCells){
                Enemy enemy = cell.getCurrentEnemy();
                if(enemy instanceof BossLevel4 || enemy instanceof BossLevel8){

                    int hpPercent = (int) (((double) enemy.getHealth() / enemy.getMaxHealth()) * 100);
                    int barWidth = 400;
                    int barHeight = 20;
                    int barX = (getWidth() - barWidth) / 2;
                    int barY = 45;

                    g.setColor(new Color(0x151515));
                    g.fillRect(barX, barY, barWidth, barHeight);

                    if(hpPercent > 50)
                        g.setColor(new Color(50, 205, 50));
                    else if (hpPercent > 25)
                        g.setColor(Color.orange);
                    else
                        g.setColor(new Color(0x6A0303));

                    int currentWidth = (int) ((hpPercent / 100.0) * barWidth);
                    g.fillRect(barX, barY, currentWidth, barHeight);

                    g.setColor(Color.white);
                    g.drawRect(barX, barY, barWidth, barHeight);

                    g.setFont(new Font("Arial", Font.BOLD, 14));
                    g.drawString("BOSS", barX + barWidth / 2 - 20, barY + 15);
                }
            }
        }

        //draw level name on screen
        if(levelTransitionTimer > 0){
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            String msg = (currentLevel == 8) ? "Winner Winner\nChicken Dinner!" : "LEVEL " + (currentLevel + 1);
            if(currentLevel == 3 || currentLevel == 7)
                msg = "BOSS LEVEL!";

            g2d.setColor(new Color(0xEC4A05));
            g2d.setFont(new Font("Arial", Font.ITALIC, 55));
            FontMetrics fm = g2d.getFontMetrics();
            int msgX = (getWidth() - fm.stringWidth(msg)) / 2;
            int msgY = getHeight() / 2;
            g2d.drawString(msg, msgX, msgY);
        }

        if(bossExplosionTimer > 0 && bossExplosionImg != null)
            g.drawImage(bossExplosionImg, bossExpX, bossExpY, 200, 200, this);

        //draw level
        if(levelTransitionTimer == 0) {
            g.setColor(Color.yellow);
            g.drawString("LEVEL: " + currentLevel, getWidth() / 2 - 40, 30);
        }

        //draw pause
        if(isPaused){
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(new Color(0xAC0A0A));
            g2d.setFont(new Font("Arial", Font.ITALIC, 50));
            String msg = "PAUSED";
            FontMetrics fm = g2d.getFontMetrics();
            int msgX = (getWidth() - fm.stringWidth(msg)) / 2;
            int msgY = getHeight() / 2;
            g2d.drawString(msg, msgX, msgY);
        }
    }

    public void triggerGameOver(){
        gameTimer.stop();
        SoundManager.playGameOverSound("sound-effects/mixkit-retro-arcade-game-over-470.wav");
        System.out.println("Game Over");

        for(Egg egg : eggs)
            egg.removeFromPanel(GamePanel.this);
        eggs.clear();

        for(PowerUp powerUp : powerUps)
            powerUp.removeFromPanel(GamePanel.this);
        powerUps.clear();

        for(Bullet bullet : bullets)
            bullet.removeFromPanel(GamePanel.this);
        bullets.clear();

        if(this.gameMain != null)
            gameMain.showPanel("MainMenu");
    }

    public int getRandomEnemyTypeForLevel(int level){
        if(level == 1)
            return 1;
        else if (level == 2) {
            return (Math.random() > 0.5) ? 1 : 2;
        }
        else if (level == 3) {
            return (Math.random() > 0.5) ? 1 : 3;
        }

        return 1;
    }

    private Enemy creatEnemyByType(int type, int startX, int startY){
        if(type == 2)
            return new FastEnemy(startX, startY, currentLevel, fastChickenIcon, this);
        if(type == 3)
            return  new ZigzagEnemy(startX, startY, currentLevel, zigzagChickenIcon, this);
        if(type == 4)
            return new BossLevel4(startX, startY, 50, boss1Icon, this);
        return new NormalEnemy(startX, startY, currentLevel, normalChickenIcon, this);
    }
}
