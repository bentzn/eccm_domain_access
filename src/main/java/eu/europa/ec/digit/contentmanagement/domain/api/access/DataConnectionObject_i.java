package eu.europa.ec.digit.contentmanagement.domain.api.access;

/**
 * 
 * @author bentsth
 */
public interface DataConnectionObject_i {

    void beginTransaction();
    
    void commitTransaction();
    
    void rollbackTransaction();
    
    void close();
}
