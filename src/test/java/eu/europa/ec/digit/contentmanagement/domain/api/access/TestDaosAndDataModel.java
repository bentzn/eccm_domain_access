package eu.europa.ec.digit.contentmanagement.domain.api.access;

import static org.junit.Assert.*;

import java.util.*;

import org.apache.log4j.Logger;

import eu.europa.ec.digit.contentmanagement.domain.api.access.specific.*;
import eu.europa.ec.digit.contentmanagement.domain.api.entities.*;
import eu.europa.ec.digit.contentmanagement.domain.api.util.collections.PaginatedList_i;

/**
 * 
 * @author bentsth
 */
public abstract class TestDaosAndDataModel {

    private static final Logger logger = Logger.getLogger(TestDaosAndDataModel.class);


    protected void run(DaoModule_i daoModule) throws Exception {
        daoModule.generateTestData();
        testGeneratedData(daoModule);
        testEntityNames(daoModule);
        testCrudl(daoModule);
        testNoTransaction(daoModule);
        testCrudWithTransaction(daoModule);
        testDeleteAfterTransaction(daoModule);
        testCreateData(daoModule);
        testPaginatedList(daoModule);

        testTypeDefinitionGetChildren(daoModule);
        testArtifactGetChildrenAndParents(daoModule);
    }


    protected void testGeneratedData(DaoModule_i daoModule) throws Exception {
        RepositoryDao_i<?> daoRepository = daoModule.getRepositoryDao();
        ArtifactDao_i<?> daoArtifact = daoModule.getArtifactDao();
        
        List<? extends Repository_i> lstRepositories = daoRepository.all();
        assertTrue(lstRepositories.size() >= 4);
        for (Repository_i repository : lstRepositories) {
            if(repository.getName().endsWith("_GENERATED")) {
                assertTrue(repository.getRootFolderId() > 0);

                Artifact_i folderRoot = daoArtifact.retrieve(repository.getRootFolderId());
                assertEquals(BaseType.FOLDER, folderRoot.getBaseType());
                assertNotNull(folderRoot);
                
                List<? extends Artifact_i> lstFoldersSub = daoArtifact.getChildren(null, folderRoot.getId(), 0, Integer.MAX_VALUE);
                assertEquals(5, lstFoldersSub.size());
                
                for (Artifact_i folderSub : lstFoldersSub) {
                    assertEquals(BaseType.FOLDER, folderSub.getBaseType());

                    List<? extends Artifact_i> lstFoldersSubSub = daoArtifact.getChildren(null, folderSub.getId(), 0, Integer.MAX_VALUE);
                    assertEquals(4, lstFoldersSubSub.size());

                    for (Artifact_i folderSubSub : lstFoldersSubSub) {
                        assertEquals(BaseType.FOLDER, folderSubSub.getBaseType());
                        List<? extends Artifact_i> lstFoldersDocuments = daoArtifact.getChildren(null, folderSubSub.getId(), 0, Integer.MAX_VALUE);
                        assertEquals(6, lstFoldersDocuments.size());
                        
                        for (Artifact_i document : lstFoldersDocuments) {
                            assertEquals(BaseType.DOCUMENT, document.getBaseType());
                        }
                    }
                }
            }
        }
    }


