package Fugorium_model;

public class SporaFactory {
    public Spora createRandomSpora() {
        System.out.println("SporaFactory createRandomSpora method called");
        return new BenitoSporaElement(); //csak pelda, kesobb majd random sporat ad vissza
    }

}
