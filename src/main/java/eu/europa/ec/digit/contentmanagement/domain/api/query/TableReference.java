package eu.europa.ec.digit.contentmanagement.domain.api.query;

/**
 * 
 * @author bentsth
 */
public class TableReference extends AbstractQueryElement {

    private String tableName;
    private String alias;
    private JoinedTable joinedTable;


    public TableReference(String tableName) {
        checkIsNotNull("tableName", tableName);
        this.tableName = tableName;
    }


    public TableReference(String tableName, String alias) {
        this(tableName);
        this.alias = alias;
    }


    public TableReference(String tableName, String alias, JoinedTable joinedTable) {
        this(tableName, alias);
        this.joinedTable = joinedTable;
    }


    public String getTableName() {
        return tableName;
    }


    public String getAlias() {
        return alias;
    }


    public JoinedTable getJoinedTable() {
        return joinedTable;
    }


    @Override
    public String toString() {
        return "TableReference [tableName=" + tableName + ", alias=" + alias + ", joinedTable=" + joinedTable + "]";
    }

    
    
}
