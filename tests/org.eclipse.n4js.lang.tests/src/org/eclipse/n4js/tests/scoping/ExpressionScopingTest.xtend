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
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.IfStatement
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.NewExpression
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.RelationalExpression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class ExpressionScopingTest {

	@Inject extension ParseHelper<Script>

	@Test
	def void testInvocationOnThis() {
		val script = '''
			public class C {
				recursive(): C {
					this.recursive().recursive
				}
			}
		'''.parse
		val c = script.scriptElements.head as N4ClassDeclaration
		val recursive = c.ownedMembers.head as N4MethodDeclaration
		val statement = recursive.body.statements.head as ExpressionStatement
		val expressionPath = statement.expression as ParameterizedPropertyAccessExpression
		Assert.assertSame(recursive, (expressionPath.property as TMember).astElement)
	}

	@Test
	def void testInvocationOnThisWithInheritance() {
		val script = '''
			public class C {
				m1(): D {}
			}
			public class D extends C {
				m2(): C {
					this.m1
				}
			}
		'''.parse
		val c = script.scriptElements.head as N4ClassDeclaration
		val d = script.scriptElements.last as N4ClassDeclaration
		val m2 = d.ownedMembers.head as N4MethodDeclaration
		val statement = m2.body.statements.head as ExpressionStatement
		val expressionPath = statement.expression as ParameterizedPropertyAccessExpression
		Assert.assertSame(c.ownedMembers.head, (expressionPath.property as TMember).astElement)
	}

	@Test
	def void testFunctionDeclarationDefinedIdentifiable() {
		val script = '''
			function a() {}
			a = null
		'''.parse
		val a = script.scriptElements.head as FunctionDeclaration
		val assignment = (script.scriptElements.last as ExpressionStatement).expression as AssignmentExpression
		val lhs = assignment.lhs as IdentifierRef
		Assert.assertSame(a.definedType, lhs.id)
	}

	@Test
	def void testInstanceof() {
		val script = '''
			function a() {}
			if (null instanceof a) {}
		'''.parse
		val a = script.scriptElements.head as FunctionDeclaration
		val cond = (script.scriptElements.last as IfStatement).expression as RelationalExpression
		val type = cond.rhs as IdentifierRef
		Assert.assertSame(a.definedType, type.id)
	}

	@Test
	def void testNew_01() {
		val script = '''
			function a() {}
			new a()
		'''.parse
		val a = script.scriptElements.head as FunctionDeclaration
		val newExpr = (script.scriptElements.last as ExpressionStatement).expression as NewExpression
		val type = newExpr.callee as IdentifierRef
		Assert.assertSame(a.definedType, type.id)
	}


	@Test
	def void testNew_02() {
		val script = '''
			var a = function() {}
			new a()
		'''.parse
		val a = (script.scriptElements.head as VariableStatement).varDecl.head
		val newExpr = (script.scriptElements.last as ExpressionStatement).expression as NewExpression
		val type = newExpr.callee as IdentifierRef
		Assert.assertSame(a.definedVariable, type.id)
	}

}
