package Fungorium_View;

import Fugorium_Model.Tekton;

import java.awt.*;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class TektonView extends AbstractEntityView {
    public TektonView(int x, int y, int width, int height) {
        super("/tekton.png", width, height, x,y);
    }

    @Override
    protected void drawEntity(Graphics2D g2, Object model) {

        if (!(model instanceof Tekton)) return;
        Tekton t = (Tekton) model;
        BufferedImage img = getImage();
        int w = img.getWidth(), h = img.getHeight();

        // Modellben tárolt pozíció (bal-felső sarok):
        int x = t.getX();
        int y = t.getY();

        // 1) Méretnövelő faktor
        double scale = 3;  // 350%–os méret
        // 2) Transzformáció összeállítása
        AffineTransform at = new AffineTransform();
        at.translate(x, y);
        at.scale(scale, scale);
        // ha a modell X/Y a sprite középpontja, akkor helyette:
        // at.translate(x - (w*scale)/2, y - (h*scale)/2);

        // 3) Kirajzoljuk
        g2.drawImage(img, at, null);
    }
}