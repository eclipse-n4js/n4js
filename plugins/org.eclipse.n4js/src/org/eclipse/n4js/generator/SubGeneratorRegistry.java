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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;

import com.google.common.base.Splitter;
import com.google.inject.Singleton;

/**
 * This is a global singleton registry to which a generator can be registered.
 */
@Singleton
public class SubGeneratorRegistry {

	private static SubGeneratorRegistry singleton;

	/** Return the singleton instance of this class. */
	public static synchronized SubGeneratorRegistry getInstance() {
		if (singleton == null) {
			singleton = new SubGeneratorRegistry();
		}
		return singleton;
	}

	private final static Logger LOGGER = Logger.getLogger(SubGeneratorRegistry.class);

	/* The extension point to subgenerators */
	private static final String SUBGENERATORS_EXTENSIONS_POINT_ID = "org.eclipse.n4js.generator.subgenerator";

	private static final String ATT_FILE_EXTENSIONS = "fileExtensions";
	private static final String ATT_SUB_GENERATOR_CLASS = "class";

	private volatile boolean isInitialized = false;
	private final Map<String, List<ISubGenerator>> generators = new HashMap<>();

	/**
	 * Register a generator. This method should only be invoked by client code directly in headless mode. When running
	 * in Eclipse, register extensions will be registered via the 'generators' extension point.
	 *
	 * @param fileExtension
	 *            without the leading dot e.g. {@code txt} (not {@code .txt})
	 */
	public void register(ISubGenerator generator, String fileExtension) {
		if (!generators.containsKey(fileExtension)) {
			List<ISubGenerator> l = new ArrayList<>();
			generators.put(fileExtension, l);
		}

		generators.get(fileExtension).add(generator);
	}

	/**
	 * Return registered file extensions.
	 */
	public Collection<ISubGenerator> getGenerators(String fileExtension) {
		if (!isInitialized) {
			initialize();
		}

		if (!generators.containsKey(fileExtension)) {
			return Collections.emptyList();
		}
		return generators.get(fileExtension);
	}

	/**
	 * Return all registered generators.
	 */
	public Collection<ISubGenerator> getGenerators() {
		if (!isInitialized) {
			initialize();
		}
		return generators.values().stream().flatMap(List::stream).collect(Collectors.toList());
	}

	/**
	 * Read information from extensions defined in plugin.xml files
	 */
	private synchronized void initialize() {
		if (isInitialized) {
			return;
		}
		try {
			final IExtensionRegistry registry = RegistryFactory.getRegistry();
			if (registry != null) {
				final IExtension[] extensions = registry.getExtensionPoint(SUBGENERATORS_EXTENSIONS_POINT_ID)
						.getExtensions();
				for (IExtension extension : extensions) {
					final IConfigurationElement[] configElems = extension.getConfigurationElements();
					for (IConfigurationElement elem : configElems) {
						try {
							String fileExtensions = elem.getAttribute(ATT_FILE_EXTENSIONS);
							List<String> fileExtensionList = Splitter.on(',').trimResults().omitEmptyStrings()
									.splitToList(fileExtensions);
							ISubGenerator generator = (ISubGenerator) elem
									.createExecutableExtension(ATT_SUB_GENERATOR_CLASS);
							for (String fileExtension : fileExtensionList) {
								register(generator, fileExtension);
							}

						} catch (Exception ex) {
							LOGGER.error(
									"Error while reading extensions for extension point "
											+ SUBGENERATORS_EXTENSIONS_POINT_ID,
									ex);
						}
					}
				}
			}
		} finally {
			isInitialized = true;
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
