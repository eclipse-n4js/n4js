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

import org.junit.Ignore;
import org.junit.Test;

/**	
 * Hover test class for N4JS elements	
 */
public class HoverSimpleTest extends AbstractHoverTest {

	/** const */
	@Test
	public void testConstNoDoc() throws Exception {
		testAtCursor(
			"const cnst = 'value'; cns<|>t;",
			"[0:22 - 0:26] [[n4js] variable cnst: string]");
	}

	/** const */
	@Ignore("GH-1669")
	@Test
	public void testConstWithDoc() throws Exception {
		testAtCursor(
			"/** JSDoc for cnst */const cnst = 'value'; cns<|>t;",
			"[0:43 - 0:47] [[n4js] variable cnst: string, [markdown] JSDoc for cnst]");
	}

	/** const */
	@Ignore("GH-1669")
	@Test
	public void testConstWithDocOnDeclaration() throws Exception {
		testAtCursor(
			"/** JSDoc for cnst */const cns<|>t = 'value'; cnst;",
			"[0:27 - 0:31] [[n4js] variable cnst: string, [markdown] JSDoc for cnst]");
	}

	/** var */
	@Test
	public void testVarNoDoc() throws Exception {
		testAtCursor(
			"var vr = 'value'; v<|>r;",
			"[0:18 - 0:20] [[n4js] variable vr: string]");
	}

	/** var */
	@Ignore("GH-1669")
	@Test
	public void testVarWithDoc() throws Exception {
		testAtCursor(
			"/** JSDoc for vr */var vr = 'value'; vr<|>;",
			"[0:37 - 0:39] [[n4js] variable vr: string, [markdown] JSDoc for vr]");
	}

	/** var */
	@Ignore("GH-1669")
	@Test
	public void testVarWithDocOnDeclaration() throws Exception {
		testAtCursor(
			"/** JSDoc for vr */var v<|>r = 'value'; vr;",
			"[0:23 - 0:25] [[n4js] variable vr: string, [markdown] JSDoc for vr]");
	}

	/** let */
	@Test
	public void testLetNoDoc() throws Exception {
		testAtCursor(
			"let lt = 'value'; l<|>t;",
			"[0:18 - 0:20] [[n4js] variable lt: string]");
	}

	/** let */
	@Ignore("GH-1669")
	@Test
	public void testLetWithDoc() throws Exception {
		testAtCursor(
			"/** JSDoc for lt */let lt = 'value'; l<|>t;",
			"[0:37 - 0:39] [[n4js] variable lt: string, [markdown] JSDoc for lt]");
	}

	/** let */
	@Ignore("GH-1669")
	@Test
	public void testLetWithDocOnDeclaration() throws Exception {
		testAtCursor(
			"/** JSDoc for lt */let l<|>t = 'value'; lt;",
			"[0:23 - 0:25] [[n4js] variable lt: string, [markdown] JSDoc for lt]");
	}

	/** function */
	@Test
	public void testFunctionNoDoc() throws Exception {
		testAtCursor(
			"function fun() { console.log('hello'); } fu<|>n();",
			"[0:41 - 0:44] [[n4js] function fun(): void]");
	}

	/** function */
	@Test
	public void testFunctionWithDoc() throws Exception {
		testAtCursor(
			"/** JSDoc for fun */function fun() { console.log('hello'); } fu<|>n();",
			"[0:61 - 0:64] [[n4js] function fun(): void, [markdown] JSDoc for fun]");
	}

	/** function */
	@Test
	public void testFunctionWithDocOnDeclaration() throws Exception {
		testAtCursor(
			"/** JSDoc for fun */function fu<|>n() { console.log('hello'); } fun();",
			"[0:29 - 0:32] [[n4js] function fun(): void, [markdown] JSDoc for fun]");
	}

	/** class */
	@Test
	public void testClassNoDoc() throws Exception {
		testAtCursor(
			"class AA { foo(a: AA) { } } class Main { main(a: A<|>A) { a.foo(null); } }",
			"[0:49 - 0:51] [[n4js] class AA]");
	}

