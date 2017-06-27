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
package org.eclipse.n4js.validation.helper;

import org.eclipse.xtext.resource.IEObjectDescription;

import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;

/**
 */
public class N4JSImportValidator_helper {

	/**
	 * Delegates to {@link IEObjectDescriptionWithError#getDescriptionWithError(IEObjectDescription)} Workaround
	 * Xtend-Problem (2.7.2) static method from interface
	 */
	public static IEObjectDescriptionWithError getDescriptionWithError(final IEObjectDescription eObjectDescription) {
		return IEObjectDescriptionWithError.getDescriptionWithError(eObjectDescription);
	}

	/**
	 * Delegates to {@link IEObjectDescriptionWithError#isErrorDescription(IEObjectDescription)} Workaround
	 * Xtend-Problem (2.7.2) static method from interface
	 */
	public static boolean isErrorDescription(final IEObjectDescription eObjectDescription) {
		return IEObjectDescriptionWithError.isErrorDescription(eObjectDescription);
	}
}
