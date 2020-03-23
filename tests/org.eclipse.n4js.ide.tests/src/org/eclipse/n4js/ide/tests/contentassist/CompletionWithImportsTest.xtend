/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.contentassist;

import java.util.List
import org.eclipse.n4js.ide.tests.server.AbstractCompletionTest
import org.junit.Test

/**
 * Code completion tests for scenarios that also might add an import statement 
 */
public class CompletionWithImportsTest extends AbstractCompletionTest {

	/** Some default modules that export a number of classes for all tests. */
	override final List<Pair<String, String>> getDefaultTestModules() {
		return #[
			"MA"  -> '''
						export class A1 {}
						export class A2 {}''',
			"MBA" -> '''
						export class B1 {}
						export class A2 {}''',
			"MC"   -> '''
						export class C1 {}
						export class C2 {}''',
			"Def" -> '''
						export class Def01 {}
						export class Def02 {}
						export class Def03 {}
						export class Def04 {}
						export default class DefCls {}'''];
	}

	@Test
	def void test01() {
		test('''
			let x = new A1<|>
		''', ''' 
			(A1, Class, MA, , , 00000, , , , ([0:12 - 0:14], A1), [([0:0 - 0:0], import {A1} from "MA";
			)], [], , )
		''');
	}

	@Test
	def void test02() {
		test('''
			let x = new A2<|>
		''', ''' 
			(A2, Class, MA, , , 00000, , , , ([0:12 - 0:14], A2), [([0:0 - 0:0], import {A2} from "MA";
			)], [], , )
			(A2, Class, MBA, , , 00001, , , , ([0:12 - 0:14], A2), [([0:0 - 0:0], import {A2} from "MBA";
			)], [], , )
		''');
	}

	@Test
	def void testAliasExists1() {
		test('''
			import {A1 as Alias1} from "MA";
			let x = new A1<|>
		''', ''' 
			(Alias1, Class, alias for MA/A1, , , 00000, , , , ([1:12 - 1:14], Alias1), [], [], , )
		''');
	}

	@Test
	def void testAliasExists2() {
		test('''
			import {A1 as Alias1} from "MA";
			let x = new Alias1<|>
		''', ''' 
			(Alias1, Class, alias for MA/A1, , , 00000, , , , ([1:12 - 1:18], Alias1), [], [], , )
		''');
	}

	@Test
	def void testAliasExists3() {
		test('''
			import {A1 as B1} from "MA";
			B1<|>
		''', ''' 
			(B1, Class, alias for MA/A1, , , 00000, , , , ([1:0 - 1:2], B1), [], [], , )
			(MBA/B1, Class, via new alias AliasB1, , , 00001, , , , ([1:0 - 1:2], AliasB1), [([0:28 - 0:28], 
			import {B1 as AliasB1} from "MBA";)], [], , )
		''');
	}

	@Test
	def void testAliasExists4() {
		test('''
			import {A2 as AliasA} from "MA";
			AliasA<|>;
		''', ''' 
			(AliasA, Class, alias for MA/A2, , , 00000, , , , ([1:0 - 1:6], AliasA), [], [], , )
		''');
	}

	@Test
	def void testAliasExists5() {
		test('''
			import {A2 as AliasA} from "MA";
			Ali<|>;
		''', ''' 
			(AliasA, Class, alias for MA/A2, , , 00000, , , , ([1:0 - 1:3], AliasA), [], [], , )
		''');
	}

	@Test
	def void testAliasNecessary1() {
		test('''
			import {A2} from "MA";
			let x = new A2<|>
		''', ''' 
			(A2, Class, MA, , , 00000, , , , ([1:12 - 1:14], A2), [], [], , )
			(MBA/A2, Class, via new alias AliasA2, , , 00001, , , , ([1:12 - 1:14], AliasA2), [([0:22 - 0:22], 
			import {A2 as AliasA2} from "MBA";)], [], , )
		''');
	}

	@Test
	def void testNamespaceExists1() {
		test('''
			import * as NSMA from "MA";
			let x = new A1<|>
		''', ''' 
			(NSMA.A1, Class, MA/A1, , , 00000, , , , ([1:12 - 1:14], NSMA.A1), [], [], , )
		''');
	}

	@Test
	def void testNamespaceExists2() {
		test('''
			import * as NSMA from "MA";
			let x = new NSMA.A1<|>
		''', ''' 
			(A1, Class, MA, , , 00000, , , , ([1:17 - 1:19], A1), [], [], , )
		''');
	}
}
