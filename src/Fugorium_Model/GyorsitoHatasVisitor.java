package Fugorium_Model;

public abstract class GyorsitoHatasVisitor implements RovarVisitor{

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
            rovar.setAllapot(RovarAllapot.GYORSITO, (rand.nextInt(3) + 1));
            System.out.println("BenitoHatasVisitor: Gyorsító hatás alkalmazva a rovarra.");
        } else {
            System.out.println("BenitoHatasVisitor: A rovar már Gyorsító hatás alatt van.");
        } 
    }
}
