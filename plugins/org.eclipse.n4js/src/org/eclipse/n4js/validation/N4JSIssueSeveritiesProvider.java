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
package org.eclipse.n4js.validation;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.preferences.IPreferenceValues;
import org.eclipse.xtext.preferences.IPreferenceValuesProvider;
import org.eclipse.xtext.validation.ConfigurableIssueCodesProvider;
import org.eclipse.xtext.validation.IssueSeveritiesProvider;
import org.eclipse.xtext.validation.SeverityConverter;

import com.google.inject.Inject;

/**
 * Customization to create {@link N4JSIssueSeverities} in
 * {@link N4JSIssueSeveritiesProvider#getIssueSeverities(Resource)}.
 */
public class N4JSIssueSeveritiesProvider extends IssueSeveritiesProvider {
	@SuppressWarnings("javadoc")
	@Inject
	protected IPreferenceValuesProvider valuesProvider;
	@SuppressWarnings("javadoc")
	@Inject
	protected ConfigurableIssueCodesProvider issueCodesProvider;
	@SuppressWarnings("javadoc")
	@Inject
	protected SeverityConverter severityConverter;

	@Override
	protected IPreferenceValuesProvider getValuesProvider() {
		return valuesProvider;
	}

	@Override
	public N4JSIssueSeverities getIssueSeverities(Resource context) {
		IPreferenceValues preferenceValues = valuesProvider.getPreferenceValues(context);
		return new N4JSIssueSeverities(preferenceValues, issueCodesProvider.getConfigurableIssueCodes(),
				severityConverter);
	}
}
