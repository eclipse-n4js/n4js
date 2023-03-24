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
public class OutgoingCallHierarchyTest extends AbstractCallHierarchyTest {

	/** */
	@Test
	public void test_function_1a() throws Exception {
		testOutgoingCallsAtCursor("""
				function f<|>() { g(); }
				function g() {}
				""", """
				((test-project/src/MyModule.n4js, g, Function, [1:0 - 2:0], [1:9 - 1:10]), [[0:15 - 0:18]])""");
	}

	/** */
	@Test
	public void test_function_1b() throws Exception {
		testOutgoingCallsAtCursor("""
				function f() { g(); }
				function g<|>() {}
				""", "");
	}

	/** */
	@Test
	public void test_method_1a() throws Exception {
		testOutgoingCallsAtCursor("""
				class C {
					f<|>() { this.g(); }
					g() {}
				}
				""", """
				((test-project/src/MyModule.n4js, g, Method, [2:1 - 2:7], [2:1 - 2:2]), [[1:7 - 1:15]])""");
	}

	/** */
	@Test
	public void test_method_1b() throws Exception {
		testOutgoingCallsAtCursor("""
				class C {
					f() { this.g(); }
					g<|>() {}
				}
				""", "");
	}

	/** */
	@Test
	public void test_method_interface_1() throws Exception {
		testOutgoingCallsAtCursor("""
				interface I { m(); }
				class C implements I {
					@Override
					m() { f(); }
				}
				function f<|>() {}
				""", "");
	}

	/** */
	@Test
	public void test_method_interface_2a() throws Exception {
		testOutgoingCallsAtCursor("""
				interface I { m<|>(); }
				class C implements I {
					@Override
					m() { f(); }
				}
				function f() {
					new C().m();
				}
				""", """
				((test-project/src/MyModule.n4js, f, Function, [5:0 - 8:0], [5:9 - 5:10]), [[3:7 - 3:10]])""");
	}

	/** */
	@Test
	public void test_method_interface_3a() throws Exception {
		testOutgoingCallsAtCursor("""
				interface I { m(); }
				class C implements I {
					@Override
					m<|>() { f(); }
				}
				function f() {
					new C().m();
				}
				""", """
				((test-project/src/MyModule.n4js, f, Function, [5:0 - 8:0], [5:9 - 5:10]), [[3:7 - 3:10]])""");
	}

	/** */
	@Test
	public void test_method_interface_2b() throws Exception {
		testOutgoingCallsAtCursor("""
				interface I { m<|>(); }
				class C implements I {
					@Override
					m() { f(); }
				}
				function f() {
					new C().m();
				}
				""", """
				((test-project/src/MyModule.n4js, f, Function, [5:0 - 8:0], [5:9 - 5:10]), [[3:7 - 3:10]])""");
	}

	/** */
	@Test
	public void test_method_interface_3b() throws Exception {
		testOutgoingCallsAtCursor("""
				interface I { m(); }
				class C implements I {
					@Override
					m<|>() { f(); }
				}
				function f() {
					(new C() as I).m();
				}
				""", """
				((test-project/src/MyModule.n4js, f, Function, [5:0 - 8:0], [5:9 - 5:10]), [[3:7 - 3:10]])""");
	}

	/** */
	@Test
	public void test_method_interface_4a() throws Exception {
		testOutgoingCallsAtCursor("""
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
				((test-project/src/MyModule.n4js, f, Function, [13:0 - 16:0], [13:9 - 13:10]), [[7:7 - 7:10]])
				((test-project/src/MyModule.n4js, f, Function, [13:0 - 16:0], [13:9 - 13:10]), [[11:7 - 11:10]])""");
	}

	/** */
	@Test
	public void test_method_interface_4b() throws Exception {
		testOutgoingCallsAtCursor("""
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
				((test-project/src/MyModule.n4js, f, Function, [13:0 - 16:0], [13:9 - 13:10]), [[11:7 - 11:10]])""");
	}

	/** */
	@Test
	public void test_method_interface_4c() throws Exception {
		testOutgoingCallsAtCursor("""
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
				((test-project/src/MyModule.n4js, f, Function, [13:0 - 16:0], [13:9 - 13:10]), [[7:7 - 7:10]])""");
	}

	/** */
	@Test
	public void test_method_interface_4d() throws Exception {
		testOutgoingCallsAtCursor("""
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
				((test-project/src/MyModule.n4js, f, Function, [13:0 - 16:0], [13:9 - 13:10]), [[11:7 - 11:10]])""");
	}

}