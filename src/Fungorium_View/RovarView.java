package Fungorium_View;

import Fugorium_Model.Rovar;
import Fugorium_Model.Tekton;
import java.awt.*;

public class RovarView extends Sprite implements Rajzolo {
    public RovarView(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
    }

@Override
public void rajzol(Graphics2D g2, Object model) {
    if (!(model instanceof Rovar rovar)) {
        System.out.println("RovarView: rossz típusú modell objektum.");
        return;
    }

    // Pozíció és méret beállítása a rovar aktuális helyzete alapján
    Tekton helyzet = rovar.getHelyzet();
    if (helyzet == null) {
        System.out.println("RovarView: a rovar nem rendelkezik érvényes helyzettel.");
        return;
    }

    // A sprite pozícióját a tekton koordinátái alapján állítjuk be
    this.x = helyzet.getId() * 20;  // (csak példa, érdemes tényleges koordinátákat használni)
    this.y = helyzet.getId() * 20;  // (detto)

    // Kirajzolás a Graphics2D felületre
    draw(g2);
}
}