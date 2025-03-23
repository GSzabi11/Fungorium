package Fugorium_model;

public class BenitoSporaElement extends Spora {
    @Override
    public void accept(SporaVisitor visitor) {
        System.out.println("BenitoSporaElement accept method called");
    }

    @Override
    public void alkalmazHatast(Rovar rovar, RovarAllapot allapot, int duration) {
        System.out.println("BenitoSporaElement alkalmazHatast method called");
    }

}
