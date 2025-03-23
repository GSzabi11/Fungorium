package Fugorium_model;

import java.util.*;

/**
 * A Tekton osztály egy tektonikai elem reprezentációja a játékban.
 * Egy tekton rendelkezhet azonosítóval, típusinformációval,
 * valamint szomszédos tektonokkal, spórákkal és gombafonalakkal.
 */
public class Tekton {
    private int id; // A tekton egyedi azonosítója
    private String tipus; // A tekton típusa
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
    public Tekton(int id, String tipus, Gomba gomba) {
        System.out.println("Tekton constructor called");
    }

    /**
     * A tekton kettétörését végrehajtó metódus.
     */
    public void kettetor() {
        System.out.println("Tekton.kettetor() called");
    }

    /**
     * Kiírja a tektonon található spórák számát.
     */
    public void getSporakSzama() {
        System.out.println("Tekton.getSporakSzama() called");
    }

    /**
     * Eltávolítja a tektonon található összes spórát.
     */
    public void clearSporak() {
        System.out.println("Tekton.clearSporak() called");
    }

    /**
     * Hozzáad egy új szomszédos tektont a listához.
     */
    public void hozzaadSzomszed() {
        System.out.println("Tektonhoz szomszédot adtunk hozza");
    }

    /**
     * Kiírja a tekton szomszédos tektonjait.
     */
    public void getSzomszedok() {
        System.out.println("Tekton.getSzomszedok() called");
    }
}
