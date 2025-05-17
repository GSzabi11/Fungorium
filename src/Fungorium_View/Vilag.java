package Fungorium_View;

import Fugorium_Model.*;
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
        // 2. Tektonok statikus elhelyezése
        mezok.add(new Tekton(0, 200, 54));
        mezok.add(new Tekton(1, 550, 65));


        // 1. Gombák statikus elhelyezése
        gombak.add(new Gomba(KEK,mezok.get(0) ,100, 200));
        gombak.add(new Gomba(KEK,mezok.get(1),150, 240));


        // 3. Rovarok statikus elhelyezése
        rovarok.add(new Rovar(BARNA,mezok.get(0),300, 400));
        rovarok.add(new Rovar(BARNA,mezok.get(1),500, 350));

        fonalak.add(new Gombafonal(gombak.getFirst(), mezok.get(0), 201, 155));

    }

    // Fonalak növekednek és elhalnak ha kell
//        for(Tekton t : mezok) {
//            for (Gombafonal gf : t.getGombafonalak()) {
//                //gf.scheduleDestruction(); // fonaltípus alapján időzített elhalás
//                //gf.eatParalyzedRovarok(); // ha van ilyen rovar
//            }
//        }
}
