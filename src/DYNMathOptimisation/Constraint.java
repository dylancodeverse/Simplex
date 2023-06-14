package DYNMathOptimisation;

import java.util.ArrayList;

public class Constraint {
    
    String originSyntax ;
    Double [] coeff ;
    String [] variables;
    StringBuffer condition ;
    Double secondMember ;
    

    public Constraint(String syntax) {
        originSyntax =syntax ;
        StringBuffer s = new StringBuffer(syntax) ;
        condition = new StringBuffer();
        deleteSpace(s);
        ArrayList<Double> coeffls = formatSyntax(s,new ArrayList<Double>()) ;
        coeff = coeffls.toArray(new Double[coeffls.size()]) ;
        setDecisions(s, new ArrayList<String>());
        setLimit(s);
    }
    protected Constraint(String originSyntax, Double[] coeff, String[] variables, StringBuffer condition,
                            Double secondMember) {
        this.originSyntax = originSyntax;
        this.coeff = coeff;
        this.variables = variables;
        this.condition = condition;
        this.secondMember = secondMember;
    }

    protected void deleteSpace(StringBuffer a){
        if(a.indexOf(" ")!=-1){
            a.deleteCharAt(a.indexOf(" ")) ;
            deleteSpace(a);
        }
    }

    protected ArrayList<Double> formatSyntax(StringBuffer s , ArrayList<Double> listCoeff){
        try {
            int first=  s.indexOf("(") ;  
            int last = s.indexOf(")") ;
            if(first==-1 || last ==-1) return listCoeff ;
            listCoeff.add( Double.parseDouble(s.substring(first+1, last)))   ;
            s.delete(first, last+1) ;
        } catch (Exception e) {
            return listCoeff; 
        }
        return formatSyntax(s, listCoeff);
    }
    
    protected void setDecisions(StringBuffer syntax ,ArrayList<String> ls) {
        int x = syntax.indexOf("[") ;
        int y = syntax.indexOf("]") ;
        if(x ==-1 || y ==-1){
            setDecisions(ls.toArray(new String [ls.size()]));
            return ;
        } 
        ls.add(syntax.substring(x+1, y));
        syntax.delete(x, y+1) ;
        setDecisions(syntax, ls);

    }
    public void setDecisions(String[] variables) {
        this.variables = variables;
    }
    public void setLimit( StringBuffer secondMember) {
        try {
            this.secondMember = Double.parseDouble(secondMember.toString());
        } catch (Exception e) {
            try {
                condition.append(secondMember.substring(0 ,1));
                secondMember.delete(0,1);
                setLimit(secondMember);
                    
            } catch (Exception e2) {
                return;
            }
        }
    }
    
    public int maxPositionCoeff(){
        int maxPosition = 0;
        Double max = coeff[0];
        for (int i = 1; i < coeff.length; i++) {
            if (coeff[i] > max) {
                max = coeff[i];
                maxPosition = i;
            }
        }
        return maxPosition;
    }
    public int minPositionCoeff() {
        int minPosition = 0;
        Double min = coeff[0];
        for (int i = 1; i < coeff.length; i++) {
            if (coeff[i] < min) {
                min = coeff[i];
                minPosition = i;
            }
        }
        return minPosition;
    }

    public void setLineDivision(Double division) {
        for (int i = 0; i < coeff.length; i++) {
            coeff[i] = coeff[i]/division ;
        }
        secondMember = secondMember / division ;
    }
    public void setLineMinus(Double coeff , Constraint other){
        for (int i = 0; i < this.coeff.length; i++) {
            this.coeff [i] = this.coeff [i] -(coeff)* other.coeff [i];
        }
        this.secondMember = this.secondMember -coeff *other.secondMember ;
    }
    public boolean allCoeffZeroOrInf(){
        for (int i = 0; i < coeff.length; i++) {
            if(coeff[i]>0) return false ;
        }
        return true ;
    }
    public boolean allCoeffZeroOrSup(){
        for (int i = 0; i < coeff.length; i++) {
            if(coeff[i]<0) return false ;
        }
        return true ;
    }

}
