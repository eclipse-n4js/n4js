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
package org.eclipse.n4js.ui.building.instructions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.n4js.generator.common.CompilerDescriptor;
import org.eclipse.n4js.generator.common.IComposedGenerator;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.xtext.ui.shared.contribution.ISharedStateContributionRegistry;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;

/**
 * This is a global registry for composed generators.
 */
public class ComposedGeneratorRegistry {
	/** see also {@link ISharedStateContributionRegistry} as a possible alternative */
	private final String EXTENSION_POINT = N4JSActivator.getInstance().getBundle().getSymbolicName()
			+ ".composedgenerator";
	private final Logger LOGGER = Logger.getLogger(BuildInstruction.class);

	private boolean isInitialized = false;

	private final List<IComposedGenerator> composedGenerators = new ArrayList<>();

	@Inject
	private Injector injector;

	/**
	 * @return a collection of composed generators that have been registered via extension point in plug-ins available
	 *         on the classpath of this N4JS ui bundle.
	 */
	public Collection<IComposedGenerator> getComposedGenerators() {
		if (!isInitialized) {
			initialize();
		}

		return Collections.unmodifiableCollection(composedGenerators);
	}

	private void initialize() {
		if (isInitialized) {
			throw new IllegalStateException("may invoke method initialize() only once");
		}
		isInitialized = true;

		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(EXTENSION_POINT);
		IExtension[] extensions = extensionPoint.getExtensions();
		for (IExtension extension : extensions) {
			IConfigurationElement[] configurationElements = extension.getConfigurationElements();
			for (IConfigurationElement configurationElement : configurationElements) {
				try {
					IComposedGenerator composedGenerator = (IComposedGenerator) configurationElement
							.createExecutableExtension("class");
					injector.injectMembers(composedGenerator);
					register(composedGenerator);

				} catch (CoreException e) {
					LOGGER.error(e.getMessage(), e);
				} catch (ProvisionException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}

	}

	private void register(IComposedGenerator composedGenerator) {
		composedGenerators.add(composedGenerator);
	}

	/**
	 * Returns the compile descriptor for the compiler with the given name, or null if no such compiler has been found.
	 */
	public CompilerDescriptor getDesiredCompilerDescriptor(String desiredCompilerName) {
		CompilerDescriptor desiredCompilerDescriptor = null;
		Collection<IComposedGenerator> composedGeneratorsLocal = getComposedGenerators();
		for (IComposedGenerator composedGenerator : composedGeneratorsLocal) {
			for (CompilerDescriptor compilerDescriptor : composedGenerator.getCompilerDescriptors()) {
				if (compilerDescriptor.getName().equals(desiredCompilerName)) {
					desiredCompilerDescriptor = compilerDescriptor;
					break;
				}
			}
		}
		return desiredCompilerDescriptor;
	}
}
