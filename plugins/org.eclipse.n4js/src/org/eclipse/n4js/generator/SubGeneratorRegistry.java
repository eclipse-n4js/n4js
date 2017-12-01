/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;

import com.google.inject.Singleton;

/**
 * This is a global singleton registry to which a generator can be registered.
 */
@Singleton
public class SubGeneratorRegistry {

	private final static Logger LOGGER = Logger.getLogger(SubGeneratorRegistry.class);

	/* The extension point to subgenerators */
	private static final String SUBGENERATORS_EXTENSIONS_POINT_ID = "org.eclipse.n4js.generator.subgenerator";
	private static final String ATT_SUB_GENERATOR_CLASS = "class";

	private boolean isInitialized = false;
	private final List<ISubGenerator> generators = new ArrayList<>();

	/**
	 * Register a generator. This method should only be invoked by client code directly in headless mode. When running
	 * in Eclipse, register extensions will be registered via the 'generators' extension point.
	 */
	public void register(ISubGenerator generator) {
		generators.add(generator);
	}

	/**
	 * Return all registered generators.
	 */
	public Collection<ISubGenerator> getGenerators() {
		if (!isInitialized) {
			initialize();
		}
		return Collections.unmodifiableCollection(this.generators);
	}

	/**
	 * Read information from extensions defined in plugin.xml files
	 */
	private void initialize() {
		if (isInitialized) {
			throw new IllegalStateException("may invoke method initialize() only once");
		}
		isInitialized = true;

		final IExtensionRegistry registry = RegistryFactory.getRegistry();
		if (registry != null) {
			final IExtension[] extensions = registry.getExtensionPoint(SUBGENERATORS_EXTENSIONS_POINT_ID)
					.getExtensions();
			for (IExtension extension : extensions) {
				final IConfigurationElement[] configElems = extension.getConfigurationElements();
				for (IConfigurationElement elem : configElems) {
					try {
						ISubGenerator generator = (ISubGenerator) elem
								.createExecutableExtension(ATT_SUB_GENERATOR_CLASS);
						register(generator);
					} catch (Exception ex) {
						LOGGER.error(
								"Error while reading extensions for extension point "
										+ SUBGENERATORS_EXTENSIONS_POINT_ID,
								ex);
					}
				}
			}
		}
	}

	/**
	 * Reset the lists of file extensions to empty
	 */
	public void reset() {
		isInitialized = false;
		generators.clear();
	}
}
