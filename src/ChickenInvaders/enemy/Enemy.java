package ChickenInvaders.enemy;

import javax.swing.*;
import java.awt.*;

public abstract class Enemy {
    protected int x, y;
    protected double floatX, floatY;
    protected int width = 45;
    protected int height = 45;
    protected int health;
    protected int maxHealth;
    protected JLabel enemyLabel;
    protected boolean isArriving = false;
    protected int speed = 2;

    public Enemy(int x, int y, int health, ImageIcon icon, JPanel mainPanel){
        this.x = x;
        this.y = y;
        this.floatX = x;
        this.floatY = y;
        this.health = health;
        this.maxHealth = health;

        this.width = icon.getIconWidth();
        this.height = icon.getIconHeight();

        this.enemyLabel = new JLabel(icon);
        this.enemyLabel.setBounds(x, y, width, height);
        mainPanel.add(enemyLabel, 0);
    }

    public abstract void attackBehavior();

    public void removeFromPanel(JPanel mainPanel){
        mainPanel.remove(enemyLabel);
    }

    public Rectangle getBounds(){
        return enemyLabel.getBounds();
    }

    public int getHealth(){
        return health;
    }

    public int getMaxHealth(){
        return maxHealth;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public void takeDamage(){
        if(health > 0)
            health--;
    }

    public boolean isDead(){
        return health <= 0;
    }

    public int getX(){
        return enemyLabel.getX();
    }

    public int getY(){
        return enemyLabel.getY();
    }

    public void setLocation(int newX, int newY){
        if(isArriving)
            return;
        this.x = newX;
        this.y = newY;
        this.floatX = newX;
        this.floatY = newY;
        enemyLabel.setLocation(newX, newY);
    }

    public boolean isArriving(){
        return isArriving;
    }

    public void setArriving(boolean arriving){
        this.isArriving = arriving;
    }

    public void flyTowards(int targetX, int targetY){
        double dx = targetX - floatX;
        double dy = targetY - floatY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if(distance <= speed){
            isArriving = false;
            setLocation(targetX, targetY);
        }
        else {
            floatX += (dx / distance) * speed;
            floatY += (dy / distance) * speed;
            this.x = (int)floatX;
            this.y = (int)floatY;
            enemyLabel.setLocation(x, y);
        }
    }
}
