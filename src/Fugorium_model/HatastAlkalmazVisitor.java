package Fugorium_model;

/*
 * SporaVisitor megvalositasa
 */
public class HatastAlkalmazVisitor implements SporaVisitor {
    /*
     * Rovar peldany
     */
    private Rovar rovar;
    
    /*
     * HatastAlkalmazVisitor konstruktora
     * @param Rovar peldany
     */
    public HatastAlkalmazVisitor(Rovar rovar) {
        System.out.println("HatastAlkalmazVisitor constructor called");
    }
    
    /*
     * Visitor design pattern reszekent, a visit metodus megvalositasa
     * @param BenitoSporaElement peldany
     */
    @Override
    public void visit(BenitoSporaElement benito) {
        System.out.println("BenitoSporaElement visit method called");
    }


    /*
     * Visitor design pattern reszekent, a visit metodus megvalositasa
     * @param GyorsitoSporaElement peldany
     */
    @Override
    public void visit(GyorsitoSporaElement gyorsito) {
        System.out.println("GyorsitoSporaElement visit method called");
    }
    
    /*
     * Visitor design pattern reszekent, a visit metodus megvalositasa
     * @param LassitoSporaElement peldany
     */
    @Override
    public void visit(LassitoSporaElement lassito) {
        System.out.println("LassitoSporaElement visit method called");
    }
    
    /*
     * Visitor design pattern reszekent, a visit metodus megvalositasa
     * @param VagastGatloSporaElement peldany
     */
    @Override
    public void visit(VagastGatloSporaElement vagastgatlo) {
        System.out.println("VagastGatloSporaElement visit method called");
    }
}
