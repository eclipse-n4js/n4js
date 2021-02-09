/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.contentAssist

import org.eclipse.n4js.tests.utils.ConvertedCompletionIdeTest
import org.junit.Test

// converted from ContentAssistBugPluginUITest
class ContentAssistBugIdeTest extends ConvertedCompletionIdeTest {

	private final String expectedProposals = '''
			(__proto__, Property, __proto__, , , 00000, , , , ([3:5 - 3:5], __proto__), [], [], , )
			(constructor, Method, constructor, , , 00001, , , , ([3:5 - 3:5], constructor), [], [], , )
			(hasOwnProperty, Method, hasOwnProperty, , , 00002, , , , ([3:5 - 3:5], hasOwnProperty), [], [], , )
			(isPrototypeOf, Method, isPrototypeOf, , , 00003, , , , ([3:5 - 3:5], isPrototypeOf), [], [], , )
			(propertyIsEnumerable, Method, propertyIsEnumerable, , , 00004, , , , ([3:5 - 3:5], propertyIsEnumerable), [], [], , )
			(toLocaleString, Method, toLocaleString, , , 00005, , , , ([3:5 - 3:5], toLocaleString), [], [], , )
			(toString, Method, toString, , , 00006, , , , ([3:5 - 3:5], toString), [], [], , )
			(valueOf, Method, valueOf, , , 00007, , , , ([3:5 - 3:5], valueOf), [], [], , )
	''';

	@Test
	def void testGB_39_01() {
		testAtCursor('''
			class A {}
			export public function* foo(req: A) { 
				yield 5;
				req.<|>
		''', expectedProposals);
	}

	@Test
	def void testGB_39_02() {
		testAtCursor('''
			class A {}
			function* foo(req: A) { 
				yield 5; // removing 5 fixes the problem
				req.<|>
		''', expectedProposals);
	}

	@Test
	def void testGB_39_03() {
		testAtCursor('''
			class A {}
			export public function* foo(req: A) { 
				yield;
				req.<|>
		''', expectedProposals);
	}

	@Test
	def void testGB_39_04() {
		testAtCursor('''
			class A {}
			function foo(req: A) { 
				yield 5; // removing 5 fixes the problem
				req.<|>
		''', expectedProposals);
	}

	@Test
	def void testGB_39_05() {
		testAtCursor('''
			class A {}
			public function* foo(req: A) { 
				yield 5;
				req.<|>
		''', expectedProposals);
	}

	@Test
	def void testGB_39_06() {
		testAtCursor('''
			class A {}
			export function* foo(req: A) { 
				yield 5;
				req.<|>
		''', expectedProposals);
	}
}
