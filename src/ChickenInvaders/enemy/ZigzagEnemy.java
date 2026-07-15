package ChickenInvaders.enemy;

import javax.swing.*;

public class ZigzagEnemy extends Enemy{
    public ZigzagEnemy(int x, int y, int level, ImageIcon icon, JPanel mainPanel){
        super(x, y, (level <= 3) ? 2 : 3, icon, mainPanel);
    }

    @Override
    public void attackBehavior() {
        
    }
}
