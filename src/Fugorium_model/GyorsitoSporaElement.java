package Fugorium_model;

public class GyorsitoSporaElement extends Spora {

    @Override
    public void accept(SporaVisitor visitor) {
        System.out.println("GyorsitoSporaElement accept method called");
    }

    @Override
    public void alkalmazHatast(Rovar rovar, RovarAllapot allapot, int duration) {
        System.out.println("GyorsitoSporaElement alkalmazHatast method called");
    }

}
