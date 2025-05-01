package Fugorium_Model;

import java.util.*;

/**
 * A Tekton osztály egy tektonikai elem reprezentációja a játékban.
 * Egy tekton rendelkezhet azonosítóval, típusinformációval,
 * valamint szomszédos tektonokkal, spórákkal és gombafonalakkal.
 */
public class Tekton {
    private int id; // A tekton egyedi azonosítója
    //private String tipus; // A tekton típusa
    private Gomba gomba; // A tektonhoz tartozó gomba objektum
    private List<Tekton> szomszedok; // A tekton szomszédos tektonjai
    private List<Spora> sporak; // A tektonon található spórák listája
    private List<Gombafonal> gombafonalak; // A tektonon lévő gombafonalak listája
    private boolean fonalfelszivodas; // Igaz, ha a fonal felszívódik a tektonon
    private boolean keresztezodhet; // Igaz, ha a tektonon kereszteződhetnek a fonalak
    private boolean nohetGomba; // Igaz, ha a tektonon nőhet gomba

    /**
     * Létrehoz egy új Tekton példányt a megadott paraméterekkel.
     *
     * @param id A tekton egyedi azonosítója
     * @param tipus A tekton típusa
     * @param gomba A tektonhoz tartozó gomba objektum
     */
    public Tekton(int id) {
        this.id = id;

        this.szomszedok = new ArrayList<>();
        this.sporak = new ArrayList<>();
        this.gombafonalak = new ArrayList<>();

        this.fonalfelszivodas = false;
        this.keresztezodhet = false;
        this.nohetGomba = false;

        this.gomba = null;
        System.out.println("Tekton constructor called");
    }

    public int getId() {
        return id;
    }

    public List<Spora> getSporak() {
        return sporak;
    }

    /*
    *setter, hogy a gomba tekton attributuma es a tekton gomba attributuma szinkronban legyen
    * @param Gomba A tektonon levo gomba referenciaja
    */    
    public void setGomba(Gomba gomba) {
        this.gomba = gomba;
    }

    /**
     * A tekton kettétörését végrehajtó metódus.
     */
    public void kettetor(Tekton ujTekton) {
        hozzaadSzomszed(ujTekton);
        ujTekton.hozzaadSzomszed(this);
        System.out.println("Tekton.kettetor called");
    }

    /**
     * Hozzáad egy új szomszédos tektont a listához.
     */
    public void hozzaadSzomszed(Tekton szomszed) {
        if (!szomszedok.contains(szomszed)) {
            szomszedok.add(szomszed);
            System.out.println("Tekton T" + id + " szomszedaihoz hozzaadjuk: T" + szomszed.getId());
        }
    }

    public void hozzaadFonal(Gombafonal fonal) {
        if (!gombafonalak.contains(fonal)) {
            gombafonalak.add(fonal);
        }
    }

    public void removeFonal(Gombafonal fonal) {
        gombafonalak.remove(fonal);
        System.out.println("Tekton T" + id + " eltavolitotta a fonalat.");
    }

    public void removeGomba(){
        this.gomba=null;
    }
    
    public List<Gombafonal> getGombafonalak() {
        return gombafonalak;
    }

    /**
     * Kiírja a tektonon található spórák számát.
     */
    public int getSporakSzama() {
        System.out.println("Tekton.getSporakSzama() called");
        return sporak.size();
    }

    /**
     * Eltávolítja a tektonon található összes spórát.
     */
    public void clearSporak() {
        System.out.println("Tekton.clearSporak() called");
    }

    /**
     * Kiírja a tekton szomszédos tektonjait.
     */
    public List<Tekton> getSzomszedok() {
        return szomszedok;
    }
}
