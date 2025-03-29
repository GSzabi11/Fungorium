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

        System.out.println("Gombafonal letrehozva: kiindulasi gomba = " + gomba.getFajta()
            + ", utvonal: T" + gomba.getTekton().getId() + " -> T" + celTekton.getId());

    }

    /**
     * Megszakítja a fonalat.
     */
    public void megszakad() {
        System.out.println("Gombafonal.megszakad()");
    }

    /**
     * Növekedést végez a megadott tekton irányába.
     *
     * @param tekton A céltekton, amelybe a fonal nő
     */
    public void novekszik(Tekton celTekton) {
        System.out.println("Gombafonal.novekszik()");
        if (kapcsolodasiPontok.contains(celTekton)) {
            System.out.println("Fonal mar tartalmazza T" + celTekton.getId() + "-t, nem novekszik tovabb.");
            return;
        }
    
            kapcsolodasiPontok.add(celTekton);
            System.out.println("Gombafonal tovabb nott T" + celTekton.getId() + "-re.");
    }

    /**
     * Csökkenti a fonal életidejét.
     */
    public void csokkentiEletidot() {
        System.out.println("Gombafonal.csokkentiEletido()");
    }

    /**
     * Felgyorsítja a fonal növekedését.
     */
    public void gyorsitNovekedest() {
        System.out.println("Gombafonal.gyorsitNovekedest()");
    }

    /**
     * Megpróbál új gombát növeszteni a megadott tektonon.
     *
     * @param tekton A céltekton, ahol új gomba növekedhet
     */
    public void probalGombatNoveszteni(Tekton tekton) {
        System.out.println("Gombafonal.probalGombatNoveszteni()");
    }

    /**
     * A fonal elpusztul.
     */
    public void elpusztul() {
        System.out.println("Gombafonal.elpusztul()");
    }
}
