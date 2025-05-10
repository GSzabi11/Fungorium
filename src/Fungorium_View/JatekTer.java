package Fungorium_View;

import Fugorium_Model.*;
import Fungorium_Controller.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

public class JatekTer extends JPanel implements PropertyChangeListener {

    private final Vilag vilag;
    private final RajzoloRegistry rajzoloTar;

    public JatekTer(Vilag vilag, RajzoloRegistry rajzoloTar) {
        this.vilag = vilag;
        this.rajzoloTar = rajzoloTar;

        // háttér szín beállítása (opcionális)
        setBackground(Color.WHITE);

        // feliratkozás eseményekre
        vilag.addPropertyChangeListener(this);

        // Tektonok figyelése egyenként (ha külön is változnak)
        for (Tekton t : vilag.getMezok()) {
            t.addPropertyChangeListener(this);
        }
        for (Rovar r : vilag.getRovarok()) {
            r.addPropertyChangeListener(this);
        }
        for (Gomba g : vilag.getGombak()) {
            g.addPropertyChangeListener(this);
        }

        // újrarajzolás időzítve is (ha pl. animációk vannak)
        Timer repaintTimer = new Timer(1000 / 30, e -> repaint()); // 30 FPS
        repaintTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Először a Tektonokat
        for (Tekton t : vilag.getMezok()) {
            rajzoloTar.rajzol(g2, t);
        }

        // Gombafonalakat (ezek gyakran keresztezik a mezőket)
        for (Tekton t : vilag.getMezok()) {
            for (Gombafonal gf : vilag.getFonalak(t)) {
                rajzoloTar.rajzol(g2, gf);
            }
        }

        // Spórák
        for (Tekton t : vilag.getMezok()) {
            for (Spora s : vilag.getSporak(t)) {
                rajzoloTar.rajzol(g2, s);
            }
        }

        // Gombatestek
        for (Gomba k : vilag.getGombak()) {
            rajzoloTar.rajzol(g2, g);
        }

        // Rovarok
        for (Rovar r : vilag.getRovarok()) {
            rajzoloTar.rajzol(g2, r);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Ha valami változott a modellben, kérünk újrarajzolást
        repaint();
    }

    void peldaHaznalat(){
        rajzoloTar.regisztral(Tekton.class, new TektonView());
        rajzoloTar.regisztral(Gombafonal.class, new GombafonalView());
        rajzoloTar.regisztral(Gomba.class, new GombaView());
        rajzoloTar.regisztral(Rovar.class, new RovarView());
        rajzoloTar.regisztral(Spora.class, new SporaView());

        rajzoloTar.rajzol(g2, tekton);
        rajzoloTar.rajzol(g2, rovar);

    }
}

