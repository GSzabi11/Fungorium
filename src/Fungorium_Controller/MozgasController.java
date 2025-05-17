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
    public void move(Rovar rovar, Tekton cel) {
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
}

