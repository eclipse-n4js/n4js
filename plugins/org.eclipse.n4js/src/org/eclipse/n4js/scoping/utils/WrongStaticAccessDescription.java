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
package org.eclipse.n4js.scoping.utils;

import org.eclipse.n4js.scoping.members.MemberScope;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * If the {@link MemberScope} finds a static accessor in a wrong context, this descriptor is used to bound the reference
 * to the wrong accessor and display an error. This may be the case if static is called in
 *
 * <ul>
 * <li>instance field or method with this</li>
 * <li>field or method access at instance variable</li>
 * </ul>
 *
 * or non static is called
 *
 * <ul>
 * <li>static field or method with this</li>
 * <li>field or method access at class type</li>
 * <li>field or method access at class type dot constructor</li>
 * </ul>
 */
public class WrongStaticAccessDescription extends AbstractDescriptionWithError {

	private final boolean staticAccess;

	/**
	 * Wraps an existing description for a type with an ambiguous import error message.
	 *
	 * @param staticAccess
	 *            if true, access is static.
	 */
	public WrongStaticAccessDescription(IEObjectDescription delegate, boolean staticAccess) {
		super(delegate);
		this.staticAccess = staticAccess;
	}

	@Override
	public String getMessage() {
		if (getEClass() == TypesPackage.eINSTANCE.getTypeVariable()) {
			// special case for TypeVariables (because the generic error message does not fit here):
			return IssueCodes.VIS_WRONG_TYPE_VARIABLE_ACCESSOR.getMessage(getName());
		}
		// standard case:
		String[] staticNonStatic = { "static", "non-static" };
		String staticMemberStr = staticNonStatic[staticAccess ? 1 : 0];
		String staticContextStr = staticNonStatic[staticAccess ? 0 : 1];
		return IssueCodes.VIS_WRONG_STATIC_ACCESSOR.getMessage(staticMemberStr, getName(), staticContextStr);
	}

	@Override
	public String getIssueCode() {
		if (getEClass() == TypesPackage.eINSTANCE.getTypeVariable()) {
			// special case:
			return IssueCodes.VIS_WRONG_TYPE_VARIABLE_ACCESSOR.name();
		}
		// standard case:
		return IssueCodes.VIS_WRONG_STATIC_ACCESSOR.name();
	}
}
