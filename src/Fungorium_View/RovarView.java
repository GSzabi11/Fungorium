package Fungorium_View;

import Fugorium_Model.Rovar;
import java.awt.Graphics2D;

public class RovarView extends AbstractEntityView {
    public RovarView(int x, int y, int width, int height) {
        super("/rovar_lila.png", width, height, x,y);
    }

    @Override
    protected void drawEntity(Graphics2D g2, Object model) {
        if (model instanceof Rovar rovar) {
            g2.drawImage(
                    getImage(),
                    rovar.getX(), rovar.getY(),
                    getWidth(), getHeight(),
                    null
            );
        }
    }
}