package Szabi;

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
    @Override
    public void visit(Rovar rovar){
        System.out.println("BenitoHatasVisitor.visit()");
    }
}
