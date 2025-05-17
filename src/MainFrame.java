import Fugorium_Model.*;
import Fungorium_Controller.GameEngine;
import Fungorium_View.*;

import javax.swing.*;

public class MainFrame {

    public void majdnemmain() {
        SwingUtilities.invokeLater(() -> {
            // Modell
            Vilag vilag = new Vilag();

            // Rajzolóregiszter feltöltése
            RajzoloTar rajzoloTar = new RajzoloTar();
            rajzoloTar.regisztral(Tekton.class, new TektonView(500,500, 50, 50, new ImageIcon("fung_pngk/image9.png").getImage()));
            rajzoloTar.regisztral(Rovar.class, new RovarView());
            rajzoloTar.regisztral(Gombafonal.class, new GombafonalView());
            rajzoloTar.regisztral(Gomba.class, new GombaView());
            rajzoloTar.regisztral(Spora.class, new SporaView());
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

