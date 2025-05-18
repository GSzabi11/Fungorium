package Fungorium_Controller;

import Fungorium_View.JatekTer;
import Fungorium_View.Vilag;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GameEngine {

    private final Vilag vilag;
    private final JatekTer jatekTer;
    private final Timer timer;
    //private final List<String> szerepek;
    private final List<Menu.Player> jatekosok;
    private int korIndex = 0;

    //private boolean rovaraszKor = true; // true = rovarász, false = gombász

    public GameEngine(Vilag vilag, JatekTer jatekTer, List<Menu.Player> jatekosok) {
        this.vilag = vilag;
        this.jatekTer = jatekTer;
        this.jatekosok = jatekosok;
        this.korIndex = 0;


        // Timer csak a repainthez és léptetéshez, de nem vált köröket
        this.timer = new Timer(1000 / 30, e -> {
            vilag.leptet();
            jatekTer.repaint();
            //vilag.initEntities();
        });
    }

    /**
     * Ezt a metódust kell meghívni, amikor a játékos befejezi a lépését.
     */
    public void kovetkezoKor() {
        korIndex = (korIndex + 1) % jatekosok.size();
        updateKorTulajdonosFelirat();

        // Itt lehet további körváltáshoz kötött logikát elhelyezni
    }

    private void updateKorTulajdonosFelirat() {
        Menu.Player aktualis = jatekosok.get(korIndex);
        String felirat = aktualis.name + " (" + aktualis.role + ")";
        jatekTer.setKorTulajdonos(felirat);
        System.out.println("Köre van: " + felirat);
    }


    public void start() {
        updateKorTulajdonosFelirat();
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void restart() {
        stop();
        start();
    }

    public int getKorIndex() {
        return korIndex;
    }
}
