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

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.n4js.generator.CompilerDescriptor;
import org.eclipse.n4js.generator.CompilerProperties;
import org.eclipse.n4js.generator.ICompositeGenerator;
import org.eclipse.n4js.transpiler.es.EcmaScriptSubGenerator;
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

	@Inject
	private ICompositeGenerator compositeGenerator;

	@Inject
	private EcmaScriptSubGenerator subGenerator;

	/**
	 * To initialize the default values of the compiler related preference store values
	 */
	public static class Initializer implements IPreferenceStoreInitializer {

		@Inject
		private ICompositeGenerator compositeGenerator;

		@Override
		public void initialize(IPreferenceStoreAccess preferenceStoreAccess) {
			IPreferenceStore store = preferenceStoreAccess.getWritablePreferenceStore();
			intializeBuilderPreferences(store);
		}

		private void intializeBuilderPreferences(IPreferenceStore store) {
			for (CompilerDescriptor compilerDescriptor : compositeGenerator.getCompilerDescriptors()) {
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
		String key = null;
		for (CompilerDescriptor compilerDescriptor : compositeGenerator.getCompilerDescriptors()) {
			key = CompilerProperties.IS_ACTIVE.getKey(compilerDescriptor.getIdentifier());
			preferenceStore.setValue(key, enabled);
		}
	}

	/** @return true iff the build is aborted in case of errors in the manifest file */
	public boolean isAbortBuildOnMfErrors(Object context) {
		IPreferenceStore preferenceStore = preferenceStoreAccess.getWritablePreferenceStore(context);
		String key = CompilerProperties.IS_ABORTING_ON_MF_ERRORS.getKey(subGenerator.getCompilerID());
		return preferenceStore.getBoolean(key);
	}

	/** Sets whether build is en-/disabled in case there are errors in the Manifest file (for all compilers) */
	public void setAbortBuildOnMfErrors(Object context, boolean enabled) {
		IPreferenceStore preferenceStore = preferenceStoreAccess.getWritablePreferenceStore(context);
		String key = null;
		for (CompilerDescriptor compilerDescriptor : compositeGenerator.getCompilerDescriptors()) {
			key = CompilerProperties.IS_ABORTING_ON_MF_ERRORS.getKey(compilerDescriptor.getIdentifier());
			preferenceStore.setValue(key, enabled);
		}
	}

}
