package ChickenInvaders.enemy;

import javax.swing.*;

public class ZigzagEnemy extends Enemy{
    private double waveAngle = 0;

    public ZigzagEnemy(int x, int y, int level, ImageIcon icon, JPanel mainPanel){
        super(x, y, (level <= 3) ? 2 : 3, icon, mainPanel);
        this.speed = 2;
    }

    @Override
    public void flyTowards(int targetX, int targetY) {
        double dx = targetX - floatX;
        double dy = targetY - floatY;
        double distance = Math.sqrt(dx*dx + dy*dy);

        if(distance <= speed){
            isArriving = false;
            setLocation(targetX, targetY);
        }
        else {
            floatX += (dx / distance) * speed;
            floatY += (dy / distance) * speed;

            waveAngle += 0.2;
            int zigzagOffset = (int)(Math.sin(waveAngle) * 20); //changing zigzag 20 pixels

            this.x = (int) floatX + zigzagOffset;
            this.y = (int) floatY;

            enemyLabel.setLocation(x, y);
        }
    }

    @Override
    public void setLocation(int newX, int newY) {
        waveAngle += 0.2;
        int zigzagOffset = (int) (Math.sin(waveAngle) * 20);

        super.setLocation(newX + zigzagOffset, newY);
    }

    @Override
    public void attackBehavior() {
        
    }
}
