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

import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;
import org.eclipse.xtext.ui.editor.preferences.PreferenceStoreAccessImpl;

import com.google.common.collect.MapDifference;
import com.google.common.collect.MapDifference.ValueDifference;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.eclipse.n4js.utils.ComponentDescriptor;
import org.eclipse.n4js.utils.IComponentProperties;

/**
 */
public abstract class AbstractN4JSPreferenceStoreAccessor<T extends ComponentDescriptor> {

	private final PreferenceStoreAccessImpl scopedAccessor;
	@Inject
	@Named("builderPreferenceInitializer")
	private IPreferenceStoreInitializer preferenceInitializer;

	/**
	 * Creates this preference store accessor.
	 */
	public AbstractN4JSPreferenceStoreAccessor(PreferenceStoreAccessImpl scopedAccessor) {
		this.scopedAccessor = scopedAccessor;
	}

	/**
	 * Returns properties for configured component.
	 */
	protected abstract IComponentProperties<T>[] getComponentPropertiesValues();

	/**
	 * @param outputName
	 *            the output name that identifies the output configuration but in our case also the registered compiler.
	 * @param compilerDescriptor
	 *            the compiler descriptor holding the values either stored last time this details page was used or the
	 *            default values for the compiler
	 */
	public void populateCompilerConfiguration(String outputName, T compilerDescriptor) {
		// defaults
		preferenceInitializer.initialize(scopedAccessor);

		boolean initialLoad = false;

		ComponentDescriptor currentlyStoredCompilerDescriptor = compilerDescriptor
				.getCurrentlyStoredComponentDescriptor();
		if (currentlyStoredCompilerDescriptor == null) {
			currentlyStoredCompilerDescriptor = compilerDescriptor; // default configuration
			initialLoad = true;
		}

		Map<String, String> originalSettings = currentlyStoredCompilerDescriptor.fillMap(outputName);

		IPreferenceStore preferenceStore = getPreferenceStore();
		// populate
		// use a copy here has we don't want to change the default values provided by the registered compilers
		@SuppressWarnings("unchecked")
		T newCompilerDescriptor = (T) compilerDescriptor.copy();
		for (IComponentProperties<T> prop : getComponentPropertiesValues()) {
			if (prop.getType() == Boolean.class) {
				prop.setValueInCompilerDescriptor(newCompilerDescriptor, outputName,
						preferenceStore.getBoolean(prop.getKey(outputName)));
			} else {
				prop.setValueInCompilerDescriptor(newCompilerDescriptor, outputName,
						preferenceStore.getString(prop.getKey(outputName)));
			}
		}

		Map<String, String> currentSettings = newCompilerDescriptor.fillMap(outputName);

		Map<String, ValueDifference<String>> changes = getPreferenceChanges(originalSettings, currentSettings);

		if (!initialLoad) {
			compilerDescriptor.setChanges(changes);
		}
		compilerDescriptor.setCurrentlyStoredComponentDescriptor(newCompilerDescriptor);
	}

	/**
	 * @param originalSettings
	 *            the settings before applying the values of the form page
	 * @param currentSettings
	 *            the settings after collecting the values of the form page
	 * @return a map keyed by the preference store key (e.g. outlet.es5.autobuilding for compiler (resp. output
	 *         configuration) with name 'es5' and property 'autobuilding') containing old and new value. Only keys whose
	 *         values has been changed are included.
	 */
	private Map<String, ValueDifference<String>> getPreferenceChanges(Map<String, String> originalSettings,
			Map<String, String> currentSettings) {
		MapDifference<String, String> mapDifference = Maps.difference(currentSettings, originalSettings);
		Map<String, ValueDifference<String>> entriesDiffering = mapDifference.entriesDiffering();
		return entriesDiffering;
	}

	private IPreferenceStore getPreferenceStore() {
		return scopedAccessor.getWritablePreferenceStore();
	}

}
