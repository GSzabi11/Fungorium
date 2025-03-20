package Fugorium_model;

import java.util.*;

class Gomba {
    private Tekton tekton;
    private Gombafaj fajta;
    private int sporaSzamlalo;
    private int eletido;
    private int szint;
    private List<Spora> termeltSporak;

    public Gomba() {
        System.out.println("Gomba konstructor");
    }

    public void sporaz() {
        System.out.println("Gomba.sporaz()");
    }

    public void novesztFonal() {
        System.out.println("Gomba.novesztFonal()");
    }

    public void sporaTermel() {
        System.out.println("Gomba.sporaTermel()");
    }

    public void getSporaszam() {
        System.out.println("Gomba.getSporaszam()");
    }

    public void elpusztul() {
        System.out.println("Gomba.elpusztul()");
    }
}