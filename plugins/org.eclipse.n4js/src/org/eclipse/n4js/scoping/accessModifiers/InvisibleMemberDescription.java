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

import org.eclipse.n4js.scoping.utils.AbstractDescriptionWithError;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.validation.IssueUserDataKeys.VIS_ILLEGAL_MEMBER_ACCESS;

/**
 * This description wraps an invisible member.
 */
public class InvisibleMemberDescription extends AbstractDescriptionWithError {

	private String accessModifierSuggestion;

	/**
	 * Creates a new instance of this wrapping description.
	 *
	 * @param delegate
	 *            the decorated description.
	 */
	public InvisibleMemberDescription(IEObjectDescription delegate) {
		super(delegate);
	}

	/**
	 * Creates a new instance of this wrapping description, containing the accessorSuggestion as user data.
	 *
	 * @param delegate
	 *            EObject description
	 * @param accessorSuggestion
	 *            accessorSuggestion
	 */
	public InvisibleMemberDescription(IEObjectDescription delegate, String accessorSuggestion) {
		super(delegate);
		this.accessModifierSuggestion = accessorSuggestion;

	}

	@Override
	public String getMessage() {
		String memberTypeName = getMemberTypeName(getEObjectOrProxy());
		String memberName = getName().getLastSegment();
		return IssueCodes.getMessageForVIS_ILLEGAL_MEMBER_ACCESS(memberTypeName, memberName);
	}

	@Override
	public String getIssueCode() {
		return IssueCodes.VIS_ILLEGAL_MEMBER_ACCESS;
	}

	@Override
	public String getUserData(String name) {

		switch (name) {

		case VIS_ILLEGAL_MEMBER_ACCESS.ACCESS_SUGGESTION:
			return this.accessModifierSuggestion;
		case VIS_ILLEGAL_MEMBER_ACCESS.DECLARATION_OBJECT_URI:
			return this.getEObjectURI().toString();
		default:
			return null;
		}

	}

	@Override
	public String[] getUserDataKeys() {

		return new String[] { VIS_ILLEGAL_MEMBER_ACCESS.ACCESS_SUGGESTION,
				VIS_ILLEGAL_MEMBER_ACCESS.DECLARATION_OBJECT_URI };
	}
}
