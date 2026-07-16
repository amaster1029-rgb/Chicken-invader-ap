package ChickenInvaders.model;

import javax.swing.*;
import java.awt.*;

public class Egg {
    private double x, y;
    private double speedX, speedY;
    private JLabel eggLabel;

    public Egg(int startX, int startY, double speedX, double speedY, ImageIcon icon, JPanel panel){
        this.x = startX;
        this.y = startY;
        this.speedX = speedX;
        this.speedY = speedY;
        this.eggLabel = new JLabel(icon);
        this.eggLabel.setBounds((int)x, (int)y, 15, 20);
        panel.add(eggLabel, 0);
    }

    public void move(){
        x += speedX;
        y += speedY;
        eggLabel.setLocation((int)x, (int)y);
    }

    public int getY(){
        return (int)y;
    }

    public Rectangle getBounds(){
        return eggLabel.getBounds();
    }

    public void removeFromPanel(JPanel panel){
        panel.remove(eggLabel);
    }
}
