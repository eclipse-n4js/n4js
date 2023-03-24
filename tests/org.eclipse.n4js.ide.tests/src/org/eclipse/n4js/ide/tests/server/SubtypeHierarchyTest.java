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

import org.eclipse.n4js.ide.tests.helper.server.AbstractTypeHierarchyTest;
import org.junit.Test;

/**
 * Test class for subtype hierarchy
 */
public class SubtypeHierarchyTest extends AbstractTypeHierarchyTest {

	/** */
	@Test
	public void test_class_1() throws Exception {
		testSubtypesAtCursor("""
				class C<|> {}
				class D1 extends C {}
				class D2 extends C {}
				""", """
				(test-project/src/MyModule.n4js, D1, Class, [1:0 - 1:21], [1:6 - 1:8])
				(test-project/src/MyModule.n4js, D2, Class, [2:0 - 2:21], [2:6 - 2:8])""");
	}

	/** */
	@Test
	public void test_class_2() throws Exception {
		testSubtypesAtCursor("""
				class C {}
				class D1<|> extends C {}
				class D2 extends C {}
				""", "");
	}

	/** */
	@Test
	public void test_class_3() throws Exception {
		testSubtypesAtCursor("""
				class C {}
				class D<|> extends C {}
				class E extends D {}
				""", """
				(test-project/src/MyModule.n4js, E, Class, [2:0 - 2:20], [2:6 - 2:7])""");
	}

	/** */
	@Test
	public void test_interface_1() throws Exception {
		testSubtypesAtCursor("""
				interface I<|> {}
				interface J1 extends I {}
				interface J2 extends I {}
				""", """
				(test-project/src/MyModule.n4js, J1, Interface, [1:0 - 1:25], [1:10 - 1:12])
				(test-project/src/MyModule.n4js, J2, Interface, [2:0 - 2:25], [2:10 - 2:12])""");
	}

	/** */
	@Test
	public void test_interface_2() throws Exception {
		testSubtypesAtCursor("""
				interface I {}
				interface J1<|> extends I {}
				interface J2 extends I {}
				""", "");
	}

	/** */
	@Test
	public void test_interface_3() throws Exception {
		testSubtypesAtCursor("""
				interface I {}
				interface J<|> extends I {}
				interface K extends J {}
				""", """
				(test-project/src/MyModule.n4js, K, Interface, [2:0 - 2:24], [2:10 - 2:11])""");
	}

	/** */
	@Test
	public void test_class_interface_1() throws Exception {
		testSubtypesAtCursor("""
				interface I<|> {}
				class C1 implements I {}
				class C2 implements I {}
				""", """
				(test-project/src/MyModule.n4js, C1, Class, [1:0 - 1:24], [1:6 - 1:8])
				(test-project/src/MyModule.n4js, C2, Class, [2:0 - 2:24], [2:6 - 2:8])""");
	}

	/** */
	@Test
	public void test_class_interface_2() throws Exception {
		testSubtypesAtCursor("""
				interface I {}
				class C1<|> implements I {}
				class C2 implements I {}
				""", "");
	}

	/** */
	@Test
	public void test_class_interface_3() throws Exception {
		testSubtypesAtCursor("""
				interface I {}
				interface J<|> extends I {}
				class C implements J {}
				""", """
				(test-project/src/MyModule.n4js, C, Class, [2:0 - 2:23], [2:6 - 2:7])""");
	}

	/** */
	@Test
	public void test_class_interface_4a() throws Exception {
		testSubtypesAtCursor("""
				interface I<|> {}
				class C {}
				class D extends C implements I {}
				""", """
				(test-project/src/MyModule.n4js, D, Class, [2:0 - 2:33], [2:6 - 2:7])""");
	}

	/** */
	@Test
	public void test_class_interface_4b() throws Exception {
		testSubtypesAtCursor("""
				interface I {}
				class C<|> {}
				class D extends C implements I {}
				""", """
				(test-project/src/MyModule.n4js, D, Class, [2:0 - 2:33], [2:6 - 2:7])""");
	}

}