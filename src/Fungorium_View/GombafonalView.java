package Fungorium_View;

import Fugorium_Model.Gombafonal;
import java.awt.Graphics2D;

public class GombafonalView extends AbstractEntityView {
    public GombafonalView(int x, int y, int width, int height) {
        super("/gombafonal_zold.png", width, height, x,y);
    }

    @Override
    protected void drawEntity(Graphics2D g2, Object model) {
        if (model instanceof Gombafonal gf) {
            g2.drawImage(
                    getImage(),
                    gf.getX(), gf.getY(),
                    getWidth(), getHeight(),
                    null
            );
        }
    }
}