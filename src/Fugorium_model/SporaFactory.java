package Fugorium_model;
import java.util.Random;

/*
 * SporaFactory osztaly ami a random sporaElement-ek letrehozasaert felel
 */
public class SporaFactory {
    private Random rand = new Random();
    /*
     * random sporaElement-ek letrehozasaert felelos metodus
     */
    public Spora createRandomSpora() {
        int tip = rand.nextInt(5);
        int tapanyag = 1 + rand.nextInt(10);
        System.out.println("SporaFactory createRandomSpora method called");
        switch (tip) {
            case 0:
                System.out.println("SporaFactory created BenitoSporaElement");
                return new BenitoSporaElement(tapanyag);
            case 1:
                System.out.println("SporaFactory created LassitoSporaElement");
                return new LassitoSporaElement(tapanyag);
            case 2:
                System.out.println("SporaFactory created GyorsitoSporaElement");
                return new GyorsitoSporaElement(tapanyag);
            case 3:
                System.out.println("SporaFactory created VagastGatloSporaElement");
                return new VagastGatloSporaElement(tapanyag);
            case 4:
                System.out.println("SporaFactory created RovarOsztodoSporaElement");
                return new RovarOsztodoSporaElement(tapanyag);
            default:
                System.out.println("SporaFactory created default BenitoSporaElement");
                return new BenitoSporaElement(tapanyag);
        }
    }

}
