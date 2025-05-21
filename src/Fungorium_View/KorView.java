package Fungorium_View;

import Fungorium_Controller.Player;

import javax.swing.*;
import java.awt.*;

public class KorView extends JPanel {

    JButton gombatestNoveszt;
    JButton sporaz;
    JButton fonalNoveszt;
    JButton mozgas;
    JButton fonalVagas;
    public JButton korVege;

    public KorView() {
        setLayout(new GridLayout(0, 1));
        gombatestNoveszt = new JButton("Gombatest növesztése");
        sporaz = new JButton("Spóraszórás");
        fonalNoveszt = new JButton("Gombafonal növesztés");
        mozgas = new JButton("Mozgás a tektonra");
        fonalVagas = new JButton("Fonal vágása");
        korVege = new JButton("Kör vége");

        add(gombatestNoveszt);
        add(sporaz);
        add(fonalNoveszt);
        add(mozgas);
        add(fonalVagas);
        add(korVege);

    }

    public void csakGombasznak() {
        gombatestNoveszt.setVisible(true);
        sporaz.setVisible(true);
        fonalNoveszt.setVisible(true);
        mozgas.setVisible(false);
        fonalVagas.setVisible(false);
    }

    public void csakKorVegeMarad() {
        gombatestNoveszt.setEnabled(false);
        sporaz.setEnabled(false);
        fonalNoveszt.setEnabled(false);
        mozgas.setEnabled(false);
        fonalVagas.setEnabled(false);
        korVege.setEnabled(true); // ez marad aktív
    }

    public void csakRovarasznak() {
        gombatestNoveszt.setVisible(false);
        sporaz.setVisible(false);
        fonalNoveszt.setVisible(false);
        mozgas.setVisible(true);
        fonalVagas.setVisible(true);
    }

    public JButton getFonalVagasButton() {
        return fonalVagas;
    }

    public JButton getGombatestNovesztButton() {
        return gombatestNoveszt;
    }

    public JButton getSporazButton() { return sporaz;}

    public JButton getFonalNovesztButton() {
        return fonalNoveszt;
    }

    public JButton getMozgasButton() {
        return mozgas;
    }

    public void resetAllButtons() {
        gombatestNoveszt.setEnabled(true);
        sporaz.setEnabled(true);
        fonalNoveszt.setEnabled(true);
        mozgas.setEnabled(true);
        fonalVagas.setEnabled(true);
        korVege.setEnabled(true);
    }

    public void mindentLetilt() {
        gombatestNoveszt.setEnabled(false);
        sporaz.setEnabled(false);
        fonalNoveszt.setEnabled(false);
        mozgas.setEnabled(false);
        fonalVagas.setEnabled(false);
        korVege.setEnabled(false);
    }


}
