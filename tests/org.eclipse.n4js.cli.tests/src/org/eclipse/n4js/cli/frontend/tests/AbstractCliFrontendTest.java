/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.cli.frontend.tests;

import org.eclipse.n4js.cli.N4jscMain;
import org.eclipse.n4js.cli.N4jscNoPerformFlag;
import org.eclipse.n4js.cli.helper.AbstractCliTest;
import org.junit.After;
import org.junit.Before;

/**  */
public class AbstractCliFrontendTest extends AbstractCliTest<String[]> {

	@Override
	public void doMain(String[] args) {
		N4jscMain.main(args);
	}

	/** Set the flag */
	@Before
	public void before2() {
		N4jscNoPerformFlag.set();
	}

	/** Restore the flag */
	@After
	public void after2() {
		N4jscNoPerformFlag.unset();
	}
}
