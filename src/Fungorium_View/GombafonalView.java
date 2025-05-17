package Fungorium_View;

import Fugorium_Model.Gombafonal;
import Fugorium_Model.Tekton;
import java.awt.*;

public class GombafonalView extends Sprite implements Rajzolo {
    public GombafonalView(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
    }
    /**
     * Kirajzolja a gombafonalat: végigmegy a kapcsolódási pontokon és
     * egymást összekötő vonalakkal jeleníti meg a fonalat.
     *
     * @param g2   a Graphics2D objektum, amire rajzolunk
     * @param gf   a modellből jövő Gombafonal objektum
     */
    @Override
    public void rajzol(Graphics2D g2, Object gf) {
        List<Tekton> pontok = gf.getKapcsolodasPontok();
        if (pontok == null || pontok.size() < 2) {
            return; // nincs elég pont a kirajzoláshoz
        }

        // Eredeti grafikai beállítások mentése
        Stroke eredetiVonal = g2.getStroke();
        Color eredetiSzín = g2.getColor();

        // Fonal vastagság és szín beállítása
        g2.setStroke(new BasicStroke(2.0f));      // 2px vastag vonal
        g2.setColor(Color.LIGHT_GRAY);            // világosszürke fonal

        // Pontok összekötése vonalakkal
        for (int i = 1; i < pontok.size(); i++) {
            Tekton elso = pontok.get(i - 1);
            Tekton masodik = pontok.get(i);
            g2.drawLine(
                    elso.getX(), elso.getY(),
                    masodik.getX(), masodik.getY()
            );
        }

        // Eredeti grafikai beállítások visszaállítása
        g2.setStroke(eredetiVonal);
        g2.setColor(eredetiSzín);
    }
}