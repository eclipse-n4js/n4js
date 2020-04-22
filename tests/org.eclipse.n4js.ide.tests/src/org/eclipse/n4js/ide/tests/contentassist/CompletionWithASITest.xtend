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
 * Code completion tests to ensure the correct behavior in the presence and absence of 
 * automatically inserted semicolons (ASI). 
 */
public class CompletionWithASITest extends AbstractCompletionTest {

	/** Some default modules that export a number of classes for all tests. */
	override final List<Pair<String, String>> getDefaultTestProject() {
		return #[
			"ImportMe"  -> '''
						export class A01 {}
						export class A02 {}
						export class A03 {}
						export class A04 {}
			'''
		];
	}

	@Test
	def void test01() {
		testAtCursor('''
			import {A02} from "ImportMe";
			A0<|>
		''', ''' 
			(A01, Class, ImportMe, , , 00000, , , , ([1:0 - 1:2], A01), [([0:29 - 0:29], 
			import {A01} from "ImportMe";)], [], , )
			(A02, Class, ImportMe, , , 00001, , , , ([1:0 - 1:2], A02), [], [], , )
			(A03, Class, ImportMe, , , 00002, , , , ([1:0 - 1:2], A03), [([0:29 - 0:29], 
			import {A03} from "ImportMe";)], [], , )
			(A04, Class, ImportMe, , , 00003, , , , ([1:0 - 1:2], A04), [([0:29 - 0:29], 
			import {A04} from "ImportMe";)], [], , )
		''');
	}

	@Test
	def void test02() {
		testAtCursor('''
			import {A02} from "ImportMe"
			A0<|>
		''', ''' 
			(A01, Class, ImportMe, , , 00000, , , , ([1:0 - 1:2], A01), [([0:28 - 0:28], 
			import {A01} from "ImportMe";)], [], , )
			(A02, Class, ImportMe, , , 00001, , , , ([1:0 - 1:2], A02), [], [], , )
			(A03, Class, ImportMe, , , 00002, , , , ([1:0 - 1:2], A03), [([0:28 - 0:28], 
			import {A03} from "ImportMe";)], [], , )
			(A04, Class, ImportMe, , , 00003, , , , ([1:0 - 1:2], A04), [([0:28 - 0:28], 
			import {A04} from "ImportMe";)], [], , )
		''');
	}

	@Test
	def void test03() {
		testAtCursor('''
			import {A02} from "ImportMe";
			
			A02;
			A01<|>
		''', ''' 
			(A01, Class, ImportMe, , , 00000, , , , ([3:0 - 3:3], A01), [([0:29 - 0:29], 
			import {A01} from "ImportMe";)], [], , )
		''');
	}
	
	@Test
	def void test04() {
		testAtCursor('''
			import {A02} from "ImportMe";
			
			A02
			A01<|>
		''', ''' 
			(A01, Class, ImportMe, , , 00000, , , , ([3:0 - 3:3], A01), [([0:29 - 0:29], 
			import {A01} from "ImportMe";)], [], , )
		''');
	}
	
	@Test
	def void test05() {
		testAtCursor('''
			import * as N from "ImportMe";
			N.A01
			N.<|>
			N.A
		''', ''' 
			(A01, Class, ImportMe, , , 00000, , , , ([2:2 - 2:2], A01), [], [], , )
			(A02, Class, ImportMe, , , 00001, , , , ([2:2 - 2:2], A02), [], [], , )
			(A03, Class, ImportMe, , , 00002, , , , ([2:2 - 2:2], A03), [], [], , )
			(A04, Class, ImportMe, , , 00003, , , , ([2:2 - 2:2], A04), [], [], , )
		''');
	}
	
	@Test
	def void test06() {
		testAtCursor('''
			import * as N from "ImportMe";
			N.A01
			N.;
			N.A<|>
		''', ''' 
			(A01, Class, ImportMe, , , 00000, , , , ([3:2 - 3:3], A01), [], [], , )
			(A02, Class, ImportMe, , , 00001, , , , ([3:2 - 3:3], A02), [], [], , )
			(A03, Class, ImportMe, , , 00002, , , , ([3:2 - 3:3], A03), [], [], , )
			(A04, Class, ImportMe, , , 00003, , , , ([3:2 - 3:3], A04), [], [], , )
		''');
	}
	
	@Test
	def void test07() {
		testAtCursor('''
			import * as N from "ImportMe";
			N.A01
			N
			N.A<|>
		''', ''' 
			(A01, Class, ImportMe, , , 00000, , , , ([3:2 - 3:3], A01), [], [], , )
			(A02, Class, ImportMe, , , 00001, , , , ([3:2 - 3:3], A02), [], [], , )
			(A03, Class, ImportMe, , , 00002, , , , ([3:2 - 3:3], A03), [], [], , )
			(A04, Class, ImportMe, , , 00003, , , , ([3:2 - 3:3], A04), [], [], , )
		''');
	}
	
	@Test
	def void test08() {
		testAtCursor('''
			import * as N from "ImportMe";
			N.A01
			N.
			A<|>
		''', ''' 
			(A01, Class, ImportMe, , , 00000, , , , ([3:0 - 3:1], A01), [], [], , )
			(A02, Class, ImportMe, , , 00001, , , , ([3:0 - 3:1], A02), [], [], , )
			(A03, Class, ImportMe, , , 00002, , , , ([3:0 - 3:1], A03), [], [], , )
			(A04, Class, ImportMe, , , 00003, , , , ([3:0 - 3:1], A04), [], [], , )
		''');
	}
	
	@Test
	def void test09() {
		testAtCursor('''
			import * as N from "ImportMe";
			N.A01
			N
			.A<|>
		''', ''' 
			(A01, Class, ImportMe, , , 00000, , , , ([3:1 - 3:2], A01), [], [], , )
			(A02, Class, ImportMe, , , 00001, , , , ([3:1 - 3:2], A02), [], [], , )
			(A03, Class, ImportMe, , , 00002, , , , ([3:1 - 3:2], A03), [], [], , )
			(A04, Class, ImportMe, , , 00003, , , , ([3:1 - 3:2], A04), [], [], , )
		''');
	}
	
	@Test
	def void test10() {
		testAtCursor('''
			import * as N from "ImportMe";
			N.A01 /* syntax errors on purpose */ garbage
			N.A<|>
		''', ''' 
			(A01, Class, ImportMe, , , 00000, , , , ([2:2 - 2:3], A01), [], [], , )
			(A02, Class, ImportMe, , , 00001, , , , ([2:2 - 2:3], A02), [], [], , )
			(A03, Class, ImportMe, , , 00002, , , , ([2:2 - 2:3], A03), [], [], , )
			(A04, Class, ImportMe, , , 00003, , , , ([2:2 - 2:3], A04), [], [], , )
		''');
	}

}
