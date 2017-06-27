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

import org.eclipse.xtext.resource.IEObjectDescription;

import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.validation.IssueCodes;

/**
 * Error-Description of invisible constructor-access
 */
public class InvisibleCtorDescription extends InvisibleMemberDescription {

	@SuppressWarnings("unused")
	private final TypeTypeRef typeRef;
	private final Type staticType;

	/**
	 * @param delegate
	 *            the constructor
	 * @param typeRef
	 *            reference to the constructor - used for better error messages.
	 * @param staticType
	 *            the static type for the given TypeTypeRef
	 */
	public InvisibleCtorDescription(IEObjectDescription delegate, TypeTypeRef typeRef, Type staticType) {
		super(delegate);
		this.typeRef = typeRef;
		this.staticType = staticType;
	}

	@Override
	public String getMessage() {
		String containerName = staticType.getTypeAsString();
		return IssueCodes.getMessageForVIS_NEW_CANNOT_INSTANTIATE_INVISIBLE_CONSTRUCTOR(containerName);
	}

	@Override
	public String getIssueCode() {
		return IssueCodes.VIS_NEW_CANNOT_INSTANTIATE_INVISIBLE_CONSTRUCTOR;
	}
}
