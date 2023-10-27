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
package org.eclipse.n4js.xsemantics;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getPredefinedTypes;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.n4JS.AdditiveExpression;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.MultiplicativeExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests for judgment expectedtypein, see n4js.xsemantics for judgment, axiom and rules.
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class JudgmentExpectedTypeInTest extends AbstractTypesystemTest {

	@Inject
	ValidationTestHelper valTestHelper;

	@Test
	public void testAssignmentsOK() {

		// class A{}
		Script script = createScript(JavaScriptVariant.n4js,
				"""
						class A{}
						class B extends A{}
						var a: A;
						var b: B;
						a;
						b;
						a = a;
						a = b;
						""");
		RuleEnvironment G = newRuleEnvironment(script);

		List<Issue> issues = valTestHelper.validate(script);
		assertEquals(0, issues.size());
		IdentifierRef a = (IdentifierRef) ((ExpressionStatement) script.getScriptElements().get(4)).getExpression();
		IdentifierRef b = (IdentifierRef) ((ExpressionStatement) script.getScriptElements().get(5)).getExpression();
		AssignmentExpression aToA = (AssignmentExpression) ((ExpressionStatement) script.getScriptElements().get(6))
				.getExpression();
		AssignmentExpression aToB = (AssignmentExpression) ((ExpressionStatement) script.getScriptElements().get(7))
				.getExpression();

		var result = ts.type(G, a);
		assertNotNull(result);
		assertFalse(result instanceof UnknownTypeRef);
		assertEquals("A", ((ParameterizedTypeRef) result).getDeclaredType().getName());

		result = ts.type(G, b);
		assertNotNull(result);
		assertFalse(result instanceof UnknownTypeRef);
		assertEquals("B", ((ParameterizedTypeRef) result).getDeclaredType().getName());

		result = ts.type(G, aToA);
		assertNotNull(result);
		assertFalse(result instanceof UnknownTypeRef);
		assertEquals("A", ((ParameterizedTypeRef) result).getDeclaredType().getName());

		result = ts.type(G, aToB);
		assertNotNull(result);
		assertFalse(result instanceof UnknownTypeRef);
		assertEquals("B", ((ParameterizedTypeRef) result).getDeclaredType().getName());

	}

	@Test
	public void testAssignmentNotOK() {

		// class A{}
		Script script = createScript(JavaScriptVariant.n4js,
				"""
						class A{}
						class B extends A{}
						var a: A;
						var b: B;
						a;
						b;
						b = a;
						""");
		RuleEnvironment G = newRuleEnvironment(script);

		IdentifierRef a = (IdentifierRef) ((ExpressionStatement) script.getScriptElements().get(4)).getExpression();
		IdentifierRef b = (IdentifierRef) ((ExpressionStatement) script.getScriptElements().get(5)).getExpression();
		AssignmentExpression bToA = (AssignmentExpression) ((ExpressionStatement) script.getScriptElements().get(6))
				.getExpression();

		TypeRef result = ts.type(G, a);
		assertNotNull(result);
		assertFalse(result instanceof UnknownTypeRef);
		assertEquals("A", ((ParameterizedTypeRef) result).getDeclaredType().getName());

		result = ts.type(G, b);
		assertNotNull(result);
		assertFalse(result instanceof UnknownTypeRef);
		assertEquals("B", ((ParameterizedTypeRef) result).getDeclaredType().getName());

		Expression aInAssignment = bToA.getRhs();
		TypeRef expectedType = ts.expectedType(G, aInAssignment.eContainer(), aInAssignment);
		assertNotNull(expectedType);

		// eventually:
		List<Issue> issues = valTestHelper.validate(script);
		assertEquals(1, issues.size());

	}

	@Test
	public void testMultiplicativeExpression() {

		// class A{}
		Script script = createScript(JavaScriptVariant.n4js,
				"""
						5 * 2;
						5 * "hello"
						""");
		RuleEnvironment G = newRuleEnvironment(script);

		MultiplicativeExpression mul1 = (MultiplicativeExpression) ((ExpressionStatement) script.getScriptElements()
				.get(0)).getExpression();
		MultiplicativeExpression mul2 = (MultiplicativeExpression) ((ExpressionStatement) script.getScriptElements()
				.get(1)).getExpression();

		BuiltInTypeScope builtInTypeScope = getPredefinedTypes(G).builtInTypeScope;

		TypeRef result = ts.type(G, mul1);
		assertType(result, builtInTypeScope.getNumberType());

		result = ts.type(G, mul2);
		assertType(result, builtInTypeScope.getNumberType());

	}

	@Test
	public void testAdditiveExpression() {

		// class A{}
		Script script = createScript(JavaScriptVariant.n4js,
				"""
						5 + 2;
						5 + "hello";
						"hello" + 4;
						5- 2;
						5-"Hello";
						""");

		RuleEnvironment G = newRuleEnvironment(script);

		AdditiveExpression add1 = (AdditiveExpression) ((ExpressionStatement) script.getScriptElements().get(0))
				.getExpression();
		AdditiveExpression add2 = (AdditiveExpression) ((ExpressionStatement) script.getScriptElements().get(1))
				.getExpression();
		AdditiveExpression add3 = (AdditiveExpression) ((ExpressionStatement) script.getScriptElements().get(2))
				.getExpression();
		AdditiveExpression sub1 = (AdditiveExpression) ((ExpressionStatement) script.getScriptElements().get(3))
				.getExpression();
		AdditiveExpression sub2 = (AdditiveExpression) ((ExpressionStatement) script.getScriptElements().get(4))
				.getExpression();

		BuiltInTypeScope builtInTypeScope = getPredefinedTypes(G).builtInTypeScope;

		TypeRef result = ts.type(G, add1);
		assertType(result, builtInTypeScope.getNumberType());

		result = ts.type(G, add2);
		assertType(result, builtInTypeScope.getStringType());

		result = ts.type(G, add3);
		assertType(result, builtInTypeScope.getStringType());

		result = ts.type(G, sub1);
		assertType(result, builtInTypeScope.getNumberType());

		result = ts.type(G, sub2);
		assertType(result, builtInTypeScope.getNumberType());

	}

	@Test
	public void testExpectedTypeInVariableDeclaration() {

		// class A{}
		Script script = createScript(JavaScriptVariant.n4js,
				"""
						class A{}
						class B extends A{}
						var b: B;
						var a: A = b;
						""");

		List<Issue> issues = valTestHelper.validate(script);
		assertEquals(0, issues.size());
		List<VariableDeclaration> varDecls = variableDeclarations(script);

		assertExpectedTypeName("A", last(varDecls).getExpression());
	}

	@Test
	public void testExpectedTypeInCallExpression() {
		Script script = createScript(JavaScriptVariant.n4js,
				"""
						class A{}
						class B extends A{}
						function func(t: A): A { return null;}
						var b: B;
						func(b);
						""");

		List<Issue> issues = valTestHelper.validate(script);
		assertEquals(0, issues.size());
		Expression argExpr = callExpressions(script).get(0).getArguments().get(0).getExpression();
		assertExpectedTypeName("A", argExpr);
	}

	@Test
	public void testExpectedTypeInCallExpressionWithGenerics() {
		Script script = createScript(JavaScriptVariant.n4js,
				"""
						class A{}
						class B extends A{}
						function <T> func(t: T): T { return null;}
						var b: B;
						func(b);
						""");

		List<Issue> issues = valTestHelper.validate(script);
		assertEquals(0, issues.size());
		Expression argExpr = callExpressions(script).get(0).getArguments().get(0).getExpression();
		assertExpectedTypeName("B", argExpr);
	}

}
