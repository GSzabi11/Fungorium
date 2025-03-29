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
        Tekton tekton = new Tekton(1);
        Gomba gomba = new Gomba(Gombafaj.KEK, tekton);
        Tekton ujTekton = null;
        for (int i = 0; i < k; i++) {
            ujTekton = new Tekton(2 + i);
            tekton.kettetor(ujTekton);
        }
        Gombafonal gombafonal = new Gombafonal(gomba, ujTekton);
        SporaFactory sporaFactory = new SporaFactory();
        Rovar rovar = new Rovar(Rovarfaj.CIAN, tekton);
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
        Tekton tekton = new Tekton(1);
        Tekton cel = new Tekton(2);
        Rovar rovar = new Rovar(Rovarfaj.CIAN, tekton);
        Gomba gomba = new Gomba(Gombafaj.KEK, tekton);
        Gombafonal gombafonal = new Gombafonal(gomba, cel);
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
        Tekton tekton = new Tekton(1);
        Gomba gomba = new Gomba(Gombafaj.KEK, tekton);
        Tekton tekton1 = new Tekton(2);
        tekton.hozzaadSzomszed(tekton1);
        tekton1.hozzaadSzomszed(tekton);
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
        Tekton tekton = new Tekton(1);
        Rovar rovar = new Rovar(Rovarfaj.CIAN, tekton);
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
        Tekton tekton = new Tekton(1);
        Rovar rovar = new Rovar(Rovarfaj.CIAN, tekton);
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
        Tekton tekton = new Tekton(1);
        Rovar rovar = new Rovar(Rovarfaj.CIAN, tekton);
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
        Tekton tekton = new Tekton(1);
        Rovar rovar = new Rovar(Rovarfaj.CIAN, tekton);
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
        Tekton tekton = new Tekton(1);
        Tekton tekton1 = new Tekton(2);
        Gomba gomba = new Gomba(Gombafaj.KEK, tekton);
        Gombafonal gombafonal = new Gombafonal(gomba, tekton1);
        
        gombafonal.probalGombatNoveszteni(tekton1);
    }

    /**
     * A rovar mozgásához tartozó teszt függvény
     * @param i  A lisában az i edik tekton kiválasztása
     * @param allapot A rovar képzeletbeli állapota
     */
    public void RovarMozgasa(int i, int allapot){
        Tekton tekton = new Tekton(1);
        Tekton tekton1 = new Tekton(2);
        Rovar rovar = new Rovar(Rovarfaj.CIAN, tekton);
        tekton.hozzaadSzomszed(tekton1);
        tekton1.hozzaadSzomszed(tekton);
        if(allapot == 0){
            rovar.mozog(tekton1);
        }
    }
}
