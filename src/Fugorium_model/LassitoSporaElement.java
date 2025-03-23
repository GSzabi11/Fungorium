package Fugorium_model;

public class LassitoSporaElement extends Spora {

    @Override
    public void accept(SporaVisitor visitor) {
        System.out.println("LassitoSporaElement accept method called");
    }

    @Override
    public void alkalmazHatast(Rovar rovar, RovarAllapot allapot, int duration) {
        System.out.println("LassitoSporaElement alkalmazHatast method called");
    }

}
