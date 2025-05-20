package Fungorium_Controller;

import Fugorium_Model.*;
import Fungorium_View.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import Fugorium_Model.RovarAllapot;

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
            infoPanel.setPreferredSize(new Dimension(140, 768)); // << kisebb sáv
            infoPanel.setMinimumSize(new Dimension(140, 768));
            infoPanel.setMaximumSize(new Dimension(140, 768));
            infoPanel.add(new JLabel("Nincs kijelölt objektum"));

            // Main layout
            JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, korView, jatekTer);
            mainSplit.setDividerLocation(200);

            JSplitPane fullSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainSplit, infoPanel);
            fullSplit.setDividerLocation(1260); // kb. a játéktérnél válassza szét
            fullSplit.setResizeWeight(1.0); // főleg a középső panel nőljön

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
                Gomba kivalasztottGomba = engine.getKivalasztottGomba(); // Kijelölt gomba lekérése
                if (celTekton != null && kivalasztottGomba != null) {
                    if (celTekton.isNohetGomba() && MozgasController.novezhetGomba(celTekton, kivalasztottGomba)) {
                        Gombafaj fajta = kivalasztottGomba.getFajta();
                        Gomba ujGomba = new Gomba(fajta, celTekton, celTekton.getX(), celTekton.getY());
                        vilag.addGomba(ujGomba);
                        korView.csakKorVegeMarad();
                        //engine.getKivalasztottCelTekton().clearSporak(engine.getKivalasztottCelTekton());
                        vilag.removeSpora(engine.getKivalasztottCelTekton(), engine.getKivalasztottCelTekton().getSporak());
                        System.out.println("[DEBUG] Új gomba növesztve a T" + celTekton.getId() + " tektonon.");
                    } else {
                        JOptionPane.showMessageDialog(korView,
                                "Nem lehet gombát növeszteni ezen a tektonon.",
                                "Hiba",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(korView,
                            "Nincs kijelölt tekton vagy gomba.",
                            "Hiba",
                            JOptionPane.ERROR_MESSAGE);
                }
            });


            // Spórázás
            korView.getSporazButton().addActionListener(e -> {
                Gomba g = engine.getKivalasztottGomba();
                System.out.println("Gomba megvan");
                if (g == null) {
                    JOptionPane.showMessageDialog(null,
                            "Válassz ki egy gombát a spórázáshoz!",
                            "Hiba",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // --- ide jön a térképen végigiterálás ---

                /*Tekton t1 = g.getTekton();
                System.out.println("Tekton megvan");
                if (t1 == null) {System.out.println("Szivas");}

                List<Tekton> szomszik = new ArrayList<>();
                szomszik = t1.getSzomszedok();
                if (szomszik.size() == 0) {System.out.println("Nincs szomszed");}*/

                Map<Tekton,List<Spora>> kiosztas = g.sporaz();
                for (var es : kiosztas.entrySet()) {
                    Tekton t = es.getKey();
                    for (Spora s : es.getValue()) {
                        vilag.addSpora(t, s);
                    }
                }
                jatekTer.repaint();

                // majd vissza a kör végét jelző állapotba
                korView.csakKorVegeMarad();
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
                        for (int i = 0; i < engine.getKivalasztottCelTekton().getSporakSzama(); i++){
                            engine.getKivalasztottRovar().fogyaszt(engine.getKivalasztottCelTekton().getSporak().getFirst());
                        }
                        vilag.removeSpora(engine.getKivalasztottCelTekton(), engine.getKivalasztottCelTekton().getSporak());
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
            panel.add(new JLabel("Szint: " + g.getSzint()));
            panel.add(new JLabel("Pozíció: (" + g.getX() + ", " + g.getY() + ")"));
            panel.add(new JLabel("Termelt spórák: " + g.getTermeltSporakSzama()));
        } else if (o instanceof Tekton t) {
            panel.add(new JLabel("Típus: Tekton"));
            panel.add(new JLabel("Azonosító: " + t.getId()));
            panel.add(new JLabel("Pozíció: (" + t.getX() + ", " + t.getY() + ")"));
            int sporaSzam = t.getSporak() != null ? t.getSporak().size() : 0;
            panel.add(new JLabel("Spórák száma: " + sporaSzam));

            panel.add(Box.createVerticalStrut(8));
            panel.add(new JLabel("Spórák listája:"));
            for (Spora s : t.getSporak()) {
                String nev;
                if (s instanceof BenitoSporaElement)         nev = "Bénító spóra";
                else if (s instanceof GyorsitoSporaElement)   nev = "Gyorsító spóra";
                else if (s instanceof LassitoSporaElement)    nev = "Lassító spóra";
                else if (s instanceof VagastGatloSporaElement)nev = "Vágásgátló spóra";
                else if (s instanceof RovarOsztodoSporaElement)nev = "Osztódó spóra";
                else                                          nev = s.getClass().getSimpleName();

                panel.add(new JLabel("  • " + nev));
            }

        } else if (o instanceof Rovar r) {
            panel.add(new JLabel("Típus: Rovar"));
            panel.add(new JLabel("Fajta: " + r.getFajta().toString()));
            panel.add(new JLabel("Pozíció: (" + r.getX() + ", " + r.getY() + ")"));
            panel.add(new JLabel("Tapanyag: " + r.getTapanyag()));

            panel.add(Box.createVerticalStrut(8));       // kis térköz
            panel.add(new JLabel("Állapotok:"));
            for (var entry : r.getAllapotMap().entrySet()) {
                RovarAllapot allapot = entry.getKey();
                int hatralevo = entry.getValue();
                panel.add(new JLabel("  • " + allapot + ": " + hatralevo + " kör"));
            }

        }
        panel.setPreferredSize(new Dimension(140, 768));
        panel.revalidate();
        panel.repaint();
        panel.setPreferredSize(new Dimension(140, 768));
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
