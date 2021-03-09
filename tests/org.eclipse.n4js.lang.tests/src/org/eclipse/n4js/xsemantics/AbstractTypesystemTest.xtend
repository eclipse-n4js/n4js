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
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.N4JSParseHelper
import org.eclipse.n4js.N4JSTestHelper
import org.eclipse.n4js.N4JSValidationTestHelper
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.ScriptElement
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef
import org.eclipse.n4js.ts.typeRefs.Wildcard
import org.eclipse.n4js.ts.types.TypableElement
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.Result
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.n4js.utils.Log
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.diagnostics.Severity
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.validation.Issue
import org.junit.runner.RunWith

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*
import static org.junit.Assert.*

/*
 * Base class for type system tests (with xsemantics)
 */
@Log
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
abstract class AbstractTypesystemTest {

	@Inject protected extension N4JSTestHelper
	@Inject protected extension N4JSParseHelper
	@Inject protected extension N4JSValidationTestHelper

	@Inject protected N4JSTypeSystem ts;
	@Inject protected TypeSystemHelper tsh;

	def Script createScript(JavaScriptVariant variant, String src) {
		return src.parse(variant)
	}

	def Script createAndValidateScript(JavaScriptVariant variant, String src) {
		val result = createScript(variant, src);
		assertNoValidationErrors(result);
		return result;
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


	// for readability of test cases we enforce using different TypeRef creation methods for generic and non-generic types:
	def protected static TypeRef ref(Type type) {
		if(type.generic)
			throw new IllegalArgumentException("type may not be generic; use methods #of() to create TypeRefs for generic types");
		return TypeUtils.createTypeRef(type);
	}
	def protected static TypeRef rawTypeRef(Type type) {
		if(!type.generic)
			throw new IllegalArgumentException("type must generic; use method #ref() to create TypeRefs for non-generic types");
		return TypeUtils.createTypeRef(type);
	}
	def protected static TypeRef of(Type type, Type... types) {
		return of(type,types.map[ref]);
	}
	def protected static TypeRef of(Type type, TypeArgument... typeArgs) {
		if(!type.generic)
			throw new IllegalArgumentException("type must generic; use method #ref() to create TypeRefs for non-generic types");
		if(type.typeVars.size !== typeArgs.size)
			throw new IllegalArgumentException("incorrect number of type arguments provided; required: "+type.typeVars.size+", got: "+typeArgs.size);
		return TypeUtils.createTypeRef(type,typeArgs);
	}


	def protected static UnionTypeExpression union(Type... types) {
		return TypeUtils.createNonSimplifiedUnionType(types.map[it.ref]);
	}
	def protected static IntersectionTypeExpression intersection(Type... types) {
		return TypeUtils.createNonSimplifiedIntersectionType(types.map[it.ref]);
	}
	def protected static UnionTypeExpression union(TypeRef... typeRefs) {
		return TypeUtils.createNonSimplifiedUnionType(typeRefs);
	}
	def protected static IntersectionTypeExpression intersection(TypeRef... typeRefs) {
		return TypeUtils.createNonSimplifiedIntersectionType(typeRefs);
	}
	def protected Wildcard wildcard() {
		return TypeRefsFactory.eINSTANCE.createWildcard;
	}
	def protected Wildcard wildcardExtends(Type type) {
		val w = TypeRefsFactory.eINSTANCE.createWildcard;
		w.declaredUpperBound = type.ref;
		return w;
	}
	def protected Wildcard wildcardExtends(TypeRef typeRef) {
		val w = TypeRefsFactory.eINSTANCE.createWildcard;
		w.declaredUpperBound = typeRef;
		return w;
	}
	def protected Wildcard wildcardSuper(Type type) {
		val w = TypeRefsFactory.eINSTANCE.createWildcard;
		w.declaredLowerBound = type.ref;
		return w;
	}
	def protected Wildcard wildcardSuper(TypeRef typeRef) {
		val w = TypeRefsFactory.eINSTANCE.createWildcard;
		w.declaredLowerBound = typeRef;
		return w;
	}
	def protected FunctionTypeExpression functionType(Type returnType, Type... fparTypes) {
		return functionType(#[], returnType, fparTypes);
	}
	def protected FunctionTypeExpression functionType(TypeRef returnTypeRef, TypeRef... fparTypeRefs) {
		return functionType(#[], returnTypeRef, fparTypeRefs);
	}
	def protected FunctionTypeExpression functionType(TypeVariable[] typeVars, Type returnType, Type... fparTypes) {
		return functionType(typeVars, returnType.ref, fparTypes.map[ref]);
	}
	def protected FunctionTypeExpression functionType(TypeVariable[] typeVars, TypeRef returnTypeRef, TypeRef... fparTypeRefs) {
		val fpars = fparTypeRefs.map[typeRef|
			val fpar = TypesFactory.eINSTANCE.createTFormalParameter;
			fpar.typeRef = typeRef;
			return fpar;
		];
		return TypeUtils.createFunctionTypeExpression(null, typeVars, fpars, returnTypeRef);
	}
}
