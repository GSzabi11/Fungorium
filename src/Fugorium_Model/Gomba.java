package Fugorium_Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
/**
 * A Gomba osztály egy gomba objektumot reprezentál a játékban.
 * Egy gomba egy adott tektonon nőhet, spórákat termelhet, és életideje van.
 */
public class Gomba {
    private Tekton tekton; // A gomba által elfoglalt tekton
    private Gombafaj fajta; // A gomba fajtája
    private int sporaSzamlalo; // Az eddig termelt spórák száma
    private int eletido; // A gomba élettartama
    public int szint; // A gomba fejlettségi szintje
    private List<Spora> termeltSporak; // A gomba által termelt spórák listája
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    int x;
    int y;

    /**
     * Létrehoz egy új Gomba példányt.
     */
    public Gomba(Gombafaj fajta, Tekton tekton, int x, int y) {
        this.fajta = fajta;
        this.tekton = tekton;

        this.eletido = 5;           // alapból 5 kor
        this.sporaSzamlalo = 0;     // meg nem termelt egy sporat sem
        this.szint = 1;             // alap szint

        this.termeltSporak = new ArrayList<>();
        this.x = x;
        this.y = y;

        System.out.println("Gomba constructor called");
    }

    public Tekton getTekton() {
        return tekton;
    }

    public Gombafaj getFajta() {
        return fajta;
    }

    public int getTermeltSporakSzama() {
        return termeltSporak.size();
    }

    /**
     * A gomba spórákat szór szét a környezetébe.
     */
    public void sporaz() {
        System.out.println("Gomba.sporaz() called");
        if (termeltSporak.isEmpty()) {
            System.out.println("Nincs mit szorjon, a gombanak nincs termelt sporaja.");
            return;
        }

        Set<Tekton> celpontok = new LinkedHashSet<>(tekton.getSzomszedok());

        if(szint >=2)
        {
            for (Tekton sz : tekton.getSzomszedok()) {
                celpontok.addAll(sz.getSzomszedok());
            }
            celpontok.remove(tekton); //sajat magat ne sporazza
        }

        List<Tekton> celpontLista = new ArrayList<>(celpontok);
        int i = 0;
        for (Spora spora : termeltSporak) {
            Tekton cel = celpontLista.get(i % celpontLista.size());
            cel.getSporak().add(spora);
            System.out.println("Gomba sporaz: spora atkerult T" + cel.getId() + "-re.");
            i++;
        }

        for (Tekton cel : celpontLista) {
            for (Gombafonal fonal : cel.getGombafonalak()) {
                fonal.setGyorsitottNovekedes(true);
                System.out.println("Gombafonal gyorsitva a T" + cel.getId() + " tektonon.");
            }
        }

        termeltSporak.clear();
        System.out.println("Gomba.sporaz(): minden spora elszorva, lista uritve.");
    }

    /**
     * A gomba egy uj gombafonalat noveszt.
     */
    public Gombafonal novesztUjFonal(Tekton celTekton) {
        if (!tekton.getSzomszedok().contains(celTekton)) {
            System.out.println("HIBA: T" + celTekton.getId() + " nem szomszedja a gomba tektonjanak (T" + tekton.getId() + ")");
            return null;
        }

        Gombafonal fonal = new Gombafonal(this, celTekton, this.x,this.y);
        System.out.println("Gomba uj gombafonalat noveszt T" + celTekton.getId() + "-re.");
        return fonal;
    }

        /**
     * A gomba egy mar meglevo gombafonalat noveszt tovabb.
     */
    public void novesztMeglevoFonalt(Gombafonal fonal, Tekton celTekton) {
        if (celTekton == null) {
            System.out.println("Gomba: Hiba - érvénytelen céltekton.");
            return;
        }

        // ellenőrizzük, hogy a céltekton szomszédos-e az aktuálissal
        Collection<Tekton> szomszedok = this.tekton.getSzomszedok();
        if (!szomszedok.contains(celTekton)) {
            System.out.println("Gomba: Hiba - T" + celTekton.getId() + " nem szomszédos a gomba aktuális tektonjával.");
            return;
        }

        // ha minden rendben, növesztjük a meglévő fonalat
        fonal.novekszik(celTekton);
        System.out.println("Gomba: Meglévő fonal növesztése sikeres T" + celTekton.getId() + "-re.");
    }

    public void fejlodik() {
        this.szint ++;
    }

    /**
     * A gomba új spórákat termel.
     */
    public void sporaTermel() {
        System.out.println("Sporak szama spora termeles elott: " + sporaSzamlalo);
        SporaFactory factory = new SporaFactory();
        Spora ujSpora = factory.createRandomSpora();
        termeltSporak.add(ujSpora);
        sporaSzamlalo++;
        System.out.println("Gomba.sporaTermel() - sporak szama: " + sporaSzamlalo);
    }

    /**
     * Kiírja a gomba által termelt spórák számát.
     */
    public void getSporaszam() {
        System.out.println("Gomba.getSporaszam()");
    }

    /**
     * A gomba elpusztul, és eltűnik a tektonról.
     */
    public void elpusztul() {
        this.tekton.removeGomba();
        System.out.println("Gomba.elpusztul()");
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(String property, Object oldVal, Object newVal) {
        pcs.firePropertyChange(property, oldVal, newVal);
    }

    /**
     * A gomba élettartamának csökkentése.
     */
    public int getX() {
        return x;
    }

    /**
     * A gomba élettartamának csökkentése.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * A gomba élettartamának csökkentése.
     */
    public int getY() {
        return y;
    }

    /**
     * A gomba élettartamának csökkentése.
     */
    public void setY(int y) {
        this.y = y;
    }
}