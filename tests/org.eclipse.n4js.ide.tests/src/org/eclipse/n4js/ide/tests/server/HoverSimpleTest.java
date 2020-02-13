/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.n4js.ide.tests.server;

import org.eclipse.xtext.testing.HoverTestConfiguration;
import org.junit.Test;

/**
 * Hover test class
 */
public class HoverSimpleTest extends AbstractHoverTest {

	/** const */
	@Test
	public void testConstNoDoc() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("const cnst = 'value'; cnst;");
		htc.setLine(0);
		htc.setColumn("const cnst = 'value'; cns".length());
		htc.setExpectedHover("[0:22 - 0:26] [] variable  cnst: string");

		test(htc);
	}

	/** const */
	@Test
	public void testConstWithDoc() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("/** JSDoc for cnst */const cnst = 'value'; cnst;");
		htc.setLine(0);
		htc.setColumn("/** JSDoc for cnst */const cnst = 'value'; cns".length());
		htc.setExpectedHover("[0:43 - 0:47] [] variable  cnst: string");

		test(htc);
	}

	/** const */
	@Test
	public void testConstWithDocOnDeclaration() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("/** JSDoc for cnst */const cnst = 'value'; cnst;");
		htc.setLine(0);
		htc.setColumn("/** JSDoc for cnst */const cns".length());
		htc.setExpectedHover("[0:27 - 0:31] [] variable  cnst: string");

		test(htc);
	}

	/** var */
	@Test
	public void testVarNoDoc() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("var vr = 'value'; vr;");
		htc.setLine(0);
		htc.setColumn("var vr = 'value'; v".length());
		htc.setExpectedHover("[0:18 - 0:20] [] variable  vr: string");

		test(htc);
	}

	/** var */
	@Test
	public void testVarWithDoc() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("/** JSDoc for vr */var vr = 'value'; vr;");
		htc.setLine(0);
		htc.setColumn("/** JSDoc for vr */var vr = 'value'; v".length());
		htc.setExpectedHover("[0:37 - 0:39] [] variable  vr: string");

		test(htc);
	}

	/** var */
	@Test
	public void testVarWithDocOnDeclaration() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("/** JSDoc for vr */var vr = 'value'; vr;");
		htc.setLine(0);
		htc.setColumn("/** JSDoc for vr */var v".length());
		htc.setExpectedHover("[0:23 - 0:25] [] variable  vr: string");

		test(htc);
	}

	/** let */
	@Test
	public void testLetNoDoc() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("let lt = 'value'; lt;");
		htc.setLine(0);
		htc.setColumn("let lt = 'value'; l".length());
		htc.setExpectedHover("[0:18 - 0:20] [] variable  lt: string");

		test(htc);
	}

	/** let */
	@Test
	public void testLetWithDoc() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("/** JSDoc for lt */let lt = 'value'; lt;");
		htc.setLine(0);
		htc.setColumn("/** JSDoc for lt */let lt = 'value'; l".length());
		htc.setExpectedHover("[0:37 - 0:39] [] variable  lt: string");

		test(htc);
	}

	/** let */
	@Test
	public void testLetWithDocOnDeclaration() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("/** JSDoc for lt */let lt = 'value'; lt;");
		htc.setLine(0);
		htc.setColumn("/** JSDoc for lt */let l".length());
		htc.setExpectedHover("[0:23 - 0:25] [] variable  lt: string");

		test(htc);
	}

	/** function */
	@Test
	public void testFunctionNoDoc() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("function fun() { console.log('hello'); } fun();");
		htc.setLine(0);
		htc.setColumn("function fun() { console.log('hello'); } fu".length());
		htc.setExpectedHover("[0:41 - 0:44] [] function fun(): void");

		test(htc);
	}

	/** function */
	@Test
	public void testFunctionWithDoc() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("/** JSDoc for fun */function fun() { console.log('hello'); } fun();");
		htc.setLine(0);
		htc.setColumn("/** JSDoc for fun */function fun() { console.log('hello'); } fu".length());
		htc.setExpectedHover("[0:66 - 0:69] [] function fun(): void, [markdown] JSDoc for fun");

		test(htc);
	}

	/** function */
	@Test
	public void testFunctionWithDocOnDeclaration() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("/** JSDoc for fun */function fun() { console.log('hello'); } fun();");
		htc.setLine(0);
		htc.setColumn("/** JSDoc for fun */function fu".length());
		htc.setExpectedHover("[0:66 - 0:69] [] function fun(): void, [markdown] JSDoc for fun");

		test(htc);
	}

	/** class */
	@Test
	public void testClassNoDoc() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("class AA { foo(a: AA) { } } class Main { main(a: AA) { a.foo(null); } }");
		htc.setLine(0);
		htc.setColumn("class AA { foo(a: AA) { } } class Main { main(a: A".length());
		htc.setExpectedHover("[0:49 - 0:51] [] class AA");

		test(htc);
	}

	/** class */
	@Test
	public void testClassWithDoc() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("/** JSDoc for AA */class AA { foo(a: AA) { } } class Main { main(a: AA) { a.foo(null); } }");
		htc.setLine(0);
		htc.setColumn("/** JSDoc for AA */class AA { foo(a: AA) { } } class Main { main(a: A".length());
		htc.setExpectedHover("[0:68 - 0:70] [] class AA, [markdown] JSDoc for AA");

		test(htc);
	}

	/** class */
	@Test
	public void testClassWithDocOnDeclaration() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("/** JSDoc for AA */class AA { foo(a: A) { } } class Main { main(a: A) { a.foo(null); } }");
		htc.setLine(0);
		htc.setColumn("/** JSDoc for AA */class A".length());
		htc.setExpectedHover("[0:25 - 0:27] [] class AA, [markdown] JSDoc for AA");

		test(htc);
	}

	/** method */
	@Test
	public void testMethodNoDoc() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("class A { foo(a: A) { } } class Main { main(a: A) { a.foo(null); } }");
		htc.setLine(0);
		htc.setColumn("class A { foo(a: A) { } } class Main { main(a: A) { a.fo".length());
		htc.setExpectedHover("[0:54 - 0:57] [] method foo(a: A): void");

		test(htc);
	}

	/** method */
	@Test
	public void testMethodWithDoc() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("class A { /** JSDoc for foo */foo(a: A) { } } class Main { main(a: A) { a.foo(null); } }");
		htc.setLine(0);
		htc.setColumn("class A { /** JSDoc for foo */foo(a: A) { } } class Main { main(a: A) { a.fo".length());
		htc.setExpectedHover("[0:74 - 0:77] [] method foo(a: A): void, [markdown] JSDoc for foo");

		test(htc);
	}

	/** method */
	@Test
	public void testMethodWithDocOnDeclaration() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("class A { /** JSDoc for foo */foo(a: A) { } } class Main { main(a: A) { a.foo(null); } }");
		htc.setLine(0);
		htc.setColumn("class A { /** JSDoc for foo */fo".length());
		htc.setExpectedHover("[0:30 - 0:33] [] method foo(a: A): void, [markdown] JSDoc for foo");

		test(htc);
	}

	/** datafield */
	@Test
	public void testDatafieldNoDoc() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("class A { aField: string; } let a = new A(); a.aField;");
		htc.setLine(0);
		htc.setColumn("class A { aField: string; } let a = new A(); a.aFie".length());
		htc.setExpectedHover("[0:47 - 0:53] [] field aField: string");

		test(htc);
	}

	/** datafield */
	@Test
	public void testDatafieldWithDoc() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("class A { /** JSDoc for aField */aField: string; } let a = new A(); a.aField;");
		htc.setLine(0);
		htc.setColumn("class A { /** JSDoc for aField */aField: string; } let a = new A(); a.aFie".length());
		htc.setExpectedHover("[0:70 - 0:76] [] field aField: string, [markdown] JSDoc for aField");

		test(htc);
	}

	/** datafield */
	@Test
	public void testDatafieldWithDocOnDeclaration() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("class A { /** JSDoc for aField */aField: string; } let a = new A(); a.field;");
		htc.setLine(0);
		htc.setColumn("class A { /** JSDoc for aField */aFie".length());
		htc.setExpectedHover("[0:33 - 0:39] [] field aField: string, [markdown] JSDoc for aField");

		test(htc);
	}

	/** getter */
	@Test
	public void testGetterNoDoc() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("class A { get gtr(): int { return 0; } } let a = new A(); a.gtr;");
		htc.setLine(0);
		htc.setColumn("class A { get gtr(): int { return 0; } } let a = new A(); a.gt".length());
		htc.setExpectedHover("[0:60 - 0:63] [] getter get gtr(): int");

		test(htc);
	}

	/** getter */
	@Test
	public void testGetterWithDoc() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("class A { /** JSDoc for gtr */get gtr(): int { return 0; } } let a = new A(); a.gtr;");
		htc.setLine(0);
		htc.setColumn("class A { /** JSDoc for gtr */get gtr(): int { return 0; } } let a = new A(); a.gt".length());
		htc.setExpectedHover("[0:80 - 0:83] [] getter get gtr(): int, [markdown] JSDoc for gtr");

		test(htc);
	}

	/** getter */
	@Test
	public void testGetterWithDocOnDeclaration() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("class A { /** JSDoc for gtr */get gtr(): int { return 0; } } let a = new A(); a.gtr;");
		htc.setLine(0);
		htc.setColumn("class A { /** JSDoc for gtr */get gt".length());
		htc.setExpectedHover("[0:34 - 0:37] [] getter get gtr(): int, [markdown] JSDoc for gtr");

		test(htc);
	}

	/** setter */
	@Test
	public void testSetterNoDoc() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("class A { set str(i: int) { } } let a = new A(); a.str = 0;");
		htc.setLine(0);
		htc.setColumn("class A { set str(i: int) { } } let a = new A(); a.st".length());
		htc.setExpectedHover("[0:51 - 0:54] [] setter set str(i: int)");

		test(htc);
	}

	/** setter */
	@Test
	public void testSetterWithDoc() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("class A { /** JSDoc for gtr */set str(i: int) { } } let a = new A(); a.str = 0;");
		htc.setLine(0);
		htc.setColumn("class A { /** JSDoc for gtr */set str(i: int) { } } let a = new A(); a.st".length());
		htc.setExpectedHover("[0:71 - 0:74] [] setter set str(i: int), [markdown] JSDoc for gtr");

		test(htc);
	}

	/** setter */
	@Test
	public void testSetterWithDocOnDeclaration() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("class A { /** JSDoc for gtr */set str(i: int) { } } let a = new A(); a.str = 0;");
		htc.setLine(0);
		htc.setColumn("class A { /** JSDoc for gtr */set st".length());
		htc.setExpectedHover("[0:34 - 0:37] [] setter set str(i: int), [markdown] JSDoc for gtr");

		test(htc);
	}

	/** parameter */
	@Test
	public void testParameterNoDocOnDeclaration() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("function fun(param: int) {} fun(3);");
		htc.setLine(0);
		htc.setColumn("function fun(par".length());
		htc.setExpectedHover("[0:13 - 0:18] [] parameter  param: int");

		test(htc);
	}

	/** parameter */
	@Test
	public void testParameterWithDocOnDeclaration() throws Exception {
		HoverTestConfiguration htc = new HoverTestConfiguration();
		htc.setModel("function fun(/** JSDoc for param */param: int) {} fun(3);");
		htc.setLine(0);
		htc.setColumn("function fun(/** JSDoc for param */par".length());
		htc.setExpectedHover("[0:35 - 0:40] [] parameter  param: int, [markdown] JSDoc for param");

		test(htc);
	}

}
