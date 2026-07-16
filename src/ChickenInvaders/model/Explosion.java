package ChickenInvaders.model;

import java.awt.*;

public class Explosion {
    private int x, y;
    private int radius;
    private int alpha;
    private Color color;

    public Explosion(int x, int y, Color color){
        this.x = x;
        this.y = y;
        this.radius = 5;
        this.alpha = 255;
        this.color = color;
    }

    public void update(){
        radius += 3;
        alpha -= 15;
        if(alpha < 0)
            alpha = 0;
    }

    public boolean isVanished(){
        return alpha <= 0;
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha / 255f));
        g2d.setColor(color);
        g2d.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
