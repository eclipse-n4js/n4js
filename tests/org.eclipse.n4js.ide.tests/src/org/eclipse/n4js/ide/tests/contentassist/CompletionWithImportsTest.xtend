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
						export class A2 {}'''];
	}

	@Test
	def void test01() {
		testAtCursor('''
			let x = new A1<|>
		''', ''' 
			(A1, Class, MA, , , 00000, , , , ([0:12 - 0:14], A1), [([0:0 - 0:0], import {A1} from "MA";
			)], [], , )
		''');
	}

	@Test
	def void test02() {
		testAtCursor('''
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
		testAtCursor('''
			import {A1 as Alias1} from "MA";
			let x = new A1<|>
		''', ''' 
			(Alias1, Class, alias for MA/A1, , , 00000, , , , ([1:12 - 1:14], Alias1), [], [], , )
		''');
	}

	@Test
	def void testAliasExists2() {
		testAtCursor('''
			import {A1 as Alias1} from "MA";
			let x = new Alias1<|>
		''', ''' 
			(Alias1, Class, alias for MA/A1, , , 00000, , , , ([1:12 - 1:18], Alias1), [], [], , )
		''');
	}

	@Test
	def void testAliasExists3() {
		testAtCursor('''
			import {A1 as B1} from "MA";
			B1<|>
		''', ''' 
			(B1, Class, alias for MA/A1, , , 00000, , , , ([1:0 - 1:2], B1), [], [], , )
			(B1, Class, via new alias Alias_MBA_B1 for MBA/B1
			
			Introduces the new alias 'Alias_MBA_B1' for element MBA/B1, , , 00001, , , , ([1:0 - 1:2], Alias_MBA_B1), [([0:28 - 0:28], 
			import {B1 as Alias_MBA_B1} from "MBA";)], [], , )
		''');
	}

	@Test
	def void testAliasExists4() {
		testAtCursor('''
			import {A2 as AliasA} from "MA";
			AliasA<|>;
		''', ''' 
			(AliasA, Class, alias for MA/A2, , , 00000, , , , ([1:0 - 1:6], AliasA), [], [], , )
		''');
	}

	@Test
	def void testAliasExists5() {
		testAtCursor('''
			import {A2 as AliasA} from "MA";
			Ali<|>;
		''', ''' 
			(AliasA, Class, alias for MA/A2, , , 00000, , , , ([1:0 - 1:3], AliasA), [], [], , )
		''');
	}

	@Test
	def void testAliasExists6() {
		testAtCursor('''
			import {A2 as Alias1} from "MA";
			import {A2 as Alias2} from "MBA";
			A2<|>;
		''', ''' 
			(Alias1, Class, alias for MA/A2, , , 00000, , , , ([2:0 - 2:2], Alias1), [], [], , )
			(Alias2, Class, alias for MBA/A2, , , 00001, , , , ([2:0 - 2:2], Alias2), [], [], , )
		''');
	}

	@Test
	def void testAliasExists7() {
		testAtCursor('''
			import {A2 as Alias1} from "MA";
			import {A2 as Alias2} from "MBA";
			Ali<|>;
		''', ''' 
			(Alias1, Class, alias for MA/A2, , , 00000, , , , ([2:0 - 2:3], Alias1), [], [], , )
			(Alias2, Class, alias for MBA/A2, , , 00001, , , , ([2:0 - 2:3], Alias2), [], [], , )
		''');
	}

	@Test
	def void testAliasExists8() {
		testAtCursor('''
			import {A2 as A2_Alias} from "MA";
			A2<|>;
		''', ''' 
			(A2, Class, MBA, , , 00000, , , , ([1:0 - 1:2], A2), [([0:34 - 0:34], 
			import {A2} from "MBA";)], [], , )
			(A2_Alias, Class, alias for MA/A2, , , 00001, , , , ([1:0 - 1:2], A2_Alias), [], [], , )
		''');
	}

	@Test
	def void testAliasExists9() {
		testAtCursor('''
			import {A2} from "MA";
			import {A2 as A2_Alias} from "MBA";
			A2<|>;
		''', ''' 
			(A2, Class, MA, , , 00000, , , , ([2:0 - 2:2], A2), [], [], , )
			(A2_Alias, Class, alias for MBA/A2, , , 00001, , , , ([2:0 - 2:2], A2_Alias), [], [], , )
		''');
	}

	@Test
	def void testAliasExists10() {
		testAtCursor('''
			import {A1 as A2} from "MA";
			A2<|>;
		''', ''' 
			(A2, Class, alias for MA/A1, , , 00000, , , , ([1:0 - 1:2], A2), [], [], , )
			(A2, Class, via new alias Alias_MA_A2 for MA/A2
			
			Introduces the new alias 'Alias_MA_A2' for element MA/A2, , , 00001, , , , ([1:0 - 1:2], Alias_MA_A2), [([0:28 - 0:28], 
			import {A2 as Alias_MA_A2} from "MA";)], [], , )
			(A2, Class, via new alias Alias_MBA_A2 for MBA/A2
			
			Introduces the new alias 'Alias_MBA_A2' for element MBA/A2, , , 00002, , , , ([1:0 - 1:2], Alias_MBA_A2), [([0:28 - 0:28], 
			import {A2 as Alias_MBA_A2} from "MBA";)], [], , )
		''');
	}

	@Test
	def void testAliasNecessary1() {
		testAtCursor('''
			import {A2} from "MA";
			let x = new A2<|>
		''', ''' 
			(A2, Class, MA, , , 00000, , , , ([1:12 - 1:14], A2), [], [], , )
			(A2, Class, via new alias Alias_MBA_A2 for MBA/A2
			
			Introduces the new alias 'Alias_MBA_A2' for element MBA/A2, , , 00001, , , , ([1:12 - 1:14], Alias_MBA_A2), [([0:22 - 0:22], 
			import {A2 as Alias_MBA_A2} from "MBA";)], [], , )
		''');
	}

	@Test
	def void testNamespaceExists1() {
		testAtCursor('''
			import * as NSMA from "MA";
			let x = new A1<|>
		''', ''' 
			(NSMA.A1, Class, MA/A1, , , 00000, , , , ([1:12 - 1:14], NSMA.A1), [], [], , )
		''');
	}

	@Test
	def void testNamespaceExists2() {
		testAtCursor('''
			import * as NSMA from "MA";
			let x = new NSMA.A1<|>
		''', ''' 
			(A1, Class, MA, , , 00000, , , , ([1:17 - 1:19], A1), [], [], , )
		''');
	}

	@Test
	def void testNamespaceExists3() {
		testAtCursor('''
			import * as NSMA from "MA";
			A2<|>
		''', ''' 
			(A2, Class, MBA, , , 00000, , , , ([1:0 - 1:2], A2), [([0:27 - 0:27], 
			import {A2} from "MBA";)], [], , )
			(NSMA.A2, Class, MA/A2, , , 00001, , , , ([1:0 - 1:2], NSMA.A2), [], [], , )
		''');
	}

	@Test
	def void testNamespaceExists4() {
		testAtCursor('''
			import * as A2 from "MA";
			A2<|>
		''', ''' 
			(A2, Color, A2, , , 00000, , , , ([1:0 - 1:2], A2), [], [], , )
			(A2.A2, Class, MA/A2, , , 00001, , , , ([1:0 - 1:2], A2.A2), [], [], , )
			(A2, Class, via new alias Alias_MBA_A2 for MBA/A2
			
			Introduces the new alias 'Alias_MBA_A2' for element MBA/A2, , , 00002, , , , ([1:0 - 1:2], Alias_MBA_A2), [([0:25 - 0:25], 
			import {A2 as Alias_MBA_A2} from "MBA";)], [], , )
		''');
	}

	@Test
	def void testBuiltinType1() {
		testAtCursor('''
			Obj<|>
		''', ''' 
			(Object, Text, Object, , , 00000, , , , ([0:0 - 0:3], Object), [], [], , )
		''');
	}

	@Test
	def void testBuiltinType2() {
		testAtCursor('''
			N4En<|>
		''', ''' 
			(N4Enum, Text, N4Enum, , , 00000, , , , ([0:0 - 0:4], N4Enum), [], [], , )
			(N4EnumType, Class, N4EnumType, , , 00001, , , , ([0:0 - 0:4], N4EnumType), [], [], , )
		''');
	}

	@Test
	def void testGlobal1() {
		testAtCursor('''
			isNaN<|>
		''', ''' 
			(isNaN, Text, isNaN, , , 00000, , , , ([0:0 - 0:5], isNaN), [], [], , )
		''');
	}

	@Test
	def void testInsideImportStatement1() {
		testAtCursor('''
			import {A<|>
		''', ''' 
			(A1, Class, MA, , , 00000, , , , ([0:8 - 0:9], A1), [], [], , )
			(A2, Class, MA, , , 00001, , , , ([0:8 - 0:9], A2), [], [], , )
			(A2, Class, MBA, , , 00002, , , , ([0:8 - 0:9], A2), [], [], , )
			(any, Text, any, , , 00003, , , , ([0:8 - 0:9], any), [], [], , )
			(Array, Text, Array, , , 00004, , , , ([0:8 - 0:9], Array), [], [], , )
		''');
	}

	@Test
	def void testInsideImportStatement2() {
		testAtCursor('''
			import {A<|> from "MA";
		''', ''' 
			(A1, Class, MA, , , 00000, , , , ([0:8 - 0:9], A1), [], [], , )
			(A2, Class, MA, , , 00001, , , , ([0:8 - 0:9], A2), [], [], , )
			(A2, Class, MBA, , , 00002, , , , ([0:8 - 0:9], A2), [], [], , )
			(any, Text, any, , , 00003, , , , ([0:8 - 0:9], any), [], [], , )
			(Array, Text, Array, , , 00004, , , , ([0:8 - 0:9], Array), [], [], , )
		''');
	}

	@Test
	def void testInsideImportStatement3() {
		testAtCursor('''
			import {A<|>} from "MA";
		''', ''' 
			(A1, Class, MA, , , 00000, , , , ([0:8 - 0:9], A1), [], [], , )
			(A2, Class, MA, , , 00001, , , , ([0:8 - 0:9], A2), [], [], , )
		''');
	}

}
