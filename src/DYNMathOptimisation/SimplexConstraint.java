package DYNMathOptimisation;

public class SimplexConstraint extends Constraint{
    String basicVariables ;
    Double basicVariablesCoefficient ;
    public SimplexConstraint(String syntax , String basicVariables) {
        super(syntax);
        this.basicVariables = basicVariables ;
    }
    public SimplexConstraint(String syntax , String basicVariables ,Double basicVariablesCoefficient) {
        super(syntax);
        this.basicVariables = basicVariables ;
        this.basicVariablesCoefficient = basicVariablesCoefficient ;
    }

    public SimplexConstraint(Constraint origin , String basicVariables) {
        super(origin.originSyntax, origin.coeff, origin.variables, origin.condition, origin.secondMember) ;
            this.basicVariables = basicVariables ;
    }

    public void setCoeffSolutionInit(Double coeffS){
        this.basicVariablesCoefficient = coeffS ;
    }
    

}
