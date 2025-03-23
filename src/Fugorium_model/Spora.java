package Fugorium_model;

/*
 * Spora absztrakt osztaly amibol a kulonbozo sporaElement-ek szarmaznak
 */
public abstract class  Spora {
    /*
     * tapanyagtartalom (a sporat elfogyasztva ennyi tapanyaggal no a rovar tapanyag parametere)
     */
    private int tapanyagtartalom;

    /*
     * Spora konstruktora
     */
    Spora() {
        System.out.println("Spora constructor called");
    }

    /*
     * Visitor design pattern reszekent, az accept metodus deklaracioja
     */
    public abstract void accept(SporaVisitor visitor);

    /*
     * Visitor design pattern reszekent, az alkalmazHatast metodus deklaracioja
     * @param Rovar peldany amire az allapotot alkalmazza a metodus
     * @param RovarAllapot amilyen allapotot alklamaz a metodus a rovarra
     * @param duration az allapot idotartama
     */
    public abstract void alkalmazHatast(Rovar rovar, RovarAllapot allapot, int duration);
}
