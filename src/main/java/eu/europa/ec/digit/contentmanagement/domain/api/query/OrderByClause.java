package eu.europa.ec.digit.contentmanagement.domain.api.query;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author bentsth
 */
public class OrderByClause extends AbstractQueryElement{

    private List<SortSpecification> sortSpecifications;


    public OrderByClause(SortSpecification sortSpecification) {
        checkIsNotNull("sortSpecification", sortSpecification);
        sortSpecifications = new LinkedList<>();
        sortSpecifications.add(sortSpecification);
    }


    public OrderByClause(List<SortSpecification> sortSpecifications) {
        checkIsNotNull("sortSpecifications", sortSpecifications);
        this.sortSpecifications = sortSpecifications;
    }


    public List<SortSpecification> getSortSpecifications() {
        return sortSpecifications;
    }


    @Override
    public String toString() {
        return "OrderByClause [sortSpecifications=" + sortSpecifications + "]";
    }

    
    
}
