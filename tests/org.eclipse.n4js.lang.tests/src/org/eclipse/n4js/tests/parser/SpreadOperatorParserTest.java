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
package org.eclipse.n4js.tests.parser;

import org.eclipse.n4js.n4JS.AnnotablePropertyAssignment;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration;
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.PropertyNameValuePairSingleName;
import org.eclipse.n4js.n4JS.PropertySetterDeclaration;
import org.eclipse.n4js.n4JS.PropertySpread;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.junit.Test;

public class SpreadOperatorParserTest extends AbstractParserTest {

	// call expressions:

	@Test
	public void testSpreadInCallExpression01() {
		Script script = parseESSuccessfully("""
				function myFunction(p0, p1, p2, p3, p4) {}
				var args = [0, 1, 2, 3, 4];
				myFunction(...args);
				""");

		ParameterizedCallExpression callExpr = IteratorExtensions
				.head(IteratorExtensions.filter(script.eAllContents(), ParameterizedCallExpression.class));
		assertNotNull(callExpr);
		assertTrue(callExpr.getArguments().get(0).isSpread());
	}

	@Test
	public void testSpreadInCallExpression02() {
		Script script = parseESSuccessfully("""
				function myFunction(p0, p1, p2, p3, p4) {}
				var args = [0, 1, 2];
				myFunction('a', ...args, 'b');
				""");

		ParameterizedCallExpression callExpr = IteratorExtensions
				.head(IteratorExtensions.filter(script.eAllContents(), ParameterizedCallExpression.class));
		assertNotNull(callExpr);
		assertFalse(callExpr.getArguments().get(0).isSpread());
		assertTrue(callExpr.getArguments().get(1).isSpread());
		assertFalse(callExpr.getArguments().get(2).isSpread());
	}

	@Test
	public void testSpreadInCallExpression03() {
		Script script = parseESSuccessfully("""
				function myFunction(p0, p1, p2, p3, p4) {}
				var args = [0, 1];
				myFunction(...args, 'a', ...args);
				""");

		ParameterizedCallExpression callExpr = IteratorExtensions
				.head(IteratorExtensions.filter(script.eAllContents(), ParameterizedCallExpression.class));
		assertNotNull(callExpr);
		assertTrue(callExpr.getArguments().get(0).isSpread());
		assertFalse(callExpr.getArguments().get(1).isSpread());
		assertTrue(callExpr.getArguments().get(2).isSpread());
	}

	// new expressions:

	@Test
	public void testSpreadInNewExpression01() {
		Script script = parseESSuccessfully("""
				class C {
					constructor(p0, p1, p2, p3, p4) {}
				}
				var args = [0, 1, 2, 3, 4];
				new C(...args);
				""");

		NewExpression newExpr = IteratorExtensions
				.head(IteratorExtensions.filter(script.eAllContents(), NewExpression.class));
		assertNotNull(newExpr);
		assertTrue(newExpr.getArguments().get(0).isSpread());
	}

	@Test
	public void testSpreadInNewExpression02() {
		Script script = parseESSuccessfully("""
				class C {
					constructor(p0, p1, p2, p3, p4) {}
				}
				var args = [0, 1, 2];
				new C('a', ...args, 'b');
				""");

		NewExpression newExpr = IteratorExtensions
				.head(IteratorExtensions.filter(script.eAllContents(), NewExpression.class));
		assertNotNull(newExpr);
		assertFalse(newExpr.getArguments().get(0).isSpread());
		assertTrue(newExpr.getArguments().get(1).isSpread());
		assertFalse(newExpr.getArguments().get(2).isSpread());
	}

	@Test
	public void testSpreadInNewExpression03() {
		Script script = parseESSuccessfully("""
				class C {
					constructor(p0, p1, p2, p3, p4) {}
				}
				var args = [0, 1];
				new C(...args, 'a', ...args);
				""");

		NewExpression newExpr = IteratorExtensions
				.head(IteratorExtensions.filter(script.eAllContents(), NewExpression.class));
		assertNotNull(newExpr);
		assertTrue(newExpr.getArguments().get(0).isSpread());
		assertFalse(newExpr.getArguments().get(1).isSpread());
		assertTrue(newExpr.getArguments().get(2).isSpread());
	}

