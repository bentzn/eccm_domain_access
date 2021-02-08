package eu.europa.ec.digit.contentmanagement.domain.api.query;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author bentsth
 */
public class FromClause extends AbstractQueryElement {

    private List<TableReference> tableReferences;


    public FromClause(TableReference tableReference) {
        checkIsNotNull("tableReference", tableReference);
        checkIsNotNull("tableReference.tableName", tableReference.getTableName());
        tableReferences = new LinkedList<>();
        tableReferences.add(tableReference);
    }


    public FromClause(List<TableReference> tableReferences) {
        checkIsNotNullOrEmpty("tableReferences", tableReferences);
        checkIsNotNull("tableReferences.first.tableName", tableReferences.get(0).getTableName());
        // Check that only the first table reference has a table name
        for (int i = 1; i < tableReferences.size(); i++) {
            if(tableReferences.get(i).getTableName() != null)
                throw new IllegalArgumentException("Table name must be null [something better here]");
        }
        this.tableReferences = tableReferences;
    }


    public List<TableReference> getTableReferences() {
        return tableReferences;
    }


    @Override
    public String toString() {
        return "FromClause [tableReferences=" + tableReferences + "]";
    }

}
