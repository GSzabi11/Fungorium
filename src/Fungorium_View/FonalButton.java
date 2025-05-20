package Fungorium_View;

import javax.swing.JButton;
import java.awt.*;

public class FonalButton extends JButton {
    private final Point p1, p2;
    private final double halfThick;

    public FonalButton(Point p1, Point p2, double thickness) {
        super();            // nincs ikon, nincs szöveg
        this.p1 = p1;
        this.p2 = p2;
        this.halfThick = thickness/2.0;
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    /**
     * Csak akkor térünk vissza true-val, ha a (x,y) pont  a global koordinátákba
     * átszámolva legfeljebb halfThick távol van a p1–p2 szakasztól.
     */
    @Override
    public boolean contains(int x, int y) {
        // x,y itt a gomb lokális koordinátái → globálisba:
        int gx = getX() + x;
        int gy = getY() + y;
        // pontos távolság a szakasztól:
        double dist = ptSegDist(p1.x, p1.y, p2.x, p2.y, gx, gy);
        return dist <= halfThick;
    }

    // Java belső helperszakasz‐távolság számoló (copypaste a java.awt.geom.Line2D-ból)
    private static double ptSegDist(double x1, double y1,
                                    double x2, double y2,
                                    double px, double py) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        if ((dx == 0) && (dy == 0)) {
            // a szakasz egy pont
            dx = px - x1;
            dy = py - y1;
            return Math.hypot(dx, dy);
        }
        // vetítés
        double t = ((px - x1) * dx + (py - y1) * dy) / (dx*dx + dy*dy);
        if (t < 0) {
            dx = px - x1; dy = py - y1;
        } else if (t > 1) {
            dx = px - x2; dy = py - y2;
        } else {
            double projx = x1 + t*dx;
            double projy = y1 + t*dy;
            dx = px - projx; dy = py - projy;
        }
        return Math.hypot(dx, dy);
    }
}
