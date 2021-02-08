package eu.europa.ec.digit.contentmanagement.domain.api.query;

/**
 * 
 * @author bentsth
 */
public class SearchCondition extends AbstractQueryElement {

    private LogicalOperator logicalOperator = null;
    private LogicalExpression logicalExpression;


    public SearchCondition(LogicalExpression logicalExpression) {
        checkIsNotNull("logicalExpression", logicalExpression);
        this.logicalExpression = logicalExpression;
    }


    public SearchCondition(LogicalOperator logicalOperator, LogicalExpression logicalExpression) {
        this(logicalExpression);
        this.logicalOperator = logicalOperator;
    }


    public LogicalOperator getLogicalOperator() {
        return logicalOperator;
    }


    public LogicalExpression getLogicalExpression() {
        return logicalExpression;
    }


    @Override
    public String toString() {
        return "SearchCondition [logicalOperator=" + logicalOperator + ", logicalExpression=" + logicalExpression + "]";
    }

    
    
}
