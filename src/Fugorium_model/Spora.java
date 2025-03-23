package Fugorium_model;

public abstract class  Spora {
    private int tapanyagtartalom;

    Spora() {
        System.out.println("Spora constructor called");
    }


    public abstract void accept(SporaVisitor visitor);

    public abstract void alkalmazHatast(Rovar rovar, RovarAllapot allapot, int duration);
}
