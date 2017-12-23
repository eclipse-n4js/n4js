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
package org.eclipse.n4js.n4idl.scoping;

import org.eclipse.n4js.scoping.utils.AbstractDescriptionWithError;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * An {@link IEObjectDescriptionWithError} to indicate missing imports for the required version of type.
 */
public class RequiredVersionNotImportedDescription extends AbstractDescriptionWithError {

	private final String name;

	/**
	 */
	protected RequiredVersionNotImportedDescription(QualifiedName qualifiedName,
			IEObjectDescription delegate) {
		super(delegate);
		this.name = qualifiedName.toString();
	}

	@Override
	public String getMessage() {
		return IssueCodes.getMessageForIDL_REQUIRED_VERSION_NOT_IMPORTED(this.name);
	}

	@Override
	public String getIssueCode() {
		return IssueCodes.IDL_REQUIRED_VERSION_NOT_IMPORTED;
	}

}
