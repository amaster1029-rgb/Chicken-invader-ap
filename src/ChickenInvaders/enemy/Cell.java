package ChickenInvaders.enemy;

import ChickenInvaders.enemy.Enemy;

public class Cell {
    private int offsetX;
    private int offsetY;
    private int respawnCounter;
    private Enemy currentEnemy;

    public Cell(int offsetX, int offsetY, int initialCounter){
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.respawnCounter = initialCounter;
    }

    public int getOffsetX(){
        return offsetX;
    }

    public int getOffsetY(){
        return offsetY;
    }

    public int getRespawnCounter(){
        return respawnCounter;
    }

    public Enemy getCurrentEnemy(){
        return currentEnemy;
    }

    public void setCurrentEnemy(Enemy enemy){
        this.currentEnemy = enemy;
    }

    public void enemyKilled(){
        this.currentEnemy = null;
        if(respawnCounter > 0)
            respawnCounter--;
    }

    public boolean isCleared(){
        return respawnCounter <= 0 && currentEnemy == null;
    }
}
