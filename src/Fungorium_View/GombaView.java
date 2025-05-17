package Fungorium_View;

import Fugorium_Model.Gomba;

import java.awt.Graphics2D;

public class GombaView extends AbstractEntityView {
    public GombaView(int x, int y, int width, int height) {
        super("/gomba_kek.png", width, height, x,y);
    }

    @Override
    protected void drawEntity(Graphics2D g2, Object model) {
        if (model instanceof Gomba gomba) {
            g2.drawImage(
                    getImage(),
                    gomba.getX(), gomba.getY(),
                    getWidth(), getHeight(),
                    null
            );
        }
    }
}