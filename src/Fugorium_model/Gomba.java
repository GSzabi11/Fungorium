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

        //System.out.println("Gomba constructor called");
    }

    /**
     * A gomba spórákat szór szét a környezetébe.
     */
    public void sporaz() {
        System.out.println("Gomba.sporaz()");
    }

    /**
     * A gomba egy új gombafonalat növeszt.
     */
    public void novesztFonal() {
        System.out.println("Gomba.novesztFonal()");
    }

    /**
     * A gomba új spórákat termel.
     */
    public void sporaTermel() {
        System.out.println("Gomba.sporaTermel()");
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