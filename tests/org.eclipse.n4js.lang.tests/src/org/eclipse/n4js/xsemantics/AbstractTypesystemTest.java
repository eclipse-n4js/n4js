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

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.n4js.utils.Strings.join;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.size;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.N4JSParseHelper;
import org.eclipse.n4js.N4JSTestHelper;
import org.eclipse.n4js.N4JSValidationTestHelper;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.Log;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.validation.Issue;
import org.junit.Assert;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Base class for type system tests (with xsemantics)
 */
@Log
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
abstract public class AbstractTypesystemTest {

	@Inject
	protected N4JSTestHelper n4TestHelper;
	@Inject
	protected N4JSParseHelper n4ParseHelper;
	@Inject
	protected N4JSValidationTestHelper n4ValTestHelper;

	@Inject
	protected N4JSTypeSystem ts;
	@Inject
	protected TypeSystemHelper tsh;

	protected Script createScript(JavaScriptVariant variant, String src) {
		try {
			return n4ParseHelper.parse(src, variant);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
			return null;
		}
	}

	protected Script createAndValidateScript(JavaScriptVariant variant, String src) {
		Script result = createScript(variant, src);
		assertNoValidationErrors(result);
		return result;
	}

	protected void assertTypeByName(String expectedTypeAsString, TypableElement expression) {
		RuleEnvironment G = newRuleEnvironment(expression);
		TypeRef typeExpr = ts.type(G, expression);
		if (typeExpr instanceof UnknownTypeRef) {
			fail("UnknownTypeRef while typing " + expression);
		}
		assertEquals("Assert at " + expression, expectedTypeAsString, typeExpr.getTypeRefAsString());
	}

	/* Tests that the result's value is a ParameterizedTypeRef and the declared type must equal the given type. */
	protected void assertType(TypeRef result, Type expectedType) {
		assertNotNull(result);
		assertTrue(result instanceof ParameterizedTypeRef);
		assertEquals(expectedType, ((ParameterizedTypeRef) result).getDeclaredType());
	}

	protected TypeRef checkedType(RuleEnvironment G, TypableElement eobj) {
		TypeRef result = ts.type(G, eobj);
		if (result instanceof UnknownTypeRef) {
			fail("UnknownTypeRef while typing " + eobj);
		}
		return result;
	}

	/**
	 * Tests that the inferred type of a given element equals the expected name,
	 */
	protected void assertTypeName(String expectedTypeName, TypableElement elementToBeTypeInferred) {

		RuleEnvironment G = newRuleEnvironment(elementToBeTypeInferred);
		TypeRef result = ts.type(G, elementToBeTypeInferred);
		assertEquals("Wrong type inferred", expectedTypeName, result.getTypeRefAsString());
	}

	/**
	 * Tests that the expected type of a given element, in the context of its container, equals the expected name,
	 */
	protected void assertExpectedTypeName(String expectedTypeName, Expression element) {
		RuleEnvironment G = newRuleEnvironment(element);
		TypeRef result = ts.expectedType(G, element.eContainer(), element);

		assertNotNull("No expected type inferred", result);
		assertEquals("Wrong expected type inferred", expectedTypeName, result.getTypeRefAsString());
	}

	protected void assertSubtype(RuleEnvironment G, TypeRef left, TypeRef right, boolean expectedResult) {
		assertNotNull("Left hand side must not be null", left);
		assertNotNull("Right hand side must not be null", right);
		assertFalse(left instanceof UnknownTypeRef);
		assertFalse(right instanceof UnknownTypeRef);

		Result result = ts.subtype(G, left, right);
		if (expectedResult) {
			assertNoFailure(result);
			assertEquals(expectedResult, result.isSuccess());
		} else {
			assertFailure(result);
		}
	}

	/** Tests that the result's value is a boolean and equals the expected result. */
	protected void assertSubtype(Result result, boolean expectedResult) {
		if (expectedResult) {
			assertNoFailure(result);
			assertEquals(expectedResult, result.isSuccess());
		} else {
			assertTrue(result.isFailure());
		}
	}

	protected void assertNoFailure(Result result) {
		assertFalse(result.getFailureMessage(), result.isFailure());
	}

	protected void assertFailure(Result result, String expectedFailureMessage) {
		assertFailure(result);
		assertEquals(expectedFailureMessage, result.getFailureMessage());
	}

	protected void assertFailure(Result result) {
		assertTrue("unexpected success", result.isFailure());
		assertNotNull(result.getFailureMessage());
	}

	protected void assertIssueCount(int expectedNumber, List<Issue> issues) {
		if ((issues == null && expectedNumber == 0) || (issues != null && issues.size() == expectedNumber)) {
			return;
		}
		if (issues == null || issues.isEmpty()) {
			fail("Got no issues, expected " + expectedNumber);
			return;
		}
		StringBuffer sb = new StringBuffer("Expected " + expectedNumber + " issues, got " + issues.size() + ":");
		for (Issue issue : issues) {
			sb.append("\n    " + issue);
		}
		fail(sb.toString());
	}

	protected void assertErrorMessages(List<Issue> issues, CharSequence expectedMessages) {
		assertEquals(expectedMessages.toString().trim(),
				join("\n", i -> i.getMessage(), issues));
	}

	protected void assertNoValidationErrors(Script script) {
		List<Issue> issues = toList(
				filter(n4ValTestHelper.validate(script), issue -> issue.getSeverity() != Severity.WARNING));
		assertEquals("unexpected validation errors:\n\t" + join("\n\t", iss -> iss.toString(), issues),
				0, issues.size());
	}

