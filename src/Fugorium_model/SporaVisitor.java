package Fugorium_model;

public interface SporaVisitor {
    /*
     * Visitor design pattern reszekent, a visit metodus deklaracioja
     */
    public void visit(BenitoSporaElement benito);

     /*
     * Visitor design pattern reszekent, a visit metodus deklaracioja
     */
    public void visit(GyorsitoSporaElement gyorsito);

     /*
     * Visitor design pattern reszekent, a visit metodus deklaracioja
     */
    public void visit(LassitoSporaElement lassito);
    
     /*
     * Visitor design pattern reszekent, a visit metodus deklaracioja
     */
    public void visit(VagastGatloSporaElement vagastgatlo);

    public void visit(RovarOsztodoSporaElement  rovarosztodo);

}
