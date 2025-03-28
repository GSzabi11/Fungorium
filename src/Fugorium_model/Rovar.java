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
    public Rovar(Rovarfaj rfajta, Tekton helyzet){
        this.rfajta = rfajta;
        this.helyzet = helyzet;
        this.tapanyag = 0;
        this.sebesseg = 1.0;
        this.allapot = new HashMap<>();
        //HashMap feltoltese
        for (RovarAllapot a : RovarAllapot.values()) {
            allapot.put(a, 0);
        }
        //System.out.println("Rovar konstructor");
    }

    public Tekton getHelyzet() {
        return helyzet;
    }
    
    public Rovarfaj getFajta() {
        return rfajta;
    }

    public void setAllapot(RovarAllapot allapot, int duration) {
        this.allapot.put(allapot, duration);
        System.out.println("Allapot beállítva: " + allapot + " (" + duration + " korre)");
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
    public void mozog(Tekton celtekton){
        if (allapot.get(RovarAllapot.BENITO) > 0) {
            System.out.println("A rovar benult, nem tud mozogni.");
            return;
        }
    
        if (allapot.get(RovarAllapot.GYORSITO) > 0) {
            this.sebesseg = 2.0;
        } else if (allapot.get(RovarAllapot.LASSITO) > 0) {
            this.sebesseg = 0.5;
        } else {
            this.sebesseg = 1.0;
        }
    
        this.helyzet = celtekton;
        System.out.println("Rovar mozog: uj hely: T" + celtekton.getId() + ", sebesseg: " + sebesseg);
        System.out.println("Rovar.mozog()");
    }

    /** Elfogyasztja a paraméterként kapott spórát
     * @param spora fogyasztandó spóra
     */
    public void fogyaszt(Spora spora){
        int tapanyagHozzaadas = spora.getTapanyagtartalom();
        this.tapanyag += tapanyagHozzaadas;
        System.out.println("Rovar elfogyasztotta a sporat! +" + tapanyagHozzaadas + " tapanyag. Osszesen: " + tapanyag);
        System.out.println("Rovar.fogyaszt()");

        HatastAlkalmazVisitor visitor = new HatastAlkalmazVisitor(this);
        spora.accept(visitor);
    }

    public int getTapanyag() {
        return tapanyag;
    }

    /** Elvágja a paraméterként kapott fonalat
     * @param gombafonal vágnivaló gombafonal
     */
    public void fonalatVag(Gombafonal gombafonal){
        if (allapot.get(RovarAllapot.VAGASTGATLO) > 0) {
            System.out.println("A rovar nem tud vagni (vagasgatlo hatas alatt van).");
            return;
        }
    
        // különben...
        System.out.println("Rovar elvagta a fonalat.");
        gombafonal.megszakad();
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
