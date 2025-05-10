package Fungorium_View;

import Fugorium_Model.Tekton;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

/**
 * A RajzoloTar egy regiszter, amely összekapcsolja a modellosztályokat
 * a hozzájuk tartozó megjelenítő (rajzoló) View osztályokkal.
 */
public class RajzoloTar {

    private final Map<Class<?>, Rajzolo> rajzolok = new HashMap<>();

    /**
     * Regisztrál egy rajzolót a megadott osztályhoz.
     *
     * @param clazz     a modellosztály, pl. Tekton.class
     * @param rajzolo   a rajzoló, ami ezt az osztályt tudja megjeleníteni
     */
    public void regisztral(Class<?> clazz, Rajzolo rajzolo) {
        rajzolok.put(clazz, rajzolo);
    }

    /**
     * Lekéri a megfelelő rajzolót a modell példányához.
     *
     * @param model a modell objektum
     * @return a hozzátartozó rajzoló
     * @throws IllegalArgumentException ha nincs regisztrált rajzoló
     */
    public Rajzolo getRajzolo(Object model) {
        Class<?> clazz = model.getClass();

        // 1. Pontos egyezés
        Rajzolo r = rajzolok.get(clazz);
        if (r != null) return r;

        // 2. Keresés a leszármazottak között
        for (Map.Entry<Class<?>, Rajzolo> entry : rajzolok.entrySet()) {
            if (entry.getKey().isAssignableFrom(clazz)) {
                return entry.getValue();
            }
        }

        throw new IllegalArgumentException("Nincs regisztrált rajzoló ehhez: " + clazz.getName());
    }

    /**
     * Kirajzolja a modell objektumot a megfelelő rajzolóval.
     *
     * @param g2    a grafikai kontextus
     * @param model a modell objektum
     */
    public void rajzol(Graphics2D g2, Object model) {
        Rajzolo rajzolo = getRajzolo(model);
        rajzolo.rajzol(g2, model);
    }

    public void register(Class<Tekton> tektonClass, TektonView tektonView) {
    }
}

