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
package org.eclipse.n4js.semver.validation;

import java.util.Map;

import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.preferences.IPreferenceValues;
import org.eclipse.xtext.preferences.PreferenceKey;
import org.eclipse.xtext.validation.IssueSeverities;
import org.eclipse.xtext.validation.SeverityConverter;

/**
 * {@link IssueSeverities} implementation for SEMVER resources.
 *
 * Provides issue severities based on SEMVER-related issue codes of {@link SemverIssueCodes}.
 */
public class SemverIssueSeverities extends IssueSeverities {
	@SuppressWarnings("javadoc")
	protected final Map<String, PreferenceKey> configurableIssueCodes;

	@SuppressWarnings("javadoc")
	public SemverIssueSeverities(IPreferenceValues preferenceValues, Map<String, PreferenceKey> configurableIssueCodes,
			SeverityConverter converter) {
		super(preferenceValues, configurableIssueCodes, converter);
		this.configurableIssueCodes = configurableIssueCodes;
	}

	/**
	 * @return the Severity for the given severity code. If no configured severity could be found in the configurable
	 *         issue codes, the default severity configured in messages.properties (contained in this package) will be
	 *         returned.
	 */
	@Override
	public Severity getSeverity(String code) {
		if (!configurableIssueCodes.containsKey(code)) {
			Severity severity = SemverIssueCodes.getSeverityForName(code);
			if (severity != null) {
				return severity;
			}
		}
		return super.getSeverity(code);
	}
}
