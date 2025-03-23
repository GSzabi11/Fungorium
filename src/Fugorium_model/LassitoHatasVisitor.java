package Fugorium_model;

public class LassitoHatasVisitor implements RovarVisitor{

    /**
     * Konstruktor
     */
    public LassitoHatasVisitor(){
        System.out.println("LassitoHatasVisitor constructor");
    }

    /** Implementálja a RovarVisitor függvényét és meglátogatja a paraméterként kapott rovaron
     * @param rovar Ezen látogatja meg a RovarVisitor függvényét
     */
    @Override
    public void visit(Rovar rovar){
        System.out.println("LassitoHatasVisitor.visit()");
    }
}
