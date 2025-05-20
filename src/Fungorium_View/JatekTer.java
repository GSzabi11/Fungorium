package Fungorium_View;

import Fugorium_Model.*;
import Fungorium_Controller.GameEngine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.*;

public class JatekTer extends JPanel implements PropertyChangeListener {

    public final Vilag vilag;
    private final RajzoloTar rajzoloTar;
    private Image backgroundImage;
    private String korTulajdonos = "";
    private GameEngine gameEngine;
    public JButton gomb = new JButton();

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

    public void setKijeloltObjektum(Object o) {
        this.kijeloltObjektum = o;
    }

    public void setGameEngine (GameEngine ge){
        this.gameEngine = ge;
    }

    @Override
    protected void paintComponent(Graphics g) {
        firePropertyChange("selectedObject", null, this.kijeloltObjektum);

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

//        // Tektonokat
//        for (Tekton t : vilag.getMezok()) {
//            rajzoloTar.rajzol(g2, t);
//        }

//        // Spórák
//        for (Tekton t : vilag.getMezok()) {
//            for (Spora s : vilag.getSporak(t)) {
//                rajzoloTar.rajzol(g2, s);
//            }
//        }

//        // Gombatestek
//        for (Gomba k : vilag.getGombak()) {
//            rajzoloTar.rajzol(g2, k);
//        }
//        // Gombatestek
//        for (Gomba k : vilag.getGombak()) {
//            objektumGombok.get(k).repaint();
//        }
//        // Rovarok
//        for (Rovar r : vilag.getRovarok()) {
//            rajzoloTar.rajzol(g2, r);
//        }

        // Visszaállítjuk az eredeti koordinátarendszert
        g2.translate(0, -sávMagasság);
    }

