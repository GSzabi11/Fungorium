package Fugorium_model;

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

    /**
     * Létrehoz egy új Gomba példányt.
     */
    public Gomba(Gombafaj fajta, Tekton tekton) {
        this.fajta = fajta;
        this.tekton = tekton;

        this.eletido = 5;           // alapból 5 kor
        this.sporaSzamlalo = 0;     // meg nem termelt egy sporat sem
        this.szint = 1;             // alap szint

        this.termeltSporak = new ArrayList<>();

        System.out.println("Gomba constructor called");
    }

    public Tekton getTekton() {
        return tekton;
    }

    public Gombafaj getFajta() {
        return fajta;
    }

    /**
     * A gomba spórákat szór szét a környezetébe.
     */
    public void sporaz() {
        System.out.println("Gomba.sporaz()");
    }

    /**
     * A gomba egy uj gombafonalat noveszt.
     */
    public Gombafonal novesztUjFonal(Tekton celTekton) {
        if (!tekton.getSzomszedok().contains(celTekton)) {
            System.out.println("HIBA: T" + celTekton.getId() + " nem szomszedja a gomba tektonjanak (T" + tekton.getId() + ")");
            return null;
        }

        Gombafonal fonal = new Gombafonal(this, celTekton);
        System.out.println("Gomba uj gombafonalat noveszt T" + celTekton.getId() + "-re.");
        return fonal;
    }

        /**
     * A gomba egy mar meglevo gombafonalat noveszt tovabb.
     */
    public void novesztMeglevoFonalt(Gombafonal fonal, Tekton celTekton) {
        fonal.novekszik(celTekton);
        System.out.println("Gomba meglevo fonalat noveszt T" + celTekton.getId() + "-re.");
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
        System.out.println("Gomba.elpusztul()");
    }
}