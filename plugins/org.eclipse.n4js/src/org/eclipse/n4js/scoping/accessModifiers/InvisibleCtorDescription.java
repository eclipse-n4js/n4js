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
package org.eclipse.n4js.scoping.accessModifiers;

import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * Error-Description of invisible constructor-access
 */
public class InvisibleCtorDescription extends InvisibleMemberDescription {

	private final Type staticType;

	/**
	 * @param delegate
	 *            the constructor
	 */
	public InvisibleCtorDescription(IEObjectDescription delegate, Type staticType) {
		super(delegate);

		this.staticType = staticType;
	}

	@Override
	public String getMessage() {
		String containerName = staticType.getTypeAsString();
		return IssueCodes.getMessageForVIS_NEW_CANNOT_INSTANTIATE_INVISIBLE_CONSTRUCTOR("constructor", containerName);
	}

	@Override
	public String getIssueCode() {
		return IssueCodes.VIS_NEW_CANNOT_INSTANTIATE_INVISIBLE_CONSTRUCTOR;
	}
}
