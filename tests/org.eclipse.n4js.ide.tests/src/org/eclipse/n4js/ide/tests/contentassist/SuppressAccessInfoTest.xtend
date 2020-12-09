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

import org.eclipse.n4js.ide.tests.helper.server.AbstractCompletionTest
import org.junit.Test

/**
 * Test for content assist on a receiver structurally typed as ~r~
 * but used at write location in the AST (i.e. left hand side). 
 */
public class SuppressAccessInfoTest extends AbstractCompletionTest {

	@Test
	def void test01() {
		testAtCursorPartially('''
			class T {
			    public field: string;
			}
			
			function fn(param: ~r~T) {
			    param.<|>
			    const c = 1;
			    c;
			}
		''', ''' 
			(field, Field, field, , , 00002, , , , ([5:10 - 5:10], field), [], [], , )
		''');
	}

	@Test
	def void test02() {
		testAtCursor('''
			class T {
			    public field: string;
			}
			
			function fn(param: T) {
			    param.<|>
			    const c = 1;
			    c;
			}
		''', '''
			(__proto__, Property, __proto__, , , 00000, , , , ([5:10 - 5:10], __proto__), [], [], , )
			(constructor, Method, constructor, , , 00001, , , , ([5:10 - 5:10], constructor), [], [], , )
			(field, Field, field, , , 00002, , , , ([5:10 - 5:10], field), [], [], , )
			(hasOwnProperty, Method, hasOwnProperty, , , 00003, , , , ([5:10 - 5:10], hasOwnProperty), [], [], , )
			(isPrototypeOf, Method, isPrototypeOf, , , 00004, , , , ([5:10 - 5:10], isPrototypeOf), [], [], , )
			(propertyIsEnumerable, Method, propertyIsEnumerable, , , 00005, , , , ([5:10 - 5:10], propertyIsEnumerable), [], [], , )
			(toLocaleString, Method, toLocaleString, , , 00006, , , , ([5:10 - 5:10], toLocaleString), [], [], , )
			(toString, Method, toString, , , 00007, , , , ([5:10 - 5:10], toString), [], [], , )
			(valueOf, Method, valueOf, , , 00008, , , , ([5:10 - 5:10], valueOf), [], [], , )
		''');
	}

	@Test
	def void test03() {
		testAtCursor('''
			class T {
			    public field: string;
			}
			
			function fn(param: ~T) {
			    param.<|>
			    const c = 1;
			    c;
			}
		''', '''
			(__proto__, Property, __proto__, , , 00000, , , , ([5:10 - 5:10], __proto__), [], [], , )
			(constructor, Method, constructor, , , 00001, , , , ([5:10 - 5:10], constructor), [], [], , )
			(field, Field, field, , , 00002, , , , ([5:10 - 5:10], field), [], [], , )
			(hasOwnProperty, Method, hasOwnProperty, , , 00003, , , , ([5:10 - 5:10], hasOwnProperty), [], [], , )
			(isPrototypeOf, Method, isPrototypeOf, , , 00004, , , , ([5:10 - 5:10], isPrototypeOf), [], [], , )
			(propertyIsEnumerable, Method, propertyIsEnumerable, , , 00005, , , , ([5:10 - 5:10], propertyIsEnumerable), [], [], , )
			(toLocaleString, Method, toLocaleString, , , 00006, , , , ([5:10 - 5:10], toLocaleString), [], [], , )
			(toString, Method, toString, , , 00007, , , , ([5:10 - 5:10], toString), [], [], , )
			(valueOf, Method, valueOf, , , 00008, , , , ([5:10 - 5:10], valueOf), [], [], , )
		''');
	}

