/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import static com.google.common.base.Strings.nullToEmpty;
import static org.eclipse.core.runtime.IStatus.ERROR;

import java.util.Map;
import java.util.Objects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.eclipse.core.runtime.Status;

import com.google.inject.Injector;

/**
 * Workaround for fusing extension point based contributions with Guice without Xtext dependencies.
 */
public abstract class N4ExecutableExtensionFactory implements IExecutableExtensionFactory, IExecutableExtension {

	private static final String GUICE_KEY = "guicekey";

	private String clazzName;
	private IConfigurationElement config;

	@Override
	public final void setInitializationData(final IConfigurationElement config, final String propertyName,
			final Object data) throws CoreException {

		if (data instanceof String) {
			clazzName = (String) data;
		} else if (data instanceof Map) {
			clazzName = Objects.toString(((Map<?, ?>) data).get(GUICE_KEY));
		}
		if (clazzName == null) {
			throw new IllegalArgumentException("Couldn't handle passed data : " + data);
		}
		this.config = config;
	}

	@Override
	public final Object create() throws CoreException {
		try {
			final Class<?> clazz = getClassLoader().loadClass(clazzName);
			final Injector injector = getInjector();
			final Object result = injector.getInstance(clazz);
			if (result instanceof IExecutableExtension) {
				((IExecutableExtension) result).setInitializationData(config, null, null);
			}
			return result;
		} catch (final Exception e) {
			throw new CoreException(new Status(ERROR, getBundleId(),
					nullToEmpty(e.getMessage()) + " ExtensionFactory: " + getClass().getName(), e));
		}
	}

	/**
	 * Returns with the {@link ClassLoader class loader} for the executable extension factory.
	 *
	 * @return the class loader for the factory.
	 */
	protected abstract ClassLoader getClassLoader();

	/**
	 * Returns with the injector for the executable extension factor.
	 *
	 * @return the injector for the factory.
	 */
	protected abstract Injector getInjector();

	/**
	 * Returns with the bundle ID for logging purposes. By default this method returns with an empty string to support
	 * headless, pure OSGi bundles without activator. Clients may override this method.
	 *
	 * @return the bundle ID for the logging purposes.
	 */
	protected String getBundleId() {
		return "";
	}

}
