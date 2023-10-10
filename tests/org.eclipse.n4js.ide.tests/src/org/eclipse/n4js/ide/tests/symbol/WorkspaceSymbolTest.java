/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.symbol;

import org.eclipse.n4js.ide.tests.helper.server.AbstractWorkspaceSymbolTest;
import org.junit.Test;

/**
 * Test for selected elements to show when calling LSP workspace symbol endpoint
 */
public class WorkspaceSymbolTest extends AbstractWorkspaceSymbolTest {

	
	@Test
	public void openWorkspaceSymbolsClassTest() throws Exception {
		test("class C { }",
				"(C, Class, (test-project/src/MyModule.n4js, [0:6 - 0:7]))");
	}

	
	@Test
	public void openWorkspaceSymbolsInterfaceTest() throws Exception {
		test("interface I { }",
				"(I, Interface, (test-project/src/MyModule.n4js, [0:10 - 0:11]))");
	}

	
	@Test
	public void openWorkspaceSymbolsNamespaceTest() throws Exception {
		test("namespace N { class C {} interface I {}}",
				"(N, Namespace, (test-project/src/MyModule.n4js, [0:10 - 0:11])), " +
						"(C, Class, (test-project/src/MyModule.n4js, [0:20 - 0:21])), " +
						"(I, Interface, (test-project/src/MyModule.n4js, [0:35 - 0:36]))");
	}

	
	@Test
	public void openWorkspaceSymbolsEnumTest() throws Exception {
		test("enum E {green, red}",
				"(E, Enum, (test-project/src/MyModule.n4js, [0:5 - 0:6]))");
	}

	
	@Test
	public void openWorkspaceSymbolsTypeAliasTest() throws Exception {
		test("class A {}; type T = A;",
				"(A, Class, (test-project/src/MyModule.n4js, [0:6 - 0:7]))");
	}

}
