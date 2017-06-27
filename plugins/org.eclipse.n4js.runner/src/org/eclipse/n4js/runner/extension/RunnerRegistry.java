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
package org.eclipse.n4js.runner.extension;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import org.eclipse.n4js.runner.IRunner;
import org.eclipse.n4js.runner.RunConfiguration;

/**
 * Utility for working with runner description extension point.
 */
@Singleton
public class RunnerRegistry {

	private final static Logger log = Logger.getLogger(RunnerRegistry.class);

	/** ID of the 'runners' extension point for registering N4JS runners. */
	public static final String RUNNERS_EXTENSION_POINT_ID = "org.eclipse.n4js.runner.runners";

	private final Map<String, IRunnerDescriptor> descriptors = new HashMap<>();
	private boolean isInitialized = false;

	@Inject
	private Injector injector;

	/**
	 * Register a new N4JS runner defined by the given runner descriptor. This method should only be invoked by client
	 * code directly in headless mode. When running in Eclipse, runners will be registered via the 'runners' extension
	 * point.
	 * <p>
	 * Will throw an exception if a runner for the same ID is already registered.
	 */
	public void register(IRunnerDescriptor runnerDescriptor) {
		final String runnerId = runnerDescriptor.getId();
		if (descriptors.containsKey(runnerId))
			throw new IllegalArgumentException("cannot register two runners with the same ID: " + runnerId);
		descriptors.put(runnerId, runnerDescriptor);
	}

	/**
	 * Checks if a runner is registered.
	 *
	 * @return true if runnerId is registered
	 */
	public boolean isRegistered(String runnerId) {
		return getDescriptors().get(runnerId) != null;
	}

	/**
	 * Returns descriptors of all registered runners.
	 */
	public Map<String, IRunnerDescriptor> getDescriptors() {
		if (!isInitialized)
			initialize();
		return Collections.unmodifiableMap(descriptors);
	}

	/**
	 * Returns descriptor of the registered runner with the given ID. Throws an {@link IllegalArgumentException} if not
	 * found.
	 */
	public IRunnerDescriptor getDescriptor(String runnerId) {
		final IRunnerDescriptor result = getDescriptors().get(runnerId);
		if (result == null)
			throw new IllegalArgumentException("no runner found for ID: " + runnerId);
		return result;
	}

	/**
	 * Returns the registered runner with the given ID. Throws an {@link IllegalArgumentException} if not found.
	 */
	public IRunner getRunner(String runnerId) {
		return getDescriptor(runnerId).getRunner();
	}

	/**
	 * Convenience method. Same as {@link #getRunner(String)}, but takes a run configuration as argument and throws
	 * {@link IllegalArgumentException} if given run configuration does not define a runner ID or the ID is not found.
	 */
	public IRunner getRunner(RunConfiguration runConfig) {
		final String runnerId = runConfig.getRunnerId();
		if (runnerId == null || runnerId.trim().isEmpty())
			throw new IllegalArgumentException("no runner ID defined in given run configuration");
		return getRunner(runnerId);
	}

	/**
	 * Reads information from extensions defined in plugin.xml files.
	 */
	private void initialize() {
		if (isInitialized)
			throw new IllegalStateException("may invoke method initialize() only once");
		isInitialized = true;

		final IExtensionRegistry registry = RegistryFactory.getRegistry();
		if (registry != null) {
			final IConfigurationElement[] configElems = registry
					.getConfigurationElementsFor(RUNNERS_EXTENSION_POINT_ID);

			for (IConfigurationElement elem : configElems) {
				try {
					final EclipseRunnerDescriptor descriptor = new EclipseRunnerDescriptor(elem);
					injector.injectMembers(descriptor);
					register(descriptor);
				} catch (Exception ex) {
					log.error("Error while reading extensions for extension point " + RUNNERS_EXTENSION_POINT_ID, ex);
				}
			}
		}
	}
}
