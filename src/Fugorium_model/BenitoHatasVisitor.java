package Fugorium_model;

public class BenitoHatasVisitor implements RovarVisitor{

    public BenitoHatasVisitor(){
        System.out.println("BenitoHatasVisitor constructor");
    }
    public void visit(Rovar rovar){
        System.out.println("BenitoHatasVisitor.visit()");
    }
}
