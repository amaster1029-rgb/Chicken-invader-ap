package ChickenInvaders.enemy;

import javax.swing.*;
import java.awt.*;

public abstract class Enemy {
    protected int x, y;
    protected int width = 45;
    protected int height = 45;
    protected int health;
    protected JLabel enemyLabel;

    public Enemy(int x, int y, int health, ImageIcon icon, JPanel mainPanel){
        this.x = x;
        this.y = y;
        this.health = health;
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

    public void takeDamage(){
        if(health > 0)
            health--;
    }

    public boolean isDead(){
        return health <= 0;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setLocation(int newX, int newY){
        this.x = newX;
        this.y = newY;
        enemyLabel.setLocation(x, y);
    }
}
