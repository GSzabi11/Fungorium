import Fugorium_model.*;

public class Tests {
    public void InitTest(int k, int s){
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
        if(gomba.getSporaszam() > 3) gomba.sporaz(); System.out.println("A teszt sikeres");
    }
    public void RovarAtvagjaAGombafonalat() {
        Rovar rovar = new Rovar();
        Gombafonal gombafonal = new Gombafonal();
        if (rovar.allapot.get(VAGASGATLO) == 0) rovar.fonalatVag();
        for (int i=0; i<3; i++){
            gombafonal.csokkentiEletidot();
        }
        if (gombafonal.eletido == 0){
            gombafonal.elpusztul();
            gombafonal.megszakad();
            System.out.println("A teszt sikeres");
        }

    }

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

    public void RovarBenitoSporatFogyaszt(){
        Rovar rovar = new Rovar();
        BenitoSporaElement spora = new BenitoSporaElement();
        rovar.fogyaszt(spora);
        spora.accept(visitor);
        HatastAlkalmazVisitor visitor = new HatastAlkalmazVisitor(rovar);
        visitor.visit(benito);
        BenitoHatasVisitor benitovisitor = new BenitoHatasVisitor();
        benitovisitor.visit(rovar);
        visitor.alkalmazHatast(rovar, allapot, duration);
        if(rovar.getAllapot() == "benito") System.out.println("Benito");
    }

    public void RovarGyorsitoSporatFogyaszt(){
        Rovar rovar = new Rovar();
        GyorsitoSporaElement spora = new GyorsitoSporaElement();
        rovar.fogyaszt(spora);
        spora.accept(visitor);
        HatastAlkalmazVisitor visitor = new HatastAlkalmazVisitor(rovar);
        visitor.visit(gyorsito);
        GyorsitoHatasVisitor gyoritovisitor = new GyorsitoHatasVisitor();
        gyoritovisitor.visit(rovar);
        visitor.alkalmazHatast(rovar, allapot, duration);
        if(rovar.getAllapot() == "Gyorsito") System.out.println("Gyorsito");
    }
    public void RovarLassitoSporatFogyaszt(){
        Rovar rovar = new Rovar();
        LassitoSporaElement spora = new LassitoSporaElement();
        rovar.fogyaszt(spora);
        spora.accept(visitor);
        HatastAlkalmazVisitor visitor = new HatastAlkalmazVisitor(rovar);
        visitor.visit(lassito);
        LassitoHatasVisitor lassitovisitor = new LassitoHatasVisitor();
        lassitovisitor.visit(rovar);
        visitor.alkalmazHatast(rovar, allapot, duration);
        if(rovar.getAllapot() == "lassito") System.out.println("lassito");
    }
    public void RovarLassitoSporatFogyaszt(){
        Rovar rovar = new Rovar();
        VagastGatloSporaElement spora = new VagastGatloSporaElement();
        rovar.fogyaszt(spora);
        spora.accept(visitor);
        HatastAlkalmazVisitor visitor = new HatastAlkalmazVisitor(rovar);
        visitor.visit(lassito);
        VagasGatloHatasVisitor vagasgatlovisitor = new VagasGatloHatasVisitor();
        vagasgatlovisitor.visit(rovar);
        visitor.alkalmazHatast(rovar, allapot, duration);
        if(rovar.getAllapot() == "vagasgatlo") System.out.println("vagasgatlo");
    }
    public void GombatestNovesztes(){
        Gomba gomba = new Gomba();
        Gombafonal gombafonal = new Gombafonal();
        Tekton tekton = new Tekton(1, "Standard", gomba);
        Tekton tekton1 = new Tekton(2, "Standard", null);
        gombafonal.probalGombatNoveszteni(tekton1);
    }

    public void RovarMozgasa(int i){
        Rovar rovar = new Rovar();
        Tekton tekton = new Tekton(1, "Standard", null);
        Tekton tekton1 = new Tekton(2, "Standard", null);
        tekton.hozzaadSzomszed(tekton1);
        if(roval.allapot.get(BENITO) == 0){
            rovar.mozog(tekton.getSzomszedok().get(i));
        }
    }
}
