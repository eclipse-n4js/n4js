/**
 * Copyright (c) 2019 NumberFour AG.
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
 *
 */
public class N4jscNoPerformFlag {

	/** Set the flag */
	static public void set() {
		N4jscMain.TESTFLAG_NO_PERFORM = true;
	}

	/** Restore the flag */
	static public void unset() {
		N4jscMain.TESTFLAG_NO_PERFORM = false;
	}

}
