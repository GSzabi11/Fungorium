package Fugorium_model;
import java.util.*;
/**
 * A Gombafonal osztály egy gomba növekedését és kapcsolatát biztosító fonalat reprezentál.
 */
public class Gombafonal {
    private Gomba kiindulasiGomba; // A fonalat létrehozó gomba
    private List<Tekton> kapcsolodasiPontok; // A fonal által érintett tektonok
    public int eletido = 0; // A fonal jelenlegi életideje
    private int max_eletido; // A fonal maximális élettartama
    private boolean haldoklik; // Jelzi, ha a fonal haldoklik
    private int spora_kuszob_gomba_novekedeshez; // A spóraküszöb egy új gomba növekedéséhez
    private double novekedesSebesseg;

    /**
     * Létrehoz egy új Gombafonal példányt.
     */
    public Gombafonal(Gomba gomba, Tekton celTekton) {
        System.out.println("Gombafonal constructor called");
        this.kiindulasiGomba = gomba;
        this.kapcsolodasiPontok = new ArrayList<>();
        this.eletido = 0;
        this.max_eletido = 10;
        this.haldoklik = false;
        this.spora_kuszob_gomba_novekedeshez = 3;

        // Kiinduló pont: a gomba helye
        this.kapcsolodasiPontok.add(gomba.getTekton());

        // Majd a cél tekton
        this.kapcsolodasiPontok.add(celTekton);

        gomba.getTekton().hozzaadFonal(this);
        celTekton.hozzaadFonal(this);

        System.out.println("Gombafonal letrehozva: kiindulasi gomba = " + gomba.getFajta()
            + ", utvonal: T" + gomba.getTekton().getId() + " -> T" + celTekton.getId());

    }

    public Gombafonal(Gomba gomba, List<Tekton> pontok) {
        System.out.println("Gombafonal constructor #2 called");
        this.kiindulasiGomba = gomba;
        this.kapcsolodasiPontok = new ArrayList<>(pontok);
        this.eletido = 0;
        this.max_eletido = 10;
        this.haldoklik = false;
        this.spora_kuszob_gomba_novekedeshez = 3;
    
        for (Tekton t : pontok) {
            t.hozzaadFonal(this);
        }
    
        System.out.println("Gombafonal letrehozva, utvonal: " + pontok.stream().map(p -> "T" + p.getId()).toList());
    }

    public List<Tekton> getKapcsolodasiPontok() {
        return kapcsolodasiPontok;
    }

    /**
     * Megszakítja a fonalat.
     */
    public void megszakad() {
        System.out.println("Gombafonal.megszakad() method called");

        for (int i = 0; i < kapcsolodasiPontok.size() - 1; i++) {
            Tekton egyik = kapcsolodasiPontok.get(i);
            Tekton masik = kapcsolodasiPontok.get(i + 1);

            if (!egyik.getGombafonalak().contains(this) && !masik.getGombafonalak().contains(this)) {
                // Ez a pont a szakadasi hely
                System.out.println("Fonal megszakadt a pontnal: T" + egyik.getId() + " <-> T" + masik.getId());
    
                // Szakadasi pont utani szakasz
                List<Tekton> haldokloPontok = new ArrayList<>(kapcsolodasiPontok.subList(i + 1, kapcsolodasiPontok.size()));
    
                // A gombához kozelebbi resz marad az eredeti fonal
                kapcsolodasiPontok = new ArrayList<>(kapcsolodasiPontok.subList(0, i + 1));
    
                // A szakadas utani resz uj fonal objektum
                Gombafonal haldokloFonal = new Gombafonal(this.kiindulasiGomba, haldokloPontok);
                haldokloFonal.haldoklik = true;
    
                // Ha egyik tekton sem eletben tarto, akkor csokkentjuk az eletidot
                boolean eletbenTartoVan = false;
                for (Tekton t : haldokloPontok) {
                    if (t.getFonalFelszivodas()) {
                        eletbenTartoVan = true;
                        break;
                    }
                }
    
                if (!eletbenTartoVan) {
                    haldokloFonal.eletido = haldokloFonal.max_eletido;
                    System.out.println("Haldoklo fonal letrehozva, el fog pusztulni " + haldokloFonal.max_eletido + " kor mulva.");
                } else {
                    haldokloFonal.haldoklik = false;
                    System.out.println("Fonal nem haldoklik, mert van eletben tarto tekton.");
                }
    
                // Fontos: hozza kell adni a o fonalat a pontjaihoz
                for (Tekton t : haldokloPontok) {
                    t.hozzaadFonal(haldokloFonal);
                }
    
                return;
            }
        }
    
        System.out.println("Nem talaltunk vagasi pontot, kapcsolodasiPontok valtozatlan."); //nem kellene elofordulnia
    }

    /**
     * Növekedést végez a megadott tekton irányába.
     *
     * @param tekton A céltekton, amelybe a fonal nő
     */
    public void novekszik(Tekton celTekton) {
        System.out.println("Gombafonal.novekszik()");
        Tekton utolso = kapcsolodasiPontok.get(kapcsolodasiPontok.size() - 1);
        if (!utolso.getSzomszedok().contains(celTekton)) {
            System.out.println("HIBA: T" + celTekton.getId() + " nem szomszedja a fonal utolso pontjanak (T" + utolso.getId() + ")");
            return;
        }

        if (kapcsolodasiPontok.contains(celTekton)) {
            System.out.println("Fonal mar tartalmazza T" + celTekton.getId() + "-t, nem novekszik tovabb.");
            return;
        }
    
            kapcsolodasiPontok.add(celTekton);
            celTekton.hozzaadFonal(this);
            System.out.println("Gombafonal tovabb nott T" + celTekton.getId() + "-re.");
    }

    /**
     * Csökkenti a fonal életidejét.
     */
    public void csokkentiEletidot() {
        System.out.println("Gombafonal.csokkentiEletido()");
        if (haldoklik) {
            eletido--;
            System.out.println("Haldoklo fonal eletideje csokkent: " + eletido);
            if (eletido <= 0) {
                elpusztul();
            }
        }
    }

    /**
     * Felgyorsítja a fonal növekedését.
     */
    public void gyorsitNovekedest() {
        System.out.println("Gombafonal.gyorsitNovekedest()");
        novekedesSebesseg = novekedesSebesseg * 0.5;
    }

    /**
     * Megpróbál új gombát növeszteni a megadott tektonon.
     *
     * @param tekton A céltekton, ahol új gomba növekedhet
     */
    public void probalGombatNoveszteni(Tekton tekton) {
        System.out.println("Gombafonal.probalGombatNoveszteni()");
        int sporaCount = tekton.getSporakSzama();
        if (sporaCount >= spora_kuszob_gomba_novekedeshez) {
            Gomba ujGomba = new Gomba(Gombafaj.KEK, tekton);
            tekton.clearSporak();
            System.out.println(
                "Gombafonal: Új gombatest növesztése sikeres a T" 
                + tekton.getId() + " ponton."
            );
        } else {
            System.out.println(
                "Gombafonal: Nem elegendő spóra a gombatest növekedéséhez T" 
                + tekton.getId() + "."
            );
        }
    }

    /**
     * A fonal elpusztul.
     */
    public void elpusztul() {
        System.out.println("Gombafonal.elpusztul()");
        for (Tekton t : kapcsolodasiPontok) {
            t.removeFonal(this);
        }
    }
}
