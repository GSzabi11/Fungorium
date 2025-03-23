package Fugorium_model;

public class HatastAlkalmazVisitor implements SporaVisitor {
    private Rovar rovar;
    
    public HatastAlkalmazVisitor(Rovar rovar) {
        System.out.println("HatastAlkalmazVisitor constructor called");
    }
    
    @Override
    public void visit(BenitoSporaElement benito) {
        System.out.println("BenitoSporaElement visit method called");
    }


    @Override
    public void visit(GyorsitoSporaElement gyorsito) {
        System.out.println("GyorsitoSporaElement visit method called");
    }
    
    @Override
    public void visit(LassitoSporaElement lassito) {
        System.out.println("LassitoSporaElement visit method called");
    }
    
    @Override
    public void visit(VagastGatloSporaElement vagastgatlo) {
        System.out.println("VagastGatloSporaElement visit method called");
    }
}
