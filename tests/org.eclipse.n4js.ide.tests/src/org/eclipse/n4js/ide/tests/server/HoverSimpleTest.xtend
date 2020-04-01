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
package org.eclipse.n4js.ide.tests.server

import org.junit.Test
import org.junit.Ignore

/**
 *
 */
class HoverSimpleTest extends AbstractHoverTest {

	/** const */
	@Test
	def void testConstNoDoc() throws Exception {
		testAtCursor(
			'''const cnst = 'value'; cns<|>t;''',
			"[0:22 - 0:26] [[n4js] variable cnst: string]");
	}

	/** const */
	@Ignore("GH-1669")
	@Test
	def void testConstWithDoc() throws Exception {
		testAtCursor(
			'''/** JSDoc for cnst */const cnst = 'value'; cns<|>t;''',
			"[0:43 - 0:47] [[n4js] variable cnst: string, [markdown] JSDoc for cnst]");
	}

	/** const */
	@Ignore("GH-1669")
	@Test
	def void testConstWithDocOnDeclaration() throws Exception {
		testAtCursor(
			'''/** JSDoc for cnst */const cns<|>t = 'value'; cnst;''',
			"[0:27 - 0:31] [[n4js] variable cnst: string, [markdown] JSDoc for cnst]");
	}

	/** var */
	@Test
	def void testVarNoDoc() throws Exception {
		testAtCursor(
			'''var vr = 'value'; v<|>r;''',
			"[0:18 - 0:20] [[n4js] variable vr: string]");
	}

	/** var */
	@Ignore("GH-1669")
	@Test
	def void testVarWithDoc() throws Exception {
		testAtCursor(
			'''/** JSDoc for vr */var vr = 'value'; vr<|>;''',
			"[0:37 - 0:39] [[n4js] variable vr: string, [markdown] JSDoc for vr]");
	}

	/** var */
	@Ignore("GH-1669")
	@Test
	def void testVarWithDocOnDeclaration() throws Exception {
		testAtCursor(
			'''/** JSDoc for vr */var v<|>r = 'value'; vr;''',
			"[0:23 - 0:25] [[n4js] variable vr: string, [markdown] JSDoc for vr]");
	}

	/** let */
	@Test
	def void testLetNoDoc() throws Exception {
		testAtCursor(
			'''let lt = 'value'; l<|>t;''',
			"[0:18 - 0:20] [[n4js] variable lt: string]");
	}

	/** let */
	@Ignore("GH-1669")
	@Test
	def void testLetWithDoc() throws Exception {
		testAtCursor(
			'''/** JSDoc for lt */let lt = 'value'; l<|>t;''',
			"[0:37 - 0:39] [[n4js] variable lt: string, [markdown] JSDoc for lt]");
	}

	/** let */
	@Ignore("GH-1669")
	@Test
	def void testLetWithDocOnDeclaration() throws Exception {
		testAtCursor(
			'''/** JSDoc for lt */let l<|>t = 'value'; lt;''',
			"[0:23 - 0:25] [[n4js] variable lt: string, [markdown] JSDoc for lt]");
	}

	/** function */
	@Test
	def void testFunctionNoDoc() throws Exception {
		testAtCursor(
			'''function fun() { console.log('hello'); } fu<|>n();''',
			"[0:41 - 0:44] [[n4js] function fun(): void]");
	}

	/** function */
	@Test
	def void testFunctionWithDoc() throws Exception {
		testAtCursor(
			'''/** JSDoc for fun */function fun() { console.log('hello'); } fu<|>n();''',
			"[0:61 - 0:64] [[n4js] function fun(): void, [markdown] JSDoc for fun]");
	}

	/** function */
	@Test
	def void testFunctionWithDocOnDeclaration() throws Exception {
		testAtCursor(
			'''/** JSDoc for fun */function fu<|>n() { console.log('hello'); } fun();''',
			"[0:29 - 0:32] [[n4js] function fun(): void, [markdown] JSDoc for fun]");
	}

	/** class */
	@Test
	def void testClassNoDoc() throws Exception {
		testAtCursor(
			'''class AA { foo(a: AA) { } } class Main { main(a: A<|>A) { a.foo(null); } }''',
			"[0:49 - 0:51] [[n4js] class AA]");
	}

	/** class */
	@Test
	def void testClassWithDoc() throws Exception {
		testAtCursor(
			'''/** JSDoc for AA */class AA { foo(a: AA) { } } class Main { main(a: A<|>A) { a.foo(null); } }''',
			"[0:68 - 0:70] [[n4js] class AA, [markdown] JSDoc for AA]");
	}

	/** class */
	@Test
	def void testClassWithDocOnDeclaration() throws Exception {
		testAtCursor(
			'''/** JSDoc for AA */class A<|>A { foo(a: A) { } } class Main { main(a: A) { a.foo(null); } }''',
			"[0:25 - 0:27] [[n4js] class AA, [markdown] JSDoc for AA]");
	}

	/** generic class */
	@Test
	def void testGenericClassWithDoc() throws Exception {
		testAtCursor(
			'''/** JSDoc for AAT */class AAT<T> { foo(t: T) { } } let aat: AA<|>T<String>; aat;''',
			"[0:60 - 0:63] [[n4js] class AAT<String>, [markdown] JSDoc for AAT]");
	}

	/** generic class */
	@Test
	def void testGenericClassWithDocOnDeclaration() throws Exception {
		testAtCursor(
			'''/** JSDoc for AAT */class AA<|>T<T> { foo(t: T) { } } let aat: AAT<String>; aat;''',
			"[0:26 - 0:29] [[n4js] class AAT<T>, [markdown] JSDoc for AAT]");
	}

