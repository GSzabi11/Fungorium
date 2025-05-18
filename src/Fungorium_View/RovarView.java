package Fungorium_View;

import Fugorium_Model.Gombafaj;
import Fugorium_Model.Rovar;
import Fugorium_Model.Rovarfaj;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class RovarView extends AbstractEntityView {
    public RovarView(int x, int y, int width, int height) {
        super("/rovar_lila.png", width, height, x,y);
    }

//    private static String getImagePath(Rovarfaj fajta) {
//        return switch (fajta) {
//            case LILA -> "/rovar_lila.png";
//            case CIAN -> "/rovar_zold.png";
//            case BARNA -> "/rovar_barna.png";
//            case NARANCS -> "/rovar_narancs.png";
//        };
//    }

    @Override
    protected void drawEntity(Graphics2D g2, Object model) {

        if (!(model instanceof Rovar)) {
            return;
        }
        Rovar rovar = (Rovar) model;

        Rovarfaj fajta = rovar.getFajta();
        String spriteFile;
        switch (fajta) {
            case BARNA:
                spriteFile = "/rovar_barna.png";
                break;
            case CIAN:
                spriteFile = "/rovar_zold.png";
                break;
            case LILA:
                spriteFile = "/rovar_lila.png";
                break;
            default:
                spriteFile = "/rovar_narancs.png";
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

        int cx = 0;
        int cy = 0;
        if (rovar.getFajta() == Rovarfaj.LILA)
        {
            cx = rovar.getHelyzet().getX() + 23;
            cy = rovar.getHelyzet().getY() + 12;
        }
        if (rovar.getFajta() == Rovarfaj.BARNA)
        {
            cx = rovar.getHelyzet().getX() + 23;
            cy = rovar.getHelyzet().getY() + 28;
        }
        if (rovar.getFajta() == Rovarfaj.NARANCS)
        {
            cx = rovar.getHelyzet().getX() + 25;
            cy = rovar.getHelyzet().getY() + 45;
        }
        if (rovar.getFajta() == Rovarfaj.CIAN)
        {
            cx = rovar.getHelyzet().getX() + 31;
            cy = rovar.getHelyzet().getY() + 59;
        }

        g2.drawImage(img, cx - w/2, cy - h/2, null);
    }
}