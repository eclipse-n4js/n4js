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

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.preferences.IPreferenceValues;

import org.eclipse.n4js.validation.N4JSIssueSeveritiesProvider;

/**
 * Customization to create {@link N4JSXIssueSeverities} in
 * {@link N4JSXIssueSeveritiesProvider#getIssueSeverities(Resource)}.
 */
@SuppressWarnings("restriction")
public class N4JSXIssueSeveritiesProvider extends N4JSIssueSeveritiesProvider {

	@Override
	public N4JSXIssueSeverities getIssueSeverities(Resource context) {
		IPreferenceValues preferenceValues = valuesProvider.getPreferenceValues(context);
		return new N4JSXIssueSeverities(preferenceValues, issueCodesProvider.getConfigurableIssueCodes(),
				severityConverter);
	}
}
