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
package org.eclipse.n4js.binaries;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

/**
 * Exception indicating invalid binary configuration state.
 */
public class IllegalBinaryStateException extends CoreException {

	/**
	 * The issue code that has to be used for representing this illegal binary state.
	 *
	 * @see IStatus#getCode()
	 */
	public static final int ISSUE_CODE = 4405777;

	private final Binary binary;

	/**
	 * Creates a new exception with the given status argument.
	 *
	 * @param binary
	 *            the binary that has the illegal state.
	 * @param status
	 *            the status argument describing the cause of the problem. Must not be {@link IStatus#isOK() OK} status,
	 *            otherwise a runtime exception is thrown.
	 */
	public IllegalBinaryStateException(final Binary binary, final IStatus status) {
		super(status);
		checkArgument(!status.isOK(), "Expected a status that with severity: INFO, WARNING or ERROR. " + status);
		this.binary = checkNotNull(binary, "binary");
	}

	/**
	 * Returns with the binary that has the illegal state.
	 *
	 * @return the binary with the illegal state.
	 */
	public Binary getBinary() {
		return binary;
	}

}
