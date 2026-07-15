package ChickenInvaders.enemy;

import javax.swing.*;
import java.awt.*;

public abstract class Enemy {
    protected int x, y;
    protected int width, height;
    protected int health;
    protected JLabel enemyLabel;

    public Enemy(int x, int y, int width, int height, int health, ImageIcon icon, JPanel mainPanel){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health;
        this.enemyLabel = new JLabel(icon);
        this.enemyLabel.setBounds(x, y, width, height);
        mainPanel.add(enemyLabel, 0);
    }

    public abstract void move();

    public void removeFromPanel(JPanel mainPanel){
        mainPanel.remove(enemyLabel);
    }

    public Rectangle getBounds(){
        return enemyLabel.getBounds();
    }

    public int getHealth(){
        return health;
    }

    public void damage(){
        if(health > 0)
            health--;
    }
}
