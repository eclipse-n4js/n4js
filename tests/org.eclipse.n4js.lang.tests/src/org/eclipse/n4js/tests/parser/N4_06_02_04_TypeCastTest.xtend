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

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.N4JSTestHelper
import org.eclipse.n4js.n4JS.CastExpression
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.MultiplicativeExpression
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(N4JSInjectorProvider))
class N4_06_02_04_TypeCastTest {

	@Inject
	extension N4JSTestHelper

	@Test
	def void testCastExpression() {
		val script = '''
			class A {
				d() {}
			}
			var s: any = new A();
			s as A;
		'''.parseAndValidateSuccessfully

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)

		var expr = (script.scriptElements.last as ExpressionStatement).expression;

		val ce = assertType(CastExpression, expr)
		assertEquals("A", ce.targetTypeRef.declaredType.name)
		val idref = assertType(IdentifierRef, ce.expression)
		assertEquals("s", idref.id.name)
	}

	@Test
	def void testCastExpressionAndDivision() {
		val script = '''
			var s: any = 1;
			var x =1;
			s as number/x;
		'''.parseAndValidateSuccessfully

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)

		var expr = (script.scriptElements.last as ExpressionStatement).expression;
		val me = assertType(MultiplicativeExpression, expr);
		val ce = assertType(CastExpression, me.lhs)
		assertEquals("number", ce.targetTypeRef.declaredType.name)
		val idref = assertType(IdentifierRef, ce.expression)
		assertEquals("s", idref.id.name)
	}



	def <T> assertType(Class<T> type, Expression expression) {
		assertNotNull("Expected type " + type.name + ", was null", expression)
		assertTrue("Expected type " + type.name + ", was " + expression.eClass.name,
			type.isInstance(expression)
		)
		return expression as T;
	}

}
