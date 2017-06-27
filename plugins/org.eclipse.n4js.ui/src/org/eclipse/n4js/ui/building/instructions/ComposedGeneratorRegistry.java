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
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.xtext.ui.shared.contribution.ISharedStateContributionRegistry;

import com.google.inject.ProvisionException;

import org.eclipse.n4js.generator.common.CompilerDescriptor;
import org.eclipse.n4js.generator.common.IComposedGenerator;
import org.eclipse.n4js.ui.internal.N4JSActivator;

/**
 */
public class ComposedGeneratorRegistry {
	/** see also {@link ISharedStateContributionRegistry} as a possible alternative */
	private static String EXTENSION_POINT = N4JSActivator.getInstance().getBundle().getSymbolicName()
			+ ".composedgenerator";
	private static final Logger LOGGER = Logger.getLogger(BuildInstruction.class);

	/**
	 * @return a list of composed generators that have been registered via extension point in plug-ins available on the
	 *         classpath of this N4JS ui bundle.
	 */
	public static List<IComposedGenerator> getComposedGenerators() {
		List<IComposedGenerator> composedGenerators = new ArrayList<>();
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(EXTENSION_POINT);
		IExtension[] extensions = extensionPoint.getExtensions();
		for (IExtension extension : extensions) {
			IConfigurationElement[] configurationElements = extension.getConfigurationElements();
			for (IConfigurationElement configurationElement : configurationElements) {
				try {
					IComposedGenerator composedGenerator = (IComposedGenerator) configurationElement
							.createExecutableExtension("class");
					if (composedGenerator != null) {
						composedGenerators.add(composedGenerator);
					}
				} catch (CoreException e) {
					LOGGER.error(e.getMessage(), e);
				} catch (ProvisionException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}

		return composedGenerators;
	}

	/**
	 * Returns the compile descriptor for the compiler with the given name, or null if no such compiler has been found.
	 */
	public static CompilerDescriptor getDesiredCompilerDescriptor(String desiredCompilerName) {
		CompilerDescriptor desiredCompilerDescriptor = null;
		List<IComposedGenerator> composedGenerators = ComposedGeneratorRegistry.getComposedGenerators();
		for (IComposedGenerator composedGenerator : composedGenerators) {
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