	@Test
	def void test04() {
		testAtCursor('''
			class T {
			    public field: string;
			}
			
			function fn(param: ~~T) {
			    param.<|>
			    const c = 1;
			    c;
			}
		''', '''
			(__proto__, Property, __proto__, , , 00000, , , , ([5:10 - 5:10], __proto__), [], [], , )
			(constructor, Method, constructor, , , 00001, , , , ([5:10 - 5:10], constructor), [], [], , )
			(field, Field, field, , , 00002, , , , ([5:10 - 5:10], field), [], [], , )
			(hasOwnProperty, Method, hasOwnProperty, , , 00003, , , , ([5:10 - 5:10], hasOwnProperty), [], [], , )
			(isPrototypeOf, Method, isPrototypeOf, , , 00004, , , , ([5:10 - 5:10], isPrototypeOf), [], [], , )
			(propertyIsEnumerable, Method, propertyIsEnumerable, , , 00005, , , , ([5:10 - 5:10], propertyIsEnumerable), [], [], , )
			(toLocaleString, Method, toLocaleString, , , 00006, , , , ([5:10 - 5:10], toLocaleString), [], [], , )
			(toString, Method, toString, , , 00007, , , , ([5:10 - 5:10], toString), [], [], , )
			(valueOf, Method, valueOf, , , 00008, , , , ([5:10 - 5:10], valueOf), [], [], , )
		''');
	}

	@Test
	def void test05() {
		testAtCursor('''
			class T {
			    public field: string;
			}
			
			function fn(param: ~r~T) {
			    param.<|>
			    const c = 1;
			    c;
			}
		''', '''
			(__proto__, Property, __proto__, , , 00000, , , , ([5:10 - 5:10], __proto__), [], [], , )
			(constructor, Method, constructor, , , 00001, , , , ([5:10 - 5:10], constructor), [], [], , )
			(field, Field, field, , , 00002, , , , ([5:10 - 5:10], field), [], [], , )
			(hasOwnProperty, Method, hasOwnProperty, , , 00003, , , , ([5:10 - 5:10], hasOwnProperty), [], [], , )
			(isPrototypeOf, Method, isPrototypeOf, , , 00004, , , , ([5:10 - 5:10], isPrototypeOf), [], [], , )
			(propertyIsEnumerable, Method, propertyIsEnumerable, , , 00005, , , , ([5:10 - 5:10], propertyIsEnumerable), [], [], , )
			(toLocaleString, Method, toLocaleString, , , 00006, , , , ([5:10 - 5:10], toLocaleString), [], [], , )
			(toString, Method, toString, , , 00007, , , , ([5:10 - 5:10], toString), [], [], , )
			(valueOf, Method, valueOf, , , 00008, , , , ([5:10 - 5:10], valueOf), [], [], , )
		''');
	}

	@Test
	def void test06() {
		testAtCursor('''
			class T {
			    public field: string;
			}
			
			function fn(param: ~w~T) {
			    param.<|>
			    const c = 1;
			    c;
			}
		''', '''
			(__proto__, Property, __proto__, , , 00000, , , , ([5:10 - 5:10], __proto__), [], [], , )
			(constructor, Method, constructor, , , 00001, , , , ([5:10 - 5:10], constructor), [], [], , )
			(field, Field, field, , , 00002, , , , ([5:10 - 5:10], field), [], [], , )
			(hasOwnProperty, Method, hasOwnProperty, , , 00003, , , , ([5:10 - 5:10], hasOwnProperty), [], [], , )
			(isPrototypeOf, Method, isPrototypeOf, , , 00004, , , , ([5:10 - 5:10], isPrototypeOf), [], [], , )
			(propertyIsEnumerable, Method, propertyIsEnumerable, , , 00005, , , , ([5:10 - 5:10], propertyIsEnumerable), [], [], , )
			(toLocaleString, Method, toLocaleString, , , 00006, , , , ([5:10 - 5:10], toLocaleString), [], [], , )
			(toString, Method, toString, , , 00007, , , , ([5:10 - 5:10], toString), [], [], , )
			(valueOf, Method, valueOf, , , 00008, , , , ([5:10 - 5:10], valueOf), [], [], , )
		''');
	}
}
