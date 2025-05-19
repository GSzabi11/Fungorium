package Fungorium_Controller;

import Fugorium_Model.*;
import java.util.List;

public class MozgasController {

    /**
     * Megpróbálja a rovar áthelyezését a megadott céltektorra,
     * ha azt gombafonal köti össze az aktuális mezővel.
     *
     * @param rovar a mozgatandó rovar
     * @param cel   a céltekton
     */
    public static void move(Rovar rovar, Tekton cel) {
        Tekton aktualis = rovar.getHelyzet();

        if (aktualis == null) {
            System.err.println("Rovar helyzete ismeretlen.");
            return;
        }

        // Ellenőrzés: van-e gombafonal az aktuális és cél mező között
        List<Gombafonal> fonalak = aktualis.getGombafonalak();

        boolean vanFonal = false;
        for (Gombafonal gf : fonalak) {
            List<Tekton> pontok = gf.kapcsolodasiPontok;
            if (pontok.contains(aktualis) && pontok.contains(cel)) {
                vanFonal = true;
                break;
            }
        }

        if (vanFonal) {
            rovar.mozog(cel);
            System.out.println("Rovar áthelyezve: " + aktualis.getId() + " → " + cel.getId());
        } else {
            System.out.println("Nincs gombafonal az áthaladáshoz: " +
                    aktualis.getId() + " ↛ " + cel.getId());
        }

    }

    public static boolean novezhetGomba(Tekton celTekton, Gomba gomba) {

        Tekton forras = gomba.getTekton();
        if (forras == null) {
            System.err.println("Gomba helyzete ismeretlen.");
            return false;
        }

        // 2) Végigmegyünk az összes fonalon, ami a forráson elindul
        for (Gombafonal gf : forras.getGombafonalak()) {
            // a Gombafonal ezen a két ponton köt össze mezőket
            List<Tekton> pontok = gf.kapcsolodasiPontok;
            if (pontok.contains(forras) && pontok.contains(celTekton)) {
                // ha a fonal rajta van mindkét mezőn, visszatérünk true-val
                return true;
            }
        }

        // ha egyik fonal sem kötötte össze a két mezőt
        return false;
    }
}

