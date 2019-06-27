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

import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.NewExpression
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.n4JS.PropertyNameValuePairSingleName
import org.eclipse.n4js.n4JS.PropertySetterDeclaration
import org.eclipse.n4js.n4JS.PropertySpread
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.junit.Test
import org.eclipse.n4js.n4JS.AnnotablePropertyAssignment

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

	// object literals:

	@Test
	def void testSpreadInObjectLiteral01() {
		val script = '''
			let obj: Object;
			({
				... obj
			});
		'''.parseESSuccessfully;

		val objLit = script.eAllContents.filter(ObjectLiteral).head;
		assertNotNull(objLit);
		assertEquals(1, objLit.propertyAssignments.size);
		assertTrue(objLit.propertyAssignments.get(0) instanceof PropertySpread);

		val expr = (objLit.propertyAssignments.get(0) as PropertySpread).expression;
		assertNotNull(expr);
		assertTrue(expr instanceof IdentifierRef);
		assertSame(script.eAllContents.filter(VariableDeclaration).head, (expr as IdentifierRef).id);
	}

	@Test
	def void testSpreadInObjectLiteral01_withAnnotations() {
		val script = '''
			let obj: Object;
			({
				@FancyProp
				... obj
			});
		'''.parseESSuccessfully;

		val objLit = script.eAllContents.filter(ObjectLiteral).head;
		assertNotNull(objLit);
		assertEquals(1, objLit.propertyAssignments.size);
		assertTrue(objLit.propertyAssignments.get(0) instanceof PropertySpread);

		assertEquals("FancyProp", (objLit.propertyAssignments.get(0) as AnnotablePropertyAssignment).annotations.head.name);

		val expr = (objLit.propertyAssignments.get(0) as PropertySpread).expression;
		assertNotNull(expr);
		assertTrue(expr instanceof IdentifierRef);
		assertSame(script.eAllContents.filter(VariableDeclaration).head, (expr as IdentifierRef).id);
	}

	@Test
	def void testSpreadInObjectLiteral02() {
		val script = '''
			let obj: Object;
			let prop2 = 'world';
			({
				prop1: 'hello',
				... obj,
				prop2,
				... obj,
				get foo() { return '!!!'; },
				... obj,
				set bar(value) {},
				... obj,
				m() {}
			});
		'''.parseESWithError;
		
		assertEquals(1, script.eResource.errors.size);
		assertEquals(6, script.eResource.errors.head.line);
		assertEquals(
			"Single name syntax in object literals is unsupported at the moment (only allowed in object destructuring patterns).",
			script.eResource.errors.head.message);

		val objLit = script.eAllContents.filter(ObjectLiteral).head;
		assertNotNull(objLit);
		assertEquals(9, objLit.propertyAssignments.size);
		assertTrue(objLit.propertyAssignments.get(0) instanceof PropertyNameValuePair);
		assertTrue(objLit.propertyAssignments.get(1) instanceof PropertySpread);
		assertTrue(objLit.propertyAssignments.get(2) instanceof PropertyNameValuePairSingleName);
		assertTrue(objLit.propertyAssignments.get(3) instanceof PropertySpread);
		assertTrue(objLit.propertyAssignments.get(4) instanceof PropertyGetterDeclaration);
		assertTrue(objLit.propertyAssignments.get(5) instanceof PropertySpread);
		assertTrue(objLit.propertyAssignments.get(6) instanceof PropertySetterDeclaration);
		assertTrue(objLit.propertyAssignments.get(7) instanceof PropertySpread);
		assertTrue(objLit.propertyAssignments.get(8) instanceof PropertyMethodDeclaration);
	}

	@Test
	def void testSpreadInObjectLiteral02_withAnnotations() {
		val script = '''
			let obj: Object;
			let prop2 = 'world';
			({
				@FancyProp
				prop1: 'hello',
				@FancyProp
				... obj,
				@FancyProp
				prop2,
				@FancyProp
				... obj,
				@FancyProp
				get foo() { return '!!!'; },
				@FancyProp
				... obj,
				@FancyProp
				set bar(value) {},
				@FancyProp
				... obj,
				@FancyProp
				m() {}
			});
		'''.parseESWithError;
		
		assertEquals(1, script.eResource.errors.size);
		assertEquals(8, script.eResource.errors.head.line);
		assertEquals(
			"Single name syntax in object literals is unsupported at the moment (only allowed in object destructuring patterns).",
			script.eResource.errors.head.message);

		val objLit = script.eAllContents.filter(ObjectLiteral).head;
		assertNotNull(objLit);
		assertEquals(9, objLit.propertyAssignments.size);
		assertTrue(objLit.propertyAssignments.get(0) instanceof PropertyNameValuePair);
		assertTrue(objLit.propertyAssignments.get(1) instanceof PropertySpread);
		assertTrue(objLit.propertyAssignments.get(2) instanceof PropertyNameValuePairSingleName);
		assertTrue(objLit.propertyAssignments.get(3) instanceof PropertySpread);
		assertTrue(objLit.propertyAssignments.get(4) instanceof PropertyGetterDeclaration);
		assertTrue(objLit.propertyAssignments.get(5) instanceof PropertySpread);
		assertTrue(objLit.propertyAssignments.get(6) instanceof PropertySetterDeclaration);
		assertTrue(objLit.propertyAssignments.get(7) instanceof PropertySpread);
		assertTrue(objLit.propertyAssignments.get(8) instanceof PropertyMethodDeclaration);

		assertEquals("FancyProp", (objLit.propertyAssignments.get(0) as AnnotablePropertyAssignment).annotations.head.name);
		assertEquals("FancyProp", (objLit.propertyAssignments.get(1) as AnnotablePropertyAssignment).annotations.head.name);
		assertEquals("FancyProp", (objLit.propertyAssignments.get(2) as AnnotablePropertyAssignment).annotations.head.name);
		assertEquals("FancyProp", (objLit.propertyAssignments.get(3) as AnnotablePropertyAssignment).annotations.head.name);
		assertEquals("FancyProp", (objLit.propertyAssignments.get(4) as AnnotablePropertyAssignment).annotations.head.name);
		assertEquals("FancyProp", (objLit.propertyAssignments.get(5) as AnnotablePropertyAssignment).annotations.head.name);
		assertEquals("FancyProp", (objLit.propertyAssignments.get(6) as AnnotablePropertyAssignment).annotations.head.name);
		assertEquals("FancyProp", (objLit.propertyAssignments.get(7) as AnnotablePropertyAssignment).annotations.head.name);
		assertEquals("FancyProp", (objLit.propertyAssignments.get(8) as AnnotablePropertyAssignment).annotations.head.name);
	}

	@Test
	def void testSpreadInObjectLiteral03() {
		val script = '''
			({
				... {
					... {}
				}
			});
		'''.parseESSuccessfully;

		val objLit = script.eAllContents.filter(ObjectLiteral).head;
		assertNotNull(objLit);
		assertEquals(1, objLit.propertyAssignments.size);
		assertTrue(objLit.propertyAssignments.get(0) instanceof PropertySpread);
	}
}