    protected void testEntityNames(DaoModule_i daoModule) {
        assertEquals("Artifact", daoModule.getArtifactDao().getDataSourceNameOfEntity());
        assertEquals("Repository", daoModule.getRepositoryDao().getDataSourceNameOfEntity());
        assertEquals("TypeDefinition", daoModule.getTypeDefinitionDao().getDataSourceNameOfEntity());
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void testCrudl(DaoModule_i daoModule) throws Exception {
        for (EntityDao_i dao : daoModule.getDaos()) {
            if (logger.isDebugEnabled())
                logger.debug("Now testing: " + dao);

            AbstractEntity_i entity0 = dao.getNewEntityForTest(123);
            dao.create(entity0);
            assertTrue(entity0.getId() >= 0);

            AbstractEntity_i entity1 = dao.retrieve(entity0.getId());
            assertEquals(entity0.getId(), entity1.getId());

            dao.update(entity1);

            DataConnectionObject_i dco = dao.openNewDco();
            AbstractEntity_i entity2 = dao.retrieve(dco, entity0.getId());
            dao.delete(dco, entity2);
            dco.close();
        }
    }


    @SuppressWarnings("unchecked")
    protected void testNoTransaction(DaoModule_i daoModule) throws Exception {
        new TestDaoCrudWithoutTransaction<Repository_i>()
                .test((AbstractDao<?, Repository_i, ? extends Repository_i>) daoModule.getRepositoryDao());
        new TestDaoCrudWithoutTransaction<Artifact_i>()
                .test((AbstractDao<?, Artifact_i, ? extends Artifact_i>) daoModule.getArtifactDao());
        new TestDaoCrudWithoutTransaction<TypeDefinition_i>()
                .test((AbstractDao<?, TypeDefinition_i, ? extends TypeDefinition_i>) daoModule.getTypeDefinitionDao());
    }

    class TestDaoCrudWithoutTransaction<TYPE extends AbstractEntity_i> {
        void test(AbstractDao<?, TYPE, ? extends TYPE> dao) throws Exception {
            // Create
            TYPE entity0a = dao.getNewEntityForTest(1);
            dao.create(entity0a);
            long id = entity0a.getId();
            assertTrue(id >= 0);

            // Retrieve
            TYPE entity0b = dao.retrieve(id);

            // Retrieve by UUID
            TYPE entity0c = dao.retrieve(entity0b.getUuid());
            assertEquals(entity0b, entity0c);

            // Update
            String name = entity0b.getName() + "UPDATED";
            entity0b.setName(name);
            dao.update(entity0b);
            assertEquals(id, entity0b.getId());

            TYPE entity0d = dao.retrieve(id);
            assertEquals(id, entity0d.getId());
            assertEquals(name, entity0d.getName());

            // Retrieve non-existing by id
            TYPE entity0e = dao.retrieve(Integer.MAX_VALUE - 123);
            assertNull(entity0e);

            // Retrieve non-existing by id
            TYPE entity0f = dao.retrieve("aaaaaaaaaaaaaa");
            assertNull(entity0f);
        }
    }


    protected void testCrudWithTransaction(DaoModule_i daoModule) throws Exception {
        RepositoryDao_i<?> dao = daoModule.getRepositoryDao();

        DataConnectionObject_i dco = dao.openNewDco();
        dco.beginTransaction();

        // Create
        Repository_i repo0a = dao.getNewEntityForTest(1);
        dao.create(dco, repo0a);
        long id = repo0a.getId();
        assertTrue(id > 0);

        // Retrieve
        Repository_i repo0b = dao.retrieve(dco, id);

        // Update
        String name = repo0b.getName() + "UPDATED";
        repo0b.setName(name);
        dao.update(null, repo0b);
        assertEquals(id, repo0b.getId());

        Repository_i repo0c = dao.retrieve(dco, id);
        assertEquals(id, repo0c.getId());
        assertEquals(name, repo0c.getName());

        // Delete
        dao.delete(dco, repo0c);
        Repository_i repo0d = dao.retrieve(dco, id);
        assertNull(repo0d);

        dco.commitTransaction();
    }


    protected void testDeleteAfterTransaction(DaoModule_i daoModule) throws Exception {
        RepositoryDao_i<?> dao = daoModule.getRepositoryDao();

        // Create
        Repository_i repo0a = dao.getNewEntityForTest(1);
        dao.create(repo0a);
        long id = repo0a.getId();
        assertTrue(id > 0);

        // Delete - inside transaction
        DataConnectionObject_i dco = dao.openNewDco();
        dco.beginTransaction();

        Repository_i repo0b = dao.retrieve(dco, id);
        assertNotNull(repo0b);

        dao.delete(dco, repo0b);

        Repository_i repo0c = dao.retrieve(dco, id);
        assertNull(repo0c);

        dco.commitTransaction();

        // Retrieve outside the transaction
        Repository_i repo0d = dao.retrieve(id);
        assertNull(repo0d);
    }


    protected void testCreateData(DaoModule_i daoModule) throws Exception {
        daoModule.generateTestData();
    }


    protected void testPaginatedList(DaoModule_i daoModule) throws Exception {
        RepositoryDao_i<?> dao = daoModule.getRepositoryDao();

        for (int i = 0; i < 25; i++) {
            Repository_i repo = dao.getNewEntityForTest(i);
            dao.create(null, repo);
            assertTrue(repo.getId() > 0);
        }

        List<? extends Repository_i> lst = dao.all();
        assertTrue(lst.size() >= 25);
        int countTotal = lst.size();

        PaginatedList_i<? extends Repository_i> lstPage0 = dao.list(0, 5);
        assertEquals(0, lstPage0.getSkipItems());
        assertEquals(5, lstPage0.getMaxItems());
        assertEquals(5, lstPage0.size());
        assertEquals(countTotal, lstPage0.getTotalItems());
        lst.removeAll(lstPage0);
        assertEquals(countTotal - 5, lst.size());

        PaginatedList_i<? extends Repository_i> lstPage1 = dao.list(1, 5);
        assertEquals(1, lstPage1.getSkipItems());
        assertEquals(5, lstPage1.getMaxItems());
        assertEquals(countTotal, lstPage1.getTotalItems());
        lst.removeAll(lstPage1);
        assertEquals(countTotal - 6, lst.size());

        PaginatedList_i<? extends Repository_i> lstPage2 = dao.list(2, 6);
        assertEquals(2, lstPage2.getSkipItems());
        assertEquals(6, lstPage2.getMaxItems());
        assertEquals(countTotal, lstPage2.getTotalItems());
        lst.removeAll(lstPage2);
        assertEquals(countTotal - 8, lst.size());

        PaginatedList_i<? extends Repository_i> lstPage3 = dao.list(0, 1000);
        assertEquals(0, lstPage3.getSkipItems());
        assertEquals(1000, lstPage3.getMaxItems());
        assertEquals(countTotal, lstPage3.getTotalItems());

        PaginatedList_i<? extends Repository_i> lstPage4 = dao.list(1000, 1000);
        assertEquals(1000, lstPage4.getSkipItems());
        assertEquals(1000, lstPage4.getMaxItems());
        assertEquals(countTotal, lstPage4.getTotalItems());
        assertEquals(0, lstPage4.size());

        try {
            dao.list(-1, 6);
            fail();
        } catch (Exception e) {
        }

        try {
            dao.list(1, -3);
            fail();
        } catch (Exception e) {
        }
    }


    /*
     * Specific methods
     */

    protected void testTypeDefinitionGetChildren(DaoModule_i daoModule) throws Exception {
        TypeDefinitionDao_i<?> dao = daoModule.getTypeDefinitionDao();

        TypeDefinition_i typeDefParent = dao.getNewEntityForTest(99);
        dao.create(typeDefParent);

        Set<TypeDefinition_i> setChildren = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            TypeDefinition_i typeDef = dao.getNewEntityForTest(i);
            typeDef.setParentTypeDefinition(typeDefParent);
            dao.create(typeDef);
            setChildren.add(typeDef);
        }

        PaginatedList_i<? extends TypeDefinition_i> children = dao.getChildren(null, typeDefParent.getUuid(), 0,
                Integer.MAX_VALUE);
        assertEquals(5, children.size());
        for (TypeDefinition_i typeDefinition : children) {
            assertTrue(setChildren.contains(typeDefinition));
        }
    }


