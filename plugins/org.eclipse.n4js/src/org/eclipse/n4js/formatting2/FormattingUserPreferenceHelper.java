/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.formatting2;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.preferences.IPreferenceValues;

import com.google.inject.Inject;

/**
 * Helper for computing user preference regarding space between import specifiers and curly brace.
 *
 * Extracted to separate module to contain access restriction warning.
 */
@SuppressWarnings("restriction")
public class FormattingUserPreferenceHelper {
	@Inject
	private N4JSSimpleFormattingPreferenceProvider formattingPreferenceProvider;

	/** Compute user preference regarding space between import specifiers and curly brace. */
	public String getSpacingPreference(Resource resource) {
		IPreferenceValues prefValues = formattingPreferenceProvider.getPreferenceValues(resource);
		String spacePreference = prefValues.getPreference(
				N4JSFormatterPreferenceKeys.FORMAT_SURROUND_IMPORT_LIST_WITH_SPACE);

		return Boolean.valueOf(spacePreference) ? " " : "";
	}

}
