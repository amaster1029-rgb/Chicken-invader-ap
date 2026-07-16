package ChickenInvaders.enemy;

import javax.swing.*;

public class FastEnemy extends Enemy{
    public FastEnemy(int x, int y, int level, ImageIcon icon, JPanel mainPanel){
        super(x, y, (level <= 3) ? 1 : 2, icon, mainPanel);
        this.speed = 4;
    }

    @Override
    public void attackBehavior() {
        
    }
}
