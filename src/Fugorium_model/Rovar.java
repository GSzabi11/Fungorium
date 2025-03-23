package Fugorium_model;

import java.util.HashMap;

public class Rovar {
    
    private Tekton helyzet;
    private Rovarfaj rfajta;
    private int tapanyag;
    private double sebesseg;
    private HashMap<RovarAllapot, Integer> allapot;


    /**
     * Konstruktor
     */
    public Rovar(){
        System.out.println("Rovar konstructor");
    }

    /** Fogadja a HatasVisitorok visit függvényét
     * @param visitor HatasVisitor függvényét fogadja
     */
    public void accept(RovarVisitor visitor){
        System.out.println("Rovar.accept()");
    }

    /** Rovart mozgat a paraméterként kapott tektonra
     * @param tekton céltekton
     */
    public void mozog(Tekton tekton){
        System.out.println("Rovar.mozog()");
    }

    /** Elfogyasztja a paraméterként kapott spórát
     * @param spora fogyasztandó spóra
     */
    public void fogyaszt(Spora spora){
        System.out.println("Rovar.fogyaszt()");
    }

    /** Elvágja a paraméterként kapott fonalat
     * @param gombafonal vágnivaló gombafonal
     */
    public void fonalatVag(Gombafonal gombafonal){
        System.out.println("Rovar.fonalatVag()");
    }

    /** Visszaadja a paraméterként kapott rovarállapotot, hogy hatása alatt van-e
     * @param rovarallapot ellenőrzendő állapot
     */
    public void vanAllapot(RovarAllapot rovarallapot){
        System.out.println("Rovar.vanAllapot()");
    }

    /** Csökkenti a paraméterként kapott állapot időtartamát
     * @param rovarallapot ezen állapot időtartamát csökkentjük
     */
    public void csokkentAllapotIdotartam(RovarAllapot rovarallapot){
        System.out.println("Rovar.csokkentAllapotIdotartam()");
    }
}
