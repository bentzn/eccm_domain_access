package eu.europa.ec.digit.contentmanagement.domain.api.access;

import java.util.List;

import eu.europa.ec.digit.contentmanagement.domain.api.entities.AbstractEntity_i;
import eu.europa.ec.digit.contentmanagement.domain.api.util.collections.PaginatedList_i;

/**
 * 
 * @author bentsth
 */
public interface EntityDao_i<TYPE extends AbstractEntity_i, IMPL> extends Dao_i {
    
    String getDataSourceNameOfEntity();
    
    void create(TYPE entity) throws Exception; 
    void create(DataConnectionObject_i dco, TYPE entity) throws Exception; 

    TYPE retrieve(long id) throws Exception;
    TYPE retrieve(DataConnectionObject_i dco, long id) throws Exception;

    TYPE retrieve(String uuid) throws Exception;
    TYPE retrieve(DataConnectionObject_i dco, String uuid) throws Exception;

    void update(TYPE entity) throws Exception;
    void update(DataConnectionObject_i dco, TYPE entity) throws Exception;

    void delete(DataConnectionObject_i dco, TYPE entity) throws Exception;

    List<IMPL> all() throws Exception;
    List<IMPL> all(DataConnectionObject_i dco) throws Exception;

    PaginatedList_i<IMPL> list(int pageNo, int pageLength) throws Exception;
    PaginatedList_i<IMPL> list(DataConnectionObject_i dco, int pageNo, int pageLength) throws Exception;

    long count() throws Exception;
    long count(DataConnectionObject_i dco) throws Exception;

    /**
     * Will return an un-persisted entity used for test
     * @param seed used to make the entity unique
     * @return the entity
     */
    TYPE getNewEntityForTest(int seed);
    
}