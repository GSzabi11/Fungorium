package Fungorium_View;

import Fugorium_Model.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

import static Fugorium_Model.Gombafaj.*;
import static Fugorium_Model.Rovarfaj.BARNA;

public class JatekTer extends JPanel implements PropertyChangeListener {

    private final Vilag vilag;
    private final RajzoloTar rajzoloTar;
    private Image backgroundImage;
    private String korTulajdonos = "";

    private final Map<Object, JButton> objektumGombok = new HashMap<>();
    private Object kijeloltObjektum = null;

    public JatekTer(Vilag vilag, RajzoloTar rajzoloTar, KorView korView) {
        this.vilag = vilag;
        this.rajzoloTar = rajzoloTar;

        setLayout(null);
        setBackground(Color.BLACK);

        try {
            backgroundImage = new ImageIcon(getClass().getResource("/background.jpg")).getImage();
        } catch (Exception e) {
            backgroundImage = null;
        }

        vilag.addPropertyChangeListener(this);
        for (Tekton t : vilag.getMezok()) t.addPropertyChangeListener(this);
        for (Rovar r : vilag.getRovarok()) r.addPropertyChangeListener(this);
        for (Gomba g : vilag.getGombak()) g.addPropertyChangeListener(this);

        javax.swing.Timer repaintTimer = new javax.swing.Timer(1000 / 30, e -> repaint());
        repaintTimer.start();

//        // Hardcoded 10 Tekton
//        for (int i = 0; i < 10; i++) {
//            int x = 100 + (i % 5) * 120;
//            int y = 100 + (i / 5) * 120;
//            Tekton t = new Tekton(i, x, y);
//            vilag.addTekton(t);
//        }
//
//        // Hardcoded 2 Gomba (különböző fajták)
//        Tekton t0 = vilag.getMezok().get(0);
//        Tekton t1 = vilag.getMezok().get(1);
//        vilag.lerakGombat(KEK, t0, t0.getX(), t0.getY());
//        vilag.lerakGombat(PIROS, t1, t1.getX(), t1.getY());
    }

    public void setKorTulajdonos(String korTulajdonos) {
        this.korTulajdonos = korTulajdonos;
        repaint();
    }

    public String getKorTulajdonos() {
        return korTulajdonos;
    }

    public Object getKijeloltObjektum() {
        return kijeloltObjektum;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        if (backgroundImage != null)
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        int sávMagasság = 40;
        g2.setColor(new Color(50, 50, 50));
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

        // Először a Gombafonalakat
        for (Tekton t : vilag.getMezok()) {
            for (Gombafonal gf : vilag.getFonalak(t)) {
                rajzoloTar.rajzol(g2, gf);
            }
        }
        // Tektonokat
        for (Tekton t : vilag.getMezok()) {
            rajzoloTar.rajzol(g2, t);
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

    private void hozzaadGombaGombkent(Gomba gomba) {
        GombaView view = new GombaView(gomba.getX(), gomba.getY(), 50, 50, gomba.getFajta());
        JButton gomb = new JButton();
        gomb.setBounds(gomba.getX(), gomba.getY(), view.getWidth(), view.getHeight());
        gomb.setIcon(new ImageIcon(view.getImage()));
        gomb.setBorderPainted(false);
        gomb.setContentAreaFilled(false);
        gomb.addActionListener(e -> {
            kijeloltObjektum = gomba;
            System.out.println("Gomba kijelölve+++++++++++++++++++++++++++++++++++++++++++++++++++: " + gomba);
        });
        objektumGombok.put(gomba, gomb);
        add(gomb);
        setComponentZOrder(gomb, 0);
        revalidate();
        repaint();
    }

    private void hozzaadTektonGombkent(Tekton tekton) {
        TektonView view = (TektonView) rajzoloTar.getRajzolo(tekton);
        JButton gomb = new JButton();
        gomb.setBounds(tekton.getX(), tekton.getY(), view.getWidth() + 10, view.getHeight() + 10);
        gomb.setIcon(new ImageIcon(view.getImage()));
        gomb.setBorderPainted(false);
        gomb.setContentAreaFilled(false);
        gomb.addActionListener(e -> {
            kijeloltObjektum = tekton;
            System.out.println("Tekton kijelölve-----------------------------------------------: " + tekton);
        });
        objektumGombok.put(tekton, gomb);
        add(gomb);
        setComponentZOrder(gomb, getComponentCount() - 1);
        revalidate();
        repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
                switch (evt.getPropertyName()) {
            case "tekton" -> {
                Tekton uj = (Tekton) evt.getNewValue();
                hozzaadTektonGombkent(uj);
            }
            case "gomba" -> {
                Gomba uj = (Gomba) evt.getNewValue();
                hozzaadGombaGombkent(uj);
            }
        }
        repaint();
    }
}
