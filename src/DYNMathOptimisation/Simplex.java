package DYNMathOptimisation;

import java.util.ArrayList;

public class Simplex {
    Constraint equation ;
    SimplexConstraint [] matrix ;
    Constraint decisionConstraint;


    public void simplexMaximization(String equationSyntax , String [] multiconstraintsFormat , Constraint decisionCondition) throws Exception{
        if(isBigMform(multiconstraintsFormat)) bigmMaximisation(equationSyntax,multiconstraintsFormat,decisionCondition) ;
        else simplexInfMaximization(equationSyntax, multiconstraintsFormat, decisionCondition);
    }   
    public void simplexMinimisation(String equationSyntax, String [] multiconstraintsFormat, Constraint decisionCondition)throws Exception{
        if(isBigMform(multiconstraintsFormat)) bigmMinimisation(equationSyntax,multiconstraintsFormat,decisionCondition) ;
        else simplexInfMinimalization(equationSyntax, multiconstraintsFormat, decisionCondition);
    }

    protected boolean isBigMform(String [] multiConstraintsForm){
        for (int i = 0; i < multiConstraintsForm.length; i++) {
            if(!multiConstraintsForm[i].contains("<")) return true;
        }
        return false;
    }

    protected void simplexInfMaximization(String equationSyntax , String [] multiconstraintsFormat , Constraint decisionCondition) throws Exception{
        setEquation(equationSyntax, multiconstraintsFormat.length);
        multiconstraintsFormat = formatSyntaxForMatrix(multiconstraintsFormat) ;
        setMatrix(multiconstraintsFormat);
        // mety ho maro ihany nefa ity fa aleo alony amzao
        this.decisionConstraint = decisionCondition ;
        while(!equation.allCoeffZeroOrInf()){
            // jerena ny coefficient max @ ilay equation
            int maxPosition = equation.maxPositionCoeff();
            // alaina izay sup a 0 @ iny colomne iny
            Double [] sup0= superiorToZero(maxPosition) ;
            if(counterOfDouble(sup0) ==0) throw new Exception("No solution found") ;
            // jerena indray ny minimum ( base / coeff) : pivot
            int pivotPositionY = pivotPosition(sup0,0) ;
            // ovaina ilay ery amn sisiny droite iny
                matrix[pivotPositionY].basicVariables = equation.variables[maxPosition];
            // transformation de la ligne du pivot (sur pivot daholo)
            matrix[pivotPositionY].setLineDivision(matrix[pivotPositionY].coeff[maxPosition]); 
            // transformation de chaque ligne bas et haut tel que : Ln =Ln - (Ln.coeff[maxPosition])
            doSimplexLineTransformation( pivotPositionY, maxPosition);
        }
        equation.secondMember*=-1;

    }
    protected void simplexInfMinimalization(String equationSyntax, String [] multiconstraintsFormat, Constraint decisionCondition) throws Exception{
        setEquation(equationSyntax, multiconstraintsFormat.length);
        multiconstraintsFormat = formatSyntaxForMatrix(multiconstraintsFormat) ;
        setMatrix(multiconstraintsFormat);
        // mety ho maro ihany nefa ity fa aleo alony amzao
        this.decisionConstraint = decisionCondition ;
        while(! equation.allCoeffZeroOrSup()){
            // jerena ny coefficient min @ ilay equation
            int minPosition = equation.minPositionCoeff();
            // alaina izay sup a 0 @ iny colomne iny
            Double [] sup0= superiorToZero(minPosition) ;
            if(counterOfDouble(sup0) ==0) throw new Exception("No solution found") ;
            // jerena indray ny minimum ( base / coeff) : pivot
            int pivotPositionY = pivotPosition(sup0,0) ;
            // ovaina ilay ery amn sisiny droite iny
                matrix[pivotPositionY].basicVariables = equation.variables[minPosition];
            // transformation de la ligne du pivot (sur pivot daholo)
            matrix[pivotPositionY].setLineDivision(matrix[pivotPositionY].coeff[minPosition]); 
            // transformation de chaque ligne bas et haut tel que : Ln =Ln - (Ln.coeff[maxPosition])
            doSimplexLineTransformation( pivotPositionY, minPosition);

        }
    }
    protected void bigmMaximisation(String equationSyntax , String [] multiconstraintsFormat , Constraint decisionCondition) throws Exception{
        setMatrixBigM( equationSyntax ,multiconstraintsFormat, -1);
        this.decisionConstraint = decisionCondition ;
        Double [] refDecision = new Double [matrix[0].coeff.length];
        refDecision = getSumMatrixCoeff();
        while (!allCoeffInfOrZero(refDecision)) {
            // jerena ny coefficient max @ ilay refDecision
            int maxPosition = maxPositionCoeff(refDecision);
            // alaina izay sup a 0 @ iny colomne iny
            Double [] sup0= superiorToZero(maxPosition) ;
            if(counterOfDouble(sup0) ==0) throw new Exception("No Solution found") ;
            // jerena indray ny minimum ( base / coeff) : pivot
            int pivotPositionY = pivotPosition(sup0,0) ;
            // ovaina ilay ery amn sisiny droite iny
                matrix[pivotPositionY].basicVariables = equation.variables[maxPosition];
                // lasa soloina ihany koa ilay coeffSolutionInit
                matrix[pivotPositionY].setCoeffSolutionInit(equation.coeff[maxPosition]);
            // transformation de la ligne du pivot (sur pivot daholo)
            matrix[pivotPositionY].setLineDivision(matrix[pivotPositionY].coeff[maxPosition]); 
            // transformation de chaque ligne bas et haut tel que : Ln =Ln - (Ln.coeff[maxPosition])
            doSimplexLineTransformationBigM(pivotPositionY, maxPosition);

            refDecision = getSumMatrixCoeff() ;
        }
        // manome anle reponse farany anle maximization:
        equation.secondMember = 0.;
        for (int i = 0; i < matrix.length; i++) {
            equation.secondMember = equation.secondMember + matrix[i].secondMember*matrix[i].basicVariablesCoefficient;
        }
        equation.secondMember*=-1;


    }
    protected void bigmMinimisation(String equationSyntax , String[] multiconstraintsFormat ,Constraint decisionCondition) throws Exception{
        setMatrixBigM( equationSyntax ,multiconstraintsFormat, 1);
        this.decisionConstraint = decisionCondition ;
        Double [] refDecision = new Double [matrix[0].coeff.length];
        refDecision = getSumMatrixCoeff();
        while (!allCoeffSupOrZero(refDecision)) {
            // jerena ny coefficient min @ ilay refDecision
            int minPosition = minPositionCoeff(refDecision);
            // alaina izay sup a 0 @ iny colomne iny
            Double [] sup0= superiorToZero(minPosition) ;
            if(counterOfDouble(sup0) ==0) throw new Exception("No Solution found") ; ;
            // jerena indray ny minimum ( base / coeff) : pivot
            int pivotPositionY = pivotPosition(sup0,0) ;
            // ovaina ilay ery amn sisiny droite iny
                matrix[pivotPositionY].basicVariables = equation.variables[minPosition];
                // lasa soloina ihany koa ilay coeffSolutionInit
                matrix[pivotPositionY].setCoeffSolutionInit(equation.coeff[minPosition]);
            // transformation de la ligne du pivot (sur pivot daholo)
            matrix[pivotPositionY].setLineDivision(matrix[pivotPositionY].coeff[minPosition]); 
            // transformation de chaque ligne bas et haut tel que : Ln =Ln - (Ln.coeff[minPosition])
            doSimplexLineTransformationBigM(pivotPositionY, minPosition);

            refDecision = getSumMatrixCoeff() ;
        }
        equation.secondMember = 0.;
        for (int i = 0; i < matrix.length; i++) {
            equation.secondMember = equation.secondMember + matrix[i].secondMember*matrix[i].basicVariablesCoefficient;
        }
        
    }
    protected Double [] getSumMatrixCoeff(){
        Double [] sum = new Double [matrix[0].coeff.length];
        for (int i = 0; i < sum.length; i++) {
            sum[i] =-1.*equation.coeff[i] ;
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < sum.length; j++) {
                sum[j] =( sum[j]+matrix[i].coeff[j]*matrix[i].basicVariablesCoefficient);  
            }
        }        
        for (int i = 0; i < sum.length; i++) {
            sum[i]*=-1;
        }
        

