package eu.europa.ec.digit.contentmanagement.domain.api.access.specific;

import java.util.Set;

import eu.europa.ec.digit.contentmanagement.domain.api.access.DataConnectionObject_i;
import eu.europa.ec.digit.contentmanagement.domain.api.access.EntityDao_i;
import eu.europa.ec.digit.contentmanagement.domain.api.entities.Artifact_i;
import eu.europa.ec.digit.contentmanagement.domain.api.util.collections.PaginatedList_i;

/**
 * 
 * @author bentsth
 */
public interface ArtifactDao_i<IMPL extends Artifact_i> extends EntityDao_i<Artifact_i, IMPL>{

    PaginatedList_i<? extends Artifact_i> getChildren(DataConnectionObject_i dco, long id, int skipItems, int maxItems) throws Exception;
    
    Set<Artifact_i> getParentsAll(DataConnectionObject_i dco, long id) throws Exception;

    Set<Artifact_i> getParents(DataConnectionObject_i dco, long id) throws Exception;

}
