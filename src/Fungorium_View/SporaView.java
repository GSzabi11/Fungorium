package Fungorium_View;

import Fugorium_Model.Spora;
import Fugorium_Model.Tekton;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SporaView extends AbstractEntityView {
    public SporaView(int x, int y, int width, int height) {
        super("/spora.png", width, height, x,y);
    }

    @Override
    protected void drawEntity(Graphics2D g2, Object model) {

        if (!(model instanceof Spora)) return;
        Spora spora = (Spora) model;

        BufferedImage img = getImage();
        int w = img.getWidth();
        int h = img.getHeight();

        // régi megoldás, saját koordinátából:
        int cx = spora.getX();
        int cy = spora.getY();
        g2.drawImage(img, cx - w/2, cy - h/2, null);
        /*

        if (!(model instanceof Spora)) return;
        Spora spora = (Spora) model;

        // 1) betöltjük a spóra-sprite-ot
        BufferedImage img = getImage();
        int w = img.getWidth(), h = img.getHeight();

        // 2) lekérjük a spórát tároló tekton-t
        Tekton parent = spora.getTekton();  // vagy getFromTekton()/ami nálad van

        // 3) lekérjük a tekton sprite méreteit és a scale-t (ha van)
        //    Itt példaként ugyanazt a super-getImage-t használjuk, amit a TektonView:
        BufferedImage tekImg;
        try {
            tekImg = ImageIO.read(getClass().getResourceAsStream("/tekton.png"));
        } catch (IOException | NullPointerException e) {
            tekImg = new BufferedImage(64,64,BufferedImage.TYPE_INT_ARGB); // fallback
        }
        int tw = tekImg.getWidth(), th = tekImg.getHeight();
        double scale = 1.5;  // ugyanaz a scale, amit a TektonView-ban használsz

        // 4) kiszámoljuk a jobb-alsó sarok pixelkoordinátáit
        //    feltételezzük, hogy a parent.getX()/getY() a tekton bal-felsője
        double cornerX = parent.getX() + tw * scale;
        double cornerY = parent.getY() + th * scale;

        // 5) ide rajzoljuk a spórát, középre igazítva
        int drawX = (int)(cornerX - w/2.0);
        int drawY = (int)(cornerY - h/2.0);

        g2.drawImage(img, drawX, drawY, null);
        */
    }
}