	/** class */
	@Test
	public void testClassWithDoc() throws Exception {
		testAtCursor(
			"/** JSDoc for AA */class AA { foo(a: AA) { } } class Main { main(a: A<|>A) { a.foo(null); } }",
			"[0:68 - 0:70] [[n4js] class AA, [markdown] JSDoc for AA]");
	}

	/** class */
	@Test
	public void testClassWithDocOnDeclaration() throws Exception {
		testAtCursor(
			"/** JSDoc for AA */class A<|>A { foo(a: A) { } } class Main { main(a: A) { a.foo(null); } }",
			"[0:25 - 0:27] [[n4js] class AA, [markdown] JSDoc for AA]");
	}

	/** generic class */
	@Test
	public void testGenericClassWithDoc() throws Exception {
		testAtCursor(
			"/** JSDoc for AAT */class AAT<T> { foo(t: T) { } } let aat: AA<|>T<String>; aat;",
			"[0:60 - 0:63] [[n4js] class AAT<String>, [markdown] JSDoc for AAT]");
	}

	/** generic class */
	@Test
	public void testGenericClassWithDocOnDeclaration() throws Exception {
		testAtCursor(
			"/** JSDoc for AAT */class AA<|>T<T> { foo(t: T) { } } let aat: AAT<String>; aat;",
			"[0:26 - 0:29] [[n4js] class AAT<T>, [markdown] JSDoc for AAT]");
	}

	/** generic class */
	@Test
	public void testGenericClassWithDocOnInstance() throws Exception {
		testAtCursor(
			"/** JSDoc for AAT */class AAT<T> { foo(t: T) { } } let aat: AA<|>T<String>; aat;",
			"[0:60 - 0:63] [[n4js] class AAT<String>, [markdown] JSDoc for AAT]");
	}

	/** type variable */
	@Test
	public void testTypeVariableNoDoc() throws Exception {
		testAtCursor(
			"class TVC<T<|>V extends String> { foo(tv: TV) { } } let tvc: TVC<String>; tvc;",
			"[0:10 - 0:12] [[n4js] type variable TV extends String]");
	}

	/** type variable */
	@Test
	public void testTypeVariableWithDocOnDeclaration() throws Exception {
		testAtCursor(
			"class TVC</** JSDoc for TV */T<|>V extends String> { foo(tv: TV) { } } let tvc: TVC<String>; tvc;",
			"[0:29 - 0:31] [[n4js] type variable TV extends String, [markdown] JSDoc for TV]");
	}
	
	/** type variable */	
	@Test	
	@Ignore("see doc inside")	
	public void testTypeVariableWithDoc() throws Exception {
		/**	
		 * Note: To enable JSDoc on TypeVariables, mind org.eclipse.n4js.n4JS.N4JSASTUtils#getCorrespondingASTNode()	
		 * TypeVariables are not subtype of SyntaxRelatedTElement and hence their JSDoc is not displayed.	
		 */	
		testAtCursor(
			"class TVC</** JSDoc for TV */TV extends String> { foo(tv: T<|>V) { } } let tvc: TVC<String>; tvc;",
			"[0:58 - 0:60] [[n4js] type variable TV, [markdown] JSDoc for TV]");	
	}


	/** method */
	@Test
	public void testMethodNoDoc() throws Exception {
		testAtCursor(
			"class A { foo(a: A) { } } class Main { main(a: A) { a.fo<|>o(null); } }",
			"[0:54 - 0:57] [[n4js] method foo(a: A): void]");
	}

	/** method */
	@Test
	public void testMethodWithDoc() throws Exception {
		testAtCursor(
			"class A { /** JSDoc for foo */foo(a: A) { } } class Main { main(a: A) { a.fo<|>o(null); } }",
			"[0:74 - 0:77] [[n4js] method foo(a: A): void, [markdown] JSDoc for foo]");
	}

