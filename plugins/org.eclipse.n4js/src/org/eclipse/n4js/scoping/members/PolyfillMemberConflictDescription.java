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

import java.util.Collection;

import org.eclipse.n4js.scoping.utils.AbstractDescriptionWithError;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.base.Joiner;

/**
 * Creates a description for error code {@link IssueCodes#CLF_POLYFILL_MULTIPOLYFILLS_MEMBER_CONFLICT}.
 */
public class PolyfillMemberConflictDescription extends AbstractDescriptionWithError {

	private final Collection<String> conflictingModuleNames;
	private final String memberName;

	/**
	 * @param delegate
	 *            the original description used for the binding
	 * @param memberName
	 *            the member name
	 * @param conflictingModuleNames
	 *            names of the modules with the conflicting polyfills
	 */
	public PolyfillMemberConflictDescription(IEObjectDescription delegate, String memberName,
			Collection<String> conflictingModuleNames) {
		super(delegate);
		this.conflictingModuleNames = conflictingModuleNames;
		this.memberName = memberName;
	}

	@Override
	public String getMessage() {

		return IssueCodes.CLF_POLYFILL_MULTIPOLYFILLS_MEMBER_CONFLICT.getMessage(
				Joiner.on(", ").join(conflictingModuleNames), memberName);
	}

	@Override
	public String getIssueCode() {
		return IssueCodes.CLF_POLYFILL_MULTIPOLYFILLS_MEMBER_CONFLICT.name();
	}

}
