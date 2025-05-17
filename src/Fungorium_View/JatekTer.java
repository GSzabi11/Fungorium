package Fungorium_View;

import Fugorium_Model.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

public class JatekTer extends JPanel implements PropertyChangeListener {

    private final Vilag vilag;
    private final RajzoloTar rajzoloTar;
    private Image backgroundImage;

    // Új mező: kié a kör
    private String korTulajdonos = "Rovarász"; // alapértelmezett

    public JatekTer(Vilag vilag, RajzoloTar rajzoloTar) {
        this.vilag = vilag;
        this.rajzoloTar = rajzoloTar;

        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/background.jpg")));
        } catch (Exception e) {
            System.err.println("Hiba a háttérkép betöltésekor: " + e.getMessage());
            backgroundImage = new ImageIcon("images/background.jpg").getImage();
        }

        vilag.addPropertyChangeListener(this);

        for (Tekton t : vilag.getMezok()) {
            t.addPropertyChangeListener(this);
        }
        for (Rovar r : vilag.getRovarok()) {
            r.addPropertyChangeListener(this);
        }
        for (Gomba g : vilag.getGombak()) {
            g.addPropertyChangeListener(this);
        }

        Timer repaintTimer = new Timer(1000 / 30, e -> repaint());
        repaintTimer.start();
    }

    /**
     * Beállítja, hogy kinek a köre van, és újrarajzoltatja a panelt.
     */
    public void setKorTulajdonos(String korTulajdonos) {
        this.korTulajdonos = korTulajdonos;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // Kirajzoljuk a felső sávot
        int sávMagasság = 40;
        g2.setColor(new Color(50, 50, 50)); // sötétszürke háttér
        g2.fillRect(0, 0, getWidth(), sávMagasság);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        String szoveg = "Köre van: " + korTulajdonos;
        FontMetrics fm = g2.getFontMetrics();
        int szovegSzelesseg = fm.stringWidth(szoveg);
        int x = (getWidth() - szovegSzelesseg) / 2;
        int y = (sávMagasság + fm.getAscent()) / 2 - 4;
        g2.drawString(szoveg, x, y);

        // A játék többi eleme alatta, kicsit lejjebb rajzolva, hogy ne takarja a sávot
        g2.translate(0, sávMagasság);

        // Először a Tektonokat
        for (Tekton t : vilag.getMezok()) {
            rajzoloTar.rajzol(g2, t);
        }

        // Gombafonalakat
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
            rajzoloTar.rajzol(g2, k);
        }

        // Rovarok
        for (Rovar r : vilag.getRovarok()) {
            rajzoloTar.rajzol(g2, r);
        }

        // Visszaállítjuk az eredeti koordinátarendszert
        g2.translate(0, -sávMagasság);


    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        repaint();
    }
}
