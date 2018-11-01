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
package org.eclipse.n4js.xsemantics

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.n4JS.AdditiveExpression
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.MultiplicativeExpression
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/*
 * Tests for judgment expectedtypein, see n4js.xsemantics for judgment, axiom and rules.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class JudgmentExpectedTypeInTest extends AbstractTypesystemTest {

	@Inject
	extension ValidationTestHelper

	@Test
	def void testAssignmentsOK() {

		// class A{}
		val script = createScript(JavaScriptVariant.n4js,
			'''
				class A{}
				class B extends A{}
				var a: A;
				var b: B;
				a;
				b;
				a = a;
				a = b;
			''')
		val G = newRuleEnvironment(script);

		val issues = script.validate();
		assertEquals(0, issues.size)
		val a = (script.scriptElements.get(4) as ExpressionStatement).expression as IdentifierRef
		val b = (script.scriptElements.get(5) as ExpressionStatement).expression as IdentifierRef
		val aToA = (script.scriptElements.get(6) as ExpressionStatement).expression as AssignmentExpression
		val aToB = (script.scriptElements.get(7) as ExpressionStatement).expression as AssignmentExpression

		var result = ts.type(G, a);
		assertNotNull(result)
		assertFalse(result instanceof UnknownTypeRef)
		assertEquals("A", (result as ParameterizedTypeRef).declaredType.name)

		result = ts.type(G, b);
		assertNotNull(result)
		assertFalse(result instanceof UnknownTypeRef)
		assertEquals("B", (result as ParameterizedTypeRef).declaredType.name)

		result = ts.type(G, aToA);
		assertNotNull(result)
		assertFalse(result instanceof UnknownTypeRef)
		assertEquals("A", (result as ParameterizedTypeRef).declaredType.name)

		result = ts.type(G, aToB);
		assertNotNull(result)
		assertFalse(result instanceof UnknownTypeRef)
		assertEquals("B", (result as ParameterizedTypeRef).declaredType.name)

	}

	@Test
	def void testAssignmentNotOK() {

		// class A{}
		val script = createScript(JavaScriptVariant.n4js,
			'''
				class A{}
				class B extends A{}
				var a: A;
				var b: B;
				a;
				b;
				b = a;
			''')
		val G = newRuleEnvironment(script);

		val a = (script.scriptElements.get(4) as ExpressionStatement).expression as IdentifierRef
		val b = (script.scriptElements.get(5) as ExpressionStatement).expression as IdentifierRef
		val bToA = (script.scriptElements.get(6) as ExpressionStatement).expression as AssignmentExpression

		var result = ts.type(G, a);
		assertNotNull(result)
		assertFalse(result instanceof UnknownTypeRef)
		assertEquals("A", (result as ParameterizedTypeRef).declaredType.name)

		result = ts.type(G, b);
		assertNotNull(result)
		assertFalse(result instanceof UnknownTypeRef)
		assertEquals("B", (result as ParameterizedTypeRef).declaredType.name)

		val aInAssignment = bToA.rhs
		var expectedType = ts.expectedTypeIn(G, aInAssignment.eContainer, aInAssignment);
		assertNotNull(expectedType)

		// eventually:
		val issues = script.validate();
		assertEquals(1, issues.size)

	}

	@Test
	def void testMultiplicativeExpression() {

		// class A{}
		val script = createScript(JavaScriptVariant.n4js,
			'''
				5 * 2;
				5 * "hello"
			''')
		val G = newRuleEnvironment(script);

		val mul1 = (script.scriptElements.get(0) as ExpressionStatement).expression as MultiplicativeExpression
		val mul2 = (script.scriptElements.get(1) as ExpressionStatement).expression as MultiplicativeExpression

		val builtInTypeScope = G.predefinedTypes.builtInTypeScope;

		var result = ts.type(G, mul1);
		assertType(result, builtInTypeScope.numberType)

		result = ts.type(G, mul1.lhs)
		assertType(result, builtInTypeScope.intType)

		result = ts.type(G, mul2);
		assertType(result, builtInTypeScope.numberType)

		result = ts.type(G, mul2.rhs)
		assertType(result, builtInTypeScope.stringType)

	}

	@Test
	def void testAdditiveExpression() {

		// class A{}
		val script = createScript(JavaScriptVariant.n4js,
			'''
				5 + 2;
				5 + "hello";
				"hello" + 4;
				5- 2;
				5-"Hello";
			''')

		val G = newRuleEnvironment(script);

		val add1 = (script.scriptElements.get(0) as ExpressionStatement).expression as AdditiveExpression
		val add2 = (script.scriptElements.get(1) as ExpressionStatement).expression as AdditiveExpression
		val add3 = (script.scriptElements.get(2) as ExpressionStatement).expression as AdditiveExpression
		val sub1 = (script.scriptElements.get(3) as ExpressionStatement).expression as AdditiveExpression
		val sub2 = (script.scriptElements.get(4) as ExpressionStatement).expression as AdditiveExpression

		val builtInTypeScope = G.predefinedTypes.builtInTypeScope;

		var result = ts.type(G, add1);
		assertType(result, builtInTypeScope.numberType)

		result = ts.type(G, add2);
		assertType(result, builtInTypeScope.stringType)

		result = ts.type(G, add3);
		assertType(result, builtInTypeScope.stringType)

		result = ts.type(G, sub1.lhs)
		assertType(result, builtInTypeScope.intType)

		result = ts.type(G, sub2.lhs)
		assertType(result, builtInTypeScope.intType)

	}

	@Test
	def void testExpectedTypeInVariableDeclaration() {

		// class A{}
		val script = createScript(JavaScriptVariant.n4js,
			'''
				class A{}
				class B extends A{}
				var b: B;
				var a: A = b;
			''')

		val issues = script.validate();
		assertEquals(0, issues.size)
		val varDecls = script.variableDeclarations

		"A".assertExpectedTypeName(varDecls.last.expression)
	}

	@Test
	def void testExpectedTypeInCallExpression() {
		val script = createScript(JavaScriptVariant.n4js,
			'''
				class A{}
				class B extends A{}
				function func(t: A): A { return null;}
				var b: B;
				func(b);
			''')

		val issues = script.validate();
		assertEquals(0, issues.size)
		val argExpr = script.callExpressions.head.arguments.head.expression
		"A".assertExpectedTypeName(argExpr)
	}

	@Test
	def void testExpectedTypeInCallExpressionWithGenerics() {
		val script = createScript(JavaScriptVariant.n4js,
			'''
				class A{}
				class B extends A{}
				function <T> func(t: T): T { return null;}
				var b: B;
				func(b);
			''')

		val issues = script.validate();
		assertEquals(0, issues.size)
		val argExpr = script.callExpressions.head.arguments.head.expression
		"B".assertExpectedTypeName(argExpr)
	}

}
