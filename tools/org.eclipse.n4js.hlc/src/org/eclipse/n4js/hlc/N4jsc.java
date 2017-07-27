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
package org.eclipse.n4js.hlc;

import org.eclipse.n4js.hlc.base.N4jscBase;

/**
 * N4JS Compiler.
 *
 * This class is the entry point to all command-line options and provides a main-Method for starting the compiler.
 *
 * Upon errors main-method exits the program through System.exit() providing an error code different to zero.
 *
 * This should only contain a single main method. All actual logic should go to the super class {@link N4jscBase} (which
 * is contained in an OSGI bundle) to facilitate dependency management through Tycho and reuse of the logic in tests and
 * MWE2 work flows (see {@link N4jscBase#doMain(String...)}).
 */
public class N4jsc {

	/**
	 * Entry point to start the compiler. Parses the Parameters.
	 *
	 * @param args
	 *            the parameters.
	 */
	public static void main(String[] args) {
		N4jscBase.main(args);
	}
}
