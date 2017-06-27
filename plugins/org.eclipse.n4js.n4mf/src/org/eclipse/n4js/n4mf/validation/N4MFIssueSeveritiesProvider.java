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
package org.eclipse.n4js.n4mf.validation;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.preferences.IPreferenceValues;
import org.eclipse.xtext.preferences.IPreferenceValuesProvider;
import org.eclipse.xtext.validation.ConfigurableIssueCodesProvider;
import org.eclipse.xtext.validation.IssueSeveritiesProvider;
import org.eclipse.xtext.validation.SeverityConverter;

import com.google.inject.Inject;

/**
 * Customization to create {@link N4MFIssueSeverities} in
 * {@link N4MFIssueSeveritiesProvider#getIssueSeverities(Resource)}.
 */
@SuppressWarnings("restriction")
public class N4MFIssueSeveritiesProvider extends IssueSeveritiesProvider {
	@Inject
	private IPreferenceValuesProvider valuesProvider;
	@Inject
	private ConfigurableIssueCodesProvider issueCodesProvider;
	@Inject
	private SeverityConverter severityConverter;

	@Override
	protected IPreferenceValuesProvider getValuesProvider() {
		return valuesProvider;
	}

	@Override
	public N4MFIssueSeverities getIssueSeverities(Resource context) {
		IPreferenceValues preferenceValues = valuesProvider.getPreferenceValues(context);
		return new N4MFIssueSeverities(preferenceValues, issueCodesProvider.getConfigurableIssueCodes(),
				severityConverter);
	}
}
