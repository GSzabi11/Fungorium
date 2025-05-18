package Fungorium_View;

import Fugorium_Model.Rovar;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class RovarView extends AbstractEntityView {
    public RovarView(int x, int y, int width, int height) {
        super("/rovar_lila.png", width, height, x,y);
    }

    @Override
    protected void drawEntity(Graphics2D g2, Object model) {

        if (!(model instanceof Rovar)) {
            return;
        }
        Rovar rovar = (Rovar) model;
        BufferedImage img = getImage();

        int w = img.getWidth();
        int h = img.getHeight();
        int cx = rovar.getHelyzet().getX() + 23;
        int cy = rovar.getHelyzet().getY() + 32;

        g2.drawImage(img, cx - w/2, cy - h/2, null);
    }
}