package Fugorium_Model;

public abstract class VagastGatloHatasVisitor implements RovarVisitor{
    
    /** 
     * Konstruktor
     */
    public VagastGatloHatasVisitor(){
        System.out.println("VagastGatloHatasVisitor constructor");
    }

    /** Implementálja a RovarVisitor függvényét és meglátogatja a paraméterként kapott rovaron
     * @param rovar Ezen látogatja meg a RovarVisitor függvényét
     */
    @Override
    public void visit(Rovar rovar){
        System.out.println("VagastGatloHatasVisitor.visit()");
    }
}