	// object literals:

	@Test
	public void testSpreadInObjectLiteral01() {
		Script script = parseESSuccessfully("""
				let obj: Object;
				({
					... obj
				});
				""");

		ObjectLiteral objLit = IteratorExtensions
				.head(IteratorExtensions.filter(script.eAllContents(), ObjectLiteral.class));
		assertNotNull(objLit);
		assertEquals(1, objLit.getPropertyAssignments().size());
		assertTrue(objLit.getPropertyAssignments().get(0) instanceof PropertySpread);

		Expression expr = ((PropertySpread) objLit.getPropertyAssignments().get(0)).getExpression();
		assertNotNull(expr);
		assertTrue(expr instanceof IdentifierRef);
		assertSame(IteratorExtensions.head(IteratorExtensions.filter(script.eAllContents(), VariableDeclaration.class))
				.getDefinedVariable(), ((IdentifierRef) expr).getId());
	}

	@Test
	public void testSpreadInObjectLiteral01_withAnnotations() {
		Script script = parseESSuccessfully("""
				let obj: Object;
				({
					@FancyProp
					... obj
				});
				""");

		ObjectLiteral objLit = IteratorExtensions
				.head(IteratorExtensions.filter(script.eAllContents(), ObjectLiteral.class));
		assertNotNull(objLit);
		assertEquals(1, objLit.getPropertyAssignments().size());
		assertTrue(objLit.getPropertyAssignments().get(0) instanceof PropertySpread);

		assertEquals("FancyProp", ((AnnotablePropertyAssignment) objLit.getPropertyAssignments().get(0))
				.getAnnotations().get(0).getName());

		Expression expr = ((PropertySpread) objLit.getPropertyAssignments().get(0)).getExpression();
		assertNotNull(expr);
		assertTrue(expr instanceof IdentifierRef);
		assertSame(IteratorExtensions.head(IteratorExtensions.filter(script.eAllContents(), VariableDeclaration.class))
				.getDefinedVariable(), ((IdentifierRef) expr).getId());
	}

	@Test
	public void testSpreadInObjectLiteral02() {
		Script script = parseESWithError("""
				let obj: Object;
				let prop2 = 'world';
				({
					prop1: 'hello',
					... obj,
					prop2,
					... obj,
					prop3 = "oof",
					... obj,
					get foo() { return '!!!'; },
					... obj,
					set bar(value) {},
					... obj,
					m() {}
				});
				""");

		assertEquals(1, script.eResource().getErrors().size());
		assertEquals(8, script.eResource().getErrors().get(0).getLine());
		assertEquals(
				"A default value is only allowed within a destructuring pattern.",
				script.eResource().getErrors().get(0).getMessage());

		ObjectLiteral objLit = IteratorExtensions
				.head(IteratorExtensions.filter(script.eAllContents(), ObjectLiteral.class));
		assertNotNull(objLit);
		assertEquals(11, objLit.getPropertyAssignments().size());
		assertTrue(objLit.getPropertyAssignments().get(0) instanceof PropertyNameValuePair);
		assertTrue(objLit.getPropertyAssignments().get(1) instanceof PropertySpread);
		assertTrue(objLit.getPropertyAssignments().get(2) instanceof PropertyNameValuePairSingleName);
		assertTrue(objLit.getPropertyAssignments().get(3) instanceof PropertySpread);
		assertTrue(objLit.getPropertyAssignments().get(4) instanceof PropertyNameValuePairSingleName);
		assertTrue(objLit.getPropertyAssignments().get(5) instanceof PropertySpread);
		assertTrue(objLit.getPropertyAssignments().get(6) instanceof PropertyGetterDeclaration);
		assertTrue(objLit.getPropertyAssignments().get(7) instanceof PropertySpread);
		assertTrue(objLit.getPropertyAssignments().get(8) instanceof PropertySetterDeclaration);
		assertTrue(objLit.getPropertyAssignments().get(9) instanceof PropertySpread);
		assertTrue(objLit.getPropertyAssignments().get(10) instanceof PropertyMethodDeclaration);
	}

