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

    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        pcs.firePropertyChange(propertyName, oldValue, newValue);
    }

    private void fireChange(String prop, Object oldVal, Object newVal) {
        pcs.firePropertyChange(prop, oldVal, newVal);
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
        for (Rovar r : new ArrayList<>(rovarok)) {
            r.csokkentAllapotIdotartam();
        }
        for (Gomba g : new ArrayList<>(gombak)) {
            g.sporaTermel();
        }
    }

    public void initEntities() {
        // 2. Tektonok statikus elhelyezése
        mezok.add(new Tekton(0, 200, 54));
        mezok.add(new Tekton(1, 550, 65));


        // 1. Gombák statikus elhelyezése
        gombak.add(new Gomba(KEK, mezok.get(0), 100, 200));
        gombak.add(new Gomba(KEK, mezok.get(1), 150, 240));


        // 3. Rovarok statikus elhelyezése
        rovarok.add(new Rovar(BARNA, mezok.get(0), 300, 400));
        rovarok.add(new Rovar(BARNA, mezok.get(1), 500, 350));

        fonalak.add(new Gombafonal(gombak.getFirst(), mezok.get(0), 201, 155));
        fonalak.add(new Gombafonal(gombak.get(1), mezok.get(1), 202, 155));
    }

    public void lerakGombat(Gombafaj fajta, Tekton cel, int x, int y) {
        Gomba g = new Gomba(fajta, cel, x, y);
        gombak.add(g);
        firePropertyChange("gomba", null, g);
    }


    public void addTekton(Tekton t) {
        mezok.add(t);
        firePropertyChange("tekton", null, t);
    }

}