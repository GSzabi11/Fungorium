package Fungorium_View;

import Fugorium_Model.Gomba;
import Fugorium_Model.Gombafaj;

import java.awt.Graphics2D;

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
        if (model instanceof Gomba gomba) {
            g2.drawImage(
                    getImage(),
                    gomba.getX(), gomba.getY(),
                    getWidth(), getHeight(),
                    null
            );
        }
    }
}
