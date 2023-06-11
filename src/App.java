import DYNMathOptimisation.DYNConstraint;
import DYNMathOptimisation.MathOptimisation;

public class App {
    public static void main(String[] args) throws Exception {
        // DYNConstraint equation = new DYNConstraint("(2)[x][1](y)[3](z)");
        // String [] multiConstraintsForm = new String []{"(1)[x](1)[y](2)[z]<=5" ,"(2)[x](3)[y](4)[z]<=2","(5)[x](2)[y](-2)[z]<=3"};
        // DYNConstraint decision = new DYNConstraint("[x][y][z]>=0");
        // MathOptimisation x= new MathOptimisation();
        // x.simplex("(2)[x](1)[y](3)[z]", multiConstraintsForm, decision);
        // System.out.println(x);


        MathOptimisation x = new MathOptimisation() ;
        x.simplexMaximisation("(1)[x](-1)[y](3)[z]", new String []{"(1)[x](1)[y](0)[z]<=20","(1)[x](0)[y](1)[z]=5","(0)[x](1)[y](1)[z]>=10"}, new DYNConstraint("[x][y][z]>=0"));

        MathOptimisation y = new MathOptimisation() ;
        y.simplexMaximisation("(3)[x1](4)[x2](1)[x3]",new String []{"(1)[x1](2)[x2](2)[x3]<="+(8.0/3.0) ,"(1)[x1](2)[x2](3)[x3]>="+(7.0/3.0)}, new DYNConstraint("[x1][x2][x3]") );
        // System.out.println("hbh");
    }
}
