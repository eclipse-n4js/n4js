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

import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * Some operations {@link ExpressionExtensions#isBothReadFromAndWrittenTo(org.eclipse.emf.ecore.EObject) require both
 * read *and* write access to a field or accessor}.
 * <p>
 * In case only one is available, this description can be used.
 */
public class UnsatisfiedRWAccessDescription extends AbstractDescriptionWithError {

	private final boolean isReadMissing;

	/**
	 * Wraps the existing description and adds info on which of read or write is missing.
	 */
	public UnsatisfiedRWAccessDescription(IEObjectDescription delegate, boolean isReadMissing) {
		super(delegate);
		this.isReadMissing = isReadMissing;
	}

	@Override
	public String getMessage() {
		String available = (isReadMissing ? "write-access" : "read-access");
		return IssueCodes.getMessageForVIS_WRONG_READ_WRITE_ACCESS(
				"operation", "requires both read and write access, but only " + available, "available");
	}

	@Override
	public String getIssueCode() {
		return IssueCodes.VIS_WRONG_READ_WRITE_ACCESS;
	}

}
