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

public class GameEngine {

    private final Vilag vilag;
    private final JatekTer jatekTer;
    private final Timer timer;
    //private final List<String> szerepek;
    //private final ArrayList<Player> players = new ArrayList<>();
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
            vilag.leptet();
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

        if (k % 5 == 0) {
            // másolatot készítünk arról, hol tartunk most
            List<Tekton> eredeti = new ArrayList<>(vilag.getMezok());
            Collections.shuffle(eredeti);
            // végigmegyünk minden negyedik tektonon
            for (int i = 0; i < eredeti.size(); i += 4) {
                Tekton t = eredeti.get(i);

                // generálunk egy új, egyedi ID-t
                int maxId = vilag.getMezok().stream()
                        .mapToInt(Tekton::getId)
                        .max().orElse(0);
                int ujId = maxId + 1;

                // az új tekton ugyanoda kerül, egy kis eltolással, hogy látható legyen
                Tekton uj = new Tekton(ujId, t.getX() + 20, t.getY() + 20);

                // modellben kettéhasítjuk:
                t.kettetor(uj);

                // és felvesszük a világba, így a JatekTer automatikusan
                // létrehozza hozzá a gombot/nézetet is
                vilag.addTekton(uj);
                System.out.println("[KOR " + k + "] Tekton T" + t.getId()
                        + " kettétörve → T" + uj.getId());
            }
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
            g.sporaTermel();
            g.fejlodik();
        }
        for (Tekton t : vilag.getMezok()){
            if(t.getSporakSzama() >= 3){
                t.setNohetGomba(true);
            }
        }
        deadGomba();

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
        List<Gomba> gombak = vilag.getGombak();
        List<Gomba> halottgombak = new ArrayList<>();
        for (Gomba g : gombak) {
            if (g.getSzint() >= 10) {
                halottgombak.add(g);
            }
        }


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
                sb.append("- ID: ").append(g.getFajta()).append("\n");
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
    }
}
