package eu.europa.ec.digit.contentmanagement.domain.api.query.literals;

/**
 * 
 * @author bentsth
 */
public class AbstractLiteral<T> {

    protected T value;


    public AbstractLiteral(T value) {
        this.value = value;
    }


    public T getValue() {
        return value;
    }


    @Override
    public String toString() {
        return value == null ? "null" : value.toString();
    }

}
