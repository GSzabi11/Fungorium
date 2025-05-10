import Fugorium_Model.*;
import Fungorium_Controller.GameEngine;
import Fungorium_View.*;

import javax.swing.*;

public class MainFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Modell
            Vilag vilag = new Vilag();

            // Rajzolóregiszter feltöltése
            RajzoloTar rajzoloTar = new RajzoloTar();
            rajzoloTar.register(Tekton.class, new TektonView());
            rajzoloTar.register(Rovar.class, new RovarView());
            rajzoloTar.register(Gombafonal.class, new GombafonalView());
            rajzoloTar.register(Gomba.class, new GombaView());
            rajzoloTar.register(Spora.class, new SporaView());
            // … további regisztrációk, pl. OsztodoSpora, ha van külön típus

            // Nézet
            JatekTer jatekTer = new JatekTer(vilag, rajzoloTar);

            // Ablak
            JFrame frame = new JFrame("Fungorium");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1024, 768);
            frame.setLocationRelativeTo(null); // középre igazítás
            frame.setContentPane(jatekTer);
            frame.setVisible(true);

            // Kontroller: elindítja az időzített játékmenetet
            GameEngine engine = new GameEngine(vilag, jatekTer);
            engine.start();
        });
    }
}

