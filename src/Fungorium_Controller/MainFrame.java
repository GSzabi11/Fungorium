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
            rajzoloTar.regisztral(Tekton.class, new TektonView(20,12, 40, 40));
            rajzoloTar.regisztral(Rovar.class, new RovarView(21,24, 50, 50));
            rajzoloTar.regisztral(Gombafonal.class, new GombafonalView(145,76, 50, 56));
            rajzoloTar.regisztral(Gomba.class, new GombaView(28,15, 50, 50));
            rajzoloTar.regisztral(Spora.class, new SporaView(35,46, 50, 50));
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

