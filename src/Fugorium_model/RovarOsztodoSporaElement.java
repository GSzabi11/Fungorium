package Fugorium_model;

public class RovarOsztodoSporaElement extends Spora {

    public RovarOsztodoSporaElement(int tapanyagtartalom) {
        super(tapanyagtartalom);
        System.out.println("RovarOsztodoSporaElement létrehozva, tápanyag: " + tapanyagtartalom);
    }

    @Override
    public void accept(SporaVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void alkalmazHatast(Rovar rovar, RovarAllapot unusedAllapot, int unusedDuration) {
        Tekton helyzet = rovar.getHelyzet();
        Rovarfaj fajta = rovar.getFajta();

        Rovar klonRovar = new Rovar(fajta, helyzet);

        // Most hova tegyük az új rovart?
        // Ideiglenesen csak logoljuk:
        System.out.println("Új rovar klónozva! (" + fajta + ") Tekton: T" + helyzet.getId());

    }

}
