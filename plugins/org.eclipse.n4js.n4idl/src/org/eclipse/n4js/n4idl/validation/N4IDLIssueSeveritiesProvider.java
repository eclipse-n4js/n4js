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
package org.eclipse.n4js.n4idl.validation;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.validation.N4JSIssueSeveritiesProvider;
import org.eclipse.xtext.preferences.IPreferenceValues;

/**
 * Customization to create {@link N4IDLIssueSeverities} in
 * {@link N4IDLIssueSeveritiesProvider#getIssueSeverities(Resource)}.
 */
@SuppressWarnings("restriction")
public class N4IDLIssueSeveritiesProvider extends N4JSIssueSeveritiesProvider {

	@Override
	public N4IDLIssueSeverities getIssueSeverities(Resource context) {
		IPreferenceValues preferenceValues = valuesProvider.getPreferenceValues(context);
		return new N4IDLIssueSeverities(preferenceValues, issueCodesProvider.getConfigurableIssueCodes(),
				severityConverter);
	}
}
