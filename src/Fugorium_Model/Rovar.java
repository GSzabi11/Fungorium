package Fugorium_Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.List;

public class Rovar {
    
    private Tekton helyzet;
    private Rovarfaj rfajta;
    private int tapanyag;
    private double sebesseg;
    private HashMap<RovarAllapot, Integer> allapot;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    int x;
    int y;

    /**
     * Konstruktor
     */
    public Rovar(Rovarfaj rfajta, Tekton helyzet, int x, int y) {
        this.rfajta = rfajta;
        this.helyzet = helyzet;
        this.tapanyag = 0;
        this.sebesseg = 1.0;
        this.allapot = new HashMap<>();
        this.x = x;
        this.y = y;
        //HashMap feltoltese
        for (RovarAllapot a : RovarAllapot.values()) {
            allapot.put(a, 0);
        }
        System.out.println("Rovar konstruktor");
    }

    public Tekton getHelyzet() {
        return helyzet;
    }
    
    public Rovarfaj getFajta() {
        return rfajta;
    }

    public void setAllapot(RovarAllapot allapot, int duration) {
        this.allapot.put(allapot, duration);
        System.out.println("Allapot beallitva: " + allapot + " (" + duration + " korre)");
    }

    public HashMap<RovarAllapot, Integer> getAllapotMap() {
        return this.allapot;
    }

    /** Fogadja a HatasVisitorok visit függvényét
     * @param visitor HatasVisitor függvényét fogadja
     */
    public void accept(RovarVisitor visitor){
        System.out.println("Rovar.accept()");
    }

    /** Rovart mozgat a paraméterként kapott tektonra
     * @param celtekton céltekton
     */
    public void mozog(Tekton celtekton){
        if (allapot.get(RovarAllapot.BENITO) > 0) {
            System.out.println("A rovar benult, nem tud mozogni.");
            return;
        }

        boolean vanFonal = false;

            for (Gombafonal fonal : helyzet.getGombafonalak()) {
                List<Tekton> pontok = fonal.getKapcsolodasiPontok();
                for (int i = 0; i < pontok.size() - 1; i++) {
                    if ((pontok.get(i) == helyzet && pontok.get(i + 1) == celtekton) ||
                        (pontok.get(i) == celtekton && pontok.get(i + 1) == helyzet)) {
                        vanFonal = true;
                        break;
                    }
                }
                if (vanFonal) break;
            }

            if (!vanFonal) {
                System.out.println("A rovar nem tud ide mozogni, nincs fonal.");
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

        //Spora eltavolitasa a tektonrol
        List<Spora> sporak = helyzet.getSporak();
        sporak.remove(spora);

        //Ha az elfogyasztott spora volt az utolso a tektonon, akkor kikapcsoljuk a gyorsitast a fonalon
        if (sporak.isEmpty()) {
            for (Gombafonal fonal : helyzet.getGombafonalak()) {
                fonal.setGyorsitottNovekedes(false);
            }
        }
    }

    public int getTapanyag() {
        System.out.println("A rovar tapanyagtartalma: " + tapanyag);
        return tapanyag;
    }

    /** Elvágja a paraméterként kapott fonalat
     * @param gombafonal vágnivaló gombafonal
     */
    public void fonalatVag(Gombafonal gombafonal){
        System.out.println("Rovar.fonalatVag() method called");
        if (allapot.get(RovarAllapot.VAGASTGATLO) > 0) {
            System.out.println("A rovar nem tud vagni (vagasgatlo hatas alatt van).");
            return;
        }

        Tekton jelenlegi = this.getHelyzet();
        List<Tekton> pontok = gombafonal.getKapcsolodasiPontok();
        for (int i = 0; i < pontok.size() - 1; i++) {
            Tekton egyik = pontok.get(i);
            Tekton masik = pontok.get(i + 1);
    
            // Ha a rovar az egyik ponton van, akkor az o egyik szomszedjat vagjuk
            if (jelenlegi == egyik || jelenlegi == masik) {
                egyik.removeFonal(gombafonal);
                masik.removeFonal(gombafonal);
                System.out.println("Rovar elvagta a fonalat a kovetkezo pontok kozott: T" + egyik.getId() + " <-> T" + masik.getId());
                gombafonal.megszakad();
                return;
            }
        }
    
        System.out.println("A rovar nem tud vagni, nincs fonal a jelenlegi helyzetenel.");
    }

    /** Visszaadja a paraméterként kapott rovarállapotot, hogy hatása alatt van-e
     * @param rovarallapot ellenőrzendő állapot
     */
    public Boolean vanAllapot(RovarAllapot rovarallapot){
        if(allapot.get(RovarAllapot.GYORSITO) > 0) {
            System.out.println("A rovar gyorsitott allapotvan van.");
            return true;
        }
        if(allapot.get(RovarAllapot.LASSITO) > 0) {
            System.out.println("A rovar gyorsitott allapotvan van.");
            return true;
        }
        if(allapot.get(RovarAllapot.BENITO) > 0) {
            System.out.println("A rovar gyorsitott allapotvan van.");
            return true;
        }
        if(allapot.get(RovarAllapot.VAGASTGATLO) > 0) {
            System.out.println("A rovar gyorsitott allapotvan van.");
            return true;
        }
        System.out.println("NINCS ALLAPOT");
        return false;




    }

    /** Csökkenti a paraméterként kapott állapot időtartamát
     */
    public void csokkentAllapotIdotartam(){
        int duration = allapot.get(RovarAllapot.GYORSITO);
        if(duration > 0) {
            duration -= 1;
        }
        int duration2 = allapot.get(RovarAllapot.LASSITO);
        if(duration2 > 0) {
            duration2 -= 1;
        }
        int duration3 = allapot.get(RovarAllapot.BENITO);
        if(duration3 > 0) {
            duration3 -= 1;
        }
        int duration4 = allapot.get(RovarAllapot.VAGASTGATLO);
        if(duration4 > 0) {
            duration4 -= 1;
        }
    }

    public void elpusztul() {
        System.out.println("Rovar elpusztult!");
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
     * @return the x coordinate of the Rovar
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y coordinate of the Rovar
     */
    public int getY() {
        return y;
    }

    /**
     * @param x the x coordinate to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @param y the y coordinate to set
     */
    public void setY(int y) {
        this.y = y;
    }

}