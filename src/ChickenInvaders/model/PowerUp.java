package ChickenInvaders.model;

import javax.swing.*;
import java.awt.*;

public class PowerUp {
    public enum Type{
        addFire,
        rapidFire,
        extraLife,
        shield,
        freeze
    }

    private double x, y;
    private double speedY = 2;
    private JLabel powerUpLabel;
    private Type type;

    public PowerUp(int startX, int startY, ImageIcon icon, Type type, JPanel panel){
        this.x = startX;
        this.y = startY;
        this.type = type;
        this.powerUpLabel = new JLabel(icon);
        this.powerUpLabel.setBounds((int)x, (int)y, 30, 30);
        panel.add(powerUpLabel, 0);
    }

    public Type getType(){
        return type;
    }

    public void move(){
        y += speedY;
        powerUpLabel.setLocation((int)x, (int)y);
    }

    public int getY(){
        return (int)y;
    }

    public Rectangle getBounds(){
        return powerUpLabel.getBounds();
    }

    public void removeFromPanel(JPanel panel){
        panel.remove(powerUpLabel);
    }
}