	/** generic class */
	@Test
	def void testGenericClassWithDocOnInstance() throws Exception {
		testAtCursor(
			'''/** JSDoc for AAT */class AAT<T> { foo(t: T) { } } let aat: AA<|>T<String>; aat;''',
			"[0:60 - 0:63] [[n4js] class AAT<String>, [markdown] JSDoc for AAT]");
	}

	/** type variable */
	@Test
	def void testTypeVariableNoDoc() throws Exception {
		testAtCursor(
			'''class TVC<T<|>V extends String> { foo(tv: TV) { } } let tvc: TVC<String>; tvc;''',
			"[0:10 - 0:12] [[n4js] type variable TV extends String]");
	}

	/** type variable */
	@Test
	def void testTypeVariableWithDocOnDeclaration() throws Exception {
		testAtCursor(
			'''class TVC</** JSDoc for TV */T<|>V extends String> { foo(tv: TV) { } } let tvc: TVC<String>; tvc;''',
			"[0:29 - 0:31] [[n4js] type variable TV extends String, [markdown] JSDoc for TV]");
	}


	/** method */
	@Test
	def void testMethodNoDoc() throws Exception {
		testAtCursor(
			'''class A { foo(a: A) { } } class Main { main(a: A) { a.fo<|>o(null); } }''',
			"[0:54 - 0:57] [[n4js] method foo(a: A): void]");
	}

	/** method */
	@Test
	def void testMethodWithDoc() throws Exception {
		testAtCursor(
			'''class A { /** JSDoc for foo */foo(a: A) { } } class Main { main(a: A) { a.fo<|>o(null); } }''',
			"[0:74 - 0:77] [[n4js] method foo(a: A): void, [markdown] JSDoc for foo]");
	}

	/** method */
	@Test
	def void testMethodWithDocOnDeclaration() throws Exception {
		testAtCursor(
			'''class A { /** JSDoc for foo */fo<|>o(a: A) { } } class Main { main(a: A) { a.foo(null); } }''',
			"[0:30 - 0:33] [[n4js] method foo(a: A): void, [markdown] JSDoc for foo]");
	}

	/** datafield */
	@Test
	def void testDatafieldNoDoc() throws Exception {
		testAtCursor(
			'''class A { aField: string; } let a = new A(); a.aFie<|>ld;''',
			"[0:47 - 0:53] [[n4js] field aField: string]");
	}

	/** datafield */
	@Test
	def void testDatafieldWithDoc() throws Exception {
		testAtCursor(
			'''class A { /** JSDoc for aField */aField: string; } let a = new A(); a.aFie<|>ld;''',
			"[0:70 - 0:76] [[n4js] field aField: string, [markdown] JSDoc for aField]");
	}

	/** datafield */
	@Test
	def void testDatafieldWithDocOnDeclaration() throws Exception {
		testAtCursor(
			'''class A { /** JSDoc for aField */aFie<|>ld: string; } let a = new A(); a.field;''',
			"[0:33 - 0:39] [[n4js] field aField: string, [markdown] JSDoc for aField]");
	}

	/** getter */
	@Test
	def void testGetterNoDoc() throws Exception {
		testAtCursor(
			'''class A { get gtr(): int { return 0; } } let a = new A(); a.gt<|>r;''',
			"[0:60 - 0:63] [[n4js] getter get gtr(): int]");
	}

	/** getter */
	@Test
	def void testGetterWithDoc() throws Exception {
		testAtCursor(
			'''class A { /** JSDoc for gtr */get gtr(): int { return 0; } } let a = new A(); a.gt<|>r;''',
			"[0:80 - 0:83] [[n4js] getter get gtr(): int, [markdown] JSDoc for gtr]");
	}

	/** getter */
	@Test
	def void testGetterWithDocOnDeclaration() throws Exception {
		testAtCursor(
			'''class A { /** JSDoc for gtr */get gt<|>r(): int { return 0; } } let a = new A(); a.gtr;''',
			"[0:34 - 0:37] [[n4js] getter get gtr(): int, [markdown] JSDoc for gtr]");
	}

	/** setter */
	@Test
	def void testSetterNoDoc() throws Exception {
		testAtCursor(
			'''class A { set str(i: int) { } } let a = new A(); a.st<|>r = 0;''',
			"[0:51 - 0:54] [[n4js] setter set str(i: int)]");
	}

	/** setter */
	@Test
	def void testSetterWithDoc() throws Exception {
		testAtCursor(
			'''class A { /** JSDoc for gtr */set str(i: int) { } } let a = new A(); a.st<|>r = 0;''',
			"[0:71 - 0:74] [[n4js] setter set str(i: int), [markdown] JSDoc for gtr]");
	}

	/** setter */
	@Test
	def void testSetterWithDocOnDeclaration() throws Exception {
		testAtCursor(
			'''class A { /** JSDoc for gtr */set st<|>r(i: int) { } } let a = new A(); a.str = 0;''',
			"[0:34 - 0:37] [[n4js] setter set str(i: int), [markdown] JSDoc for gtr]");
	}

	/** parameter */
	@Test
	def void testParameterNoDocOnDeclaration() throws Exception {
		testAtCursor(
			'''function fun(par<|>am: int) {} fun(3);''',
			"[0:13 - 0:18] [[n4js] parameter param: int]");
	}

	/** parameter */
	@Test
	def void testParameterWithDocOnDeclaration() throws Exception {
		testAtCursor(
			'''function fun(/** JSDoc for param */par<|>am: int) {} fun(3);''',
			"[0:35 - 0:40] [[n4js] parameter param: int, [markdown] JSDoc for param]");
	}
	
}