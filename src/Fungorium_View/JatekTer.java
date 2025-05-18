package Fungorium_View;

import Fugorium_Model.*;
import Fungorium_Controller.GameEngine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.*;

public class JatekTer extends JPanel implements PropertyChangeListener {

    private final Vilag vilag;
    private final RajzoloTar rajzoloTar;
    private Image backgroundImage;
    private String korTulajdonos = "";
    private GameEngine gameEngine;
    public JButton gomb = new JButton();

    private final Map<Object, JButton> objektumGombok = new HashMap<>();
    private Object kijeloltObjektum = null;
    private Graphics2D g2;

    public JatekTer(Vilag vilag, RajzoloTar rajzoloTar, KorView korView, GameEngine gameEngine) {
        this.vilag = vilag;
        this.rajzoloTar = rajzoloTar;
        this.gameEngine = gameEngine;

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
        g2 = (Graphics2D) g;

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
//        GombaView view = new GombaView(gomba.getX(), gomba.getY(), 50, 50);
//        view.drawEntity(g2, gomba);
//        JButton gomb = new JButton();
//        gomb.setBounds(gomba.getX(), gomba.getY(), view.getWidth(), view.getHeight());
//        gomb.setIcon(new ImageIcon(view.getImage()));
//        gomb.setBorderPainted(false);
//        gomb.setContentAreaFilled(false);
//        gomb.addActionListener(e -> {
//            kijeloltObjektum = gomba;
//            System.out.println("Gomba kijelölve+++++++++++++++++++++++++++++++++++++++++++++++++++: " + gomba);
//        });
//        objektumGombok.put(gomba, gomb);
//        add(gomb);
//        setComponentZOrder(gomb, 0);
//        revalidate();
//        repaint();

        // 1) Dinamikusan betöltöm a hozzá tartozó sprite-ot
        Gombafaj fajta = gomba.getFajta();
        String spriteFile;
        switch (fajta) {
            case KEK:
                spriteFile = "/gomba_kek.png";
                break;
            case SARGA:
                spriteFile = "/gomba_sarga.png";
                break;
            case PIROS:
                spriteFile = "/gomba_piros.png";
                break;
            default:
                spriteFile = "/gomba_zold.png";
        }
        // 2) Beolvassuk a BufferedImage-et
        BufferedImage gImg;
        try {
            gImg = ImageIO.read(getClass().getResourceAsStream(spriteFile));
        } catch (Exception e) {
            // ne omoljunk be, vegyük a view‐fallbacket
            gImg = (BufferedImage)new GombaView(0,0,0,0).getImage();
        }
        int gw = gImg.getWidth(), gh = gImg.getHeight();

        // 2) Pontosan ugyanaz a scale és offsetX, amit a GombaView-ben használsz
        double gScale  = 2.25;
        int    offsetX = 80;

        // 3) A tőle jobbra levő GombaView az alábbiak szerint számolná a középpontot:
        //    cx = gomba.getX() + tektonCenterOffsetX
        //    cy = gomba.getY() + tektonCenterOffsetY
        //
        //  Mi most viszont vegyük a *tekton* közepét:
        Tekton parent = gomba.getTekton();
        //   (feltételezzük, hogy a tekton sprite eredeti mérete pl. 32×32, scale=1.5)
        BufferedImage tImg;
        try {
            tImg = ImageIO.read(getClass().getResourceAsStream("/Images/tekton.png"));
        } catch (Exception e) {
            tImg = new BufferedImage(32,32,BufferedImage.TYPE_INT_ARGB);
        }
        double tScale = 1.5;
        int tw = (int)(tImg.getWidth()  * tScale);
        int th = (int)(tImg.getHeight() * tScale);

        //  Tekton bal‐felső koordináta a paneleken:
        int tX = parent.getX();
        int tY = parent.getY();
        //  Ez a tekton *képernyőn* megjelenő középpontja:
        int centerX = tX + tw/2  - offsetX;
        int centerY = tY + th/2;

        // 4) A gomb tényleges mérete és bal‐felső pozíciója:
        int bW = (int)Math.round(gw * gScale);
        int bH = (int)Math.round(gh * gScale);
        int bX = centerX - bW/2;
        int bY = centerY - bH/2;

        // 5) Gomb létrehozása és ikonozása
        JButton btn = new JButton(new ImageIcon(gImg));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBounds(bX, bY, bW, bH);

        // 4) Végül a panelra tesszük
        add(btn);
        repaint();
    }

    public void hozzaadTektonGombkent(Tekton tekton) {
        TektonView view = (TektonView) rajzoloTar.getRajzolo(tekton);

        gomb.setBounds(tekton.getX(), tekton.getY(), view.getWidth() + 10, view.getHeight() + 10);
        gomb.setIcon(new ImageIcon(view.getImage()));
        gomb.setBorderPainted(false);
        gomb.setContentAreaFilled(false);
        gomb.addActionListener(e -> {
            kijeloltObjektum = tekton;
            gameEngine.setKivalasztottCelTekton(tekton);
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