	/** method */
	@Test
	public void testMethodWithDocOnDeclaration() throws Exception {
		testAtCursor(
			"class A { /** JSDoc for foo */fo<|>o(a: A) { } } class Main { main(a: A) { a.foo(null); } }",
			"[0:30 - 0:33] [[n4js] method foo(a: A): void, [markdown] JSDoc for foo]");
	}

	/** datafield */
	@Test
	public void testDatafieldNoDoc() throws Exception {
		testAtCursor(
			"class A { aField: string; } let a = new A(); a.aFie<|>ld;",
			"[0:47 - 0:53] [[n4js] field aField: string]");
	}

	/** datafield */
	@Test
	public void testDatafieldWithDoc() throws Exception {
		testAtCursor(
			"class A { /** JSDoc for aField */aField: string; } let a = new A(); a.aFie<|>ld;",
			"[0:70 - 0:76] [[n4js] field aField: string, [markdown] JSDoc for aField]");
	}

	/** datafield */
	@Test
	public void testDatafieldWithDocOnDeclaration() throws Exception {
		testAtCursor(
			"class A { /** JSDoc for aField */aFie<|>ld: string; } let a = new A(); a.field;",
			"[0:33 - 0:39] [[n4js] field aField: string, [markdown] JSDoc for aField]");
	}

	/** getter */
	@Test
	public void testGetterNoDoc() throws Exception {
		testAtCursor(
			"class A { get gtr(): int { return 0; } } let a = new A(); a.gt<|>r;",
			"[0:60 - 0:63] [[n4js] getter get gtr(): int]");
	}

	/** getter */
	@Test
	public void testGetterWithDoc() throws Exception {
		testAtCursor(
			"class A { /** JSDoc for gtr */get gtr(): int { return 0; } } let a = new A(); a.gt<|>r;",
			"[0:80 - 0:83] [[n4js] getter get gtr(): int, [markdown] JSDoc for gtr]");
	}

	/** getter */
	@Test
	public void testGetterWithDocOnDeclaration() throws Exception {
		testAtCursor(
			"class A { /** JSDoc for gtr */get gt<|>r(): int { return 0; } } let a = new A(); a.gtr;",
			"[0:34 - 0:37] [[n4js] getter get gtr(): int, [markdown] JSDoc for gtr]");
	}

	/** setter */
	@Test
	public void testSetterNoDoc() throws Exception {
		testAtCursor(
			"class A { set str(i: int) { } } let a = new A(); a.st<|>r = 0;",
			"[0:51 - 0:54] [[n4js] setter set str(i: int)]");
	}

	/** setter */
	@Test
	public void testSetterWithDoc() throws Exception {
		testAtCursor(
			"class A { /** JSDoc for gtr */set str(i: int) { } } let a = new A(); a.st<|>r = 0;",
			"[0:71 - 0:74] [[n4js] setter set str(i: int), [markdown] JSDoc for gtr]");
	}

	/** setter */
	@Test
	public void testSetterWithDocOnDeclaration() throws Exception {
		testAtCursor(
			"class A { /** JSDoc for gtr */set st<|>r(i: int) { } } let a = new A(); a.str = 0;",
			"[0:34 - 0:37] [[n4js] setter set str(i: int), [markdown] JSDoc for gtr]");
	}

	/** parameter */
	@Test
	public void testParameterNoDocOnDeclaration() throws Exception {
		testAtCursor(
			"function fun(par<|>am: int) {} fun(3);",
			"[0:13 - 0:18] [[n4js] parameter param: int]");
	}

	/** parameter */
	@Test
	public void testParameterWithDocOnDeclaration() throws Exception {
		testAtCursor(
			"function fun(/** JSDoc for param */par<|>am: int) {} fun(3);",
			"[0:35 - 0:40] [[n4js] parameter param: int, [markdown] JSDoc for param]");
	}
	
}