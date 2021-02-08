package eu.europa.ec.digit.contentmanagement.domain.api.query;

/**
 * 
 * @author bentsth
 */
public class SqlGenerator {

    public static String getSql(EccmQuery query) {
        StringBuffer buf = new StringBuffer();
        buf.append("SELECT ");
        for (SelectSubList selectSubList : query.getSimpleTable().getSelectList().getSelectSubLists()) {
            if(selectSubList.getQualifier() != null) {
                buf.append(selectSubList.getQualifier());
                buf.append('.');
            }
            
            if(selectSubList.isStar()) {
                buf.append("*");
                buf.append(' ');
            }
            else {
                buf.append(selectSubList.getValueExpression());
                buf.append(' ');
            }
            
            if(selectSubList.getAlias() != null) {
                buf.append("AS");
                buf.append(' ');
                buf.append(selectSubList.getAlias());
                buf.append(' ');
            }
        }

        buf.append("FROM");
        buf.append(' ');
        for (TableReference tableReference : query.getSimpleTable().getFromClause().getTableReferences()) {
            if(tableReference.getTableName() != null) {
                buf.append(tableReference.getTableName());
                buf.append(' ');
                
                if(tableReference.getAlias() != null) {
                    buf.append(tableReference.getAlias());
                    buf.append(' ');
                }
            }
            
            if(tableReference.getJoinedTable() != null) {
                buf.append(tableReference.getJoinedTable().getJoinOperator().toString());
                buf.append(' ');
                buf.append("JOIN");
                buf.append(' ');

                buf.append(tableReference.getJoinedTable().getTableName());
                buf.append(' ');

                if(tableReference.getJoinedTable().getAlias() != null) {
                    buf.append(tableReference.getJoinedTable().getAlias());
                    buf.append(' ');
                }

                buf.append("ON");
                buf.append(' ');
                
                if(tableReference.getJoinedTable().getAliasForeign() != null) {
                    buf.append(tableReference.getJoinedTable().getAliasForeign());
                    buf.append('.');
                }

                buf.append(tableReference.getJoinedTable().getForeignKey());
                buf.append(' ');

                buf.append("=");
                buf.append(' ');
                
                if(tableReference.getJoinedTable().getAlias() != null) {
                    buf.append(tableReference.getJoinedTable().getAlias());
                    buf.append('.');
                }

                buf.append(tableReference.getJoinedTable().getKey());
                buf.append(' ');
            }
        }
        
        if(query.getSimpleTable().getWhereClause() != null) {
            buf.append("WHERE");
            buf.append(' ');
            
            boolean firstPass = true;
            for (SearchCondition searchCondition : query.getSimpleTable().getWhereClause().getSearchConditions()) {
                if(!firstPass) {
                    // AND/OR
                    buf.append(searchCondition.getLogicalOperator());
                    buf.append(' ');
                }
                firstPass = false;

                /*
                 * This does not take CONTAINS, ANY, IN_TREE and IN_FOLDER into account
                 */
                buf.append(searchCondition.getLogicalExpression().getFirstElement().toString());
                buf.append(' ');
                buf.append(searchCondition.getLogicalExpression().getPredicateOperator().toString());
                buf.append(' ');
                buf.append(searchCondition.getLogicalExpression().getSecondElement().toString());
                buf.append(' ');
            }
        }

        return buf.toString();
    }
}




