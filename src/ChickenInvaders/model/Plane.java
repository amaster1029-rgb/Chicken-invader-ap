package ChickenInvaders.model;

import javax.swing.*;
import java.awt.*;

public class Plane {
    private int x, y;
    private int speed = 5;
    private int lives = 3;
    private JLabel planeLabel;
    private int weaponLevel = 1;
    private final int maxLives = 5;
    private boolean isShielded = false;

    public Plane(int startX, int startY, ImageIcon planeIcon, JPanel mainPanel){
        this.x = startX;
        this.y = startY;
        this.planeLabel = new JLabel(planeIcon);
        this.planeLabel.setBounds(x, y, 60, 60);
        mainPanel.add(planeLabel);
    }

    public void moveRight(int panelWidth){
        if(x < panelWidth - 60){
            x += speed;
            updateBounds();
        }
    }

    public void moveLeft(){
        if(x > 0){
            x -= speed;
            updateBounds();
        }
    }

    public void moveDown(int panelHeight){
        if(y < panelHeight -60){
            y +=speed;
            updateBounds();
        }
    }

    public void moveUp(){
        if(y > 250){
            y -= speed;
            updateBounds();
        }
    }

    public void updateBounds(){
        planeLabel.setBounds(x, y, 60, 60);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getLives(){
        return lives;
    }

    public void takeDamage(){
        if(lives > 0)
            lives--;
    }

    public boolean isDead(){
        return lives <= 0;
    }

    public Rectangle getBounds(){
        return planeLabel.getBounds();
    }

    public void removeFromPanel(JPanel panel){
        panel.remove(planeLabel);
    }

    public int getWeaponLevel(){
        return weaponLevel;
    }

    public void upgradeWeapon(){
        if(weaponLevel < 5)
            weaponLevel++;
    }

    public void addLife(){
        if(lives < maxLives)
            lives++;
    }

    public boolean isShielded(){
        return isShielded;
    }

    public void setShielded(boolean shielded){
        this.isShielded = shielded;
    }

    public void decreaseLive(){
        if(!isShielded && lives > 0)
            lives--;
    }
}
