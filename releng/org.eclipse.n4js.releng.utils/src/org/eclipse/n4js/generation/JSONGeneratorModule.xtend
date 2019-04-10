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
import org.eclipse.n4js.serializer.StableOrderSyntacticSequencerPDAProvider
import org.eclipse.xtext.serializer.analysis.SyntacticSequencerPDAProvider
import org.eclipse.xtext.xtext.generator.DefaultGeneratorModule
import org.eclipse.xtext.xtext.generator.XtextGeneratorNaming
import org.eclipse.xtext.xtext.generator.model.TypeReference
import org.eclipse.xtext.xtext.generator.model.project.IXtextProjectConfig

/**
 * A Guice {@link Module Module} for injecting customizations for the JSON language
 * into the (new) Xtext language generator.
 */
class JSONGeneratorModule extends DefaultGeneratorModule {

	def configureXtextGeneratorNaming(Binder binder) {
		binder.bind(XtextGeneratorNaming).to(JSONGeneratorNaming)
	}
	
	def configurePatchedSerializerGenerator(Binder binder) {
		binder.bind(SyntacticSequencerPDAProvider).to(StableOrderSyntacticSequencerPDAProvider);
	}
}

class JSONGeneratorNaming extends XtextGeneratorNaming {

	@Inject
	IXtextProjectConfig projectConfig

	override getEclipsePluginActivator() {
		val pluginName = projectConfig.eclipsePlugin.name

		if(pluginName === null) {
			return null
		} else {
			new TypeReference(pluginName + '.internal', 'JsonActivator')
		}
	}
}
