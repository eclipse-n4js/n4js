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
import org.eclipse.n4js.cli.helper.AbstractCliTest;
import org.eclipse.n4js.cli.helper.CliResult;

/**  */
public class AbstractCliFrontendTest extends AbstractCliTest<String[]> {

	@Override
	public void doMain(String[] args, CliResult result) {
		try {
			setN4jscRedirectionsDeactivateBackend();
			N4jscMain.main(args);
		} finally {
			unsetN4jscRedirections();
		}
	}
}
