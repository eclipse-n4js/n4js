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
package org.eclipse.n4js.generation;

import org.eclipse.n4js.serializer.StableOrderSyntacticSequencerPDAProvider;
import org.eclipse.xtext.serializer.analysis.SyntacticSequencerPDAProvider;
import org.eclipse.xtext.xtext.generator.DefaultGeneratorModule;
import org.eclipse.xtext.xtext.generator.XtextGeneratorNaming;
import org.eclipse.xtext.xtext.generator.model.TypeReference;
import org.eclipse.xtext.xtext.generator.model.project.IXtextProjectConfig;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;

/**
 * A Guice {@link Module Module} for injecting customizations for the SemanticVersioning language into the (new) Xtext
 * language generator.
 */
@SuppressWarnings("restriction")
public class SemanticVersioningGeneratorModule extends DefaultGeneratorModule {

	/***/
	public void configureXtextGeneratorNaming(Binder binder) {
		binder.bind(XtextGeneratorNaming.class).to(SemanticVersioningGeneratorNaming.class);
	}

	/***/
	public void configurePatchedSerializerGenerator(Binder binder) {
		binder.bind(SyntacticSequencerPDAProvider.class).to(StableOrderSyntacticSequencerPDAProvider.class);
	}

	/***/
	static public class SemanticVersioningGeneratorNaming extends XtextGeneratorNaming {

		@Inject
		IXtextProjectConfig projectConfig;

		@Override
		public TypeReference getEclipsePluginActivator() {
			String pluginName = projectConfig.getEclipsePlugin().getName();

			if (pluginName == null) {
				return null;
			} else {
				return new TypeReference(pluginName + ".internal", "SemverActivator");
			}
		}
	}
}
