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
package org.eclipse.n4js.xpect.ui.results;

/**
 * Status of execution for given test / test suite.
 */
public enum ExecutionStatus {
	/** not yet started */
	PENDING,
	/** started but not finished */
	STARTED,
	/** is ignored */
	IGNORED,
	/** finished and passed */
	PASSED,
	/** finished and failed */
	FAILED,
	/** there was error during execution */
	ERROR
}
