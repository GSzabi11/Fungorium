package Fungorium_Controller;

import Fugorium_Model.*;

public class SporaController {

    /**
     * A megadott spóra hatását alkalmazza a rovarra.
     *
     * @param sp    a spóra, amit a rovar elfogyaszt
     * @param rovar a rovar, aki megeszi a spórát
     */
    public void hatas(Spora sp, Rovar rovar) {
        // Visitor példány, amely alkalmazza a spóra hatását a rovarra
        HatastAlkalmazVisitor visitor = new HatastAlkalmazVisitor(rovar);

        // A spóra elfogadja a látogatót, és az alkalmazza a hatást
        sp.accept(visitor);

        // A rovar tápanyagtartalma nő a spóra tartalmával
        rovar.fogyaszt(sp);  // feltételezve, hogy van ilyen metódus

        // Itt el is távolíthatnánk a spórát a tektonról, ha szükséges lenne
        // (pl. ha tudjuk, hol van a spóra, vagy a Tekton példányt is átadnánk)
    }
}

