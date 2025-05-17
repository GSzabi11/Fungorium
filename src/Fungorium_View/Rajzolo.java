package Fungorium_View;

import java.awt.*;

public interface Rajzolo {
    /**
     * Kirajzolja a modellobjektumot a megadott Graphics2D felületre.
     *
     * @param g2    a Graphics2D objektum, amire rajzolunk
     * @param model a megjelenítendő modellobjektum (pl. Tekton, Rovar, Gombafonal stb.)
     */
    void rajzol(Graphics2D g2, Object model);
}
