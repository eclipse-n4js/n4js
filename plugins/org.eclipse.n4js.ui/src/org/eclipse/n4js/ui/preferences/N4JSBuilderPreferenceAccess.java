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
package org.eclipse.n4js.ui.preferences;

import java.util.Collection;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.n4js.generator.CompilerDescriptor;
import org.eclipse.n4js.generator.CompilerProperties;
import org.eclipse.n4js.generator.ICompositeGenerator;
import org.eclipse.n4js.ui.building.instructions.ComposedGeneratorRegistry;
import org.eclipse.xtext.builder.preferences.BuilderPreferenceAccess;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Copied and adapted from org.eclipse.xtext.builder.preferences.BuilderPreferenceAccess
 */
@SuppressWarnings("restriction")
@Singleton
public class N4JSBuilderPreferenceAccess extends BuilderPreferenceAccess {

	private IPreferenceStoreAccess preferenceStoreAccess;
	private ComposedGeneratorRegistry composedGeneratorRegistry;

	/**
	 * To initialize the default values of the compiler related preference store values
	 */
	public static class Initializer implements IPreferenceStoreInitializer {

		@Inject
		private ComposedGeneratorRegistry composedGeneratorRegistry;

		@Override
		public void initialize(IPreferenceStoreAccess preferenceStoreAccess) {
			IPreferenceStore store = preferenceStoreAccess.getWritablePreferenceStore();
			intializeBuilderPreferences(store);
		}

		private void intializeBuilderPreferences(IPreferenceStore store) {
			Collection<ICompositeGenerator> composedGenerators = composedGeneratorRegistry.getComposedGenerators();
			for (ICompositeGenerator composedGenerator : composedGenerators) {
				for (CompilerDescriptor compilerDescriptor : composedGenerator.getCompilerDescriptors()) {
					for (CompilerProperties prop : CompilerProperties.values()) {
						if (prop.getType() == Boolean.class) {
							store.setDefault(
									prop.getKey(compilerDescriptor.getIdentifier()),
									(Boolean) prop.getValueInCompilerDescriptor(compilerDescriptor,
											compilerDescriptor.getIdentifier()));
						} else {
							store.setDefault(
									prop.getKey(compilerDescriptor.getIdentifier()),
									(String) prop.getValueInCompilerDescriptor(compilerDescriptor,
											compilerDescriptor.getIdentifier()));
						}
					}
				}
			}
		}
	}

	@Override
	@Inject
	public void setPreferenceStoreAccess(IPreferenceStoreAccess preferenceStoreAccess) {
		this.preferenceStoreAccess = preferenceStoreAccess;
	}

	/**
	 * Set ComposedGeneratorRegistry.
	 */
	@Inject
	public void setComposedGeneratorRegistry(ComposedGeneratorRegistry composedGeneratorRegistry) {
		this.composedGeneratorRegistry = composedGeneratorRegistry;
	}

	@Override
	public boolean isAutoBuildEnabled(Object context) {
		// always return true, as otherwise the dirty state handling (that is also handled in the builder participant)
		// wouldn't work
		return true;
	}

	@Override
	public void setAutoBuildEnabled(Object context, boolean enabled) {
		IPreferenceStore preferenceStore = preferenceStoreAccess.getWritablePreferenceStore(context);
		Collection<ICompositeGenerator> composedGenerators = composedGeneratorRegistry.getComposedGenerators();
		String key = null;
		for (ICompositeGenerator composedGenerator : composedGenerators) {
			for (CompilerDescriptor compilerDescriptor : composedGenerator.getCompilerDescriptors()) {
				key = CompilerProperties.IS_ACTIVE.getKey(compilerDescriptor.getIdentifier());
				preferenceStore.setValue(key, enabled);
			}
		}
	}
}
