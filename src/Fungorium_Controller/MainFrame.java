package Fungorium_Controller;

import Fugorium_Model.*;
import Fungorium_View.*;
import javax.swing.*;

public class MainFrame {

    public void jatekMenu() {
        SwingUtilities.invokeLater(() -> {
            // Modell
            Vilag vilag = new Vilag();

            // Rajzolóregiszter feltöltése
            RajzoloTar rajzoloTar = new RajzoloTar();
            rajzoloTar.regisztral(Tekton.class, new TektonView(500,500, 50, 50, new ImageIcon("fung_pngk/tekton.png").getImage()));
            rajzoloTar.regisztral(Rovar.class, new RovarView(500,500, 50, 50, new ImageIcon("fung_pngk/rovar_barna.png").getImage()));
            rajzoloTar.regisztral(Gombafonal.class, new GombafonalView(500,500, 50, 50, new ImageIcon("gombafonal_lila/image9.png").getImage()));
            rajzoloTar.regisztral(Gomba.class, new GombaView(500,500, 50, 50, new ImageIcon("fung_pngk/gomba_kek.png").getImage()));
            rajzoloTar.regisztral(Spora.class, new SporaView(500,500, 50, 50, new ImageIcon("fung_pngk/spora.png").getImage()));
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

