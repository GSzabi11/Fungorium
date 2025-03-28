package Fugorium_model;

/**
 * BenitoSporaElement osztaly, a Spora leszarmazottja
 */
public class BenitoSporaElement extends Spora {
    
    public BenitoSporaElement() {
        super();  // hivja a szulo parameter nelkuli konstruktorat
        System.out.println("BenitoSporaElement constructor called, nutrition value: 0");
    }

    public BenitoSporaElement(int tapanyagtartalom) {
        super(tapanyagtartalom);  // hivja a szulo parameteres konstruktorat
        System.out.println("BenitoSporaElement constructor called, nutrition value: " + tapanyagtartalom);
    }
    
    /*
     * Visitor design pattern reszekent, az accept metodus megvalositasa
     */
    @Override
    public void accept(SporaVisitor visitor) {
        visitor.visit(this);
        System.out.println("BenitoSporaElement accept method called");
    }

    /*
     * Visitor design pattern reszekent, az alkalmazHatast metodus megvalositasa
     * @param Rovar peldany amire az allapotot alkalmazza a metodus
     * @param RovarAllapot amilyen allapotot alklamaz a metodus a rovarra
     * @param duration az allapot idotartama
     */
    @Override
    public void alkalmazHatast(Rovar rovar, RovarAllapot allapot, int duration) {
        rovar.setAllapot(RovarAllapot.BENITO, duration);
        System.out.println("BenitoSporaElement alkalmazHatast method called");
    }

}
