package Szabi;

import Ricsi.Spora;
import Ricsi.SporaVisitor;
import Ricsi.Tekton;

public class RovarOsztodoSporaElement extends Spora {

    public RovarOsztodoSporaElement(int tapanyagtartalom) {
        super(tapanyagtartalom);
        System.out.println("RovarOsztodoSporaElement letrehozva, tapanyag: " + tapanyagtartalom);
    }

    @Override
    public void accept(SporaVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void alkalmazHatast(Rovar rovar, RovarAllapot unusedAllapot, int unusedDuration) {
        Tekton helyzet = rovar.getHelyzet();
        Rovarfaj fajta = rovar.getFajta();

        Rovar klonRovar = new Rovar(fajta, helyzet);

        // Most hova tegyük az új rovart?
        // Ideiglenesen csak logoljuk:
        System.out.println("Uj rovar klonozva! (" + fajta + ") Tekton: T" + helyzet.getId());

    }

}
