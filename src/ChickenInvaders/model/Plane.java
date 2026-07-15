package ChickenInvaders.model;

import javax.swing.*;
import java.awt.*;

public class Plane {
    private int x, y;
    private int speed = 5;
    private int lives = 3;
    private JLabel planeLabel;

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
        if(y >= 0){
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

    public void decreaseLive(){
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
}
