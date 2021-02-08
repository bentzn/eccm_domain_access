package eu.europa.ec.digit.contentmanagement.domain.api.query;

/**
 * 
 * @author bentsth
 */
public enum JoinOperator {

    INNER("INNER"),
    LEFT("LEFT"),
    LEFT_OUTER("LEFT OUTER");
    
    private String val;
    
    private JoinOperator(String val) {
        this.val = val;
    }
    
    @Override
    public String toString() {
        return val;
    }
}
