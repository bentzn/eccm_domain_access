package eu.europa.ec.digit.contentmanagement.domain.api.query;

/**
 * Modelled more or less over
 * http://docs.oasis-open.org/cmis/CMIS/v1.1/errata01/os/CMIS-v1.1-errata01-os-complete.html#x1-10500014
 * 2.1.14.2.1 BNF Grammar
 *
 * @author bentsth
 */
public class EccmQuery extends AbstractQueryElement {

    private SimpleTable simpleTable;
    private OrderByClause orderByClause;


    public EccmQuery(SimpleTable simpleTable, OrderByClause orderByClause) {
        checkIsNotNull("simpleTable", simpleTable);
        this.simpleTable = simpleTable;
        this.orderByClause = orderByClause;
    }


    public SimpleTable getSimpleTable() {
        return simpleTable;
    }


    public OrderByClause getOrderByClause() {
        return orderByClause;
    }


    @Override
    public String toString() {
        return "Query [simpleTable=" + simpleTable + ", orderByClause=" + orderByClause + "]";
    }
}
