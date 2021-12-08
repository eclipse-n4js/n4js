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
package org.eclipse.n4js.formatting2;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.formatting2.FormatterPreferenceValuesProvider;
import org.eclipse.xtext.preferences.IPreferenceValues;
import org.eclipse.xtext.preferences.PreferenceKey;

/**
 * Formatting preference provider for N4JS language.
 */
public class N4JSSimpleFormattingPreferenceProvider extends FormatterPreferenceValuesProvider {

	@Override
	public IPreferenceValues getPreferenceValues(Resource context) {

		final IPreferenceValues preferenceValues = super.getPreferenceValues(context);

		return new IPreferenceValues() {
			@Override
			public String getPreference(PreferenceKey key) {

				if (key == N4JSFormatterPreferenceKeys.FORMAT_PARENTHESIS) {
					return Boolean.TRUE.toString(); // TODO make this configurable, see super-class
				}
				if (key == N4JSFormatterPreferenceKeys.FORMAT_SURROUND_PAREN_CONTENT_WITH_SPACE) {
					return Boolean.FALSE.toString(); // TODO make this configurable, see super-class
				}

				return preferenceValues.getPreference(key);
			}
		};
	}

}
