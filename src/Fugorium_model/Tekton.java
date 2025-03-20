package Fugorium_model;
import java.util.*;


class Tekton {
    private int id;
    private String tipus;
    private Gomba gomba;
    private List<Tekton> szomszedok;
    private List<Spora> sporak;
    private List<Gombafonal> gombafonalak;
    private boolean fonalfelszivodas;
    private boolean keresztezodhet;
    private boolean nohetGomba;

    public Tekton(int id, String tipus, Gomba gomba) {
        System.out.println("Tekton constructor called");
    }

    public void kettetor() {
        System.out.println("Tekton.kettetor() called");
    }

    public void getSporakSzama() {
        System.out.println("Tekton.getSporakSzama() called");
    }

    public void clearSporak() {
        System.out.println("Tekton.clearSporak() called");
    }

    public void hozzaadSzomszed() {
        System.out.println("Tektonhoz szomszédot adtunk hozza");
    }

    public void getSzomszedok() {
        System.out.println("Tekton.getSzomszedok() called");
    }
}