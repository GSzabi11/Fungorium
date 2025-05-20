package Fungorium_View;

import Fugorium_Model.Spora;
import Fugorium_Model.Tekton;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
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
        Tekton parent = spora.getTekton();
        if (parent == null) return;

        // 1) Betöltjük a tekton sprite-ot
        BufferedImage tekImg;
        try {
            tekImg = ImageIO.read(getClass().getResourceAsStream("/tekton.png"));
        } catch (IOException|NullPointerException ex) {
            tekImg = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
        }
        double scale = 3.0;  // ugyanaz, mint a TektonView-ban
        int tw = (int)(tekImg.getWidth() * scale);
        int th = (int)(tekImg.getHeight() * scale);

        // 2) A spóra sprite
        BufferedImage spImg = getImage();
        int sw = spImg.getWidth(), sh = spImg.getHeight();

        // 3) Jobb–felső sarok koordinátái
        //    Feltételezzük, hogy parent.getX/Y() a tekton bal-felső sarok
        int cornerX = parent.getX() + tw;
        int cornerY = parent.getY();

        // 4) Középre igazítjuk a spórát erre a pontra
        int drawX = cornerX - sw/2;
        int drawY = cornerY - sh/2;

        // 5) Rajzolás
        g2.drawImage(spImg, drawX, drawY, null);

    /*protected void drawEntity(Graphics2D g2, Object model) {
        if (!(model instanceof Spora)) return;
        Spora spora = (Spora) model;

        BufferedImage img = getImage();
        int w = img.getWidth();
        int h = img.getHeight();

        // Modellben tárolt pozíció (jobb-felső sarok):
        int x = spora.getX();
        int y = spora.getY();

        // 1) Méretnövelő faktor
        double scale = 2;
        // 2) Transzformáció összeállítása
        AffineTransform at = new AffineTransform();
        at.translate(x, y);
        at.scale(scale, scale);

        // 3) Kirajzoljuk
        g2.drawImage(img, at, null);

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