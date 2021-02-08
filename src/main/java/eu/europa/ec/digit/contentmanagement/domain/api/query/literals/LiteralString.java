package eu.europa.ec.digit.contentmanagement.domain.api.query.literals;

/**
 * 
 * @author bentsth
 */
public class LiteralString extends AbstractLiteral<String> {

    public LiteralString(String value) {
        super(value);
    }

    @Override
    public String toString() {
        return value == null ? "null" : "'" + value.toString() + "'";
    }

}
