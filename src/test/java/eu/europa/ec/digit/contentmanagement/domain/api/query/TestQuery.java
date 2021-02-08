package eu.europa.ec.digit.contentmanagement.domain.api.query;

import java.util.LinkedList;
import java.util.List;

import eu.europa.ec.digit.contentmanagement.domain.api.query.literals.*;

/**
 * 
 * @author bentsth
 */
public class TestQuery {

    public static void main(String[] args) {
        System.out.println(SqlGenerator.getSql(getQuerySimple()));
        System.out.println(SqlGenerator.getSql(getQueryWithTableAlias()));
        System.out.println(SqlGenerator.getSql(getQueryWithColumnAlias()));
        System.out.println(SqlGenerator.getSql(getQueryWithJoinedTable()));
        System.out.println(SqlGenerator.getSql(getQueryWithWhereClause()));
    }


    static EccmQuery getQuerySimple() {
        TableReference tableRef = new TableReference("sometable");
        FromClause fromClause = new FromClause(tableRef);
        SelectList selectList = new SelectList(SelectSubList.getStarInstance(null));
        SimpleTable simpleTable = new SimpleTable(selectList, fromClause);
        return new EccmQuery(simpleTable, null);
    }


    static EccmQuery getQueryWithTableAlias() {
        TableReference tableRef = new TableReference("sometable", "ST");
        FromClause fromClause = new FromClause(tableRef);
        SelectList selectList = new SelectList(SelectSubList.getStarInstance(null));
        SimpleTable simpleTable = new SimpleTable(selectList, fromClause);
        return new EccmQuery(simpleTable, null);
    }


    static EccmQuery getQueryWithColumnAlias() {
        TableReference tableRef = new TableReference("sometable", "ST");
        FromClause fromClause = new FromClause(tableRef);
        SelectList selectList = new SelectList(SelectSubList.getValueExpressionInstance("ST", "someColumn", "ali"));
        SimpleTable simpleTable = new SimpleTable(selectList, fromClause);
        return new EccmQuery(simpleTable, null);
    }


    static EccmQuery getQueryWithJoinedTable() {
        JoinedTable joinedTable = new JoinedTable("otherTable", "ot", JoinOperator.LEFT, "someIdColumn", "st",
                "someOtherIdColumn");
        TableReference tableRef = new TableReference("sometable", "st", joinedTable);
        FromClause fromClause = new FromClause(tableRef);
        SelectList selectList = new SelectList(SelectSubList.getValueExpressionInstance("st", "someColumn", "ali"));
        SimpleTable simpleTable = new SimpleTable(selectList, fromClause);
        return new EccmQuery(simpleTable, null);
    }


    static EccmQuery getQueryWithWhereClause() {
        TableReference tableRef = new TableReference("sometable", "st");
        FromClause fromClause = new FromClause(tableRef);
        SelectList selectList = new SelectList(SelectSubList.getValueExpressionInstance("st", "someColumn", "ali"));
        List<SearchCondition> lstSearchConditions = new LinkedList<>();
        lstSearchConditions.add(new SearchCondition(new LogicalExpression(new LiteralQualifier("ali"),
                PredicateOperator.EQ, new LiteralString("someString"))));
        lstSearchConditions.add(new SearchCondition(LogicalOperator.OR, new LogicalExpression(
                new LiteralQualifier("something_else"), PredicateOperator.NEQ, new LiteralFloat(123.456))));
        WhereClause whereClause = new WhereClause(lstSearchConditions);
        SimpleTable simpleTable = new SimpleTable(selectList, fromClause, whereClause);
        return new EccmQuery(simpleTable, null);
    }
}