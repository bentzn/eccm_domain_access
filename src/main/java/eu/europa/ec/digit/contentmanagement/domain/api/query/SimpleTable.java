package eu.europa.ec.digit.contentmanagement.domain.api.query;

/**
 * 
 * @author bentsth
 */
public class SimpleTable extends AbstractQueryElement {

    private SelectList selectList;
    private FromClause fromClause;
    private WhereClause whereClause;


    public SimpleTable(SelectList selectList, FromClause fromClause) {
        checkIsNotNull("selectList", selectList);
        checkIsNotNull("fromClause", fromClause);
        this.selectList = selectList;
        this.fromClause = fromClause;
    }


    public SimpleTable(SelectList selectList, FromClause fromClause, WhereClause whereClause) {
        this(selectList, fromClause);
        this.whereClause = whereClause;
    }


    public SelectList getSelectList() {
        return selectList;
    }


    public FromClause getFromClause() {
        return fromClause;
    }


    public WhereClause getWhereClause() {
        return whereClause;
    }


    @Override
    public String toString() {
        return "SimpleTable [selectList=" + selectList + ", fromClause=" + fromClause + ", whereClause=" + whereClause
                + "]";
    }

    
    
}
