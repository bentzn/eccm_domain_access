package eu.europa.ec.digit.contentmanagement.domain.api.query;

import eu.europa.ec.digit.contentmanagement.domain.api.query.literals.*;

/**
 * 
 * @author bentsth
 */
public class LogicalExpression extends AbstractQueryElement {

    private AbstractLiteral<?> firstElement;
    private boolean not;
    private PredicateOperator predicateOperator;
    private AbstractLiteral<?> secondElement;


    public LogicalExpression(AbstractLiteral<?> firstElement, PredicateOperator predicateOperator,
            AbstractLiteral<?> secondElement) {
        checkIsNotNull("firstElement", firstElement);
        checkIsNotNull("predicateOperator", predicateOperator);
        checkIsNotNull("secondElement", secondElement);
        this.firstElement = firstElement;
        this.predicateOperator = predicateOperator;
        this.secondElement = secondElement;
    }


    public LogicalExpression(AbstractLiteral<?> firstElement, boolean not, PredicateOperator predicateOperator,
            AbstractLiteral<?> secondElement) {
        this(firstElement, predicateOperator, secondElement);
        this.not = not;
    }


    public AbstractLiteral<?> getFirstElement() {
        return firstElement;
    }


    public boolean isNot() {
        return not;
    }


    public PredicateOperator getPredicateOperator() {
        return predicateOperator;
    }


    public AbstractLiteral<?> getSecondElement() {
        return secondElement;
    }


    @Override
    public String toString() {
        return "LogicalExpression [firstElement=" + firstElement + ", not=" + not + ", predicateOperator="
                + predicateOperator + ", secondElement=" + secondElement + "]";
    }
    
    
    
}
