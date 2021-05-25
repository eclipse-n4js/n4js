/**
 * Copyright (c) 2021 NumberFour AG.
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
 * Performs the n4jsc goal init
 */
public class N4jscInit {

	/** Starts the compiler for goal INIT in a blocking fashion */
	public static N4jscExitState start(N4jscOptions options) {
		options.getWorkingDirectory();
		return N4jscExitState.SUCCESS;
	}
}
