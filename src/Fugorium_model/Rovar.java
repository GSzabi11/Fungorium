package Fugorium_model;

import java.util.HashMap;

public class Rovar {
    
    private Tekton helyzet;
    private Rovarfaj rfajta;
    private int tapanyag;
    private double sebesseg;
    private HashMap<RovarAllapot, Integer> allapot;
    
    public Rovar(){
        System.out.println("Rovar konstructor");
    }

    public void accept(RovarVisitor visitor){
        System.out.println("Rovar.accept()");
    }

    public void mozog(Tekton tekton){
        System.out.println("Rovar.mozog()");
    }

    public void fogyaszt(Spora spora){
        System.out.println("Rovar.fogyaszt()");
    }

    public void fonalatVag(Gombafonal gombafonal){
        System.out.println("Rovar.fonalatVag()");
    }

    public void vanAllapot(RovarAllapot rovarallapot){
        System.out.println("Rovar.vanAllapot()");
    }

    public void csokkentAllapotIdotartam(RovarAllapot rovarallapot){
        System.out.println("Rovar.csokkentAllapotIdotartam()");
    }
}
