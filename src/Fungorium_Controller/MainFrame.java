package Fungorium_Controller;

import Fugorium_Model.*;
import Fungorium_View.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private KorView korView;
    private Object kijeloltObjektum = null;
    private JatekTer jatekTer;

    public void jatekMenu(List<Player> playerList) {
        SwingUtilities.invokeLater(() -> {
            Vilag vilag = new Vilag(jatekTer);
            vilag.setJatekosok(playerList);
            GameEngine gameEngine = new GameEngine(vilag, jatekTer, playerList);
            this.korView = new KorView();

            RajzoloTar rajzoloTar = new RajzoloTar();
            rajzoloTar.regisztral(Tekton.class, new TektonView(20, 12, 40, 40));
            rajzoloTar.regisztral(Gomba.class, new GombaView(28, 15, 50, 50));
            rajzoloTar.regisztral(Gombafonal.class, new GombafonalView(145, 76, 50, 56));
            rajzoloTar.regisztral(Rovar.class, new RovarView(21, 24, 50, 50));
            rajzoloTar.regisztral(Spora.class, new SporaView(35, 46, 50, 50));

            JatekTer jatekTer = new JatekTer(vilag, rajzoloTar, korView, gameEngine);

            // 4. Infopanel, property change logic
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(Color.LIGHT_GRAY);
            infoPanel.setPreferredSize(new Dimension(600, 768));
            infoPanel.add(new JLabel("Nincs kijelölt objektum"));

            // Main layout
            JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, korView, jatekTer);
            mainSplit.setDividerLocation(200);

            JSplitPane fullSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainSplit, infoPanel);
            fullSplit.setDividerLocation(900);
            fullSplit.setResizeWeight(1.0);

            JFrame frame = new JFrame("Fungorium");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1920, 1080);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(fullSplit);

            frame.setVisible(true);

            // Infopanel - property change
            jatekTer.addPropertyChangeListener("selectedObject", evt -> {
                updateInfoPanel(infoPanel, jatekTer.getKijeloltObjektum());
            });

            // 5. GameEngine
            GameEngine engine = new GameEngine(vilag, jatekTer, playerList); // <-- megfelelő paraméterezés

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

            // Gombatest növesztés
            korView.getGombatestNovesztButton().addActionListener(e -> {
                Gomba g = gameEngine.getKivalasztottGomba();
                if (g != null && gameEngine.getKivalasztottCelTekton().isNohetGomba()) {
                    //gombatestet kell valahogy noveszteni
                    korView.csakKorVegeMarad();
                } else {
                    JOptionPane.showMessageDialog(null, "Válassz ki egy gombát először!");
                }
            });

            // Spórázás
            korView.getSporazButton().addActionListener(e -> {
                Gomba g = gameEngine.getKivalasztottGomba();
                if (g != null) {
                    g.sporaz(); // ide még kell grafika hozzá
                    korView.csakKorVegeMarad();
                } else {
                    JOptionPane.showMessageDialog(null, "Válassz ki egy gombát a spórázáshoz!");
                }
            });


            // Fonal növesztés
            korView.getFonalNovesztButton().addActionListener(e -> {
                Gomba g = gameEngine.getKivalasztottGomba();
                if (g != null && gameEngine.getKivalasztottCelTekton() != null) {
                    g.novesztUjFonal(gameEngine.getKivalasztottCelTekton()); // GRAFIKA :)
                    korView.csakKorVegeMarad();
                } else {
                    JOptionPane.showMessageDialog(null, "Válassz ki egy gombát és cél tekton mezőt!");
                }
            });

            // Rovar mozgás
            korView.getMozgasButton().addActionListener(e -> {
                if (engine.getKivalasztottRovar() != null && engine.getKivalasztottCelTekton() != null) {
                    MozgasController mozgasController = new MozgasController();
                    mozgasController.move(gameEngine.getKivalasztottRovar(), gameEngine.getKivalasztottCelTekton());
                    gameEngine.getKivalasztottRovar().mozog(gameEngine.getKivalasztottCelTekton());
                    korView.csakKorVegeMarad();
                } else {
                    JOptionPane.showMessageDialog(null, "Kérlek válassz ki egy rovart és egy cél tekton mezőt!");
                }
            });
        });
    }

    // ==== INFOPANEL: ÚJ segédfüggvény ====
    private void updateInfoPanel(JPanel panel, Object o) {
        panel.removeAll();
        if (o == null) {
            panel.add(new JLabel("Nincs kijelölt objektum"));
        } else if (o instanceof Gomba g) {
            panel.add(new JLabel("Típus: Gomba"));
            panel.add(new JLabel("Fajta: " + g.getFajta().toString()));
            panel.add(new JLabel("Pozíció: (" + g.getX() + ", " + g.getY() + ")"));
            panel.add(new JLabel("Termelt spórák: " + g.getTermeltSporakSzama()));
        } else if (o instanceof Tekton t) {
            panel.add(new JLabel("Típus: Tekton"));
            panel.add(new JLabel("Azonosító: " + t.getId()));
            panel.add(new JLabel("Pozíció: (" + t.getX() + ", " + t.getY() + ")"));
            panel.add(new JLabel("Spórák: " + (t.getSporak() != null ? t.getSporak().size() : "N/A")));
        } else if (o instanceof Rovar r) {
            panel.add(new JLabel("Típus: Rovar"));
            panel.add(new JLabel("Fajta: " + r.getFajta().toString()));
            panel.add(new JLabel("Pozíció: (" + r.getX() + ", " + r.getY() + ")"));
            panel.add(new JLabel("Tapanyag: " + r.getTapanyag()));
            // Egyéb rovar adatok is kiírhatók, pl. állapot
        }

        panel.revalidate();
        panel.repaint();
        repaint();

    }
}
