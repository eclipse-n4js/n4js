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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.N4JSTestHelper;
import org.eclipse.n4js.n4JS.CastExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.MultiplicativeExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N4_06_02_04_TypeCastTest {

	@Inject
	N4JSTestHelper testHelper;

	@Test
	public void testCastExpression() throws Exception {
		Script script = testHelper.parseAndValidateSuccessfully("""
				class A {
					d() {}
				}
				var s: any = new A();
				s as A;
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());

		var expr = ((ExpressionStatement) IterableExtensions.last(script.getScriptElements())).getExpression();

		CastExpression ce = assertType(CastExpression.class, expr);
		assertEquals("A", ce.getTargetTypeRef().getDeclaredType().getName());
		IdentifierRef idref = assertType(IdentifierRef.class, ce.getExpression());
		assertEquals("s", idref.getId().getName());
	}

	@Test
	public void testCastExpressionAndDivision() throws Exception {
		Script script = testHelper.parseAndValidateSuccessfully("""
				var s: any = 1;
				var x =1;
				s as number/x;
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());

		var expr = ((ExpressionStatement) IterableExtensions.last(script.getScriptElements())).getExpression();
		MultiplicativeExpression me = assertType(MultiplicativeExpression.class, expr);
		CastExpression ce = assertType(CastExpression.class, me.getLhs());
		assertEquals("number", ce.getTargetTypeRef().getDeclaredType().getName());
		IdentifierRef idref = assertType(IdentifierRef.class, ce.getExpression());
		assertEquals("s", idref.getId().getName());
	}

	@SuppressWarnings("unchecked")
	public <T> T assertType(Class<T> type, Expression expression) {
		assertNotNull("Expected type " + type.getName() + ", was null", expression);
		assertTrue("Expected type " + type.getName() + ", was " + expression.eClass().getName(),
				type.isInstance(expression));
		return (T) expression;
	}

}
