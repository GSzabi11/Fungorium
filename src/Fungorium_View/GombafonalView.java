package Fungorium_View;

import Fugorium_Model.Gomba;
import Fugorium_Model.Gombafaj;
import Fugorium_Model.Gombafonal;
import Fugorium_Model.Tekton;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class GombafonalView extends AbstractEntityView {
    public GombafonalView(int x, int y, int width, int height) {
        super("/gombafonal_zold.png", width, height, x,y);
    }

    @Override
    protected void drawEntity(Graphics2D g2, Object model) {
        if (!(model instanceof Gombafonal)) {
            drawEntity(g2, model);
            return;
        }

        Gombafonal gf = (Gombafonal) model;

        // 1) Lekérjük a forrás és cél Tekton-t:
        List<Tekton> pontok = gf.getKapcsolodasiPontok();
        if (pontok.size() < 2) return;
        Tekton from = pontok.get(0);
        Tekton to   = pontok.get(1);

        // 2) Két pont közti dx, dy, távolság és szög:
        int x1 = from.getX() + 38;
        int y1 = from.getY() + 24;
        int x2 = to.getX() + 38;
        int y2 = to.getY() + 24;
        double dx   = x2 - x1;
        double dy = y2 - y1;
        double dist = Math.hypot(dx, dy);
        double angle= Math.atan2(dy, dx);

        // 3) Fonal‐sprite fájlnév a kiinduló gomba fajtájából:
        Gomba source = gf.getKiindulasiGomba();
        Gombafaj fajta = source.getFajta();
        String spriteFile;
        switch (fajta) {
            case KEK:
                spriteFile = "/gombafonal_lila.png";   // kék gombából lila
                break;
            case SARGA:
                spriteFile = "/gombafonal_pink.png";   // sárga gombából pink
                break;
            case PIROS:
                spriteFile = "/gombafonal_piros.png";   // piros gombából piros
                break;
            default:
                spriteFile = "/gombafonal_zold.png";
        }

        // 5) Betöltjük a BufferedImage-et
        BufferedImage sprite;
        try {
            sprite = ImageIO.read(getClass().getResourceAsStream(spriteFile));
        } catch (IOException | NullPointerException ex) {
            // ha valamiért nem találjuk, fallback
            sprite = getImage();
        }

        int imgW = sprite.getWidth();
        int imgH = sprite.getHeight();


        // 6) transzformáció és csempézés:
        AffineTransform at = new AffineTransform();
        at.translate(x1, y1);
        at.rotate(angle);

        // --- kezdődik a vékonyító csempéző kód ---
        double segment    = imgW;
        int pieces        = (int)(dist / segment);
        double rem        = dist - pieces * segment;
        // ez lesz a magasság-scale: 1.0 = eredeti vastag, 0.5 = fele vastag stb.
        double thinFactor = 0.5;

        // 1) Teljes darabok
        for (int i = 0; i < pieces; i++) {
            AffineTransform t = new AffineTransform();
            t.translate(
                    x1 + Math.cos(angle) * i * segment,
                    y1 + Math.sin(angle) * i * segment
            );
                t.rotate(angle);
            // --------- VERTICAL SCALE ---------
            t.scale(1.0, thinFactor);
            // igazítás a sprite (fél magasságának * thinFactor) közepére
            t.translate(0, -imgH * thinFactor / 2.0);
            g2.drawImage(sprite, t, null);
        }

        // 2) Töredék darab a végén
        if (rem > 0) {
            AffineTransform t = new AffineTransform();
            t.translate(
                    x1 + Math.cos(angle) * pieces * segment,
                    y1 + Math.sin(angle) * pieces * segment
            );
            t.rotate(angle);
            // X-ben csempézésnyi (rem/segment), Y-ban vékonyítás:
            t.scale(rem / segment, thinFactor);
            t.translate(0, -imgH * thinFactor / 2.0);
            g2.drawImage(sprite, t, null);
        }
        // --- vége vékonyító csempézés ---
    }
}