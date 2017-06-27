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
package org.eclipse.n4js.ui.building

import org.eclipse.n4js.generator.common.CompilerDescriptor
import org.eclipse.n4js.generator.common.IComposedGenerator
import org.eclipse.n4js.ui.building.instructions.ComposedGeneratorRegistry
import org.eclipse.xtext.generator.IOutputConfigurationProvider
import org.eclipse.xtext.generator.OutputConfiguration

/**
 */
class N4JSOutputConfigurationProvider implements IOutputConfigurationProvider {

	/**
	 * This method is called in org.eclipse.xtext.generator.Delegate and in
	 * org.eclipse.xtext.generator.GeneratorComponent. This methods returns the default
	 * configuration. If there is something different configured in the preference
	 * page these differences will be overlaid by the caller of this method.
	 */
	override getOutputConfigurations() {
		val outputConfigurations = <OutputConfiguration>newLinkedHashSet
		val composedGenerators = ComposedGeneratorRegistry.getComposedGenerators();
		for (IComposedGenerator composedGenerator : composedGenerators) {
			for (CompilerDescriptor compilerDescriptor : composedGenerator.getCompilerDescriptors()) {
				if (compilerDescriptor.getOutputConfiguration() !== null) {
					outputConfigurations.add(compilerDescriptor.getOutputConfiguration());
				}
			}
		}
		return outputConfigurations
	}
}
