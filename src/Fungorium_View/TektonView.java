package Fungorium_View;

import Fugorium_Model.Tekton;

import java.awt.*;

import java.awt.Graphics2D;

public class TektonView extends AbstractEntityView {
    public TektonView(int x, int y, int width, int height) {
        super("/tekton.png", width, height, x,y);
    }

    @Override
    protected void drawEntity(Graphics2D g2, Object model) {
        if (model instanceof Tekton tekton) {
            g2.drawImage(
                    getImage(),
                    tekton.getX(), tekton.getY(),
                    getWidth(), getHeight(),
                    null
            );
        }
    }
}