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
package org.eclipse.n4js.tester.extension;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.n4js.tester.ITester;
import org.eclipse.n4js.tester.TestConfiguration;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * Utility for working with the 'testers' extension point.
 */
@Singleton
public class TesterRegistry {

	private final static Logger log = Logger.getLogger(TesterRegistry.class);

	/** ID of the 'testers' extension point for registering N4JS testers. */
	public static final String TESTERS_EXTENSION_POINT_ID = "org.eclipse.n4js.tester.testers";

	private final Map<String, ITesterDescriptor> descriptors = new HashMap<>();
	private boolean isInitialized = false;

	@Inject
	private Injector injector;

	/**
	 * Register a new N4JS tester defined by the given tester descriptor. This method should only be invoked by client
	 * code directly in headless mode. When running in Eclipse, testers will be registered via the 'testers' extension
	 * point.
	 * <p>
	 * Will throw an exception if a tester for the same ID is already registered.
	 */
	public void register(ITesterDescriptor testerDescriptor) {
		final String testerId = testerDescriptor.getId();
		if (!descriptors.containsKey(testerId)) {
			descriptors.put(testerId, testerDescriptor);
		}
		// throw new IllegalArgumentException("cannot register two testers with the same ID: " + testerId);
	}

	/**
	 * Returns descriptors of all registered testers.
	 */
	public Map<String, ITesterDescriptor> getDescriptors() {
		if (!isInitialized)
			initialize();
		return Collections.unmodifiableMap(descriptors);
	}

	/**
	 * Returns descriptor of the registered tester with the given ID. Throws an {@link IllegalArgumentException} if not
	 * found.
	 */
	public ITesterDescriptor getDescriptor(String testerId) {
		final ITesterDescriptor result = getDescriptors().get(testerId);
		if (result == null)
			throw new IllegalArgumentException("no tester found for ID: " + testerId);
		return result;
	}

	/**
	 * Returns the registered tester with the given ID. Throws an {@link IllegalArgumentException} if not found.
	 */
	public ITester getTester(String testerId) {
		return getDescriptor(testerId).getTester();
	}

	/**
	 * Convenience method. Same as {@link #getTester(String)}, but takes a test configuration as argument and throws
	 * {@link IllegalArgumentException} if given test configuration does not define a tester ID or the ID is not found.
	 */
	public ITester getTester(TestConfiguration testConfig) {
		final String testerId = testConfig.getTesterId();
		if (testerId == null || testerId.trim().isEmpty())
			throw new IllegalArgumentException("no tester ID defined in given test configuration");
		return getTester(testerId);
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
					.getConfigurationElementsFor(TESTERS_EXTENSION_POINT_ID);

			for (IConfigurationElement elem : configElems) {
				try {
					final EclipseTesterDescriptor descriptor = new EclipseTesterDescriptor(elem);
					injector.injectMembers(descriptor);
					register(descriptor);
				} catch (Exception ex) {
					log.error("Error while reading extensions for extension point " + TESTERS_EXTENSION_POINT_ID, ex);
				}
			}
		}
	}
}
