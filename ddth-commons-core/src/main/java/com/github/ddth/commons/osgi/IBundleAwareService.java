package com.github.ddth.commons.osgi;

import org.osgi.framework.Bundle;

/**
 * Service implements this interface will be called by
 * {@link AbstractActivator#registerService(Class, Object, java.util.Map)} to be
 * provided with a {@link Bundle} instance.
 * 
 * @author Thanh Ba Nguyen <bnguyen2k@gmail.com>
 * @since 0.2.0
 */
public interface IBundleAwareService {
    public void setBundle(Bundle bundle);
}
