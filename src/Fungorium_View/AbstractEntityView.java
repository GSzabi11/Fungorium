package Fungorium_View;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

public abstract class AbstractEntityView extends Sprite implements Rajzolo {
    private BufferedImage image;

    /**
     * @param width Az entitás kirajzolási szélessége
     * @param height Az entitás kirajzolási magassága
     */
    protected AbstractEntityView(String imagePath, int width, int height, int x, int y) {
        super(x, y, width, height,new ImageIcon(imagePath).getImage());
        // Beállítjuk a sprite méreteit, ha a Sprite osztály rendelkezik setWidth/setHeight metódussal
        setWidth(width);
        setHeight(height);
        try {
            image =ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePath)));
        } catch (ClassCastException | IOException e) {
            System.out.println("Hiba: Az image nem BufferedImage típusú.");
        }
    }

    protected BufferedImage getImage() {
        return image;
    }

    @Override
    public void rajzol(Graphics2D g2, Object model) {
        drawEntity(g2, model);
    }

    /**
     * A konkrét nézetek felülírják ezt, hogy ténylegesen kirajzolják a modell adatait.
     */
    protected abstract void drawEntity(Graphics2D g2, Object model);
}