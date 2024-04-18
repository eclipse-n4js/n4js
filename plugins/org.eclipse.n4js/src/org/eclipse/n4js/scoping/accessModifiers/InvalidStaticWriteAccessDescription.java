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

import org.eclipse.n4js.scoping.utils.AbstractDescriptionWithError;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * Description marking invalid write access to static member via subtype or other expression.
 */
public class InvalidStaticWriteAccessDescription extends AbstractDescriptionWithError {

	private final String memberDefTypeName;
	private final String aliasOfMemberDefiningType; // nullable

	/**
	 * Creates a new instance of this wrapping description.
	 *
	 * @param delegate
	 *            the decorated description.
	 * @param memberDefiningTypeName
	 *            type name to be used for correct access
	 * @param aliasOfMemberDefiningType
	 *            the current alias in the script or <code>null</code> if no alias was given.
	 */
	public InvalidStaticWriteAccessDescription(IEObjectDescription delegate, String memberDefiningTypeName,
			String aliasOfMemberDefiningType) {
		super(delegate);
		this.memberDefTypeName = memberDefiningTypeName;
		this.aliasOfMemberDefiningType = aliasOfMemberDefiningType;
	}

	@Override
	public String getMessage() {
		QualifiedName qualifiedName = getName();
		String memberName = qualifiedName.getLastSegment();
		return aliasOfMemberDefiningType == null
				? IssueCodes.VIS_ILLEGAL_STATIC_MEMBER_WRITE_ACCESS.getMessage(memberDefTypeName, memberName)
				: IssueCodes.VIS_ILLEGAL_STATIC_MEMBER_WRITE_ACCESS_WITH_ALIAS.getMessage(memberDefTypeName,
						memberName, aliasOfMemberDefiningType);
	}

	@Override
	public String getIssueCode() {
		return aliasOfMemberDefiningType == null ? IssueCodes.VIS_ILLEGAL_STATIC_MEMBER_WRITE_ACCESS.name()
				: IssueCodes.VIS_ILLEGAL_STATIC_MEMBER_WRITE_ACCESS_WITH_ALIAS.name();
	}

}
