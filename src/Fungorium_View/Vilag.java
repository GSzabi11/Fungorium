package Fungorium_View;

import Fugorium_Model.*;
import Fungorium_Controller.Menu;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

import static Fugorium_Model.Gombafaj.KEK;
import static Fugorium_Model.Rovarfaj.BARNA;

public class Vilag {

    private final List<Tekton> mezok = new ArrayList<>();
    private final List<Rovar> rovarok = new ArrayList<>();
    private final List<Gomba> gombak = new ArrayList<>();
    private final List<Gombafonal> fonalak = new ArrayList<>();
    private List<Menu.Player> jatekosok = new ArrayList<>();
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    // === OBSZERVER KEZELÉS ===
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

    public void setJatekosok(List<Menu.Player> lista) {
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

    public void addRovar(Rovar r) {
        rovarok.add(r);
        fireChange("rovar", null, r);
    }

    public void addGomba(Gomba g) {
        gombak.add(g);
        fireChange("gomba", null, g);
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

    // === LÉPTETÉS ===
    public void leptet() {
        // Rovarok mozognak
        for (Rovar r : new ArrayList<>(rovarok)) {
            r.csokkentAllapotIdotartam();  // pl. bénultság, gyorsítás lejár
            // Mozgatás lehet AI, véletlen vagy játékosvezérelt -> külön controller dönt róla
        }

        // Gombák termelnek
        for (Gomba g : new ArrayList<>(gombak)) {
            g.sporaTermel();
        }
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
                .filter(p -> p.role.equalsIgnoreCase("rovarász"))
                .count();
        long gombaszCount = jatekosok.stream()
                .filter(p -> p.role.equalsIgnoreCase("gombász"))
                .count();

        // 3) Tektonok létrehozása (3 tekton/játékos), körbeosztással
        int tektonCount = totalPlayers * 3;
        int centerX = 512, centerY = 384;       // például a panel közepe
        int radius  = 200;                     // tetszőleges sugarú kör
        for (int i = 0; i < tektonCount; i++) {
            double angle = 2 * Math.PI * i / tektonCount;
            int x = (int)(centerX + radius * Math.cos(angle));
            int y = (int)(centerY + radius * Math.sin(angle));
            mezok.add(new Tekton(i, x, y));
        }

        // 4) Gombák elhelyezése: gombászok száma = gombák száma
        //    Véletlenszerűen szétosztjuk őket a tektonok között
        List<Tekton> shuffled = new ArrayList<>(mezok);
        Collections.shuffle(shuffled);
        for (int i = 0; i < gombaszCount; i++) {
            Tekton t = shuffled.get(i % shuffled.size());
            gombak.add(new Gomba(KEK, t, t.getX(), t.getY()));
        }

        for (Gomba g : gombak) {
            g.sporaTermel();
        }

        // 5) Rovarok elhelyezése: rovarászok száma = rovarok száma
        for (int i = 0; i < rovaraszCount; i++) {
            Tekton t = shuffled.get((int) ((i + gombaszCount) % shuffled.size()));
            rovarok.add(new Rovar(BARNA, t, t.getX(), t.getY()));
        }

        // 6) Gombafonalak – egyszerű gyűrű: minden tekton összekötése a következővel
        for (int i = 0; i < tektonCount; i++) {
            Tekton t1 = mezok.get(i);
            Tekton t2 = mezok.get((i + 1) % tektonCount);
            // válasszunk ki hozzá egy kiinduló gombát (pl. az i. gombát mod gombaszCount)
            Gomba source = gombak.get((int)(i % gombak.size()));
            // a fonal középpontja legyen a két pont fele
            int midX = (t1.getX() + t2.getX()) / 2;
            int midY = (t1.getY() + t2.getY()) / 2;
            fonalak.add(new Gombafonal(source, t2, midX, midY));
        }

    }

    // Fonalak növekednek és elhalnak ha kell
//        for(Tekton t : mezok) {
//            for (Gombafonal gf : t.getGombafonalak()) {
//                //gf.scheduleDestruction(); // fonaltípus alapján időzített elhalás
//                //gf.eatParalyzedRovarok(); // ha van ilyen rovar
//            }
//        }
}
