package Szabi;

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
        System.out.println("GyorsitoHatasVisitor.visit()");
    }
}
