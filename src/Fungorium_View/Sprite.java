package Fungorium_View;

import java.awt.*;

public class Sprite {

    protected double x;
    protected double y;
    protected int width;
    protected int height;
    protected Image image;
    private Rectangle hitbox;

    public Sprite(int x, int y, int width, int height, Image image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        this.hitbox = createHitbox();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        updateHitbox();
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        updateHitbox();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        updateHitbox();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        updateHitbox();
    }

    private Rectangle createHitbox() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    public void updateHitbox() {
        this.hitbox = createHitbox();
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, (int) x, (int) y, width, height, null);
        g2d.setColor(Color.RED);
        g2d.draw(hitbox);
    }
}

