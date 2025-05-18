package Fungorium_View;

import Fugorium_Model.Gomba;
import Fugorium_Model.Gombafaj;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class GombaView extends AbstractEntityView {

    public GombaView(int x, int y, int width, int height, Gombafaj fajta) {
        super(getImagePath(fajta), width, height, x, y);
    }

    private static String getImagePath(Gombafaj fajta) {
        return switch (fajta) {
            case KEK -> "/gomba_kek.png";
            case PIROS -> "/gomba_piros.png";
            case ZOLD -> "/gomba_zold.png";
            case SARGA -> "/gomba_sarga.png";
        };
    }

    @Override
    protected void drawEntity(Graphics2D g2, Object model) {

        if (!(model instanceof Gomba)) return;

        Gomba gomba = (Gomba) model;
        BufferedImage img = getImage();
        int w = img.getWidth(), h = img.getHeight();

        // A gomba pozíciója (középpont):
        int cx = gomba.getX() + 38;
        int cy = gomba.getY() + 6;

        double scale = 1.75;

        AffineTransform at = new AffineTransform();
        at.translate(cx, cy);
        at.scale(scale, scale);
        at.translate(-w/2.0, -h/2.0);
        g2.drawImage(img, at, null);
    }
}
