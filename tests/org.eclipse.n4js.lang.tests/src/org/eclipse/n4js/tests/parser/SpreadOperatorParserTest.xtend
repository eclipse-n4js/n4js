/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.parser

import org.eclipse.n4js.n4JS.NewExpression
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.junit.Test

/**
 */
class SpreadOperatorParserTest extends AbstractParserTest {

	// call expressions:

	@Test
	def void testSpreadInCallExpression01() {
		val script = '''
			function myFunction(p0, p1, p2, p3, p4) {}
			var args = [0, 1, 2, 3, 4];
			myFunction(...args);
		'''.parseESSuccessfully;

		val callExpr = script.eAllContents.filter(ParameterizedCallExpression).head;
		assertNotNull(callExpr);
		assertTrue(callExpr.arguments.get(0).spread);
	}

	@Test
	def void testSpreadInCallExpression02() {
		val script = '''
			function myFunction(p0, p1, p2, p3, p4) {}
			var args = [0, 1, 2];
			myFunction('a', ...args, 'b');
		'''.parseESSuccessfully;

		val callExpr = script.eAllContents.filter(ParameterizedCallExpression).head;
		assertNotNull(callExpr);
		assertFalse(callExpr.arguments.get(0).spread);
		assertTrue(callExpr.arguments.get(1).spread);
		assertFalse(callExpr.arguments.get(2).spread);
	}

	@Test
	def void testSpreadInCallExpression03() {
		val script = '''
			function myFunction(p0, p1, p2, p3, p4) {}
			var args = [0, 1];
			myFunction(...args, 'a', ...args);
		'''.parseESSuccessfully;

		val callExpr = script.eAllContents.filter(ParameterizedCallExpression).head;
		assertNotNull(callExpr);
		assertTrue(callExpr.arguments.get(0).spread);
		assertFalse(callExpr.arguments.get(1).spread);
		assertTrue(callExpr.arguments.get(2).spread);
	}

	// new expressions:

	@Test
	def void testSpreadInNewExpression01() {
		val script = '''
			class C {
				constructor(p0, p1, p2, p3, p4) {}
			}
			var args = [0, 1, 2, 3, 4];
			new C(...args);
		'''.parseESSuccessfully;

		val newExpr = script.eAllContents.filter(NewExpression).head;
		assertNotNull(newExpr);
		assertTrue(newExpr.arguments.get(0).spread);
	}

	@Test
	def void testSpreadInNewExpression02() {
		val script = '''
			class C {
				constructor(p0, p1, p2, p3, p4) {}
			}
			var args = [0, 1, 2];
			new C('a', ...args, 'b');
		'''.parseESSuccessfully;

		val newExpr = script.eAllContents.filter(NewExpression).head;
		assertNotNull(newExpr);
		assertFalse(newExpr.arguments.get(0).spread);
		assertTrue(newExpr.arguments.get(1).spread);
		assertFalse(newExpr.arguments.get(2).spread);
	}

	@Test
	def void testSpreadInNewExpression03() {
		val script = '''
			class C {
				constructor(p0, p1, p2, p3, p4) {}
			}
			var args = [0, 1];
			new C(...args, 'a', ...args);
		'''.parseESSuccessfully;

		val newExpr = script.eAllContents.filter(NewExpression).head;
		assertNotNull(newExpr);
		assertTrue(newExpr.arguments.get(0).spread);
		assertFalse(newExpr.arguments.get(1).spread);
		assertTrue(newExpr.arguments.get(2).spread);
	}
}
