package Fungorium_View;

import Fugorium_Model.Tekton;

import java.awt.*;

public class TektonView extends Sprite implements Rajzolo {
    public TektonView(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
    }

    @Override
    public void rajzol(Graphics2D g2, Object model) {
        if (model instanceof Tekton tekton) {
            //g2.drawImage(getImage(), getX(), getY(), getWidth(), getHeight(), null);
            g2.setColor(Color.BLACK);
            //g2.drawString(tekton.getNev(), getX() + 5, getY() + 15);
        }
    }
}