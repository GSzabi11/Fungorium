package Fungorium_Controller;

import Fugorium_Model.*;
import Fungorium_View.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private KorView korView;
    private Object kijeloltObjektum = null;

    public void jatekMenu(List<Player> playerList) {
        SwingUtilities.invokeLater(() -> {
            Vilag vilag = new Vilag();
            GameEngine gameEngine = new GameEngine(vilag, null, playerList);
            this.korView = new KorView();
            vilag.setJatekosok(playerList);

            RajzoloTar rajzoloTar = new RajzoloTar();
            rajzoloTar.regisztral(Tekton.class, new TektonView(20, 12, 40, 40));
            rajzoloTar.regisztral(Gomba.class, new GombaView(28, 15, 50, 50));
            rajzoloTar.regisztral(Rovar.class, new RovarView(21, 24, 50, 50));
            rajzoloTar.regisztral(Gombafonal.class, new GombafonalView(145, 76, 50, 56));
            rajzoloTar.regisztral(Spora.class, new SporaView(35, 46, 50, 50));

            JatekTer jatekTer = new JatekTer(vilag, rajzoloTar, korView);

            JFrame frame = new JFrame("Fungorium");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1366, 768);
            frame.setLocationRelativeTo(null);

            // elrendezés: játék + korView bal oldalt
            JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, korView, jatekTer);
            split.setDividerLocation(200);
            frame.setContentPane(split);

            frame.setVisible(true);

            // átadjuk a játékosokat a motorba
            List<String> szerepek = playerList.stream().map(p -> p.role.equalsIgnoreCase("gombász") ? "Gombász" : "Rovarász").toList();
            GameEngine engine = new GameEngine(vilag, jatekTer, playerList); // már a teljes játékoslista

            korView.korVege.addActionListener(e -> {
                engine.kovetkezoKor();
                Player aktualis = playerList.get(engine.getKorIndex());
                if (aktualis.role.equalsIgnoreCase("gombász")) {
                    korView.csakGombasznak();
                } else {
                    korView.csakRovarasznak();
                }

                korView.resetAllButtons();
            });

            engine.startGame();

            Player aktualis = playerList.get(0);
            if (aktualis.role.equalsIgnoreCase("gombász")) {
                korView.csakGombasznak();
            } else {
                korView.csakRovarasznak();
            }

            korView.getFonalVagasButton().addActionListener(e -> {
                // Itt jönne a fonalvágás konkrétan, pl. egy controller meghívása (most még nincs)
                korView.csakKorVegeMarad();
            });

            korView.getGombatestNovesztButton().addActionListener(e -> {
                // növesztés logika ide
                korView.csakKorVegeMarad();
            });

            korView.getSporazButton().addActionListener(e -> {
                // spórázás logika ide
                korView.csakKorVegeMarad();
            });

            korView.getFonalNovesztButton().addActionListener(e -> {
                // fonal növesztés logika ide
                korView.csakKorVegeMarad();
            });

            korView.getMozgasButton().addActionListener(e -> {
                if (gameEngine.getKivalasztottRovar() != null && gameEngine.getKivalasztottCelTekton() != null) {
                    MozgasController mozgasController = new MozgasController();
                    mozgasController.move(gameEngine.getKivalasztottRovar(), gameEngine.getKivalasztottCelTekton());
                    korView.csakKorVegeMarad(); // vagy más UI frissítés
                } else {
                    JOptionPane.showMessageDialog(null, "Kérlek válassz ki egy rovart és egy cél tekton mezőt!");
                }
            });


        });
    }



}

