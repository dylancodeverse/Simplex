import DYNMathOptimisation.Constraint;
import DYNMathOptimisation.Simplex;

public class Test_Rotsy {
    public static void main(String[] args) throws Exception{
       
        Simplex m = new Simplex();
        m.simplexMinimisation( "(30)[x](-50)[y]", new String []{
            "(3)[x](5)[y]<=1800",
            "(1)[x](0)[y]<=400",
            "(0)[x](1)[y]<=600"
        }, new Constraint("(1)[x](1)[y]>=0"));
        
    }   
}
