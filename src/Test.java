import DYNMathOptimisation.DYNConstraint;
import DYNMathOptimisation.MathOptimisation;

public class Test {


    public static void main(String[] args) throws Exception{
        // maximize  3x1 +2x2 +5x3
        // subject to :
/*
            x1 +2x2 +x3 <= 430
            3x2 +2x3    <= 460
            x1 + 4x2    <= 420
            x1 ,x2 ,x3 >= 0

        
*/
        MathOptimisation x = new MathOptimisation() ;
        x.simplexMaximization("(3)[x1](2)[x2](5)[x3]", new String []{
        "(1)[x1](2)[x2](1)[x3]<=430",
        "(3)[x1](0)[x2](2)[x3]<=460",
        "(1)[x1](4)[x2](0)[x3]<= 420"},
         new DYNConstraint("(1)[x1](2)[x2](3)[x3]>=0"));


/*
 *      Minimize:
 * Min  x1 - 3 x2 + 2x3 
 *      Subject to:
 *      
 *      3x1 - x2 +2x3 <= 7
 *      -2x1 +4x2   <= 12
 *      -4x1 + 3x2 + 8x3 <= 10
 *      x1,x2,x3>=0
 */
        x.simplexMinimisation("(1)[x1] (-3) [x2]  (2)[x3]", new String []{
            "(3)[x1] (-1) [x2] (+2)[x3] <= 7",
            "(-2)[x1] (+4)[x2] (0)[x3]   <= 12",
            "(-4)[x1] (3)x2 (8)[x3] <= 10"

        } , new DYNConstraint("(1)[x1](1)[x2](1)[x3]>=0"));


/*
 *      maximize using bigM:
 *     2 x1 -  2 x2 + 3x3
 *      Subject to:
 *      x1 -x2 +2x3 <= 8
 *      -5x1+5x2 +x3 <= 3
 *      3x1 - 3x2 +x3 >= 5
 *      x1, x2 ,x3 >=0
 */

        x.simplexMaximization("(2)[x1] (-2)[x2] (3)[x3]",new String[]{
            "(1)[x1] (-1)[x2] (+2)[x3] <= 8", 
            "(-5)[x1](+5)[x2] (+1)[x3] <= 3",
            "(3)[x1] (- 3)[x2] (+1)[x3] >= 5"
        }, new DYNConstraint("(1)[x1](1)[x2] >=0"));

/*
        minimize using bigM:

        35x1 +34 x2

        4x1 +3x2 >= 504
        5x1 + x2 >= 256
        2x1 +5x2 >= 420
        x1,x2>=0

 */
        x.simplexMinimisation("(35)[x1] (+34) [x2]", new String []{
            "(4)[x1] (+3)[x2] >= 504",
            "(5)[x1] (1)[x2] >= 256",
            "(2)[x1] (+5)[x2] >= 420"
        }, new DYNConstraint("(1)[x1](1)x2>=0"));


/*
        Another test
*/        

        x.simplexMinimisation("(1)[x1](3)[x2]" ,new String[]{
            "(3)[x1](1)[x2]<=5",
            "(1)[x1](1)[x2]>=3"
        } ,new DYNConstraint("(1)[x1](1)x2>=0"));




    }
}
