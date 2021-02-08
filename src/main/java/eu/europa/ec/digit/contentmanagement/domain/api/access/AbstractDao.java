package eu.europa.ec.digit.contentmanagement.domain.api.access;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

import eu.europa.ec.digit.contentmanagement.domain.api.entities.AbstractEntity_i;
import eu.europa.ec.digit.contentmanagement.domain.api.util.collections.PaginatedList_i;

/**
 * 
 * @author bentsth
 */
public abstract class AbstractDao<CONN, TYPE extends AbstractEntity_i, IMPL extends TYPE> implements EntityDao_i<TYPE, IMPL> {

    private static final Logger logger = Logger.getLogger(AbstractDao.class);

    private volatile String dataSourceNameOfEntity;

    @Override
    public void create(TYPE entity) throws Exception {
        create(null, entity);
    }



    @Override
    public void create(DataConnectionObject_i dco, TYPE entity) throws Exception {
        if (logger.isDebugEnabled())
            logger.debug("Create entity (" + getTypeName() + "): " + entity);

        AtomicBoolean connectionObjectCreated = new AtomicBoolean(false);
        AtomicBoolean transactionCreated = new AtomicBoolean(false);
        DcoWrapper_i<CONN> dcoWrapper = wrap(dco);
        initConnectionAndTransaction(dcoWrapper, connectionObjectCreated, transactionCreated);

        try {
            createEntity(dcoWrapper.getConnectionObject(), entity);
            commitTransaction(dcoWrapper, transactionCreated);
        }
        catch (Exception e) {
            rollbackTransaction(dcoWrapper, transactionCreated, e);
        }
        finally {
            closeEntityManager(dcoWrapper, connectionObjectCreated);
        }
    }



    protected abstract void createEntity(CONN connectionObject, TYPE entity) throws Exception;



    @Override
    public TYPE retrieve(long id) throws Exception {
        return retrieve(null, id);
    }



    @Override
    public TYPE retrieve(DataConnectionObject_i dco, long id) {
        if (logger.isDebugEnabled())
            logger.debug("Retrieving entity (" + getTypeName() + "), id: " + id);

        AtomicBoolean connectionObjectCreated = new AtomicBoolean(false);
        DcoWrapper_i<CONN> dcoWrapper = wrap(dco);
        initConnectionAndTransaction(dcoWrapper, connectionObjectCreated, null);

        try {
            return retrieveEntity(dcoWrapper.getConnectionObject(), id);
        }
        catch (Exception e) {
            logger.warn("", e);
            throw e;
        }
        finally {
            closeEntityManager(dcoWrapper, connectionObjectCreated);
        }
    }



    protected abstract TYPE retrieveEntity(CONN connectionObject, long id);



    @Override
    public TYPE retrieve(String uuid) throws Exception {
        return retrieve(null, uuid);
    }



    @Override
    public TYPE retrieve(DataConnectionObject_i dco, String uuid) throws Exception {
        if (logger.isDebugEnabled())
            logger.debug("Retrieving entity (" + getTypeName() + "), uuid: " + uuid);

        AtomicBoolean connectionObjectCreated = new AtomicBoolean(false);
        DcoWrapper_i<CONN> dcoWrapper = wrap(dco);
        initConnectionAndTransaction(dcoWrapper, connectionObjectCreated, null);

        try {
            return retrieveEntity(dcoWrapper.getConnectionObject(), uuid);
        }
        catch (Exception e) {
            logger.warn("", e);
            throw e;
        }
        finally {
            closeEntityManager(dcoWrapper, connectionObjectCreated);
        }
    }



    protected abstract TYPE retrieveEntity(CONN connectionObject, String uuid) throws Exception;



    @Override
    public void update(TYPE entity) throws Exception {
        update(null, entity);
    }



    @Override
    public void update(DataConnectionObject_i dco, TYPE entity) throws Exception {
        if (logger.isDebugEnabled())
            logger.debug("Updating entity (" + getTypeName() + "): " + entity);

        AtomicBoolean connectionObjectCreated = new AtomicBoolean(false);
        AtomicBoolean transactionCreated = new AtomicBoolean(false);
        DcoWrapper_i<CONN> dcoWrapper = wrap(dco);
        initConnectionAndTransaction(dcoWrapper, connectionObjectCreated, transactionCreated);

        try {
            updateEntity(dcoWrapper.getConnectionObject(), entity);
            commitTransaction(dcoWrapper, transactionCreated);
        }
        catch (Exception e) {
            rollbackTransaction(dcoWrapper, transactionCreated, e);
        }
        finally {
            closeEntityManager(dcoWrapper, connectionObjectCreated);
        }
    }



    protected abstract void updateEntity(CONN connectionObject, TYPE entity);



    @Override
    public void delete(DataConnectionObject_i dco, TYPE entity) throws Exception {
        if (logger.isDebugEnabled())
            logger.debug("Deleting entity (" + getTypeName() + "): " + entity);

        AtomicBoolean connectionObjectCreated = new AtomicBoolean(false);
        AtomicBoolean transactionCreated = new AtomicBoolean(false);
        DcoWrapper_i<CONN> dcoWrapper = wrap(dco);
        initConnectionAndTransaction(dcoWrapper, connectionObjectCreated, transactionCreated);

        try {
            deleteEntity(dcoWrapper.getConnectionObject(), entity);
            commitTransaction(dcoWrapper, transactionCreated);
        }
        catch (Exception e) {
            rollbackTransaction(dcoWrapper, transactionCreated, e);
        }
        finally {
            closeEntityManager(dcoWrapper, connectionObjectCreated);
        }
    }



