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
        if (rovar.getAllapotMap().get(RovarAllapot.VAGASTGATLO) <= 0) {
            rovar.setAllapot(RovarAllapot.VAGASTGATLO, (rand.nextInt(3) + 1));
            System.out.println("VagastGatloHatasVisitor: Gyorsító hatás alkalmazva a rovarra.");
        } else {
            System.out.println("VagastGatloHatasVisitor: A rovar már Vagastgatló hatás alatt van.");
        }
    }
}
