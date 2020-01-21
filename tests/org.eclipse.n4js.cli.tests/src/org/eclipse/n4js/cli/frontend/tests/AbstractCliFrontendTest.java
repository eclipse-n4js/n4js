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

import org.eclipse.n4js.cli.helper.AbstractCliTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.cli.helper.CliTools;

/**  */
public class AbstractCliFrontendTest extends AbstractCliTest<String[]> {

	@Override
	protected void doN4jsc(String[] args, boolean ignoreFailure, boolean removeUsage, CliCompileResult result) {
		CliTools cliTools = new CliTools();
		cliTools.setIgnoreFailure(ignoreFailure);
		cliTools.callN4jscFrontendInprocess(args, removeUsage, result);
	}

}
