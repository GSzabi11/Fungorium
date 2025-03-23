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
    public Gombafonal() {
        System.out.println("Gombafonal konstructor");
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
    public void novekszik(Tekton tekton) {
        System.out.println("Gombafonal.novekszik()");
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
