package Fugorium_Model;

public abstract class LassitoHatasVisitor implements RovarVisitor{

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
        if (rovar.getAllapotMap().get(RovarAllapot.LASSITO) <= 0) {
            rovar.setAllapot(RovarAllapot.LASSITO, (rand.nextInt(3) + 1));
            System.out.println("LassítóHatasVisitor: Lasssitó hatás alkalmazva a rovarra.");
        } else {
            System.out.println("LassítóHatasVisitor: A rovar már Lassító hatás alatt van.");
        }
    }
}