        return sum ;
    }

    protected void setMatrixBigM(String equationOrigin , String [] form , int coeff){
        int bigM =  coeff*99999 ;
        ArrayList<String> addedV = new ArrayList<String>() ;
        // add all variables and stock
        String ecart ="[s" ,artificial ="[v" ,ecartMin = "[min" ;
        int ecartindice=0 , artificialIndice =0  ,ecartMinIndice =0;

        for (int i = 0; i < form.length; i++) {
            if (form[i].contains("<")) {
                form[i]=form[i].substring(0,form[i].lastIndexOf("]")+1)+             
                   "("+1+")"+ecart +ecartindice+"]"+ form[i].substring(form[i].lastIndexOf("]")+1);
                   addedV.add(ecart +ecartindice+"]");
                   ecartindice ++ ;
            }else if (form[i].contains(">")) {
                form[i]=form[i].substring(0,form[i].lastIndexOf("]")+1)+             
                   "("+1+")"+artificial +artificialIndice+"]"+ "("+-1+")"+ecartMin+ecartMinIndice+"]"+ form[i].substring(form[i].lastIndexOf("]")+1);
                   addedV.add(artificial +artificialIndice+"]");
                   addedV.add(ecartMin+ecartMinIndice+"]") ;
                   ecartMinIndice++;
                   artificialIndice ++ ;
            }else{
                form[i]=form[i].substring(0,form[i].lastIndexOf("]")+1)+             
                   "("+1+")"+artificial +artificialIndice+"]"+ form[i].substring(form[i].lastIndexOf("]")+1);
                    addedV.add(artificial +artificialIndice+"]") ;
                    artificialIndice++;            
            }
        }
        matrix = new SimplexConstraint[form.length];
        for (int i = 0; i < form.length; i++) {
            form[i] = fillTheMatrixBigM(form[i], addedV ,i ,bigM) ;
        }
        for (int i = 0; i < addedV.size(); i++) {
            if(!addedV.get(i).contains("v"))
            equationOrigin = equationOrigin+("(0)"+addedV.get(i)) ; 
            else equationOrigin = equationOrigin+ "("+bigM+")"+addedV.get(i) ;
        }
        equation = new Constraint(equationOrigin+"=0");

    }
    protected String fillTheMatrixBigM(String form , ArrayList<String> added ,int a ,int bigM){
        String lastIndex = "(";
        String solutionInit="";
        Double coeffSolutionInit =0. ;
        if (form.contains(">")) {
            lastIndex= "(1)[v" ;
        }
        if(form.contains("v")){
            coeffSolutionInit = bigM*1. ;
        }
        boolean s= false;
        for (int i = 0; i < added.size(); i++) {
            if (!form.contains(added.get(i))){
                if (!s) {
                    form = form.substring(0, form.lastIndexOf(lastIndex)) +"(0)"+added.get(i)+
                            form.substring(form.lastIndexOf(lastIndex)) ;
                }else{
                    form = form.substring(0, form.lastIndexOf("]")+1) + "(0)"+added.get(i)+
                            form.substring( form.lastIndexOf("]")+1) ;
                }
            }else{
                if (!s) {
                    solutionInit = added.get(i) ;
                    s= true ;
                }
            }
        }
        matrix[a]= new SimplexConstraint(form, solutionInit ,coeffSolutionInit) ;
        return form ;
    }

    protected void setEquation(String equation , int addColumn) {
        for (int i = 0; i < addColumn; i++) {
            equation = equation+"("+0+")[s"+i+"]";
        }

        this.equation = new Constraint(equation+"=0") ;
    }

    protected void setMatrix(String [] m){
        int numberOfLine = m.length ;
        matrix = new SimplexConstraint[numberOfLine];
        for (int i = 0; i < numberOfLine; i++) {
            matrix[i]= new SimplexConstraint(m[i],"s"+i) ;
        }
    }
    protected String [] formatSyntaxForMatrix(String [] m){
        String [] ls = new String [m.length]; 
        ls [0] ="(1)"; 
        for (int i = 1; i < m.length; i++) {
            ls[i] = "(0)";
        }
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < ls.length; j++) {
                m[i] = m[i].substring(0, m[i].lastIndexOf("]")+1)+ls[j]+"[s"+j+"]" + m[i].substring(m[i].lastIndexOf("]")+1);
            }
            try {
                ls[i+1]="(1)";
                ls[i]="(0)";
            } catch (Exception e) {
                return m ;
            }
        }
        return m ;
    }

    protected Double [] superiorToZero(int maxPosition){
        ArrayList<Double> ls = new ArrayList<Double>();
        for (int i = 0; i < matrix.length; i++) {
            if(matrix[i].coeff[maxPosition]>0) ls.add(matrix[i].coeff[maxPosition]);
            else ls.add(null);
        }
        return ls.toArray(new Double [ls.size()]);
    }

    protected int pivotPosition(Double [] ls ,int begin){
        int minPosition = begin;
        Double min =0. ;
        try {
             min =  matrix[0].secondMember/ ls[begin] ;
        } catch (Exception e) {
            return pivotPosition(ls, begin+1);
        }
        for (int i = 1; i < ls.length; i++) {
            if(ls[i]== null) continue ;
            Double temp = matrix[i].secondMember / ls[i] ;
            if ( temp < min) {
                min = temp;
                minPosition = i;
            }
        }
        return minPosition;
    }
    protected void doSimplexLineTransformationBigM(int pivotPosition ,int maxPosition){
        for (int i = 0; i < matrix.length; i++) {
            if(i!= pivotPosition && matrix[i].coeff[maxPosition]!=0)
            matrix[i].setLineMinus(matrix[i].coeff[maxPosition],matrix[pivotPosition]);
        }
    }

    protected void doSimplexLineTransformation(int pivotPosition ,int maxPosition){
        for (int i = 0; i < matrix.length; i++) {
            if(i!= pivotPosition && matrix[i].coeff[maxPosition]!=0)
            matrix[i].setLineMinus(matrix[i].coeff[maxPosition],matrix[pivotPosition]);
        }
        if ( equation.coeff[maxPosition]!=0)
        this.equation.setLineMinus(equation.coeff[maxPosition],matrix[pivotPosition]);
    }
    private int counterOfDouble(Double [] d){
        int init =0;
        for (int i = 0; i < d.length; i++) {
            if (d[i] != null) init ++;     
        }
        return init;

    }
    protected boolean allCoeffInfOrZero(Double[] dbl){
        for (int i = 0; i < dbl.length; i++) {
            if(dbl[i]>0) return false ;
        }
        return true ;
    }
    protected boolean allCoeffSupOrZero(Double[] dbl) {
        for (int i = 0; i < dbl.length; i++) {
            if(dbl[i] < 0) return false ;
        }
        return true ;
    }
    
    protected int maxPositionCoeff(Double [] refDecision){
        int maxP =0 ;
        Double maxN = refDecision[0] ;
        for (int i = 1; i < refDecision.length; i++) {
            if(maxN<refDecision[i]){
                maxN= refDecision[i] ;
                maxP = i ;
            } 
        }
        return maxP ;
    }
    protected int minPositionCoeff(Double [] refDecision){
        int minP =0 ;
        Double minN = refDecision[0] ;
        for (int i = 1; i < refDecision.length; i++) {
            if(minN>refDecision[i]){
                minN= refDecision[i] ;
                minP = i ;
            } 
        }
        return minP ;
    }


}
