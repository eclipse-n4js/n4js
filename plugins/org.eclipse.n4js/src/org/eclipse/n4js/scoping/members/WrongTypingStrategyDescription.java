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
package org.eclipse.n4js.scoping.members;

import org.eclipse.n4js.scoping.utils.AbstractDescriptionWithError;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 */
public class WrongTypingStrategyDescription extends AbstractDescriptionWithError {

	private final boolean fields;
	private final boolean useSite;
	private final String receiverTypeName;

	WrongTypingStrategyDescription(IEObjectDescription delegate, boolean fields, boolean useSite,
			String receiverTypeName) {
		super(delegate);
		this.fields = fields;
		this.useSite = useSite;
		this.receiverTypeName = receiverTypeName;
	}

	@Override
	public String getMessage() {
		String memberName = getName().getLastSegment();
		String memberTypeName = getMemberTypeName(getEObjectOrProxy(), false); // Flag structFieldInitMode always false,
		// here, because error message refers not to the member available in the type but to the missing member accessed
		// by the source code.
		String typeName = receiverTypeName;
		if (fields) {
			return IssueCodes.TYS_MEMBER_NOT_IN_STRUCTURAL_FIELDS_TYPE_USE_SITE.getMessage(memberTypeName,
					memberName, typeName);
		}
		if (useSite) {
			return IssueCodes.TYS_MEMBER_NOT_IN_STRUCTURAL_TYPE_USE_SITE.getMessage(memberTypeName, memberName,
					typeName);
		} else {
			return IssueCodes.TYS_MEMBER_NOT_IN_STRUCTURAL_TYPE_DEF_SITE.getMessage(memberTypeName, memberName,
					typeName);
		}
	}

	@Override
	public String getIssueCode() {
		if (fields) {
			return IssueCodes.TYS_MEMBER_NOT_IN_STRUCTURAL_FIELDS_TYPE_USE_SITE.name();
		}
		if (useSite) {
			return IssueCodes.TYS_MEMBER_NOT_IN_STRUCTURAL_TYPE_USE_SITE.name();
		}
		return IssueCodes.TYS_MEMBER_NOT_IN_STRUCTURAL_TYPE_DEF_SITE.name();
	}

}
