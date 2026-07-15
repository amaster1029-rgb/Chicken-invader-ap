package ChickenInvaders.model;

import javax.swing.*;
import java.awt.*;

public class Bullet {
    private int x, y;
    private int speed = 10;
    private JLabel bulletLabel;

    public Bullet(int startX, int startY, ImageIcon shotIcon, JPanel mainPanel){
        this.x = startX + 27;
        this.y = startY - 20;
        this.bulletLabel = new JLabel(shotIcon);
        this.bulletLabel.setBounds(x, y, 5, 20);
        mainPanel.add(bulletLabel, 0);
    }

    public void move(){
        y -= speed;
        bulletLabel.setLocation(x, y);
    }

    public void removeFromPanel(JPanel mainPanel){
        mainPanel.remove(bulletLabel);
    }

    public Rectangle getBounds(){
        return bulletLabel.getBounds();
    }

    public int getY(){
        return y;
    }
}
