package Fugorium_model;

/*
 * SporaFactory osztaly ami a random sporaElement-ek letrehozasaert felel
 */
public class SporaFactory {
    /*
     * random sporaElement-ek letrehozasaert felelos metodus
     */
    public Spora createRandomSpora() {
        System.out.println("SporaFactory createRandomSpora method called");
        return new BenitoSporaElement(); //csak pelda, kesobb majd random sporat ad vissza
    }

}
