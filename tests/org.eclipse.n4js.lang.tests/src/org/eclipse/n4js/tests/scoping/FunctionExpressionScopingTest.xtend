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
package org.eclipse.n4js.tests.scoping

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import org.eclipse.n4js.n4JS.VariableDeclaration

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class FunctionExpressionScopingTest {

	@Inject extension ParseHelper<Script>
	@Inject extension ValidationTestHelper

	@Test
	def void testScopingOfNestedFunctionExpressions() {
		val script = '''
			export function foo1() {
			    foo1;
			    foo1();
			    var b = function foo2() {
					foo2()
			    	var c = function foo3() {
			    		foo3()
			    		var d = function foo4() {
			    			foo4()
			    		}
			    	}
			    }
			}
		'''.parse
		val foo1 = (script.scriptElements.head as ExportDeclaration).exportedElement as FunctionDeclaration
		Assert.assertNotNull(foo1)
		val functionExpressions = script.eAllContents.filter(FunctionExpression).toList
		Assert.assertEquals(3, functionExpressions.size)
		val foo2 = functionExpressions.filter[name == "foo2"].head
		Assert.assertNotNull(foo2)
		val foo3 = functionExpressions.filter[name == "foo3"].head
		Assert.assertNotNull(foo3)
		val foo4 = functionExpressions.filter[name == "foo4"].head
		Assert.assertNotNull(foo4)
		val callExpressionsTargets = script.eAllContents.filter(IdentifierRef).toList
		val foo1CallTarget = callExpressionsTargets.filter[id.name == "foo1"].map[id].filter(TFunction).head
		Assert.assertNotNull(foo1CallTarget)
		val foo2CallTarget = callExpressionsTargets.filter[id.name == "foo2"].map[id].filter(TFunction).head
		Assert.assertNotNull(foo2CallTarget)
		val foo3CallTarget = callExpressionsTargets.filter[id.name == "foo3"].map[id].filter(TFunction).head
		Assert.assertNotNull(foo3CallTarget)
		val foo4CallTarget = callExpressionsTargets.filter[id.name == "foo4"].map[id].filter(TFunction).head
		Assert.assertNotNull(foo4CallTarget)
		Assert.assertSame("foo1 correctly scoped", foo1CallTarget.astElement, foo1)
		Assert.assertSame("foo2 correctly scoped", foo2CallTarget.astElement, foo2)
		Assert.assertSame("foo3 correctly scoped", foo3CallTarget.astElement, foo3)
		Assert.assertSame("foo4 correctly scoped", foo4CallTarget.astElement, foo4)
	}

	@Test
	def void testShadowingOfNestedFunctions() {
		val script = '''
			function f1() {
			    var v = function f1() {
					f1()
			    }
			}
		'''.parse
		script.assertNoErrors
		val f1 = script.eAllContents.filter(IdentifierRef).head
		val fun = f1.id as TFunction
		val expr = fun.astElement as FunctionExpression
		Assert.assertTrue(expr.eContainer.toString, expr.eContainer instanceof VariableDeclaration)
	}

	@Test
	def void testScopingOfNestedFunctionExpressionsComplex() {
		val script = '''
			var a = function foo( x ) {
				var b = function bar( y ) {
					if (y==1) { foo(2); bar(0); }
				}
				if (x==1) { b(1); }
			};
			a(1);
		'''.parse
		val functionExpressions = script.eAllContents.filter(FunctionExpression).toList
		Assert.assertEquals(2, functionExpressions.size)
		val foo = functionExpressions.filter[name == "foo"].head
		Assert.assertNotNull(foo)
		val bar = functionExpressions.filter[name == "bar"].head
		Assert.assertNotNull(bar)
		val callExpressionsTargets = script.eAllContents.filter(IdentifierRef).toList
		val fooCallTarget = callExpressionsTargets.filter[id.name == "foo"].map[id].filter(TFunction).head
		Assert.assertNotNull(fooCallTarget)
		val barCallTarget = callExpressionsTargets.filter[id.name == "bar"].map[id].filter(TFunction).head
		Assert.assertNotNull(barCallTarget)
		Assert.assertSame("foo correctly scoped", fooCallTarget.astElement, foo)
		Assert.assertSame("bar correctly scoped", barCallTarget.astElement, bar)
	}
}
