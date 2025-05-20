package Fungorium_View;

import Fugorium_Model.*;
import Fungorium_Controller.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

import static Fugorium_Model.Rovarfaj.*;

public class Vilag {

    private final List<Tekton> mezok = new ArrayList<>();
    private final List<Rovar> rovarok = new ArrayList<>();
    private final List<Gomba> gombak = new ArrayList<>();
    private final List<Gombafonal> fonalak = new ArrayList<>();
    private List<Player> jatekosok = new ArrayList<>();
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    JatekTer jatekTer;

    public Vilag(JatekTer jatekTer) {
        this.jatekTer = jatekTer;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    private void fireChange(String prop, Object oldVal, Object newVal) {
        pcs.firePropertyChange(prop, oldVal, newVal);
    }

    // === MEZŐK, ROVAROK, GOMBÁK HOZZÁADÁSA ===

    public void setJatekosok(List<Player> lista) {
        this.jatekosok = lista;
    }

    public void addTekton(Tekton t) {
        mezok.add(t);
        fireChange("tekton", null, t);
    }

    public void removeTekton(Tekton t) {
        mezok.remove(t);
        fireChange("tekton", t, null);
    }

    public void removeRovar(Rovar r) {
        rovarok.remove(r);
        fireChange("rovar", r, null);
    }

    public void addRovar(Rovar r) {
        rovarok.add(r);
        fireChange("rovar", null, r);
    }

    public void addGomba(Gomba g) {
        gombak.add(g);
        fireChange("gomba", null, g);
    }

    public void addSpora(Tekton t, Spora s) {
        System.out.println("addSpora hivva");
        t.getSporak().add(s);
        fireChange("spora", null, s);
    }

    public void removeSpora(Tekton t, List<Spora> s) {
        for (Spora sp : s)
        {
            fireChange("spora", sp, null);
        }
        t.getSporak().removeAll(s);
    }
    public void addGombafonal(Gombafonal gf) {
        fonalak.add(gf);
        fireChange("gombafonal", null, gf);
    }

    public List<Tekton> getMezok() {
        return Collections.unmodifiableList(mezok);
    }

    public List<Rovar> getRovarok() {
        return Collections.unmodifiableList(rovarok);
    }

    public List<Gomba> getGombak() {
        return Collections.unmodifiableList(gombak);
    }

    public List<Spora> getSporak(Tekton t) {
        return Collections.unmodifiableList(t.getSporak());
    }

    public List<Gombafonal> getFonalak(Tekton t) {
        return Collections.unmodifiableList(t.getGombafonalak());
    }

    public void leptet() {
        /*for (Rovar r : new ArrayList<>(rovarok)) {
            r.csokkentAllapotIdotartam();
        }*/
    }

    public void initEntities() {

        // 1) Tisztítás, ha újra hívjuk
        mezok.clear();
        rovarok.clear();
        gombak.clear();
        fonalak.clear();

        // 2) Játékos- és szerepszámok
        int totalPlayers = jatekosok.size();
        long rovaraszCount = jatekosok.stream()
                .filter(p -> p.getRole().equalsIgnoreCase("rovarasz"))
                .count();
        long gombaszCount = jatekosok.stream()
                .filter(p -> p.getRole().equalsIgnoreCase("gombasz"))
                .count();

        // 3) Tektonok létrehozása (3 tekton/játékos), körbeosztással
        int tektonCount = totalPlayers * 3;
        int centerX = 512, centerY = 384;       // például a panel közepe
        int radius = 250;                     // tetszőleges sugarú kör
        for (int i = 0; i < tektonCount; i++) {
            double angle = 2 * Math.PI * i / tektonCount;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            addTekton(new Tekton(i, x, y));
        }

//        for (Tekton t1 : mezok) {
//            for (Tekton t2 : mezok) {
//                if (t1 != t2) {
//                t1.hozzaadSzomszed(t2);
//                }
//            }
//        }

        // 4) Gombák elhelyezése: gombászok száma = gombák száma
        //    Véletlenszerűen szétosztjuk őket a tektonok között
        List<Gombafaj> tempGFaj = new ArrayList<>();
        tempGFaj.add(Gombafaj.KEK);
        tempGFaj.add(Gombafaj.ZOLD);
        tempGFaj.add(Gombafaj.PIROS);
        tempGFaj.add(Gombafaj.SARGA);
        Random rand = new Random();
        int j = Math.abs(rand.nextInt() % 4);
        List<Tekton> shuffled = new ArrayList<>(mezok);
        Collections.shuffle(shuffled);
        for (int i = 0; i < gombaszCount; i++) {
            Tekton t = shuffled.get(i % shuffled.size());
            j = j % 4;
            addGomba(new Gomba(tempGFaj.get(j), t, t.getX(), t.getY()));
            j += 1;
        }

        for (Gomba g : gombak) {
            g.sporaTermel();
        }

        // 5) Rovarok elhelyezése: rovarászok száma = rovarok száma
        List<Rovarfaj> tempRFaj = new ArrayList<>();
        tempRFaj.add(LILA);
        tempRFaj.add(BARNA);
        tempRFaj.add(NARANCS);
        tempRFaj.add(CIAN);

        Tekton t = shuffled.get((int) ((2 + gombaszCount) % shuffled.size()));
        for (int i = 0; i < rovaraszCount; i++) {
            //Tekton t = shuffled.get((int) ((i + gombaszCount) % shuffled.size()));
            j = j % 4;
            addRovar(new Rovar(tempRFaj.get(j), t, t.getX(), t.getY()));
            j += 1;
        }

        // 6) Gombafonalak – egyszerű gyűrű: minden tekton összekötése a következővel
        for (int i = 0; i < tektonCount; i++) {
            Tekton t1 = mezok.get(i);
            Tekton t2 = mezok.get((i + 1) % tektonCount);
            // válasszunk ki hozzá egy kiinduló gombát (pl. az i. gombát mod gombaszCount)
            Gomba source = gombak.get((int) (i % gombak.size()));
            // a fonal középpontja legyen a két pont fele
            int midX = (t1.getX() + t2.getX()) / 2;
            int midY = (t1.getY() + t2.getY()) / 2;
            Gombafonal temp = new Gombafonal(source, t2, midX, midY);
            //fonalak.add(temp);
            addGombafonal(temp);
        }
    }


}