package Szabi;

import Ricsi.Spora;
import Ricsi.SporaVisitor;

/**
 * LassitoSporaElement osztaly, a Spora leszarmazottja
 */
public class LassitoSporaElement extends Spora {

    public LassitoSporaElement() {
        super();  // hivja a szulo parameter nelkuli konstruktorat
        System.out.println("LassitoSporaElement constructor called, nutrition value: 0");
    }

    public LassitoSporaElement(int tapanyagtartalom) {
        super(tapanyagtartalom);  // hivja a szulo parameteres konstruktorat
        System.out.println("LassitoSporaElement constructor called, nutrition value: " + tapanyagtartalom);
    }

    /*
     * Visitor design pattern reszekent, az accept metodus megvalositasa
     */
    @Override
    public void accept(SporaVisitor visitor) {
        visitor.visit(this);
        System.out.println("LassitoSporaElement accept method called");
    }

    /*
     * Visitor design pattern reszekent, az alkalmazHatast metodus megvalositasa
     * @param Rovar peldany amire az allapotot alkalmazza a metodus
     * @param RovarAllapot amilyen allapotot alklamaz a metodus a rovarra
     * @param duration az allapot idotartama
     */
    @Override
    public void alkalmazHatast(Rovar rovar, RovarAllapot allapot, int duration) {
        rovar.setAllapot(RovarAllapot.LASSITO, duration);
        System.out.println("LassitoSporaElement alkalmazHatast method called");
    }

}
