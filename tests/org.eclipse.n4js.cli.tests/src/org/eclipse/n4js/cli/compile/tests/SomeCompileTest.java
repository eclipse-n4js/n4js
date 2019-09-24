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
package org.eclipse.n4js.cli.compile.tests;

import static org.eclipse.n4js.cli.N4jscTestOptions.get;

import java.io.File;

import org.eclipse.n4js.cli.N4jscOptions;
import org.junit.Test;

/**
 *
 */
public class SomeCompileTest extends AbstractCliCompileTest {

	@Test
	public void test1() {
		File file = new File("test.n4js");
		N4jscOptions options = get().COMPILE().f(file);

		main(options);
	}

}
