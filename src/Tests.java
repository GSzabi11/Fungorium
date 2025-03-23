import Fugorium_model.*;

import static Fugorium_model.RovarAllapot.*;

public class Tests {
    /**
     * Az program inicializációjának teszt függvénye.
     * @param k törések száma
     * @param s spórázáshoz szükséges spóraszám
     * @param sporaszam , A képzeletbeli spóraszám
     */
    public void InitTest(int k, int s, int sporaszam){
        Gomba gomba = new Gomba();
        Tekton tekton = new Tekton(1, "Standard", gomba);
        for(int i = 0; i<k; i++){
            tekton.kettetor();
        }
        Gombafonal gombafonal = new Gombafonal();
        SporaFactory sporaFactory = new SporaFactory();
        Rovar rovar = new Rovar();
        for(int i = 0; i<s; i++){
            gomba.sporaTermel();
        }
        if(sporaszam > 3) gomba.sporaz(); System.out.println("A teszt sikeres");
    }

    /**
     * A rovar gombafonal vágásának teszt függvénye
     * @param rovarallapot
     */
    public void RovarAtvagjaAGombafonalat(int rovarallapot) {
        Rovar rovar = new Rovar();
        Gombafonal gombafonal = new Gombafonal();
        if (rovarallapot == 0) rovar.fonalatVag(gombafonal);
        for (int i=0; i<3; i++){
            gombafonal.csokkentiEletidot();
        }
        if (gombafonal.eletido == 0){
            gombafonal.elpusztul();
            gombafonal.megszakad();
            System.out.println("A teszt sikeres");
        }

    }

    /**
     * A spóra szórás teszt függvénye
     * @param x A spóraszóráshoz szükséges sporák száma
     * @param sporaszam
     */
    public void sporaszoras(int x, int sporaszam){
        Gomba gomba = new Gomba();
        Tekton tekton = new Tekton(1, "Standard", gomba);
        Tekton tekton1 = new Tekton(2, "Standard", gomba);
        tekton.hozzaadSzomszed();
        if(sporaszam >= x){
            if(gomba.szint == 1){
                gomba.sporaz();
            }
            else if (gomba.szint == 2){
                gomba.sporaz();
                gomba.sporaz();
            }
        }
    }

    /**
     *  Teszt függvény a Rovar bénító spóra fogyasztásához
     */
    public void RovarBenitoSporatFogyaszt(){
        Rovar rovar = new Rovar();
        BenitoSporaElement spora = new BenitoSporaElement();
        rovar.fogyaszt(spora);
        HatastAlkalmazVisitor visitor = new HatastAlkalmazVisitor(rovar);
        spora.accept(visitor);
        BenitoHatasVisitor benitovisitor = new BenitoHatasVisitor();
        visitor.visit(spora);
        benitovisitor.visit(rovar);
        spora.alkalmazHatast(rovar, BENITO, 1);
        System.out.println("Benito");
    }

    /**
     *  Teszt függvény a Rovar gyorsító spóra fogyasztásához
     */
    public void RovarGyorsitoSporatFogyaszt(){
        Rovar rovar = new Rovar();
        GyorsitoSporaElement spora = new GyorsitoSporaElement();
        HatastAlkalmazVisitor visitor = new HatastAlkalmazVisitor(rovar);
        GyorsitoHatasVisitor gyoritovisitor = new GyorsitoHatasVisitor();
        rovar.fogyaszt(spora);
        spora.accept(visitor);
        visitor.visit(spora);
        gyoritovisitor.visit(rovar);
        spora.alkalmazHatast(rovar, GYORSITO, 1);
        System.out.println("Gyorsito");
    }

    /**
     *  Teszt függvény a Rovar lassító spóra fogyasztásához
     */
    public void RovarLassitoSporatFogyaszt(){
        Rovar rovar = new Rovar();
        LassitoSporaElement spora = new LassitoSporaElement();
        HatastAlkalmazVisitor visitor = new HatastAlkalmazVisitor(rovar);

        rovar.fogyaszt(spora);
        spora.accept(visitor);
        visitor.visit(spora);
        LassitoHatasVisitor lassitovisitor = new LassitoHatasVisitor();
        lassitovisitor.visit(rovar);
        spora.alkalmazHatast(rovar, LASSITO, 1);
        System.out.println("lassito");
    }

    /**
     *  Teszt függvény a Rovar vágásgátló spóra fogyasztásához
     */
    public void RovarVagasGatloSporatFogyaszt(){
        Rovar rovar = new Rovar();
        VagastGatloSporaElement spora = new VagastGatloSporaElement();
        HatastAlkalmazVisitor visitor = new HatastAlkalmazVisitor(rovar);
        VagastGatloHatasVisitor vagasgatlovisitor = new VagastGatloHatasVisitor();
        rovar.fogyaszt(spora);
        spora.accept(visitor);

        visitor.visit(spora);
        vagasgatlovisitor.visit(rovar);
        spora.alkalmazHatast(rovar,VAGASTGATLO, 1);
        System.out.println("vagasgatlo");
    }

    /**
     *  A gombatest növesztéséhez tesz függvény
     */
    public void GombatestNovesztes(){
        Gomba gomba = new Gomba();
        Gombafonal gombafonal = new Gombafonal();
        Tekton tekton = new Tekton(1, "Standard", gomba);
        Tekton tekton1 = new Tekton(2, "Standard", null);
        gombafonal.probalGombatNoveszteni(tekton1);
    }

    /**
     * A rovar mozgásához tartozó teszt függvény
     * @param i  A lisában az i edik tekton kiválasztása
     * @param allapot A rovar képzeletbeli állapota
     */
    public void RovarMozgasa(int i, int allapot){
        Rovar rovar = new Rovar();
        Tekton tekton = new Tekton(1, "Standard", null);
        Tekton tekton1 = new Tekton(2, "Standard", null);
        tekton.hozzaadSzomszed();
        if(allapot == 0){
            rovar.mozog(tekton1);
        }
    }
}
