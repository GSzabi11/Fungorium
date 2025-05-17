package Fugorium_Model;

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
            rovar.setAllapot(RovarAllapot.BENITO, 3);
            System.out.println("BenitoHatasVisitor: BENITO hatás alkalmazva a rovarra 3 körre.");
        } else {
            System.out.println("BenitoHatasVisitor: A rovar már BENITO hatás alatt van.");
        } 
    }

}
