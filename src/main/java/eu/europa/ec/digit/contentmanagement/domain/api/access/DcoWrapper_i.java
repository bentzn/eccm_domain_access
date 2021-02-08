package eu.europa.ec.digit.contentmanagement.domain.api.access;

/**
 * 
 * @author bentsth
 */
public interface DcoWrapper_i<CONN> {

    
    CONN getConnectionObject();
    
    
    void setConnectionObject(CONN connectionObject);
    
    
    void beginTransaction();
    
    
    void commitTransaction();
    
    
    void rollbackTransaction();
    
    
    boolean isTransactionActive();
    
    
    void close();
}
