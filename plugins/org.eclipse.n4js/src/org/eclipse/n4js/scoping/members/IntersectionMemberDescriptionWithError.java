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

import java.util.List;

import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

/**
 * This description wraps a member of a union type that is not present in all contained types or is somehow incompatible
 * so that it cannot be composed into a single valid composed member.
 */
public class IntersectionMemberDescriptionWithError extends ComposedMemberDescriptionWithError {

	/**
	 * Creates a new instance of this wrapping description.
	 *
	 * @param delegate
	 *            the decorated description.
	 */
	public IntersectionMemberDescriptionWithError(IEObjectDescription delegate,
			ComposedTypeRef composedTypeRef, IScope[] subScopes, boolean writeAccess) {

		super(delegate, composedTypeRef, subScopes, writeAccess);
	}

	@Override
	protected boolean initMessageAndCode(List<String> missingFrom, MapOfIndexes<String> indexesPerMemberType,
			QualifiedName name, boolean readOnlyField, IEObjectDescription[] descriptions,
			MapOfIndexes<String> indexesPerCode) {

		return initMemberTypeConflict(indexesPerMemberType)
				|| initSubMessages(descriptions, indexesPerCode)
				|| initDefault();
	}

	private boolean initMemberTypeConflict(MapOfIndexes<String> indexesPerMemberType) {
		if (indexesPerMemberType.size() > 1) {
			StringBuilder strb = new StringBuilder();
			for (String memberTypeName : indexesPerMemberType.keySet()) {
				String foundScopes = indexesPerMemberType.getScopeNamesForKey(memberTypeName);
				if (strb.length() != 0) {
					strb.append("; ");
				}
				strb.append(memberTypeName + " in " + foundScopes);
			}
			final String memberName = getName().getLastSegment();
			message = IssueCodes.INTER_MEMBER_TYPE_CONFLICT.getMessage(memberName, strb);
			code = IssueCodes.INTER_MEMBER_TYPE_CONFLICT.name();
			return true;
		}
		return false;
	}

	private boolean initDefault() {
		final String memberName = getName().getLastSegment();
		message = IssueCodes.INTER_UNCOMMON.getMessage(memberName);
		code = IssueCodes.INTER_UNCOMMON.name();
		return true;
	}
}