	@Test
	public void testSpreadInObjectLiteral02_withAnnotations() {
		Script script = parseESWithError("""
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
					prop3 = "oof",
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
				""");

		assertEquals(1, script.eResource().getErrors().size());
		assertEquals(13, script.eResource().getErrors().get(0).getLine());
		assertEquals(
				"A default value is only allowed within a destructuring pattern.",
				script.eResource().getErrors().get(0).getMessage());

		ObjectLiteral objLit = IteratorExtensions
				.head(IteratorExtensions.filter(script.eAllContents(), ObjectLiteral.class));
		assertNotNull(objLit);
		assertEquals(11, objLit.getPropertyAssignments().size());
		assertTrue(objLit.getPropertyAssignments().get(0) instanceof PropertyNameValuePair);
		assertTrue(objLit.getPropertyAssignments().get(1) instanceof PropertySpread);
		assertTrue(objLit.getPropertyAssignments().get(2) instanceof PropertyNameValuePairSingleName);
		assertTrue(objLit.getPropertyAssignments().get(3) instanceof PropertySpread);
		assertTrue(objLit.getPropertyAssignments().get(4) instanceof PropertyNameValuePairSingleName);
		assertTrue(objLit.getPropertyAssignments().get(5) instanceof PropertySpread);
		assertTrue(objLit.getPropertyAssignments().get(6) instanceof PropertyGetterDeclaration);
		assertTrue(objLit.getPropertyAssignments().get(7) instanceof PropertySpread);
		assertTrue(objLit.getPropertyAssignments().get(8) instanceof PropertySetterDeclaration);
		assertTrue(objLit.getPropertyAssignments().get(9) instanceof PropertySpread);
		assertTrue(objLit.getPropertyAssignments().get(10) instanceof PropertyMethodDeclaration);

		assertEquals("FancyProp", ((AnnotablePropertyAssignment) objLit.getPropertyAssignments().get(0))
				.getAnnotations().get(0).getName());
		assertEquals("FancyProp", ((AnnotablePropertyAssignment) objLit.getPropertyAssignments().get(1))
				.getAnnotations().get(0).getName());
		assertEquals("FancyProp", ((AnnotablePropertyAssignment) objLit.getPropertyAssignments().get(2))
				.getAnnotations().get(0).getName());
		assertEquals("FancyProp", ((AnnotablePropertyAssignment) objLit.getPropertyAssignments().get(3))
				.getAnnotations().get(0).getName());
		assertEquals("FancyProp", ((AnnotablePropertyAssignment) objLit.getPropertyAssignments().get(4))
				.getAnnotations().get(0).getName());
		assertEquals("FancyProp", ((AnnotablePropertyAssignment) objLit.getPropertyAssignments().get(5))
				.getAnnotations().get(0).getName());
		assertEquals("FancyProp", ((AnnotablePropertyAssignment) objLit.getPropertyAssignments().get(6))
				.getAnnotations().get(0).getName());
		assertEquals("FancyProp", ((AnnotablePropertyAssignment) objLit.getPropertyAssignments().get(7))
				.getAnnotations().get(0).getName());
		assertEquals("FancyProp", ((AnnotablePropertyAssignment) objLit.getPropertyAssignments().get(8))
				.getAnnotations().get(0).getName());
		assertEquals("FancyProp", ((AnnotablePropertyAssignment) objLit.getPropertyAssignments().get(9))
				.getAnnotations().get(0).getName());
		assertEquals("FancyProp", ((AnnotablePropertyAssignment) objLit.getPropertyAssignments().get(10))
				.getAnnotations().get(0).getName());
	}

	@Test
	public void testSpreadInObjectLiteral03() {
		Script script = parseESSuccessfully("""
				({
					... {
						... {}
					}
				});
				""");

		ObjectLiteral objLit = IteratorExtensions
				.head(IteratorExtensions.filter(script.eAllContents(), ObjectLiteral.class));
		assertNotNull(objLit);
		assertEquals(1, objLit.getPropertyAssignments().size());
		assertTrue(objLit.getPropertyAssignments().get(0) instanceof PropertySpread);
	}
}