    protected abstract void deleteEntity(CONN connectionObject, TYPE entity);



    @Override
    public List<IMPL> all() throws Exception {
        return all(null);
    }



    @Override
    public List<IMPL> all(DataConnectionObject_i dco) throws Exception {
        if (logger.isDebugEnabled())
            logger.debug("Get all");
        return list(dco, 0, Integer.MAX_VALUE);
    }



    @Override
    public PaginatedList_i<IMPL> list(int pageNo, int pageLength) throws Exception {
        return list(null, pageNo, pageLength);
    }



    @Override
    public PaginatedList_i<IMPL> list(DataConnectionObject_i dco, int skipItems, int maxItems) throws Exception {
        AtomicBoolean connectionObjectCreated = new AtomicBoolean(false);
        DcoWrapper_i<CONN> dcoWrapper = wrap(dco);
        initConnectionAndTransaction(dcoWrapper, connectionObjectCreated, null);

        try {
            return listEntities(dcoWrapper.getConnectionObject(), skipItems, maxItems);
        }
        finally {
            closeEntityManager(dcoWrapper, connectionObjectCreated);
        }
    }



    protected abstract PaginatedList_i<IMPL> listEntities(CONN connectionObject, int skipItems, int maxItems) throws Exception;



    @Override
    public long count() throws Exception {
        return count(null);
    }



    @Override
    public long count(DataConnectionObject_i dco) throws Exception {
        if (logger.isDebugEnabled())
            logger.debug("count (" + getTypeName() + ")");

        AtomicBoolean connectionObjectCreated = new AtomicBoolean(false);
        DcoWrapper_i<CONN> dcoWrapper = wrap(dco);
        initConnectionAndTransaction(dcoWrapper, connectionObjectCreated, null);

        try {
            return countEntities(dcoWrapper.getConnectionObject());
        }
        catch (Exception e) {
            logger.warn("", e);
            throw e;
        }
        finally {
            closeEntityManager(dcoWrapper, connectionObjectCreated);
        }
    }



    protected abstract long countEntities(CONN connectionObject);



    protected abstract Class<TYPE> getInterfaceOfEntity();



    protected abstract Class<IMPL> getImplementingClassOfEntity();



    protected abstract String getDataSourceNameOfEntity(Class<?> clazz);



    public String getDataSourceNameOfEntity() {
        if (dataSourceNameOfEntity == null) {
            synchronized (this) {
                if (dataSourceNameOfEntity == null)
                    dataSourceNameOfEntity = getDataSourceNameOfEntity(getImplementingClassOfEntity());
            }
        }

        return dataSourceNameOfEntity;
    }



    protected String getTypeName() {
        return getInterfaceOfEntity().getSimpleName();
    }



    protected abstract CONN createConnection();



    protected abstract DcoWrapper_i<CONN> wrap(DataConnectionObject_i dco);



    protected void initConnectionAndTransaction(DcoWrapper_i<CONN> dcoWrapper, AtomicBoolean connectionObjectCreated, AtomicBoolean transactionCreated) {
        if (dcoWrapper.getConnectionObject() == null) {
            if (logger.isTraceEnabled())
                logger.trace("We don't have an em (" + getTypeName() + "). Creating...");

            dcoWrapper.setConnectionObject(createConnection());
            connectionObjectCreated.set(true);

            if (transactionCreated != null) {
                if (logger.isTraceEnabled())
                    logger.trace("Beginning transaction (" + getTypeName() + ")...");

                dcoWrapper.beginTransaction();
                transactionCreated.set(true);
            }
        }
        else if (!dcoWrapper.isTransactionActive() && transactionCreated != null) {
            if (logger.isTraceEnabled())
                logger.trace("We don't have a transaction (" + getTypeName() + "). Beginning...");

            dcoWrapper.beginTransaction();
            transactionCreated.set(true);
        }
    }



    protected void commitTransaction(DcoWrapper_i<CONN> dcoWrapper, AtomicBoolean transactionCreated) {
        if (transactionCreated.get()) {
            if (logger.isTraceEnabled())
                logger.trace("Committing transaction (" + getTypeName() + ")");
            dcoWrapper.commitTransaction();
        }
    }



    protected void rollbackTransaction(DcoWrapper_i<CONN> dcoWrapper, AtomicBoolean transactionCreated, Exception ex) throws Exception {
        if (transactionCreated.get()) {
            if (logger.isDebugEnabled())
                logger.debug("Rolling back transaction (" + getTypeName() + ")");

            dcoWrapper.rollbackTransaction();
        }

        throw ex;
    }



    protected void closeEntityManager(DcoWrapper_i<CONN> dcoWrapper, AtomicBoolean connectionObjectCreated) {
        if (connectionObjectCreated.get() && dcoWrapper.getConnectionObject() != null)
            dcoWrapper.close();
    }

}
