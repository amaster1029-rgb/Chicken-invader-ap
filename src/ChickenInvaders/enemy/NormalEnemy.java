package ChickenInvaders.enemy;

import javax.swing.*;

public class NormalEnemy extends Enemy{
    public NormalEnemy(int x, int y, ImageIcon icon, JPanel mainPanel){
        super(x, y, 45, 45, 1, icon, mainPanel);
    }

    @Override
    public void move() {
        
    }
}
