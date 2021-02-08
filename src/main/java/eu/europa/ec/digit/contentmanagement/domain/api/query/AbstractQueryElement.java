package eu.europa.ec.digit.contentmanagement.domain.api.query;

import java.util.Collection;

/**
 * 
 * @author bentsth
 */
public class AbstractQueryElement {

    protected void checkIsNotNull(String paramName, Object obj) {
        if(obj == null)
            throw new IllegalArgumentException("Parameter [" + paramName + "] may not be null");
    }

    protected void checkIsNotNullOrEmpty(String paramName, Collection<?> obj) {
        if(obj == null)
            throw new IllegalArgumentException("Parameter [" + paramName + "] may not be null");
        if(obj.size() == 0)
            throw new IllegalArgumentException("Parameter [" + paramName + "] may not be empty");
    }
}
