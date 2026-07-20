package ChickenInvaders.enemy;

import ChickenInvaders.ui.GamePanel;

import javax.swing.*;

public class BossLevel8 extends Enemy{
    public BossLevel8(int x, int y, int hp, ImageIcon icon, GamePanel panel){
        super(x, y, hp, icon, panel);
        this.speed = 2;
    }

    @Override
    public void attackBehavior() {

    }
}
