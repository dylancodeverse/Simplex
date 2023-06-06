package DYNMathOptimisation;

public class DynSimplexConstraint extends DYNConstraint{
    String solutionInit ;
    Double coeffSolutionInit ;
    public DynSimplexConstraint(String syntax , String solutionInit) {
        super(syntax);
        this.solutionInit = solutionInit ;
    }
    public DynSimplexConstraint(String syntax , String solutionInit ,Double coeffSolutionInit) {
        super(syntax);
        this.solutionInit = solutionInit ;
        this.coeffSolutionInit = coeffSolutionInit ;
    }

    public DynSimplexConstraint(DYNConstraint origin , String solutionInit) {
        super(origin.originSyntax, origin.coeff, origin.decisions, origin.condition, origin.limit) ;
            this.solutionInit = solutionInit ;
    }

    public void setCoeffSolutionInit(Double coeffS){
        this.coeffSolutionInit = coeffS ;
    }
    

}