    private void hozzaadGombaGombkent(Gomba gomba) {

        // 1) Dinamikusan betöltjük a Gomba sprite-ot
        String spritePath;
        switch (gomba.getFajta()) {
            case KEK:   spritePath = "/gomba_kek.png";   break;
            case SARGA: spritePath = "/gomba_sarga.png"; break;
            case PIROS: spritePath = "/gomba_piros.png"; break;
            default:    spritePath = "/gomba_zold.png";  break;
        }
        BufferedImage gImg;
        try {
            gImg = ImageIO.read(getClass().getResourceAsStream(spritePath));
        } catch (Exception e) {
            // fallback
            gImg = (BufferedImage)new GombaView(0,0,0,0).getImage();
        }
        int gw = gImg.getWidth(), gh = gImg.getHeight();

        // 2) A GombaView-ben használt scale
        double gScale = 2.25; // * (Math.log(gomba.getSzint()) + 1);



        // 3) Betöltjük a Tekton sprite-ot és scale-eljük ugyanúgy, mint TektonView
        //EZ MIÉRT KELL IDE?
        BufferedImage tImg;
        try {
            tImg = ImageIO.read(getClass().getResourceAsStream("/tekton.png"));
        } catch (Exception e) {
            tImg = new BufferedImage(32,32,BufferedImage.TYPE_INT_ARGB);
        }
        double tScale = 3.0;  // ez van a TektonView-ban
        int tw = (int)(tImg.getWidth()  * tScale);
        int th = (int)(tImg.getHeight() * tScale);

        // 4) A Tekton modell X/Y a bal-felső sarka. Ebből a képernyőn a középpont:
        Tekton parent = gomba.getTekton();
        // (ha JatekTer.paintComponent-ben van eltolás, pl. g2.translate(-offsetX,0),
        // azt nem itt kell figyelembe venni, mert a Swing-gomb abszolút pozícióban van)
        int centerX = parent.getX() + tw/2;
        int centerY = parent.getY() + th/2;


        // 5) Kiszámoljuk a JButton végső méretét és pozícióját:
        int btnW = (int)Math.round(gw * gScale);
        int btnH = (int)Math.round(gh * gScale);
        int btnX = centerX - btnW/2 - 2;
        int btnY = centerY - btnH/2 + 17;

        //
        //
        //gomba.setX(centerX - btnW/2 - 2);
        //gomba.setY(centerY - btnH/2 + 17);
        //
        //

        // 6) Gomb létrehozása, ikon és bounds beállítása
        JButton gomb = new JButton(new ImageIcon(gImg.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        gomb.setBorderPainted(false);
        gomb.setContentAreaFilled(false);
        gomb.setBounds(btnX, btnY, btnW, btnH);

        // 7) ActionListener, rögzítés a térképre
        gomb.addActionListener(e -> {
            this.kijeloltObjektum = gomba;
            System.out.println("Gomba kijelölve: ++++++++++" + gomba);
            gameEngine.setKivalasztottGomba(gomba);
        });
        add(gomb);
        setComponentZOrder(gomb, 0);
        revalidate();
        repaint();

        // 8) Ha tárolod a gombokat
        objektumGombok.put(gomba, gomb);
    }

    public void hozzaadTektonGombkent(Tekton tekton) {

        // Load the Tekton image to get its size
        BufferedImage tekImg;
        try {
            tekImg = ImageIO.read(getClass().getResourceAsStream("/tekton.png"));
        } catch (IOException | NullPointerException e) {
            tekImg = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB); // fallback
        }
        int w = tekImg.getWidth();
        int h = tekImg.getHeight();

        double scale = 3.0; // same as in TektonView

        int btnW = (int) (w * scale);
        int btnH = (int) (h * scale);

        int btnX = tekton.getX();
        int btnY = tekton.getY() + 39;

        //
        //
        //tekton.setY(tekton.getY() + 39);
        //
        //

        JButton gomb = new JButton(new ImageIcon(tekImg.getScaledInstance(btnW, btnH, Image.SCALE_SMOOTH)));
        gomb.setBorderPainted(false);
        gomb.setContentAreaFilled(false);
        gomb.setBounds(btnX, btnY, btnW, btnH);

        gomb.addActionListener(e -> {
            //Object old = this.kijeloltObjektum;
            this.kijeloltObjektum = tekton;
            gameEngine.setKivalasztottCelTekton(tekton);
            System.out.println("Tekton kijelölve: +++++++++" + tekton.getId());
        });

        objektumGombok.put(tekton, gomb);
        add(gomb);
        setComponentZOrder(gomb, 0);
        revalidate();
        repaint();
    }

    public void hozzaadRovarGombkent(Rovar rovar){

        // If a button already exists for this rovar, remove it first
        JButton oldButton = objektumGombok.get(rovar);
        if (oldButton != null) {
            remove(oldButton);
            objektumGombok.remove(rovar);
        }

        // Determine sprite path based on Rovarfaj
        String spritePath;
        switch (rovar.getFajta()) {
            case BARNA:
                spritePath = "/rovar_barna.png";
                break;
            case CIAN:
                spritePath = "/rovar_zold.png";
                break;
            case LILA:
                spritePath = "/rovar_lila.png";
                break;
            default:
                spritePath = "/rovar_narancs.png";
                break;
        }

        BufferedImage img;
        try {
            img = ImageIO.read(getClass().getResourceAsStream(spritePath));
        } catch (IOException | NullPointerException e) {
            // fallback to a default image or empty image
            img = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        }

        int w = img.getWidth();
        int h = img.getHeight();

        // Use the rovar's x and y as button position (no scaling)
        int btnX = 0;
        int btnY = 0;

        if (rovar.getFajta() == Rovarfaj.LILA)
        {
            btnX = rovar.getX() + 10;
            btnY = rovar.getY() + 40;

            //
            //
            //rovar.setX(rovar.getX() + 10);
            //rovar.setY(rovar.getY() + 40);
            //
            //
        }
        if (rovar.getFajta() == Rovarfaj.BARNA)
        {
            btnX = rovar.getX() + 10;
            btnY = rovar.getY() + 56;

            //
            //
            //rovar.setX(rovar.getX() + 10);
            //rovar.setY(rovar.getY() + 56);
            //
            //
        }
        if (rovar.getFajta() == Rovarfaj.NARANCS)
        {
            btnX = rovar.getX() + 12;
            btnY = rovar.getY() + 72;

            //
            //
            //rovar.setX(rovar.getX() + 12);
            //rovar.setY(rovar.getY() + 72);
            //
            //
        }
        if (rovar.getFajta() == Rovarfaj.CIAN)
        {
            btnX = rovar.getX() + 18;
            btnY = rovar.getY() + 88;

            //
            //
            //rovar.setX(rovar.getX() + 18);
            //rovar.setY(rovar.getY() + 88);
            //
            //
        }

        // Create JButton with the image icon (original size)
        JButton gomb = new JButton(new ImageIcon(img));
        gomb.setBorderPainted(false);
        gomb.setContentAreaFilled(false);
        gomb.setBounds(btnX, btnY, w, h);

        // Add action listener to update selected object
        gomb.addActionListener(e -> {
            this.kijeloltObjektum = rovar;
            gameEngine.setKivalasztottRovar(rovar);
            System.out.println("Rovar kijelölve: +++++++++++++++++++" + rovar);
        });

        // Store button reference and add to panel
        objektumGombok.put(rovar, gomb);
        add(gomb);
        setComponentZOrder(gomb, 0);
        revalidate();
        repaint();
    }

    private void hozzaadSporaGombkent(Spora spora) {
        // Remove old button if exists
        JButton oldButton = objektumGombok.get(spora);
        if (oldButton != null) {
            remove(oldButton);
            objektumGombok.remove(spora);
        }

        // Load spora image (original size)
        String spritePath = "/spora.png"; // or choose dynamically if you want different sprites per spora type
        BufferedImage img;
        try {
            img = ImageIO.read(getClass().getResourceAsStream(spritePath));
        } catch (IOException | NullPointerException e) {
            img = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        }

        int w = img.getWidth();
        int h = img.getHeight();

        // Position the button near the Tekton the spora belongs to
        Tekton parent = spora.getTekton();
        int btnX = 0;
        int btnY = 0;
        if (parent.getSporak().size() == 1){
            btnX = parent.getX() + 80;
            btnY = parent.getY() + 42;
        }
        if (parent.getSporak().size() == 2){
            btnX = parent.getX() + 85;
            btnY = parent.getY() + 57;
        }
        if (parent.getSporak().size() == 3){
            btnX = parent.getX() + 90;
            btnY = parent.getY() + 72;
        }
        if (parent.getSporak().size() == 4){
            btnX = parent.getX() + 95;
            btnY = parent.getY() + 87;
        }
        if (parent.getSporak().size() == 5){
            btnX = parent.getX() + 82;
            btnY = parent.getY() + 87;
        }
        if (parent.getSporak().size() == 6){
            btnX = parent.getX() + 77;
            btnY = parent.getY() + 72;
        }
        if (parent.getSporak().size() == 7){
            btnX = parent.getX() + 72;
            btnY = parent.getY() + 57;
        }

        JButton gomb = new JButton(new ImageIcon(img));
        gomb.setBorderPainted(false);
        gomb.setContentAreaFilled(false);
        gomb.setBounds(btnX, btnY, w, h);

        gomb.addActionListener(e -> {
            this.kijeloltObjektum = spora;
            System.out.println("Spora kijelölve: +++++++++++" + spora);
        });

        objektumGombok.put(spora, gomb);
        add(gomb);
        setComponentZOrder(gomb, 0);
        revalidate();
        repaint();
    }

        @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        switch (evt.getPropertyName()) {
            case "tekton" -> {
                Tekton uj = (Tekton) evt.getNewValue();
                hozzaadTektonGombkent(uj);
            }
            case "gomba" -> {
                Gomba uj = (Gomba) evt.getNewValue();
                hozzaadGombaGombkent(uj);
            }

            case "rovar" -> {
                Rovar uj = (Rovar) evt.getNewValue();
                hozzaadRovarGombkent(uj);
            }
            case "spora" -> {
                Spora uj = (Spora) evt.getNewValue();
                Spora regi = (Spora) evt.getOldValue();

                if (uj != null) {
                    // spora added
                    hozzaadSporaGombkent(uj);
                }
                else if (regi != null) {
                    JButton oldButton = objektumGombok.get(regi);
                    if (oldButton != null) {
                        System.out.println("Removing spora button for spora: " + regi);
                        remove(oldButton);
                        objektumGombok.remove(regi);
                        revalidate();
                        repaint();
                    } else {
                        System.out.println("No button found for spora to remove: " + regi);
                    }
                }
            }
        }
        repaint();
    }
}
