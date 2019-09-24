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
package org.eclipse.n4js.cli.compile.tests;

import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.cli.compiler.N4jscCompiler;
import org.eclipse.n4js.cli.tests.AbstractCliTest;

/**  */
public class AbstractCliCompileTest extends AbstractCliTest<N4jscOptions> {

	@Override
	public void doMain(N4jscOptions options) throws Exception {
		N4jscCompiler.start(options);
	}

}
