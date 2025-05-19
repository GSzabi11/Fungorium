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
            // --- Vilag példány létrehozása
            Vilag vilag = new Vilag(jatekTer);
            vilag.setJatekosok(playerList);

            this.korView = new KorView();

            RajzoloTar rajzoloTar = new RajzoloTar();
            rajzoloTar.regisztral(Tekton.class, new TektonView(20, 12, 40, 40));
            rajzoloTar.regisztral(Gomba.class, new GombaView(28, 15, 50, 50));
            rajzoloTar.regisztral(Gombafonal.class, new GombafonalView(145, 76, 50, 56));
            rajzoloTar.regisztral(Rovar.class, new RovarView(21, 24, 50, 50));
            rajzoloTar.regisztral(Spora.class, new SporaView(35, 46, 50, 50));

            this.jatekTer = new JatekTer(vilag, rajzoloTar, korView);

            // --- INFO PANEL: KISEBB SZÉLESSÉG (fix: 200px)
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(Color.LIGHT_GRAY);
            infoPanel.setPreferredSize(new Dimension(140, 768)); // << kisebb sáv
            infoPanel.setMinimumSize(new Dimension(140, 768));
            infoPanel.setMaximumSize(new Dimension(140, 768));
            infoPanel.add(new JLabel("Nincs kijelölt objektum"));

            // --- Fő elrendezés
            JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, korView, jatekTer);
            mainSplit.setDividerLocation(200);

            JSplitPane fullSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainSplit, infoPanel);
            fullSplit.setDividerLocation(1260); // kb. a játéktérnél válassza szét
            fullSplit.setResizeWeight(1.0); // főleg a középső panel nőljön

            JFrame frame = new JFrame("Fungorium");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1420, 800); // Ésszerű ablakméret, szélesvászon
            frame.setLocationRelativeTo(null);
            frame.setContentPane(fullSplit);

            frame.setVisible(true);

            // InfoPanel frissítés (propertyChangeListener)
            jatekTer.addPropertyChangeListener("selectedObject", evt -> {
                updateInfoPanel(infoPanel, jatekTer.getKijeloltObjektum());
            });

            // --- GameEngine
            GameEngine engine = new GameEngine(vilag, jatekTer, playerList, korView);
            jatekTer.setGameEngine(engine);

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

            Player aktualis = playerList.get(engine.getKorIndex()%playerList.size());
            if (aktualis.role.equalsIgnoreCase("gombász")) {
                korView.csakGombasznak();
            } else {
                korView.csakRovarasznak();
            }

            korView.getFonalVagasButton().addActionListener(e -> korView.csakKorVegeMarad());

            // Gombatest növesztés gomb
            korView.getGombatestNovesztButton().addActionListener(e -> {
                Tekton celTekton = engine.getKivalasztottCelTekton();
                if (celTekton != null) {
                    if (celTekton.isNohetGomba()) {
                        Gombafaj fajta = Gombafaj.KEK;
                        Gomba ujGomba = new Gomba(fajta, celTekton, celTekton.getX(), celTekton.getY());
                        vilag.addGomba(ujGomba);
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
                    g.sporaz();
                    korView.csakKorVegeMarad();
                } else {
                    JOptionPane.showMessageDialog(null, "Válassz ki egy gombát a spórázáshoz!");
                }
            });

            // Fonal növesztés
            korView.getFonalNovesztButton().addActionListener(e -> {
                Gomba g = engine.getKivalasztottGomba();
                if (g != null && engine.getKivalasztottCelTekton() != null) {
                    g.novesztUjFonal(engine.getKivalasztottCelTekton());
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
                    if (old != engine.getKivalasztottRovar().getHelyzet()) {
                        korView.csakKorVegeMarad();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Kérlek válassz ki egy rovart és egy cél tekton mezőt!");
                }
            });
        });
    }

    // ==== INFOPANEL: SEGÉDFÜGGVÉNY ====
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
        }
        panel.setPreferredSize(new Dimension(140, 768)); // << INFO: végig fix méret!
        panel.revalidate();
        panel.repaint();
    }
}
