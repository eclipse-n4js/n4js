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
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * This description wraps a hollow type or value.
 */
public class HollowTypeOrValueDescription extends AbstractDescriptionWithError {
	final String expectation;

	/**
	 * Creates a new instance of this wrapping description.
	 *
	 * @param delegate
	 *            the decorated description.
	 */
	public HollowTypeOrValueDescription(IEObjectDescription delegate, String expectation) {
		super(delegate);
		this.expectation = expectation;
	}

	@Override
	public String getMessage() {
		String name = getName().getLastSegment();
		return IssueCodes.AST_ELEMENT_MISUSED_AS_VALUE_OR_TYPE.getMessage(name, expectation);
	}

	@Override
	public String getIssueCode() {
		return IssueCodes.AST_ELEMENT_MISUSED_AS_VALUE_OR_TYPE.name();
	}

}
