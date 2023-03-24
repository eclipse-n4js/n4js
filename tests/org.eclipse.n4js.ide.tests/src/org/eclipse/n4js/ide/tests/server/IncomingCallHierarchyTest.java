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

import org.eclipse.n4js.ide.tests.helper.server.AbstractCallHierarchyTest;
import org.junit.Test;

/**
 * Test class for incoming call hierarchy
 */
public class IncomingCallHierarchyTest extends AbstractCallHierarchyTest {

	/** */
	@Test
	public void test_function_1a() throws Exception {
		testIncomingCallsAtCursor("""
				function f<|>() { g(); }
				function g() {}
				""", "");
	}

	/** */
	@Test
	public void test_function_1b() throws Exception {
		testIncomingCallsAtCursor("""
				function f() { g(); }
				function g<|>() {}
				""", """
				((test-project/src/MyModule.n4js, f, Function, [0:0 - 0:21], [0:9 - 0:10]), [])""");
	}

	/** */
	@Test
	public void test_method_1a() throws Exception {
		testIncomingCallsAtCursor("""
				class C {
					f<|>() { this.g(); }
					g() {}
				}
				""", "");
	}

	/** */
	@Test
	public void test_method_1b() throws Exception {
		testIncomingCallsAtCursor("""
				class C {
					f() { this.g(); }
					g<|>() {}
				}
				""", """
				((test-project/src/MyModule.n4js, f, Method, [1:1 - 1:18], [1:1 - 1:2]), [])""");
	}

	/** */
	@Test
	public void test_method_interface_1() throws Exception {
		testIncomingCallsAtCursor("""
				interface I { m(); }
				class C implements I {
					@Override
					m() { f(); }
				}
				function f<|>() {}
				""", """
				((test-project/src/MyModule.n4js, m, Method, [2:1 - 3:13], [3:1 - 3:2]), [])""");
	}

	/** */
	@Test
	public void test_method_interface_2a() throws Exception {
		testIncomingCallsAtCursor("""
				interface I { m<|>(); }
				class C implements I {
					@Override
					m() { f(); }
				}
				function f() {
					new C().m();
				}
				""", """
				((test-project/src/MyModule.n4js, m, Method, [2:1 - 3:13], [3:1 - 3:2]), [])""");
	}

	/** */
	@Test
	public void test_method_interface_3a() throws Exception {
		testIncomingCallsAtCursor("""
				interface I { m(); }
				class C implements I {
					@Override
					m<|>() { f(); }
				}
				function f() {
					new C().m();
				}
				""", """
				((test-project/src/MyModule.n4js, f, Function, [5:0 - 8:0], [5:9 - 5:10]), [])""");
	}

	/** */
	@Test
	public void test_method_interface_2b() throws Exception {
		testIncomingCallsAtCursor("""
				interface I { m<|>(); }
				class C implements I {
					@Override
					m() { f(); }
				}
				function f() {
					new C().m();
				}
				""", """
				((test-project/src/MyModule.n4js, m, Method, [2:1 - 3:13], [3:1 - 3:2]), [])""");
	}

	/** */
	@Test
	public void test_method_interface_3b() throws Exception {
		testIncomingCallsAtCursor("""
				interface I { m(); }
				class C implements I {
					@Override
					m<|>() { f(); }
				}
				function f() {
					(new C() as I).m();
				}
				""", """
				((test-project/src/MyModule.n4js, f, Function, [5:0 - 8:0], [5:9 - 5:10]), [])""");
	}

	/** */
	@Test
	public void test_method_interface_4a() throws Exception {
		testIncomingCallsAtCursor("""
				interface I { m<|>(); }
				interface J extends I {
					@Override
					m();
				}
				class CI implements I {
					@Override
					m() { f(); }
				}
				class CJ implements J {
					@Override
					m() { f(); }
				}
				function f() {
					(new CJ() as I).m();
				}
				""", """
				((test-project/src/MyModule.n4js, m, Method, [2:1 - 3:5], [3:1 - 3:2]), [])
				((test-project/src/MyModule.n4js, m, Method, [6:1 - 7:13], [7:1 - 7:2]), [])
				((test-project/src/MyModule.n4js, m, Method, [10:1 - 11:13], [11:1 - 11:2]), [])""");
	}

	/** */
	@Test
	public void test_method_interface_4b() throws Exception {
		testIncomingCallsAtCursor("""
				interface I { m(); }
				interface J extends I {
					@Override
					m<|>();
				}
				class CI implements I {
					@Override
					m() { f(); }
				}
				class CJ implements J {
					@Override
					m() { f(); }
				}
				function f() {
					(new CJ() as I).m();
				}
				""", """
				((test-project/src/MyModule.n4js, f, Function, [13:0 - 16:0], [13:9 - 13:10]), [])""");
	}

	/** */
	@Test
	public void test_method_interface_4c() throws Exception {
		testIncomingCallsAtCursor("""
				interface I { m(); }
				interface J extends I {
					@Override
					m();
				}
				class CI implements I {
					@Override
					m<|>() { f(); }
				}
				class CJ implements J {
					@Override
					m() { f(); }
				}
				function f() {
					(new CJ() as I).m();
				}
				""", """
				((test-project/src/MyModule.n4js, f, Function, [13:0 - 16:0], [13:9 - 13:10]), [])""");
	}

	/** */
	@Test
	public void test_method_interface_4d() throws Exception {
		testIncomingCallsAtCursor("""
				interface I { m(); }
				interface J extends I {
					@Override
					m();
				}
				class CI implements I {
					@Override
					m() { f(); }
				}
				class CJ implements J {
					@Override
					m<|>() { f(); }
				}
				function f() {
					(new CJ() as I).m();
				}
				""", """
				((test-project/src/MyModule.n4js, f, Function, [13:0 - 16:0], [13:9 - 13:10]), [])""");
	}

}