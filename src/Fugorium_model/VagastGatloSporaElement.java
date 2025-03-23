package Fugorium_model;

public class VagastGatloSporaElement extends Spora {

    @Override
    public void accept(SporaVisitor visitor) {
        System.out.println("VagastGatloSporaElement accept method called");
    }

    @Override
    public void alkalmazHatast(Rovar rovar, RovarAllapot allapot, int duration) {
        System.out.println("VagastGatloSporaElement alkalmazHatast method called");
    }

}
