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
package org.eclipse.n4js.generation

import com.google.inject.Binder
import com.google.inject.Inject
import com.google.inject.Module
import org.eclipse.xtext.xtext.generator.XtextGeneratorNaming
import org.eclipse.xtext.xtext.generator.model.TypeReference
import org.eclipse.xtext.xtext.generator.model.project.IXtextProjectConfig

/**
 * A Guice {@link Module Module} for injecting customizations
 * into the (new) Xtext language generator.
 */
class N4IDLGeneratorModule extends AbstractPatchedGeneratorModule {

	def configureXtextGeneratorNaming(Binder binder) {
		binder.bind(XtextGeneratorNaming).to(N4IDLGeneratorNaming)
	}

}

/**
 * Customization configuring the name of the ide package (de-eclipsed UI components)
 * as well as the name of the UI plug-in's activator.
 */
class N4IDLGeneratorNaming extends XtextGeneratorNaming {

	@Inject
	IXtextProjectConfig projectConfig

	override getEclipsePluginActivator() {
		val pluginName = projectConfig.eclipsePlugin.name

		if(pluginName === null) {
			return null
		} else {
			new TypeReference(pluginName + '.internal', 'N4IDLActivator')
		}
	}
}
