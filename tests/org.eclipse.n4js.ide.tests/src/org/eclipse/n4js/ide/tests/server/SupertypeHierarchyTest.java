/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.server;

import org.eclipse.n4js.ide.tests.helper.server.AbstractSubtypeHierarchyTest;
import org.junit.Test;

/**
 * Test class for subtype hierarchy
 */
public class SupertypeHierarchyTest extends AbstractSubtypeHierarchyTest {

	/** */
	@Test
	public void test_class_1() throws Exception {
		testSupertypesAtCursor("""
				class C<|> {}
				class D1 extends C {}
				class D2 extends C {}
				""", "");
	}

	/** */
	@Test
	public void test_class_2() throws Exception {
		testSupertypesAtCursor("""
				class C {}
				class D1<|> extends C {}
				class D2 extends C {}
				""", """
				(test-project/src/MyModule.n4js, C, Class, [0:0 - 0:10], [0:6 - 0:7])""");
	}

	/** */
	@Test
	public void test_class_3() throws Exception {
		testSupertypesAtCursor("""
				class C {}
				class D<|> extends C {}
				class E extends D {}
				""", """
				(test-project/src/MyModule.n4js, C, Class, [0:0 - 0:10], [0:6 - 0:7])""");
	}

	/** */
	@Test
	public void test_interface_1() throws Exception {
		testSupertypesAtCursor("""
				interface I<|> {}
				interface J1 extends I {}
				interface J2 extends I {}
				""", "");
	}

	/** */
	@Test
	public void test_interface_2() throws Exception {
		testSupertypesAtCursor("""
				interface I {}
				interface J1<|> extends I {}
				interface J2 extends I {}
				""", """
				(test-project/src/MyModule.n4js, I, Interface, [0:0 - 0:14], [0:10 - 0:11])""");
	}

	/** */
	@Test
	public void test_interface_3() throws Exception {
		testSupertypesAtCursor("""
				interface I {}
				interface J<|> extends I {}
				interface K extends J {}
				""", """
				(test-project/src/MyModule.n4js, I, Interface, [0:0 - 0:14], [0:10 - 0:11])""");
	}

	/** */
	@Test
	public void test_interface_4() throws Exception {
		testSupertypesAtCursor("""
				interface I {}
				interface J {}
				interface K<|> extends I, J {}
				""", """
				(test-project/src/MyModule.n4js, I, Interface, [0:0 - 0:14], [0:10 - 0:11])
				(test-project/src/MyModule.n4js, J, Interface, [1:0 - 1:14], [1:10 - 1:11])""");
	}

	/** */
	@Test
	public void test_class_interface_1() throws Exception {
		testSupertypesAtCursor("""
				interface I<|> {}
				class C1 implements I {}
				class C2 implements I {}
				""", "");
	}

	/** */
	@Test
	public void test_class_interface_2() throws Exception {
		testSupertypesAtCursor("""
				interface I {}
				class C1<|> implements I {}
				class C2 implements I {}
				""", """
				(test-project/src/MyModule.n4js, I, Interface, [0:0 - 0:14], [0:10 - 0:11])""");
	}

	/** */
	@Test
	public void test_class_interface_3() throws Exception {
		testSupertypesAtCursor("""
				interface I {}
				interface J<|> extends I {}
				class C implements J {}
				""", """
				(test-project/src/MyModule.n4js, I, Interface, [0:0 - 0:14], [0:10 - 0:11])""");
	}

	/** */
	@Test
	public void test_class_interface_4() throws Exception {
		testSupertypesAtCursor("""
				interface I {}
				interface J {}
				class C<|> implements I, J {}
				""", """
				(test-project/src/MyModule.n4js, I, Interface, [0:0 - 0:14], [0:10 - 0:11])
				(test-project/src/MyModule.n4js, J, Interface, [1:0 - 1:14], [1:10 - 1:11])""");
	}

	/** */
	@Test
	public void test_class_interface_5() throws Exception {
		testSupertypesAtCursor("""
				interface I {}
				class C {}
				class D<|> extends C implements I {}
				""", """
				(test-project/src/MyModule.n4js, C, Class, [1:0 - 1:10], [1:6 - 1:7])
				(test-project/src/MyModule.n4js, I, Interface, [0:0 - 0:14], [0:10 - 0:11])""");
	}

}