package Fugorium_model;

public interface SporaVisitor {
    public void visit(BenitoSporaElement benito);

    public void visit(GyorsitoSporaElement gyorsito);

    public void visit(LassitoSporaElement lassito);
    
    public void visit(VagastGatloSporaElement vagastgatlo);

}
