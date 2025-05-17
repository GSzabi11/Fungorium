package Fugorium_Model;

/*
 * Spora absztrakt osztaly amibol a kulonbozo sporaElement-ek szarmaznak
 */
public abstract class Spora {
    /*
     * tapanyagtartalom (a sporat elfogyasztva ennyi tapanyaggal no a rovar tapanyag parametere)
     */
    private int tapanyagtartalom;
    int x;
    int y;

    /*
     * Spora parameter nelkuli konstruktora
     */
    public Spora() {
        this.tapanyagtartalom = 0;
        System.out.println("Spora constructor called");
    }

    /*
     * Spora parameteres konstruktora
     * @param tapanyagtartalom
     */
    public Spora(int tapanyagtartalom) {
        this.tapanyagtartalom = tapanyagtartalom;
    }

    public int getTapanyagtartalom() {
        return tapanyagtartalom;
    }
    
    public void setTapanyagtartalom(int tapanyagtartalom) {
        this.tapanyagtartalom = tapanyagtartalom;
    }

    /*
     * Visitor design pattern reszekent, az accept metodus deklaracioja
     */
    public void accept(SporaVisitor visitor){};

    /*
     * Visitor design pattern reszekent, az alkalmazHatast metodus deklaracioja
     * @param Rovar peldany amire az allapotot alkalmazza a metodus
     * @param RovarAllapot amilyen allapotot alklamaz a metodus a rovarra
     * @param duration az allapot idotartama
     */
    public void alkalmazHatast(Rovar rovar, RovarAllapot allapot, int duration){};

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
