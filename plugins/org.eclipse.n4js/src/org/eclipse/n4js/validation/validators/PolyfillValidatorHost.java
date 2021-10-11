/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.validation.validators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * A host validator for {@link PolyfillValidatorFragment}.
 */
public interface PolyfillValidatorHost {

	/**
	 * Adds an issue via this host validator.
	 */
	public void addIssue(String message, EObject source, EStructuralFeature feature, String issueCode,
			String... issueData);
}
