package Fugorium_model;

public class VagastGatloHatasVisitor implements RovarVisitor{
    
    public VagastGatloHatasVisitor(){
        System.out.println("VagastGatloHatasVisitor constructor");
    }
    public void visit(Rovar rovar){
        System.out.println("VagastGatloHatasVisitor.visit()");
    }
}
