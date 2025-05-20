package Fungorium_Controller;

import Fugorium_Model.Gomba;
import Fugorium_Model.Rovar;
import Fugorium_Model.Tekton;
import Fungorium_View.JatekTer;
import Fungorium_View.KorView;
import Fungorium_View.Vilag;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameEngine {

    private final Vilag vilag;
    private final JatekTer jatekTer;
    private final Timer timer;
    private List<Gomba> halottgombak = new ArrayList<>();
    private final int winningScore=200;
    private int currentPlayerIndex = 0;
    private final List<Player> jatekosok;
    private int korIndex = 0;
    private Rovar kivalasztottRovar;
    private Tekton kivalasztottCelTekton;
    private Gomba kivalasztottGomba;
    private KorView korView;
    private int k;

    //private boolean rovaraszKor = true; // true = rovarász, false = gombász

    public GameEngine(Vilag vilag, JatekTer jatekTer, List<Player> jatekosok, KorView korView) {
        this.vilag = vilag;
        this.jatekTer = jatekTer;
        this.jatekosok = jatekosok;
        this.korIndex = 0;
        this.korView = korView;


        // Timer csak a repainthez és léptetéshez, de nem vált köröket
        this.timer = new Timer(1000 / 30, e -> {
            jatekTer.repaint();
        });
    }


    private void updateKorTulajdonosFelirat() {
        Player aktualis = jatekosok.get(korIndex);
        String felirat = aktualis.name + " (" + aktualis.role + ")";
        jatekTer.setKorTulajdonos(felirat);
        System.out.println("Köre van: " + felirat);
    }


    public void startGame() {
        vilag.initEntities();
        updateKorTulajdonosFelirat();
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void restart() {
        stop();
        startGame();
    }

    public int getKorIndex() {
        return korIndex;
    }

    /**
     * Ezt a metódust kell meghívni, amikor a játékos befejezi a lépését.
     */
    public void kovetkezoKor() {
        k++;

        if (k % 7 == 0) {
            List<Tekton> mezok = new ArrayList<>(vilag.getMezok());
            Collections.shuffle(mezok);
            int darabszam = 1;

            Random rnd = new Random();
            for (int i = 0; i < darabszam; i++) {
                Tekton t = mezok.get(i);

                // új id
                int maxId = vilag.getMezok().stream()
                        .mapToInt(Tekton::getId).max().orElse(0);
                // kiszorítási távolság
                int offset = 200;

                // véletlenszerű irány
                double angle = rnd.nextDouble() * 2 * Math.PI;
                int dx = (int)(offset * Math.cos(angle));
                int dy = (int)(offset * Math.sin(angle));

                // létrehozzuk az új tekton-t OFFSETEKKEL
                Tekton uj = new Tekton(maxId + 1,
                        t.getX() + dx,
                        t.getY() + dy);

                // modellbeli törés
                t.kettetor(uj);

                // világba + GUI
                vilag.addTekton(uj);
                System.out.println("[KOR " + k + "] Törve: T"
                        + t.getId() + " → T" + uj.getId()
                        + " (@ " + uj.getX() + "," + uj.getY() + ")");
            }

            JOptionPane.showMessageDialog(
                    null,
                    "Földrengés! Véletlenszerűen megtörtek néhány tekton-t.",
                    "Földrengés",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

        for (Rovar temp : vilag.getRovarok()){
            if (k % jatekosok.size() == 0)
            {
                temp.csokkentAllapotIdotartam();
            }
        }
        korIndex = (korIndex+1) % jatekosok.size();
        updateKorTulajdonosFelirat();

        updatePlayerButtons();

        for (Gomba g : vilag.getGombak()) {
            if (g.getSzint()==15){
                halottgombak.add(g);
            }
            g.sporaTermel();
            g.fejlodik();

        }

        if(!halottgombak.isEmpty()) {
            deadGomba();

        }

        for (Gomba g : halottgombak) {
            vilag.removeGomba(g);
        }

        List<Gomba> tempHalottGombak = new ArrayList<>(halottgombak);
        halottgombak.removeAll(tempHalottGombak);

        for (Tekton t : vilag.getMezok()){
            if(t.getSporakSzama() >= 3){
                t.setNohetGomba(true);
            }
        }

        Player p = jatekosok.get(currentPlayerIndex);

        // Meghívjuk a szerepkörhöz tartozó lépéslogikát:
        if (p.getRole() == "gombasz") {
            // Gombász-lépést futtató controller
//            SporaController.hatas( );
            p.addScore(5);
        } else {
            // Rovarász‐lépést futtató controller
            MozgasController.move(kivalasztottRovar, kivalasztottCelTekton);
            p.addScore(5);
        }

        // Ellenőrizzük a győzelmet
        if (p.getScore() >= winningScore) {
            Menu.victory(jatekosok);
            return;
        }

        // Következő játékos jön
        currentPlayerIndex = (currentPlayerIndex + 1) % jatekosok.size();


    }

    //Ezt majd használni kell a kiválsztott elem lenyomásakor
    public void setKivalasztottRovar(Rovar rovar) {
        this.kivalasztottRovar = rovar;
    }

    public void setKivalasztottCelTekton(Tekton tekton) {
        this.kivalasztottCelTekton = tekton;
    }

    public Rovar getKivalasztottRovar() {
        return kivalasztottRovar;
    }

    public Tekton getKivalasztottCelTekton() {
        return kivalasztottCelTekton;
    }

    public Gomba getKivalasztottGomba() {
        return kivalasztottGomba;
    }

    public void setKivalasztottGomba(Gomba gomba) {
        this.kivalasztottGomba = gomba;
    }

    private void updatePlayerButtons() {
        Player aktualis = jatekosok.get(korIndex);

        // Példa: ha van gombod a rovarász és gombász számára
        if (aktualis.role.equals("rovarász")) {
            korView.csakGombasznak();
        } else {
            korView.csakGombasznak();
        }
    }

    public void deadGomba() {

        JFrame frame = new JFrame("Halott gombák");
        StringBuilder sb = new StringBuilder();
        if (halottgombak.isEmpty()) {
            return;
        } else {

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(300, 300);
            frame.setLocationRelativeTo(null);
            sb.append("Halott gombák:\n");
            for (Gomba g : halottgombak) {
                sb.append("- SZIN: ").append(g.getFajta()).append("\n");
            }
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> frame.dispose());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(okButton, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        frame.setVisible(true);
        frame.repaint();
    }

}
