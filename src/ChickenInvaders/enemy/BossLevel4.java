package ChickenInvaders.enemy;

import ChickenInvaders.ui.GamePanel;
import javax.swing.*;

public class BossLevel4 extends Enemy{
    private int maxHealth;
    public BossLevel4(int x, int y, int hp, ImageIcon icon, GamePanel panel){
        super(x, y, hp, icon, panel);
        this.maxHealth = hp;
        this.speed = 3;
    }

    public int getMaxHealth(){
        return maxHealth;
    }

    @Override
    public void attackBehavior() {

    }
}
