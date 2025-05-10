package Fungorium_View;

import Fugorium_Model.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class Vilag {

    private final List<Tekton> mezok = new ArrayList<>();
    private final List<Rovar> rovarok = new ArrayList<>();
    private final List<Gomba> gombak = new ArrayList<>();
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
            List<Spora> ujSporak = g.sporaTermel();
            if (ujSporak != null && !ujSporak.isEmpty()) {
                Tekton t = g.getTekton();
                for (Spora s : ujSporak) {
                    t.addSpora(s);
                }
                fireChange("spora", null, ujSporak);
            }
        }

        // Fonalak növekednek és elhalnak ha kell
        for (Tekton t : mezok) {
            for (Gombafonal gf : t.getGombafonalak()) {
                //gf.scheduleDestruction(); // fonaltípus alapján időzített elhalás
                //gf.eatParalyzedRovarok(); // ha van ilyen rovar
            }
        }
    }

    // === KERESÉSEK ID ALAPJÁN (opcionális) ===
    public Tekton findTektonById(int id) {
        return mezok.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    public Rovar findRovarById(int id) {
        return rovarok.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
    }

    public Gomba findGombaById(int id) {
        return gombak.stream().filter(g -> g.getId() == id).findFirst().orElse(null);
    }
}
