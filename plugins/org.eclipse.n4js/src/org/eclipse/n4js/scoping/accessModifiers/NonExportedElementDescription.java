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
 * This description wraps an element that was not exported.
 */
public class NonExportedElementDescription extends AbstractDescriptionWithError {

	/**
	 * Creates a new instance of this wrapping description.
	 *
	 * @param delegate
	 *            the decorated description.
	 */
	public NonExportedElementDescription(IEObjectDescription delegate) {
		super(delegate);
	}

	@Override
	public String getMessage() {
		String name = getName().getLastSegment();
		return IssueCodes.getMessageForIMP_NOT_EXPORTED(name);
	}

	@Override
	public String getIssueCode() {
		return IssueCodes.IMP_NOT_EXPORTED;
	}

}
