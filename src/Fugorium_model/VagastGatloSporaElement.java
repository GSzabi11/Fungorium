package Fugorium_model;

/**
 * VagastGatloSporaElement osztaly, a Spora leszarmazottja
 */
public class VagastGatloSporaElement extends Spora {

    /*
     * Visitor design pattern reszekent, az accept metodus megvalositasa
     */
    @Override
    public void accept(SporaVisitor visitor) {
        System.out.println("VagastGatloSporaElement accept method called");
    }
    /*
     * Visitor design pattern reszekent, az alkalmazHatast metodus megvalositasa
     * @param Rovar peldany amire az allapotot alkalmazza a metodus
     * @param RovarAllapot amilyen allapotot alklamaz a metodus a rovarra
     * @param duration az allapot idotartama
     */
    @Override
    public void alkalmazHatast(Rovar rovar, RovarAllapot allapot, int duration) {
        System.out.println("VagastGatloSporaElement alkalmazHatast method called");
    }

}
