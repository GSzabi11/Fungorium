package Fungorium_Controller;

import Fungorium_View.JatekTer;
import Fungorium_View.Vilag;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A GameEngine felelős a játék fő ütemezéséért (game loop).
 * Meghatározott időközönként lépteti a világot és újrarajzoltatja a felületet.
 */
public class GameEngine {

    private final Vilag vilag;
    private final JatekTer jatekTer;
    private final Timer timer;

    /**
     * Konstruktor – példányosítja az időzítőt, de nem indítja el.
     *
     * @param vilag     a játék logikai világa (modell)
     * @param jatekTer  a megjelenítő felület (view)
     */
    public GameEngine(Vilag vilag, JatekTer jatekTer) {
        this.vilag = vilag;
        this.jatekTer = jatekTer;

        // Timer: 1000ms / 30 = ~33ms (~30 FPS)
        this.timer = new Timer(1000 / 30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vilag.leptet();
                jatekTer.repaint();
            }
        });
    }

    /**
     * Elindítja a játékot.
     */
    public void start() {
        timer.start();
    }

    /**
     * Leállítja a játékot.
     */
    public void stop() {
        timer.stop();
    }

    /**
     * Újraindítja a játékot (opcionális).
     */
    public void restart() {
        stop();
        start();
    }
}
