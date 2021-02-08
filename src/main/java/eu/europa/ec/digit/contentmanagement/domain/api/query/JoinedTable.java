package eu.europa.ec.digit.contentmanagement.domain.api.query;

/**
 * 
 * @author bentsth
 */
public class JoinedTable extends AbstractQueryElement {

    private String tableName;
    private JoinOperator joinOperator;
    private String alias;
    private String key;
    private String aliasForeign;
    private String keyForeign;


    public JoinedTable(String tableName, JoinOperator joinOperator, String key, String keyForeign) {
        checkIsNotNull("tableName", tableName);
        checkIsNotNull("joinOperator", joinOperator);
        checkIsNotNull("foreignKey", keyForeign);
        checkIsNotNull("key", key);
        this.tableName = tableName;
        this.joinOperator = joinOperator;
        this.keyForeign = keyForeign;
        this.key = key;
    }


    public JoinedTable(String tableName, String alias, JoinOperator joinOperator, String key, String aliasForeign,
            String keyForeign) {
        this(tableName, joinOperator, key, keyForeign);
        this.aliasForeign = aliasForeign;
        this.alias = alias;
    }


    public String getTableName() {
        return tableName;
    }


    public String getAlias() {
        return alias;
    }


    public String getAliasForeign() {
        return aliasForeign;
    }


    public JoinOperator getJoinOperator() {
        return joinOperator;
    }


    public String getKey() {
        return key;
    }


    public String getForeignKey() {
        return keyForeign;
    }


    @Override
    public String toString() {
        return "JoinedTable [tableName=" + tableName + ", joinOperator=" + joinOperator + ", alias=" + alias + ", key="
                + key + ", aliasForeign=" + aliasForeign + ", keyForeign=" + keyForeign + "]";
    }

}
