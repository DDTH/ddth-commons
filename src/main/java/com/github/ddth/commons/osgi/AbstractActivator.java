package com.github.ddth.commons.osgi;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An abstract implementation of {@link BundleActivator}. Bundle can extend this
 * class as a starting point to implement its bundle activator.
 * 
 * @author Thanh Ba Nguyen <bnguyen2k@gmail.com>
 * @since 0.2.0
 */
public abstract class AbstractActivator implements BundleActivator {

    private Logger LOGGER = LoggerFactory.getLogger(AbstractActivator.class);

    private BundleContext bundleContext;
    private Bundle bundle;
    private Properties properties;
    private File bundleExtractDir;

    @SuppressWarnings("rawtypes")
    private List<ServiceRegistration> registeredServices = new ArrayList<ServiceRegistration>();

    /**
     * Gets the {@link BundleContext} instance.
     * 
     * @return
     */
    protected BundleContext bundleContext() {
        return this.bundleContext;
    }

    /**
     * Gets the {@link Bundle} instance.
     * 
     * @return
     */
    protected Bundle bundle() {
        return this.bundle;
    }

    /**
     * Gets user-defined properties associated with the bundle.
     * 
     * @return
     */
    protected Properties properties() {
        return properties;
    }

    /**
     * Gets bundle's alias (a short no-space name).
     * 
     * <p>
     * This method simply returns <code>null</code>. Sub-class may override this
     * method to return its own alias.
     * </p>
     * 
     * @return
     */
    protected String alias() {
        return null;
    }

    /**
     * Extracts content from the bundle to a directory.
     * 
     * <p>
     * Sub-class calls this method to extract all or part of bundle's content to
     * a directory on disk.
     * </p>
     * 
     * <p>
     * Note: bundle's content will be extracted to a sub-directory
     * <code>toDirRoot/bundle_symbolic_name/bundle_version/</code>
     * </p>
     * 
     * @param bundleRootPath
     *            bundle's content under this path will be extracted
     * @param toDirRoot
     *            bundle's content will be extracted to this directory
     * @throws IOException
     */
    protected void extractBundleContent(String bundleRootPath, String toDirRoot) throws IOException {
        File toDir = new File(toDirRoot);
        if (!toDir.isDirectory()) {
            throw new RuntimeException("[" + toDir.getAbsolutePath()
                    + "] is not a valid directory or does not exist!");
        }
        toDir = new File(toDir, bundle.getSymbolicName());
        toDir = new File(toDir, bundle.getVersion().toString());
        FileUtils.forceMkdir(toDir);
        bundleExtractDir = toDir;

        Enumeration<String> entryPaths = bundle.getEntryPaths(bundleRootPath);
        if (entryPaths != null) {
            while (entryPaths.hasMoreElements()) {
                extractContent(bundleRootPath, entryPaths.nextElement(), bundleExtractDir);
            }
        }
    }

    private void extractContent(String pathPrefix, String path, File rootDir) throws IOException {
        if (path.endsWith("/")) {
            extractContentDir(pathPrefix, path, rootDir);
        } else {
            extractContentFile(pathPrefix, path, rootDir);
        }
    }

    private void extractContentDir(String pathPrefix, String path, File rootDir) throws IOException {
        File dir = new File(rootDir, path.substring(pathPrefix.length()));
        FileUtils.forceMkdir(dir);
        Enumeration<String> entryPaths = bundle.getEntryPaths(path);
        if (entryPaths != null) {
            while (entryPaths.hasMoreElements()) {
                extractContent(pathPrefix, entryPaths.nextElement(), bundleExtractDir);
            }
        }
    }

    private void extractContentFile(String pathPrefix, String path, File rootDir)
            throws IOException {
        URL source = bundle.getResource(path);
        File destination = new File(rootDir, path.substring(pathPrefix.length()));
        FileUtils.copyURLToFile(source, destination);
    }

    /**
     * Sub-class overrides this method to implement its initializing business.
     * 
     * <p>
     * This method is called by {@link #start(BundleContext)} when the bundle is
     * starting.
     * </p>
     * 
     * @throws Exception
     *             throws an exception to indicate that initializing process has
     *             failed
     */
    protected abstract void init() throws Exception;