    protected void testArtifactGetChildrenAndParents(DaoModule_i daoModule) throws Exception {
        ArtifactDao_i<?> dao = daoModule.getArtifactDao();

        Artifact_i parent0 = dao.getNewEntityForTest(99);
        dao.create(parent0);

        Set<Artifact_i> setChildren = new HashSet<>();
        Artifact_i artifact0 = null;
        for (int i = 0; i < 5; i++) {
            artifact0 = dao.getNewEntityForTest(i);
            artifact0.addParent(parent0);
            dao.create(artifact0);
            setChildren.add(artifact0);
        }

        PaginatedList_i<? extends Artifact_i> children = dao.getChildren(null, parent0.getId(), 0, 3);
        assertEquals(3, children.size());
        assertEquals(5, children.getTotalItems());

        for (Artifact_i child : children) {
            assertTrue(setChildren.contains(child));
        }

        Artifact_i parent1 = dao.getNewEntityForTest(98);
        dao.create(parent1);

        artifact0.addParent(parent1);
        dao.update(artifact0);

        Set<Artifact_i> parents = dao.getParents(null, artifact0.getId());
        assertEquals(2, parents.size());

        Artifact_i parentSuper = dao.getNewEntityForTest(999);
        dao.create(parentSuper);

        parent0.addParent(parentSuper);
        dao.update(parent0);

        parent1.addParent(parentSuper);
        dao.update(parent1);

        Set<Artifact_i> allParents = dao.getParentsAll(null, artifact0.getId());
        for (Artifact_i artifact : allParents) {
            System.out.println(artifact.getId());
        }
        assertEquals(3, allParents.size());
        assertTrue(allParents.contains(parent0));
        assertTrue(allParents.contains(parent1));
        assertTrue(allParents.contains(parentSuper));
    }
}
