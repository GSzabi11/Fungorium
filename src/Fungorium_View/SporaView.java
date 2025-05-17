package Fungorium_View;

import Fugorium_Model.Spora;
import java.awt.Graphics2D;

public class SporaView extends AbstractEntityView {
    public SporaView(int x, int y, int width, int height) {
        super("/spora.png", width, height, x,y);
    }

    @Override
    protected void drawEntity(Graphics2D g2, Object model) {
        if (model instanceof Spora spora) {
            g2.drawImage(
                    getImage(),
                    spora.getX(), spora.getY(),
                    getWidth(), getHeight(),
                    null
            );
        }
    }
}