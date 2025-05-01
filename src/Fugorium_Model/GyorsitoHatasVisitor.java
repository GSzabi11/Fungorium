package Fugorium_Model;

public class GyorsitoHatasVisitor implements RovarVisitor{

    /**
     * Konstruktor
     */
    public GyorsitoHatasVisitor(){
        System.out.println("GyorsitoHatasVisitor constructor");
    }

    /** Implementálja a RovarVisitor függvényét és meglátogatja a paraméterként kapott rovaron
     * @param rovar Ezen látogatja meg a RovarVisitor függvényét
     */
    @Override
    public void visit(Rovar rovar){
        if (rovar.getAllapotMap().get(RovarAllapot.GYORSITO) <= 0) {
            rovar.setAllapot(RovarAllapot.GYORSITO, 3);
            System.out.println("BenitoHatasVisitor: BENITO hatás alkalmazva a rovarra 3 körre.");
        } else {
            System.out.println("BenitoHatasVisitor: A rovar már BENITO hatás alatt van.");
        } 
    }
}
