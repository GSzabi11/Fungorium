package Fugorium_Model;

import java.util.Random;

public class BenitoHatasVisitor implements RovarVisitor{

    /**
     * Konstruktor
     */
    public BenitoHatasVisitor(){
        System.out.println("BenitoHatasVisitor constructor");
    }

    /** Implementálja a RovarVisitor függvényét és meglátogatja a paraméterként kapott rovaron
     * @param rovar Ezen látogatja meg a RovarVisitor függvényét
     */
    public void visit(Rovar rovar){
        if (rovar.getAllapotMap().get(RovarAllapot.BENITO) <= 0) {
            rovar.setAllapot(RovarAllapot.BENITO, (rand.nextInt(3) + 1));
            System.out.println("BenitoHatasVisitor: BENITO hatás alkalmazva a rovarra.");
        } else {
            System.out.println("BenitoHatasVisitor: A rovar már BENITO hatás alatt van.");
        } 
    }

}
