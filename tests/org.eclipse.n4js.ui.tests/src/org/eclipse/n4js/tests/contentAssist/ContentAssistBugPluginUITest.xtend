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

import org.junit.Test

class ContentAssistBugPluginUITest extends AbstractN4JSContentAssistPluginUITest {

	@Test def void testGB_39_01() throws Exception {
		newBuilder().append(
			"class A {}
			export public function* foo(req: A) { 
			    yield 5;
			    req.").assertText(
				'__proto__', 'constructor', 'hasOwnProperty', 'isPrototypeOf', 'propertyIsEnumerable', 'toLocaleString', 'toString', 'valueOf');
	}
	
	@Test def void testGB_39_02() throws Exception {
		newBuilder().append(
			"class A {}
			function* foo(req: A) { 
			    yield 5; // removing 5 fixes the problem
			    req.").assertText(
				'__proto__', 'constructor', 'hasOwnProperty', 'isPrototypeOf', 'propertyIsEnumerable', 'toLocaleString', 'toString', 'valueOf');
	}
	
	@Test def void testGB_39_03() throws Exception {
		newBuilder().append(
			"class A {}
			export public function* foo(req: A) { 
			    yield;
			    req.").assertText(
				'__proto__', 'constructor', 'hasOwnProperty', 'isPrototypeOf', 'propertyIsEnumerable', 'toLocaleString', 'toString', 'valueOf');
	}
	
	@Test def void testGB_39_04() throws Exception {
		newBuilder().append(
			"class A {}
			function foo(req: A) { 
			    yield 5; // removing 5 fixes the problem
			    req.").assertText(
				'__proto__', 'constructor', 'hasOwnProperty', 'isPrototypeOf', 'propertyIsEnumerable', 'toLocaleString', 'toString', 'valueOf');
	}
	
	@Test def void testGB_39_05() throws Exception {
		newBuilder().append(
			"class A {}
			public function* foo(req: A) { 
			    yield 5;
			    req.").assertText(
				'__proto__', 'constructor', 'hasOwnProperty', 'isPrototypeOf', 'propertyIsEnumerable', 'toLocaleString', 'toString', 'valueOf');
	}
	
	@Test def void testGB_39_06() throws Exception {
		newBuilder().append(
			"class A {}
			export function* foo(req: A) { 
			    yield 5;
			    req.").assertText(
				'__proto__', 'constructor', 'hasOwnProperty', 'isPrototypeOf', 'propertyIsEnumerable', 'toLocaleString', 'toString', 'valueOf');
	}
	
}
