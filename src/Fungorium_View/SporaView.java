package Fungorium_View;

import Fugorium_Model.Spora;

import java.awt.*;

public class SporaView extends Sprite implements Rajzolo {
    public SporaView(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
    }

    @Override
    public void rajzol(Graphics2D g2, Object model) {
        if (model instanceof Spora spora) {
            g2.drawImage(image, (int) x, (int) y, width, height, null);
            // Rajzolás logika itt
            // Például: g2.drawString(spora.getName(), (int) x, (int) y);
        }
    }
}