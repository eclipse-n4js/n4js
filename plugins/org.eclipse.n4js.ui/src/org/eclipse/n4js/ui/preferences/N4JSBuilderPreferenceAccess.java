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

import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.xtext.builder.preferences.BuilderPreferenceAccess;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.n4js.generator.common.CompilerDescriptor;
import org.eclipse.n4js.generator.common.CompilerProperties;
import org.eclipse.n4js.generator.common.IComposedGenerator;
import org.eclipse.n4js.ui.building.instructions.ComposedGeneratorRegistry;

/**
 * Copied and adapted from org.eclipse.xtext.builder.preferences.BuilderPreferenceAccess
 */
@SuppressWarnings("restriction")
@Singleton
public class N4JSBuilderPreferenceAccess extends BuilderPreferenceAccess {

	private IPreferenceStoreAccess preferenceStoreAccess;

	/**
	 * To initialize the default values of the compiler related preference store values
	 */
	public static class Initializer implements IPreferenceStoreInitializer {

		@Override
		public void initialize(IPreferenceStoreAccess preferenceStoreAccess) {
			IPreferenceStore store = preferenceStoreAccess.getWritablePreferenceStore();
			intializeBuilderPreferences(store);
		}

		private void intializeBuilderPreferences(IPreferenceStore store) {
			List<IComposedGenerator> composedGenerators = ComposedGeneratorRegistry.getComposedGenerators();
			for (IComposedGenerator composedGenerator : composedGenerators) {
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

	@Override
	public boolean isAutoBuildEnabled(Object context) {
		// always return true, as otherwise the dirty state handling (that is also handled in the builder participant)
		// wouldn't work
		return true;
	}

	@Override
	public void setAutoBuildEnabled(Object context, boolean enabled) {
		IPreferenceStore preferenceStore = preferenceStoreAccess.getWritablePreferenceStore(context);
		List<IComposedGenerator> composedGenerators = ComposedGeneratorRegistry.getComposedGenerators();
		String key = null;
		for (IComposedGenerator composedGenerator : composedGenerators) {
			for (CompilerDescriptor compilerDescriptor : composedGenerator.getCompilerDescriptors()) {
				key = CompilerProperties.IS_ACTIVE.getKey(compilerDescriptor.getIdentifier());
				preferenceStore.setValue(key, enabled);
			}
		}
	}
}
