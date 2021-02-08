package eu.europa.ec.digit.contentmanagement.domain.api.access;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import eu.europa.ec.digit.contentmanagement.domain.api.AbstractServerModule;
import eu.europa.ec.digit.contentmanagement.domain.api.access.specific.*;
import eu.europa.ec.digit.contentmanagement.domain.api.entities.*;

/**
 * 
 * @author bentsth
 */
public abstract class AbstractDaoModule extends AbstractServerModule implements DaoModule_i {

    private static final Logger logger = Logger.getLogger(AbstractDaoModule.class);

    @Override
    public void generateTestData() throws Exception {
        if (logger.isInfoEnabled())
            logger.info("Generating test data");

        // Repositories
        RepositoryDao_i<?> repositoryDao = getRepositoryDao();
        List<Repository_i> lstRepos = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            Repository_i repo = repositoryDao.getNewEntityForTest(i);
            repo.setName(repo.getName() + "_GENERATED");
            repositoryDao.create(null, repo);
            lstRepos.add(repo);
            if (logger.isDebugEnabled())
                logger.debug("Created repository, id: " + repo);
        }

        // TypeDefinition
        TypeDefinitionDao_i<?> typeDefinitionDao = getTypeDefinitionDao();
        List<TypeDefinition_i> lstTypeDefs = new LinkedList<>();
        int seed = 0;
        for (Repository_i repo : lstRepos) {
            for (BaseType typeDefBase : BaseType.values()) {
                TypeDefinition_i typeDef = typeDefinitionDao.getNewEntityForTest(seed++);
                typeDef.setBaseType(typeDefBase);
                typeDef.setRepository(repo);
                typeDefinitionDao.create(null, typeDef);
                lstTypeDefs.add(typeDef);

                if (logger.isDebugEnabled())
                    logger.debug("Created type definition, id: " + typeDef);
            }
        }

        // Artifacts
        ArtifactDao_i<?> artifactDao = getArtifactDao();
        List<Artifact_i> lstFoldersSub = new LinkedList<>();
        List<Artifact_i> lstFoldersSubSub = new LinkedList<>();
        List<Artifact_i> lstDocuments = new LinkedList<>();

        for (Repository_i repository : lstRepos) {
            // Create root folder
            Artifact_i rootFolder = artifactDao.getNewEntityForTest(0);
            rootFolder.setBaseType(BaseType.FOLDER);
            rootFolder.setName("root");
            rootFolder.setRepository(repository);
            artifactDao.create(rootFolder);

            repository.setRootFolderId(rootFolder.getId());
            repositoryDao.update(repository);

            if (logger.isDebugEnabled())
                logger.debug("Created root folder: " + rootFolder);

            for (int i = 0; i < 5; i++) {
                Artifact_i folderSub = artifactDao.getNewEntityForTest(seed++);
                folderSub.setBaseType(BaseType.FOLDER);
                folderSub.setName("sub_" + i);
                folderSub.addParent(rootFolder);
                folderSub.setRepository(repository);
                artifactDao.create(folderSub);
                lstFoldersSub.add(folderSub);

                if (logger.isDebugEnabled())
                    logger.debug("Created sub folder: " + folderSub);

                for (int j = 0; j < 4; j++) {
                    Artifact_i folderSubSub = artifactDao.getNewEntityForTest(seed++);
                    folderSubSub.setBaseType(BaseType.FOLDER);
                    folderSubSub.setName("subSub_" + i + "_" + j);
                    folderSubSub.addParent(folderSub);
                    folderSubSub.setRepository(repository);
                    artifactDao.create(folderSubSub);
                    lstFoldersSubSub.add(folderSubSub);

                    if (logger.isDebugEnabled())
                        logger.debug("Created sub sub folder: " + folderSubSub);

                    for (int m = 0; m < 6; m++) {
                        Artifact_i document = artifactDao.getNewEntityForTest(seed++);
                        document.setBaseType(BaseType.DOCUMENT);
                        document.setName("document_" + i + "_" + j + "_" + m);
                        document.addParent(folderSubSub);
                        document.setRepository(repository);
                        artifactDao.create(document);
                        lstDocuments.add(document);

                        if (logger.isDebugEnabled())
                            logger.debug("Created document: " + document);
                    }

                    artifactDao.update(folderSubSub);
                }

                artifactDao.update(folderSub);
            }

            artifactDao.update(rootFolder);
        }
    }
}
