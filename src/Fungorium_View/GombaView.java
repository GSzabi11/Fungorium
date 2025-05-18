package Fungorium_View;

import Fugorium_Model.Gomba;
import Fugorium_Model.Gombafaj;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GombaView extends AbstractEntityView {

    public GombaView(int x, int y, int width, int height) {
        super("/gomba_kek.png", width, height, x, y);
    }

//    private static String getImagePath(Gombafaj fajta) {
//        return switch (fajta) {
//            case KEK -> "/gomba_kek.png";
//            case PIROS -> "/gomba_piros.png";
//            case ZOLD -> "/gomba_zold.png";
//            case SARGA -> "/gomba_sarga.png";
//        };
//    }

    @Override
    protected void drawEntity(Graphics2D g2, Object model) {

        if (!(model instanceof Gomba)) return;

        Gomba gomba = (Gomba) model;

        // 1) Dinamikus sprite-kiválasztás:
        Gombafaj fajta = gomba.getFajta();
        String spriteFile;
        switch (fajta) {
            case KEK:
                spriteFile = "/gomba_kek.png";
                break;
            case SARGA:
                spriteFile = "/gomba_sarga.png";
                break;
            case PIROS:
                spriteFile = "/gomba_piros.png";
                break;
            default:
                spriteFile = "/gomba_zold.png";
        }

        BufferedImage img;
        try {
            img = ImageIO.read(getClass().getResourceAsStream(spriteFile));
        } catch (IOException | NullPointerException ex) {
            // fallback az eredeti, semleges sprite, ha valami hiba
            img = getImage();
        }

        int w = img.getWidth();
        int h = img.getHeight();

        // A gomba pozíciója (középpont):
        int cx = gomba.getX() + 56;
        int cy = gomba.getY() + 13;

        double scale = 2.25;

        AffineTransform at = new AffineTransform();
        at.translate(cx, cy);
        at.scale(scale, scale);
        at.translate(-w/2.0, -h/2.0);
        g2.drawImage(img, at, null);
    }
}
