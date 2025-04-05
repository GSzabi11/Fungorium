package Szabi;

import Ricsi.SporaVisitor;
import Ricsi.VagastGatloSporaElement;

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
        this.rovar = rovar;
        System.out.println("HatastAlkalmazVisitor constructor called");
    }
    
    /*
     * Visitor design pattern reszekent, a visit metodus megvalositasa
     * @param BenitoSporaElement peldany
     */
    @Override
    public void visit(BenitoSporaElement benito) {
        benito.alkalmazHatast(rovar, RovarAllapot.BENITO, 3); //egyelore hard kodoltam, hogy hany korig tart a hatas
        System.out.println("BenitoSporaElement visit method called");
    }


    /*
     * Visitor design pattern reszekent, a visit metodus megvalositasa
     * @param GyorsitoSporaElement peldany
     */
    @Override
    public void visit(GyorsitoSporaElement gyorsito) {
        gyorsito.alkalmazHatast(rovar, RovarAllapot.GYORSITO, 2); //egyelore hard kodoltam, hogy hany korig tart a hatas
        System.out.println("GyorsitoSporaElement visit method called");
    }
    
    /*
     * Visitor design pattern reszekent, a visit metodus megvalositasa
     * @param LassitoSporaElement peldany
     */
    @Override
    public void visit(LassitoSporaElement lassito) {
        lassito.alkalmazHatast(rovar, RovarAllapot.LASSITO, 2); //egyelore hard kodoltam, hogy hany korig tart a hatas
        System.out.println("LassitoSporaElement visit method called");
    }
    
    /*
     * Visitor design pattern reszekent, a visit metodus megvalositasa
     * @param VagastGatloSporaElement peldany
     */
    @Override
    public void visit(VagastGatloSporaElement vagastgatlo) {
        vagastgatlo.alkalmazHatast(rovar, RovarAllapot.VAGASTGATLO, 5); //egyelore hard kodoltam, hogy hany korig tart a hatas
        System.out.println("VagastGatloSporaElement visit method called");
    }

    @Override
    public void visit(RovarOsztodoSporaElement rovarosztodo) {
        rovarosztodo.alkalmazHatast(rovar, null, 0); //csak klonoz
        System.out.println("RovarOsztodoSporaElement visit method called");
    }
}
