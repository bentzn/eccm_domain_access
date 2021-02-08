package eu.europa.ec.digit.contentmanagement.domain.api.query;

/**
 * 
 * @author bentsth
 */
public enum PredicateOperator {

    EQ("="),
    NEQ("!="),
    GT(">"),
    LT("<"),
    GTE(">="),
    LTE("<="),
    IN("IN"),
    LIKE("LIKE"),
    CONTAINS("CONTAINS"),
    ANY("ANY");
    
    
    private String val;
    
    private PredicateOperator(String val) {
        this.val = val;
    }
    
    @Override
    public String toString() {
        return val;
    }


}
