package Fugorium_model;
import java.util.*;

public class Gombafonal {
    private Gomba kiindulasiGomba;
    private List<Tekton> kapcsolodasiPontok;
    public int eletido = 0;
    private int max_eletido;
    private boolean haldoklik;
    private int spora_kuszob_gomba_novekedeshez;

    public Gombafonal() {
        System.out.println("Gombafonal konstructor");
    }

    public void megszakad() {
        System.out.println("Gombafonal.megszakad()");
    }

    public void novekszik(Tekton tekton) {
        System.out.println("Gombafonal.novekszik()");
    }

    public void csokkentiEletidot() {
        System.out.println("Gombafonal.csokkentiEletido()");
    }

    public void gyorsitNovekedest() {
        System.out.println("Gombafonal.gyorsitNovekedest()");
    }

    public void probalGombatNoveszteni(Tekton tekton) {
        System.out.println("Gombafonal.probalGombatNoveszteni()");
    }

    public void elpusztul() {
        System.out.println("Gombafonal.elpusztul()");
    }

}
