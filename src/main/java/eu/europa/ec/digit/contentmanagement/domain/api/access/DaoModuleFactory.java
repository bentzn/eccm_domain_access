package eu.europa.ec.digit.contentmanagement.domain.api.access;

import static eu.europa.ec.digit.contentmanagement.domain.api.access.EccmAccessConstants.*;

import java.util.Properties;

import org.apache.log4j.Logger;

import eu.europa.ec.digit.contentmanagement.domain.api.util.EccmUtils;

/**
 * 
 * @author bentsth
 */
public class DaoModuleFactory {

    private static final Logger logger = Logger.getLogger(DaoModuleFactory.class);
    private static volatile DaoModule_i daoModule;


    @SuppressWarnings("unchecked")
    public static Class<? extends DaoModule_i> getDaoModuleClass() throws Exception {
        if (logger.isDebugEnabled())
            logger.debug("Creating dao module, trying properties first...");

        Properties props = EccmUtils.readEccmPropsFromClasspath();
        String classname = props.getProperty(PROP_NAME_MODULE_CLASSNAME_DAO);

        Class<? extends DaoModule_i> clazz = null;
        if (classname != null) {
            if (logger.isInfoEnabled())
                logger.info("Found classname in props: " + classname);

            clazz = (Class<? extends DaoModule_i>) Class.forName(classname);
        } else {
            if (logger.isDebugEnabled())
                logger.debug("No luck with props, trying scan...");

            clazz = EccmUtils.getImplementingClass(DaoModule_i.class);

            if (clazz != null && logger.isDebugEnabled())
                logger.debug("Found class with reflection: " + clazz.getName());
        }

        return clazz;
    }


    public static DaoModule_i getDaoModule() throws Exception {
        if (daoModule == null) {
            synchronized (DaoModuleFactory.class) {
                if (daoModule == null) {
                    Class<? extends DaoModule_i> clazz = getDaoModuleClass();

                    if (clazz == null) {
                        logger.warn("Failed to create DAO module. Class not found.");
                        return null;
                    }

                    daoModule = clazz.getDeclaredConstructor().newInstance();
                    daoModule.init();

                    Runtime.getRuntime().addShutdownHook(new Thread() {
                        public void run() {
                            try {
                                daoModule.close();
                            } catch (Exception e) {
                                logger.warn("", e);
                            }
                        }
                    });
                }
            }
        }

        return daoModule;
    }
}