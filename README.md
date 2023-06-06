## Getting start


## How to use it
DYNConstraint:
 Objet qui represente une equation ou inequation

 constructor

 DYNConstraint(String syntax)

  . syntax format:
    forme standard:
        2x + 3y = 12
    forme a faire:
        (2)[x](+3)[y] =12 

attribute   
    String originSyntax 
    Double [] coeff 
    String [] decisions (les variables)
    StringBuffer condition (= ; <= > ...)
    Double limit  (2 eme membre)



<!-- 
 -->