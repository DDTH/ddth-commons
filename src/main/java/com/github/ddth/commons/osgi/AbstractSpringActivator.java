package com.github.ddth.commons.osgi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.osgi.context.support.OsgiBundleXmlApplicationContext;

/**
 * Extending {@link AbstractActivator}, this bundle activator creates and
 * initializes an instance of Spring's {@link ApplicationContext}.
 * 
 * <p>
 * Sub-class, which relies on Spring, can extend this activator to utilize the
 * {@link ApplicationContext}.
 * </p>
 * 
 * @author Thanh Ba Nguyen <bnguyen2k@gmail.com>
 * @since 0.2.0
 */
public abstract class AbstractSpringActivator extends AbstractActivator {

    private Logger LOGGER = LoggerFactory.getLogger(AbstractSpringActivator.class);

    private OsgiBundleXmlApplicationContext applicationContext;

    /**
     * Gets Spring's {@link ApplicationContext} instance.
     * 
     * @return
     */
    protected ApplicationContext applicationContext() {
        return this.applicationContext;
    }

    /**
     * Gets list of Spring's configuration files.
     * 
     * <p>
     * This methods return <code>new String[] { "/spring/*.xml" }</code>.
     * Sub-class may override this method to implement its own business logic.
     * </p>
     * 
     * @return
     */
    protected String[] getSpringConfigFiles() {
        return new String[] { "/spring/*.xml" };
    }

    protected void initApplicationContext() throws Exception {
        OsgiBundleXmlApplicationContext ac = new OsgiBundleXmlApplicationContext();
        try {
            ac.setBundleContext(bundleContext());
            ac.setPublishContextAsService(false);
            String[] springConfigFiles = getSpringConfigFiles();
            if (springConfigFiles != null && springConfigFiles.length > 0) {
                ac.setConfigLocations(springConfigFiles);
            }
            // ac.refresh();
            ac.normalRefresh();
            ac.start();
        } catch (Exception e) {
            ac.close();
            throw e;
        }
        this.applicationContext = ac;
    }

    protected void destroyApplicationContext() throws Exception {
        if (applicationContext != null) {
            applicationContext.close();
            applicationContext = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void init() throws Exception {
        initApplicationContext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void destroy() throws Exception {
        try {
            destroyApplicationContext();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
