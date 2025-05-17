package Fungorium_Controller;

import Fugorium_Model.*;
import Fungorium_View.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private KorView korView;

    public void jatekMenu(List<Menu.Player> playerList) {
        SwingUtilities.invokeLater(() -> {
            Vilag vilag = new Vilag();
            this.korView = new KorView();

            RajzoloTar rajzoloTar = new RajzoloTar();
            rajzoloTar.regisztral(Tekton.class, new TektonView(20,12, 40, 40));
            rajzoloTar.regisztral(Rovar.class, new RovarView(21,24, 50, 50));
            rajzoloTar.regisztral(Gombafonal.class, new GombafonalView(145,76, 50, 56));
            rajzoloTar.regisztral(Gomba.class, new GombaView(28,15, 50, 50));
            rajzoloTar.regisztral(Spora.class, new SporaView(35,46, 50, 50));

            JatekTer jatekTer = new JatekTer(vilag, rajzoloTar, korView);

            JFrame frame = new JFrame("Fungorium");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1024, 768);
            frame.setLocationRelativeTo(null);

            // elrendezés: játék + korView bal oldalt
            JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, korView, jatekTer);
            split.setDividerLocation(200);
            frame.setContentPane(split);

            frame.setVisible(true);

            // 🎯 átadjuk a játékosokat a motorba
            List<String> szerepek = playerList.stream().map(p -> p.role.equalsIgnoreCase("gombász") ? "Gombász" : "Rovarász").toList();
            GameEngine engine = new GameEngine(vilag, jatekTer, szerepek);
            korView.korVege.addActionListener(e -> {
                engine.kovetkezoKor();
                String ujKor = jatekTer.getKorTulajdonos();
                if (ujKor.equals("Gombász")) korView.csakGombasznak();
                else korView.csakRovarasznak();
            });
            engine.start();
        });
    }
}