	protected TypeRef variableStatementDeclaredType(ScriptElement e) {
		return ((VariableStatement) e).getVarDecl().get(0).getDeclaredTypeRef();
	}

	protected List<VariableDeclaration> variableDeclarations(Script script) {
		return EcoreUtil2.getAllContentsOfType(script, VariableDeclaration.class);
	}

	protected List<ParameterizedCallExpression> callExpressions(EObject e) {
		return EcoreUtil2.getAllContentsOfType(e, ParameterizedCallExpression.class);
	}

	protected List<N4ClassDeclaration> classDeclarations(EObject e) {
		return EcoreUtil2.getAllContentsOfType(e, N4ClassDeclaration.class);
	}

	// for readability of test cases we enforce using different TypeRef creation methods for generic and non-generic
	// types:
	protected static TypeRef ref(Type type) {
		if (type.isGeneric()) {
			throw new IllegalArgumentException(
					"type may not be generic; use methods #of() to create TypeRefs for generic types");
		}
		return TypeUtils.createTypeRef(type);
	}

	protected static TypeRef rawTypeRef(Type type, TypeArgument... typeArgs) {
		if (!type.isGeneric()) {
			throw new IllegalArgumentException(
					"type must generic; use method #ref() to create TypeRefs for non-generic types");
		}
		int minArgCount = size(filter(type.getTypeVars(), tv -> !tv.isOptional()));
		if (typeArgs.length >= minArgCount)
			throw new IllegalArgumentException(
					"enough type arguments provided; use method #of() to create non-raw TypeRefs");
		return TypeUtils.createTypeRef(type, typeArgs);
	}

	protected static TypeRef of(Type type, Type... types) {
		return of(type, toList(map(Arrays.asList(types), t -> ref(t))).toArray(new TypeRef[0]));
	}

	protected static TypeRef of(Type type, TypeArgument... typeArgs) {
		if (!type.isGeneric()) {
			throw new IllegalArgumentException(
					"type must generic; use method #ref() to create TypeRefs for non-generic types");
		}
		int minArgCount = size(filter(type.getTypeVars(), tv -> !tv.isOptional()));
		int maxArgCount = type.getTypeVars().size();
		if (typeArgs.length < minArgCount) {
			throw new IllegalArgumentException(
					"too few type arguments provided; required: at least " + minArgCount + ", got: " + typeArgs.length);
		}
		if (typeArgs.length > maxArgCount) {
			throw new IllegalArgumentException("too many type arguments provided; required: not more than "
					+ maxArgCount + ", got: " + typeArgs.length);
		}
		return TypeUtils.createTypeRef(type, typeArgs);
	}

	protected static UnionTypeExpression union(Type... types) {
		return TypeUtils
				.createNonSimplifiedUnionType(toList(map(Arrays.asList(types), t -> ref(t))).toArray(new TypeRef[0]));
	}

	protected static IntersectionTypeExpression intersection(Type... types) {
		return TypeUtils.createNonSimplifiedIntersectionType(
				toList(map(Arrays.asList(types), t -> ref(t))).toArray(new TypeRef[0]));
	}

	protected static UnionTypeExpression union(TypeRef... typeRefs) {
		return TypeUtils.createNonSimplifiedUnionType(typeRefs);
	}

	protected static IntersectionTypeExpression intersection(TypeRef... typeRefs) {
		return TypeUtils.createNonSimplifiedIntersectionType(typeRefs);
	}

	protected Wildcard wildcard() {
		return TypeRefsFactory.eINSTANCE.createWildcard();
	}

	protected Wildcard wildcardExtends(Type type) {
		Wildcard w = TypeRefsFactory.eINSTANCE.createWildcard();
		w.setDeclaredUpperBound(ref(type));
		return w;
	}

	protected Wildcard wildcardExtends(TypeRef typeRef) {
		Wildcard w = TypeRefsFactory.eINSTANCE.createWildcard();
		w.setDeclaredUpperBound(typeRef);
		return w;
	}

	protected Wildcard wildcardSuper(Type type) {
		Wildcard w = TypeRefsFactory.eINSTANCE.createWildcard();
		w.setDeclaredLowerBound(ref(type));
		return w;
	}

	protected Wildcard wildcardSuper(TypeRef typeRef) {
		Wildcard w = TypeRefsFactory.eINSTANCE.createWildcard();
		w.setDeclaredLowerBound(typeRef);
		return w;
	}

	protected FunctionTypeExpression functionType(Type returnType, Type... fparTypes) {
		return functionType(new TypeVariable[0], returnType, fparTypes);
	}

	protected FunctionTypeExpression functionType(TypeRef returnTypeRef, TypeRef... fparTypeRefs) {
		return functionType(new TypeVariable[0], returnTypeRef, fparTypeRefs);
	}

	protected FunctionTypeExpression functionType(TypeVariable[] typeVars, Type returnType, Type... fparTypes) {
		return functionType(typeVars, ref(returnType),
				toList(map(Arrays.asList(fparTypes), t -> ref(t))).toArray(new TypeRef[0]));
	}

	protected FunctionTypeExpression functionType(TypeVariable[] typeVars, TypeRef returnTypeRef,
			TypeRef... fparTypeRefs) {

		List<TFormalParameter> fpars = toList(map(Arrays.asList(fparTypeRefs), typeRef -> {
			TFormalParameter fpar = TypesFactory.eINSTANCE.createTFormalParameter();
			fpar.setTypeRef(typeRef);
			return fpar;
		}));
		return TypeUtils.createFunctionTypeExpression(null, Arrays.asList(typeVars), fpars, returnTypeRef);
	}
}
