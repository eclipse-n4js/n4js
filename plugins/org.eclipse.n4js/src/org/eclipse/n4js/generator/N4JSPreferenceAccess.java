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
package org.eclipse.n4js.generator;

import java.nio.file.Path;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.xtext.preferences.IPreferenceValues;
import org.eclipse.xtext.preferences.IPreferenceValuesProvider;
import org.eclipse.xtext.preferences.PreferenceKey;

import com.google.inject.Inject;

/**
 * Wraps the access to the preference store (or any other configured values provider).
 */
@SuppressWarnings("restriction")
public class N4JSPreferenceAccess {
	@Inject
	private IPreferenceValuesProvider valuesProvider;

	/**
	 * @param resource
	 *            the current resource, this will be used to calculate the preference store to access (e.g. if there are
	 *            project specific preferences configured).
	 * @param compilerID
	 *            the compiler id
	 * @param compilerProperty
	 *            the compiler property to ask for
	 * @param defaultDescriptor
	 *            the descriptor containing the default values to be used as fall back if there isn't a value in the
	 *            preference store
	 * @return the preference value found in the preference store or any other values provider or the default value out
	 *         of the default descriptor
	 */
	public String getPreference(Resource resource, String compilerID, CompilerProperties compilerProperty,
			CompilerDescriptor defaultDescriptor) {
		IPreferenceValues preferences = valuesProvider.getPreferenceValues(resource);
		PreferenceKey key = new PreferenceKey(compilerProperty.getKey(compilerID),
				String.valueOf(compilerProperty.getValueInCompilerDescriptor(defaultDescriptor, compilerID)));
		return preferences.getPreference(key);
	}

	/**
	 * Convenience method, calling {@link #getPreference(Resource, String, CompilerProperties, CompilerDescriptor)} with
	 * a dummy resource created from the absolute path name.
	 */
	public String getPreference(Path absolutePath, String compilerID, CompilerProperties compilerProperty,
			CompilerDescriptor defaultDescriptor) {
		Resource resource = new ResourceImpl(URIUtils.toFileUri(absolutePath));
		return getPreference(resource, compilerID, compilerProperty, defaultDescriptor);
	}

}
