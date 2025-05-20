package Fugorium_Model;

import java.util.Random;

public abstract interface RovarVisitor {

    public Random rand = new Random();

    public void visit(Rovar rovar);
}
