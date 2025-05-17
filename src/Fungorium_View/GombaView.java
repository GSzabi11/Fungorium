package Fungorium_View;

import Fugorium_Model.Gomba;

import java.awt.*;

public class GombaView extends Sprite implements Rajzolo {
    public GombaView(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
    }

    @Override
    public void rajzol(Graphics2D g2, Object model) {
        if (model instanceof Gomba gomba) {
            g2.drawImage(getImage(), (int)getX(), (int)getY(), getWidth(), getHeight(), null);
            g2.setColor(Color.BLACK);
           // g2.drawString(gomba.getNev(), getX() + 5, getY() + 15);
        }
    }

    private Image getImage() {
        // Implementálja a megfelelő képet a gomba típusának megfelelően
        return null; // Példa: return ImageLoader.loadImage("gomba.png");
    }
}