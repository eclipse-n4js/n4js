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

import com.google.common.base.StandardSystemProperty
import com.google.inject.Inject
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.N4JSParseHelper
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.ScriptElement
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef
import org.eclipse.n4js.ts.types.TypableElement
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.Result
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.utils.Log
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.diagnostics.Severity
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.eclipse.xtext.validation.Issue

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*
import static org.junit.Assert.*

/*
 * Base class for type system tests (with xsemantics)
 */
@Log
abstract class AbstractTypesystemTest {

	@Inject extension ValidationTestHelper

	@Inject protected N4JSTypeSystem ts;

	@Inject
	extension N4JSParseHelper

	def Script createScript(JavaScriptVariant variant, String src) {
		return src.parse(variant)
	}

	def assertTypeByName(String expectedTypeAsString, TypableElement expression) {
		val G = newRuleEnvironment(expression);
		val typeExpr = ts.type(G, expression)
		if (typeExpr instanceof UnknownTypeRef) {
			fail("UnknownTypeRef while typing " + expression);
		}
		assertEquals("Assert at " + expression, expectedTypeAsString, typeExpr.typeRefAsString);
	}


	/* Tests that the result's value is a ParameterizedTypeRef and the declared type must equal the given type. */
	def assertType(TypeRef result, Type expectedType) {
		assertNotNull(result)
		assertTrue(result instanceof ParameterizedTypeRef)
		assertEquals(expectedType, (result as ParameterizedTypeRef).declaredType)
	}

	def TypeRef checkedType(RuleEnvironment G, TypableElement eobj) {
		val result = ts.type(G, eobj);
		if (result instanceof UnknownTypeRef) {
			fail("UnknownTypeRef while typing " + eobj);
		}
		return result;
	}

	/*
	 * Tests that the inferred type of a given element equals the expected name,
	 */
	def void assertTypeName(String expectedTypeName, TypableElement elementToBeTypeInferred) {

		val G = newRuleEnvironment(elementToBeTypeInferred);
		val result = ts.type(G, elementToBeTypeInferred);
		assertEquals("Wrong type inferred", expectedTypeName, result.typeRefAsString)
	}

	/*
	 * Tests that the expected type of a given element, in the context of its container, equals the expected name,
	 */
	def void assertExpectedTypeName(String expectedTypeName, Expression element) {

		val G = newRuleEnvironment(element);
		val result = ts.expectedType(G,element.eContainer, element);

		assertNotNull("No expected type inferred", result)
		assertEquals("Wrong expected type inferred", expectedTypeName, result.typeRefAsString)
	}

	def void assertSubtype(RuleEnvironment G, TypeRef left, TypeRef right, boolean expectedResult) {
		assertNotNull("Left hand side must not be null", left)
		assertNotNull("Right hand side must not be null", right)
		assertFalse(left instanceof UnknownTypeRef)
		assertFalse(right instanceof UnknownTypeRef)

		val result = ts.subtype(G, left, right)
		if (expectedResult) {
			result.assertNoFailure
			assertEquals(expectedResult, result.success)
		} else {
			result.assertFailure
		}
	}

	/* Tests that the result's value is a boolean and equals the expected result. */
	def void assertSubtype(Result result, boolean expectedResult) {
		if (expectedResult) {
			result.assertNoFailure
			assertEquals(expectedResult, result.success)
		} else {
			assertTrue(result.failure)
		}
	}

	def void assertNoFailure(Result result) {
		assertFalse(result.failureMessage, result.failure)
	}

	def void assertFailure(Result result, String expectedFailureMessage) {
		result.assertFailure
		assertEquals(expectedFailureMessage, result.failureMessage)
	}

	def void assertFailure(Result result) {
		assertTrue("unexpected success", result.failure);
		assertNotNull(result.failureMessage);
	}

	def void assertIssueCount(int expectedNumber, List<Issue> issues) {
		if ((issues === null && expectedNumber == 0) || (issues !== null && issues.size() == expectedNumber)) {
			return
		}
		if (issues === null || issues.empty) {
			fail("Got no issues, expected " + expectedNumber)
		}
		val sb = new StringBuffer("Expected " + expectedNumber + " issues, got " + issues.size + ":");
		issues.forEach[sb.append("\n    " + it)]
		fail(sb.toString)
	}

	def void assertErrorMessages(List<Issue> issues, CharSequence expectedMessages) {
		assertEquals(expectedMessages.toString.trim, issues.map[message].join(StandardSystemProperty.LINE_SEPARATOR.value()))
	}

	def void assertNoValidationErrors(Script script) {
		val issues = script.validate().filter[it.severity!=Severity.WARNING].toList;
		assertEquals("unexpected validation errors:\n\t" + issues.map[toString].join("\n\t"),
			0, issues.size());
	}

	def variableStatementDeclaredType(ScriptElement e) {
		(e as VariableStatement).varDecl.head.declaredTypeRef
	}

	def variableDeclarations(Script script) {
		EcoreUtil2.getAllContentsOfType(script, VariableDeclaration)
	}

	def callExpressions(EObject e) {
		EcoreUtil2.getAllContentsOfType(e, ParameterizedCallExpression)
	}

	def classDeclarations(EObject e) {
		EcoreUtil2.getAllContentsOfType(e, N4ClassDeclaration)
	}
}
