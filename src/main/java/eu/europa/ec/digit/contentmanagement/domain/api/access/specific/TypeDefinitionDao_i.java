package eu.europa.ec.digit.contentmanagement.domain.api.access.specific;

import eu.europa.ec.digit.contentmanagement.domain.api.access.DataConnectionObject_i;
import eu.europa.ec.digit.contentmanagement.domain.api.access.EntityDao_i;
import eu.europa.ec.digit.contentmanagement.domain.api.entities.TypeDefinition_i;
import eu.europa.ec.digit.contentmanagement.domain.api.util.collections.PaginatedList_i;

/**
 * 
 * @author bentsth
 */
public interface TypeDefinitionDao_i<IMPL extends TypeDefinition_i> extends EntityDao_i<TypeDefinition_i, IMPL>{

    PaginatedList_i<? extends TypeDefinition_i> getChildren(DataConnectionObject_i dco, String uuid, int skipItems, int maxItems) throws Exception;
    
}
