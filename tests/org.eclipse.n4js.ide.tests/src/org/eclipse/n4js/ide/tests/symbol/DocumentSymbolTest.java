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

import org.eclipse.n4js.ide.tests.helper.server.AbstractDocumentSymbolTest;
import org.junit.Test;

/**
 *
 */
public class DocumentSymbolTest extends AbstractDocumentSymbolTest {

	/***/
	@Test
	public void openWorkspaceSymbolsClassTest() throws Exception {
		test("class C { field: string; method(): {}}",
				"(C, Class, [0:6 - 0:7])\n" +
						"(field, Field, [0:10 - 0:15])\n" +
						"(method, Method, [0:25 - 0:31])");
	}

	/***/
	@Test
	public void openWorkspaceSymbolsInterfaceTest() throws Exception {
		test("interface I { field: string; method(): {} }",
				"(I, Interface, [0:10 - 0:11])\n" +
						"(field, Field, [0:14 - 0:19])\n" +
						"(method, Method, [0:29 - 0:35])");
	}

	/***/
	@Test
	public void openWorkspaceSymbolsNamespaceTest() throws Exception {
		test("namespace N { class C {} interface I {}}",
				"(N, Namespace, [0:10 - 0:11])\n" +
						"(C, Class, [0:20 - 0:21])\n" +
						"(I, Interface, [0:35 - 0:36])");
	}

	/***/
	@Test
	public void openWorkspaceSymbolsEnumTest() throws Exception {
		test("enum E {green, red}",
				"(E, Enum, [0:5 - 0:6])\n" +
						"(green, EnumMember, [0:8 - 0:13])\n" +
						"(red, EnumMember, [0:15 - 0:18])");
	}

	/***/
	@Test
	public void openWorkspaceSymbolsTypeAliasTest() throws Exception {
		test("class A {}; type T = A;",
				"(A, Class, [0:6 - 0:7])\n" +
						"(T, Interface, [0:17 - 0:18])");
	}

}
