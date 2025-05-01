package Fugorium_model;

/**
 * A Gombafaj enum a játékban előforduló gombafajokat tartalmazza.
 */
public enum Gombafaj {
    PIROS(3), KEK(5), ZOLD(6), SARGA(4);

    public final int elhalasIdo;

    Gombafaj(int eletido) {
        this.elhalasIdo = eletido;
    }
}