package DYNMathOptimisation;

import java.util.ArrayList;

public class DYNConstraint {
    
    String originSyntax ;
    Double [] coeff ;
    String [] decisions;
    StringBuffer condition ;
    Double limit ;
    

    public DYNConstraint(String syntax) {
        originSyntax =syntax ;
        StringBuffer s = new StringBuffer(syntax) ;
        condition = new StringBuffer();
        deleteSpace(s);
        ArrayList<Double> coeffls = formatSyntax(s,new ArrayList<Double>()) ;
        coeff = coeffls.toArray(new Double[coeffls.size()]) ;
        setDecisions(s, new ArrayList<String>());
        setLimit(s);
    }
    protected DYNConstraint(String originSyntax, Double[] coeff, String[] decisions, StringBuffer condition,
                            Double limit) {
        this.originSyntax = originSyntax;
        this.coeff = coeff;
        this.decisions = decisions;
        this.condition = condition;
        this.limit = limit;
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
    public void setDecisions(String[] decisions) {
        this.decisions = decisions;
    }
    public void setLimit( StringBuffer limit) {
        try {
            this.limit = Double.parseDouble(limit.toString());
        } catch (Exception e) {
            try {
                condition.append(limit.substring(0 ,1));
                limit.delete(0,1);
                setLimit(limit);
                    
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

    public void setLineDivision(Double division) {
        for (int i = 0; i < coeff.length; i++) {
            coeff[i] = coeff[i]/division ;
        }
        limit = limit / division ;
    }
    public void setLineMinus(Double coeff , DYNConstraint other){
        for (int i = 0; i < this.coeff.length; i++) {
            this.coeff [i] = this.coeff [i] -(coeff)* other.coeff [i];
        }
        this.limit = this.limit -coeff *other.limit ;
    }
    public boolean allCoeffZero(){
        for (int i = 0; i < coeff.length; i++) {
            if(coeff[i]>0) return false ;
        }
        return true ;
    }

}