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
package org.eclipse.n4js.n4jsx.validation;

import java.util.Map;

import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.preferences.IPreferenceValues;
import org.eclipse.xtext.preferences.PreferenceKey;
import org.eclipse.xtext.validation.SeverityConverter;

import org.eclipse.n4js.validation.N4JSIssueSeverities;

/**
 */
@SuppressWarnings("restriction")
public class N4JSXIssueSeverities extends N4JSIssueSeverities {

	@SuppressWarnings("javadoc")
	public N4JSXIssueSeverities(IPreferenceValues preferenceValues, Map<String, PreferenceKey> configurableIssueCodes,
			SeverityConverter converter) {
		super(preferenceValues, configurableIssueCodes, converter);
	}

	/**
	 * @return the Severity for the given severity code. If no configured severity could be found in the configurable
	 *         issue codes, the default severity configured in messages.properties (contained in this package) will be
	 *         returned.
	 */
	@Override
	public Severity getSeverity(String code) {
		if (!configurableIssueCodes.containsKey(code)) {
			Severity severity = IssueCodes.getDefaultSeverity(code);
			if (severity != null) {
				return severity;
			}
		}
		return super.getSeverity(code);
	}
}
