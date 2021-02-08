package eu.europa.ec.digit.contentmanagement.domain.api.query;

/**
 * 
 * @author bentsth
 */
public class SelectSubList {

    private String qualifier;
    private boolean star;
    private String valueExpression;
    private String multiValuedColumnReference;
    private String alias;


    public static SelectSubList getStarInstance(String qualifier) {
        return new SelectSubList(qualifier, true, null, null, null);
    }


    public static SelectSubList getValueExpressionInstance(String qualifier, String valueExpression, String alias) {
        return new SelectSubList(qualifier, false, valueExpression, null, alias);
    }


    public static SelectSubList getMultiValiedColumnReferenceInstance(String qualifier,
            String multiValiedColumnReference, String alias) {
        return new SelectSubList(qualifier, false, null, multiValiedColumnReference, alias);
    }


    private SelectSubList(String qualifier, boolean star, String valueExpression, String multiValuedColumnReference,
            String alias) {
        this.qualifier = qualifier;
        this.star = star;
        this.valueExpression = valueExpression;
        this.multiValuedColumnReference = multiValuedColumnReference;
        this.alias = alias;
    }


    public String getQualifier() {
        return qualifier;
    }


    public boolean isStar() {
        return star;
    }


    public String getValueExpression() {
        return valueExpression;
    }


    public String getMultiValuedColumnReference() {
        return multiValuedColumnReference;
    }


    public String getAlias() {
        return alias;
    }


    @Override
    public String toString() {
        return "SelectSubList [star=" + star + ", valueExpression=" + valueExpression + ", multiValuedColumnReference="
                + multiValuedColumnReference + ", alias=" + alias + "]";
    }
}