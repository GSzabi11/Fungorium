package Fungorium_Controller;

import Fungorium_View.JatekTer;
import Fungorium_View.Vilag;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameEngine {

    private final Vilag vilag;
    private final JatekTer jatekTer;
    private final Timer timer;

    private boolean rovaraszKor = true; // true = rovarász, false = gombász

    public GameEngine(Vilag vilag, JatekTer jatekTer) {
        this.vilag = vilag;
        this.jatekTer = jatekTer;


        // Timer csak a repainthez és léptetéshez, de nem vált köröket
        this.timer = new Timer(1000 / 30, e -> {
            vilag.leptet();
            jatekTer.repaint();
            vilag.initEntities();
        });
    }

    /**
     * Ezt a metódust kell meghívni, amikor a játékos befejezi a lépését.
     */
    public void kovetkezoKor() {
        rovaraszKor = !rovaraszKor;
        String korTulajdonos = rovaraszKor ? "Rovarász" : "Gombász";
        System.out.println("Körváltás: most " + korTulajdonos + " köre van.");
        jatekTer.setKorTulajdonos(korTulajdonos);

        // Itt lehet további körváltáshoz kötött logikát elhelyezni
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void restart() {
        stop();
        start();
    }
}
