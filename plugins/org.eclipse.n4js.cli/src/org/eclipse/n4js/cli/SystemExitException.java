/**
 * Copyright (c) 2023 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.cli;

/**
 * Throw this exception in favor of calling {@link System#exit(int)}.
 */
public class SystemExitException extends SecurityException {
	/** Exit status */
	public final int status;

	/** Constructor */
	public SystemExitException(int status) {
		this.status = status;
	}
}
