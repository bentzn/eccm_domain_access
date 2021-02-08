package eu.europa.ec.digit.contentmanagement.domain.api.query;

/**
 * 
 * @author bentsth
 */
public class SortSpecification extends AbstractQueryElement {

    private String columnReference;
    private boolean ascending = true;


    public SortSpecification(String columnReference) {
        checkIsNotNull("columnReference", columnReference);
        this.columnReference = columnReference;
    }


    public SortSpecification(String columnReference, boolean ascending) {
        this(columnReference);
        this.ascending = ascending;
    }


    public String getColumnReference() {
        return columnReference;
    }


    public boolean isAscending() {
        return ascending;
    }


    @Override
    public String toString() {
        return "SortSpecification [columnReference=" + columnReference + ", ascending=" + ascending + "]";
    }

    
    
}