    /**
     * Sub-class overrides this method to implement its destructing process.
     * 
     * <p>
     * This method is called by:
     * </p>
     * <ul>
     * <li>{@link #start(BundleContext)} if {@link #init()} throws exception.</li>
     * <li>{@link #stop(BundleContext)} when the bundle is stopping.</li>
     * </ul>
     * 
     * @throws Exception
     */
    protected abstract void destroy() throws Exception;

    @SuppressWarnings("rawtypes")
    private void _destroy() throws Exception {
        for (ServiceRegistration sr : registeredServices) {
            try {
                sr.unregister();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        registeredServices.clear();

        if (bundleExtractDir != null) {
            try {
                FileUtils.deleteQuietly(bundleExtractDir);
            } catch (Exception e) {
                LOGGER.warn(e.getMessage(), e);
            }
        }

        destroy();
    }

    /**
     * Registers a service.
     * 
     * @param clazz
     * @param service
     * @param props
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected <S> ServiceRegistration registerService(Class<S> clazz, S service,
            Map<String, ?> props) {
        if (service instanceof IBundleAwareService) {
            ((IBundleAwareService) service).setBundle(bundle);
        }

        Dictionary<String, Object> _p = new Hashtable<String, Object>();
        if (props != null) {
            for (Entry<String, ?> entry : props.entrySet()) {
                _p.put(entry.getKey(), entry.getValue());
            }
        }
        ServiceRegistration sr = bundleContext.registerService(clazz, service, _p);
        registeredServices.add(sr);
        return sr;
    }

    /**
     * Called when another version of this bundle is found in the bundle
     * context.
     * 
     * <p>
     * This method compares this bundle's version to the other bundle's version.
     * If this bundle is newer, calls
     * {@link #handleNewerVersionAtStartup(Bundle)}; otherwise calls
     * {@link #handleOlderVersionAtStartup(Bundle)}.
     * </p>
     * 
     * @param bundle
     * @since 0.1.2
     * @throws BundleException
     */
    protected void handleAnotherVersionAtStartup(Bundle bundle) throws BundleException {
        Version myVersion = this.bundle.getVersion();
        Version otherVersion = bundle.getVersion();
        if (myVersion.compareTo(otherVersion) > 0) {
            handleNewerVersionAtStartup(bundle);
        } else {
            handleOlderVersionAtStartup(bundle);
        }
    }

    /**
     * Called by {@link #handleAnotherVersionAtStartup(Bundle)} if the other
     * bundle's version is older than this bundle's version.
     * 
     * <p>
     * This method does nothing. Sub-class may override this method to implement
     * its own business logic.
     * </p>
     * 
     * @param bundle
     * @since 0.1.2
     * @throws BundleException
     */
    protected void handleOlderVersionAtStartup(Bundle bundle) throws BundleException {
        // EMPTY
    }

    /**
     * Called by {@link #handleAnotherVersionAtStartup(Bundle)} if the other
     * bundle's version is newer than this bundle's version.
     * 
     * <p>
     * This method does nothing. Sub-class may override this method to implement
     * its own business logic.
     * </p>
     * 
     * @param bundle
     * @since 0.1.2
     * @throws BundleException
     */
    protected void handleNewerVersionAtStartup(Bundle bundle) throws BundleException {
        // EMPTY
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(BundleContext context) throws Exception {
        this.bundleContext = context;
        this.bundle = context.getBundle();

        try {
            // check if another version of the bundle exists
            long myId = this.bundle.getBundleId();
            String myName = this.bundle.getSymbolicName();
            Bundle[] currentBundles = this.bundleContext.getBundles();
            for (Bundle bundle : currentBundles) {
                if (myId != bundle.getBundleId() && myName.equals(bundle.getSymbolicName())) {
                    // found another version of me
                    handleAnotherVersionAtStartup(bundle);
                }
            }

            // set bundle's user-defined properties
            this.properties = new Properties();
            properties.put(Constants.LOOKUP_PROP_VERSION, bundle.getVersion().toString());
            String alias = alias();
            if (!StringUtils.isEmpty(alias)) {
                properties.put(Constants.LOOKUP_PROP_MODULE, alias);
            }

            init();
        } catch (Exception e) {
            _destroy();
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        _destroy();
    }
}
