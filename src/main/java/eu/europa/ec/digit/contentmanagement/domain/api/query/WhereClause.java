package eu.europa.ec.digit.contentmanagement.domain.api.query;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author bentsth
 */
public class WhereClause extends AbstractQueryElement {

    private List<SearchCondition> searchConditions;


    public WhereClause(SearchCondition searchCondition) {
        checkIsNotNull("searchCondition", searchCondition);
        searchConditions = new LinkedList<>();
        searchConditions.add(searchCondition);
    }


    public WhereClause(List<SearchCondition> searchConditions) {
        checkIsNotNullOrEmpty("searchConditions", searchConditions);
        this.searchConditions = searchConditions;
    }
    
    
    public List<SearchCondition> getSearchConditions() {
        return searchConditions;
    }


    @Override
    public String toString() {
        return "WhereClause [searchConditions=" + searchConditions + "]";
    }

    
    
}
