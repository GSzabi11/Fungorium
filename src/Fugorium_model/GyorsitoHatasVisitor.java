package Fugorium_model;

public class GyorsitoHatasVisitor implements RovarVisitor{

    public GyorsitoHatasVisitor(){
        System.out.println("GyorsitoHatasVisitor constructor");
    }
    public void visit(Rovar rovar){
        System.out.println("GyorsitoHatasVisitor.visit()");
    }
}
