package eu.europa.ec.digit.contentmanagement.domain.api.access;

import java.util.List;

import eu.europa.ec.digit.contentmanagement.domain.api.ServerModule_i;
import eu.europa.ec.digit.contentmanagement.domain.api.access.specific.*;
import eu.europa.ec.digit.contentmanagement.domain.api.entities.*;
import eu.europa.ec.digit.contentmanagement.domain.api.query.EccmQuery;
import eu.europa.ec.digit.contentmanagement.domain.api.query.ResultElement;

/**
 * 
 * @author bentsth
 */
public interface DaoModule_i extends ServerModule_i {

    void generateTestData() throws Exception;


    List<EntityDao_i<?, ?>> getDaos();


    RepositoryDao_i<? extends Repository_i> getRepositoryDao();


    TypeDefinitionDao_i<? extends TypeDefinition_i> getTypeDefinitionDao();


    ArtifactDao_i<? extends Artifact_i> getArtifactDao();


    <T> List<T> executeQuery(Class<T> clazz, EccmQuery query);


    List<ResultElement> executeQuery(EccmQuery query);

}
