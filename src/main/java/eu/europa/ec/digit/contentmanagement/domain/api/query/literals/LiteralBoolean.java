package eu.europa.ec.digit.contentmanagement.domain.api.query.literals;

/**
 * 
 * @author bentsth
 */
public class LiteralBoolean extends AbstractLiteral<Boolean>{

    public LiteralBoolean(Boolean value) {
        super(value);
    }

    @Override
    public String toString() {
        return value == null ? "NULL" : (value ? "TRUE" : "FALSE");
    }
}
