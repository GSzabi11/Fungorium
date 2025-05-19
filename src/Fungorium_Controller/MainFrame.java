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
    private RajzoloTar rajzoloTar;
    private GameEngine engine;

    public void jatekMenu(List<Player> playerList) {
        SwingUtilities.invokeLater(() -> {
            Vilag vilag = new Vilag(jatekTer);
            vilag.setJatekosok(playerList);
            //GameEngine gameEngine = new GameEngine(vilag, jatekTer, playerList);
            this.korView = new KorView();
            this.rajzoloTar = new RajzoloTar();
            this.jatekTer = new JatekTer(vilag, rajzoloTar, korView);

            engine = new GameEngine(vilag, jatekTer, playerList, korView); // <-- megfelelő paraméterezés
            jatekTer.setGameEngine(engine);



            rajzoloTar.regisztral(Tekton.class, new TektonView(20, 12, 40, 40));
            rajzoloTar.regisztral(Gomba.class, new GombaView(28, 15, 50, 50));
            rajzoloTar.regisztral(Gombafonal.class, new GombafonalView(145, 76, 50, 56));
            rajzoloTar.regisztral(Rovar.class, new RovarView(21, 24, 50, 50));
            rajzoloTar.regisztral(Spora.class, new SporaView(35, 46, 50, 50));


            // 4. Infopanel, property change logic
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(Color.LIGHT_GRAY);
            infoPanel.setPreferredSize(new Dimension(200, 600)); // Fix szélesség
            infoPanel.setMinimumSize(new Dimension(200, 600)); // Minimum méret
            infoPanel.setMaximumSize(new Dimension(200, 600)); // Maximum méret

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
                infoPanel.setPreferredSize(new Dimension(300, 768));
                updateInfoPanel(infoPanel, jatekTer.getKijeloltObjektum());
            });

            // 5. GameEngine
            korView.korVege.addActionListener(e -> {
                engine.kovetkezoKor();
                updateButtonsForCurrentPlayer(engine, korView, playerList);
            });

            engine.startGame();
            updateButtonsForCurrentPlayer(engine, korView, playerList);

            Player aktualis = playerList.get(engine.getKorIndex()%playerList.size());
            if (aktualis.role.equalsIgnoreCase("gombasz")) {
                korView.csakGombasznak();
            } else {
                korView.csakRovarasznak();
            }

            korView.getFonalVagasButton().addActionListener(e -> {
                // Itt jönne a fonalvágás konkrétan, pl. egy controller meghívása (most még nincs)
                korView.csakKorVegeMarad();
            });

            //KÉSZ VAN
            // A Gombatest növesztés gomb eseménykezelője
            korView.getGombatestNovesztButton().addActionListener(e -> {
                Tekton celTekton = engine.getKivalasztottCelTekton(); // Kijelölt tekton lekérése
                if (celTekton != null) {
                    // Ellenőrizd, hogy a tektonon lehet-e gombát növeszteni
                    if (celTekton.isNohetGomba()) {
                        Gombafaj fajta = Gombafaj.KEK; // Válaszd ki a gomba fajtáját (pl. KEK)
                        Gomba ujGomba = new Gomba(fajta, celTekton, celTekton.getX(), celTekton.getY());
                        vilag.addGomba(ujGomba); // Gomba hozzáadása a világhoz
                        System.out.println("[DEBUG] Új gomba növesztve a T" + celTekton.getId() + " tektonon.");
                    } else {
                        JOptionPane.showMessageDialog(korView,
                                "Nem lehet gombát növeszteni ezen a tektonon.",
                                "Hiba",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(korView,
                            "Nincs kijelölt tekton.",
                            "Hiba",
                            JOptionPane.ERROR_MESSAGE);
                }
            });


            // Spórázás
            korView.getSporazButton().addActionListener(e -> {
                Gomba g = engine.getKivalasztottGomba();
                if (g != null) {
                    g.sporaz(); // ide még kell grafika hozzá

                    korView.csakKorVegeMarad();
                } else {
                    JOptionPane.showMessageDialog(null, "Válassz ki egy gombát a spórázáshoz!");
                }
            });


            // Fonal növesztés
            korView.getFonalNovesztButton().addActionListener(e -> {
                Gomba g = engine.getKivalasztottGomba();
                if (g != null && engine.getKivalasztottCelTekton() != null) {
                    g.novesztUjFonal(engine.getKivalasztottCelTekton()); // GRAFIKA :)
                    korView.csakKorVegeMarad();
                } else {
                    JOptionPane.showMessageDialog(null, "Válassz ki egy gombát és cél tekton mezőt!");
                }
            });

            // Rovar mozgás
            korView.getMozgasButton().addActionListener(e -> {
                if (engine.getKivalasztottRovar() != null && engine.getKivalasztottCelTekton() != null) {
                    Tekton old = engine.getKivalasztottRovar().getHelyzet();
                    MozgasController mozgasController = new MozgasController();
                    mozgasController.move(engine.getKivalasztottRovar(), engine.getKivalasztottCelTekton());
                    jatekTer.hozzaadRovarGombkent(engine.getKivalasztottRovar());
                    if (old != engine.getKivalasztottRovar().getHelyzet()){
                        korView.csakKorVegeMarad();
                    }
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

        panel.setPreferredSize(new Dimension(200, 768));
        panel.revalidate();
        panel.repaint();

    }

    private void updateButtonsForCurrentPlayer(GameEngine eng, KorView kv, List<Player> pl) {
        Player akt = pl.get(eng.getKorIndex());
        if (akt.getRole().equalsIgnoreCase("gombasz")) {
            kv.csakGombasznak();
        } else {
            kv.csakRovarasznak();
        }
        kv.resetAllButtons();      // újra engedélyezzük azokat a gombokat
        kv.revalidate();
        kv.repaint();
    }

}
