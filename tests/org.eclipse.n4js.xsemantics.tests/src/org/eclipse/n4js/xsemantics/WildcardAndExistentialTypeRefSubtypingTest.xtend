/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xsemantics

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.Wildcard
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Test for subtype relations where at least one side is a wildcard or (closed or reopened) ExistentialTypeRef.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class WildcardAndExistentialTypeRefSubtypingTest extends AbstractTypesystemTest {

	private RuleEnvironment G;
	private Script script;
	private TClass A;
	private TClass B;
	private TClass C;
	private TClass List;

	@Before
	def void before() {
		script = createAndValidateScript(JavaScriptVariant.n4js, '''
			class A {}
			class B extends A {}
			class C extends B {}
			class List<T> {}
		''');

		G = script.newRuleEnvironment;

		val types = script.module.topLevelTypes;
		A = types.filter[name=="A"].head as TClass;
		B = types.filter[name=="B"].head as TClass;
		C = types.filter[name=="C"].head as TClass;
		List = types.filter[name=="List"].head as TClass;
		assertNotNull(A);
		assertNotNull(B);
		assertNotNull(C);
		assertNotNull(List);
	}

	@Test
	def void testNoBoundOnRHS() {
		assertSubtypeOfVariations(false, wildcard(),         wildcard()         );
		assertSubtypeOfVariations(false, wildcardExtends(B), wildcard()         );
		assertSubtypeOfVariations(false, wildcardSuper(B),   wildcard()         );
		assertSubtypeOfVariations(false, B.ref,              wildcard()         );
	}

	@Test
	def void testUpperBoundOnRHS() {
		assertSubtypeOfVariations(false, wildcard(),         wildcardExtends(B) );
		assertSubtypeOfVariations(false, wildcardExtends(A), wildcardExtends(B) );
		assertSubtypeOfVariations(false, wildcardExtends(B), wildcardExtends(B) );
		assertSubtypeOfVariations(false, wildcardExtends(C), wildcardExtends(B) );
		assertSubtypeOfVariations(false, wildcardSuper(C),   wildcardExtends(B) );
		assertSubtypeOfVariations(false, wildcardSuper(B),   wildcardExtends(B) );
		assertSubtypeOfVariations(false, wildcardSuper(A),   wildcardExtends(B) );
		assertSubtypeOfVariations(false, A.ref,              wildcardExtends(B) );
		assertSubtypeOfVariations(false, B.ref,              wildcardExtends(B) );
		assertSubtypeOfVariations(false, C.ref,              wildcardExtends(B) );
	}

	@Test
	def void testLowerBoundOnRHS() {
		assertSubtypeOfVariations(false, wildcard(),         wildcardSuper(B)   );
		assertSubtypeOfVariations(false, wildcardExtends(A), wildcardSuper(B)   );
		assertSubtypeOfVariations(true,  wildcardExtends(B), wildcardSuper(B)   );
		assertSubtypeOfVariations(true,  wildcardExtends(C), wildcardSuper(B)   );
		assertSubtypeOfVariations(false, wildcardSuper(C),   wildcardSuper(B)   );
		assertSubtypeOfVariations(false, wildcardSuper(B),   wildcardSuper(B)   );
		assertSubtypeOfVariations(false, wildcardSuper(A),   wildcardSuper(B)   );
		assertSubtypeOfVariations(false, A.ref,              wildcardSuper(B)   );
		assertSubtypeOfVariations(true,  B.ref,              wildcardSuper(B)   );
		assertSubtypeOfVariations(true,  C.ref,              wildcardSuper(B)   );
	}

	@Test
	def void testSimpleTypeOnRHS() {
		assertSubtypeOfVariations(false, wildcard(),         B.ref              );
		assertSubtypeOfVariations(true,  wildcardExtends(B), A.ref              );
		assertSubtypeOfVariations(true,  wildcardExtends(B), B.ref              );
		assertSubtypeOfVariations(false, wildcardExtends(B), C.ref              );
		assertSubtypeOfVariations(false, wildcardSuper(B),   A.ref              );
		assertSubtypeOfVariations(false, wildcardSuper(B),   B.ref              );
		assertSubtypeOfVariations(false, wildcardSuper(B),   C.ref              );
	}

	/**
	 * Asserts the subtype check {@code left <: right} to be successful or not successful (depending on
	 * the value of 'expectedResult').
	 * <p>
	 * Because in the cases tested here, i.e. wildcards appearing on the top-level of a subtype check
	 * and *not* as the type argument within a ParameterizedTypeRef, closed and reopened ExistentialTypeRefs
	 * are expected to behave the same way as wildcards. Therefore this methods also asserts these corresponding
	 * cases, to avoid duplicating the test cases above.
	 */
	def private void assertSubtypeOfVariations(boolean expectedResult, TypeArgument left, TypeArgument right) {
		// step #1: test type arguments as they were passed in
		assertSubtype("", left, "", right, expectedResult);
		// step #2: captured ExistentialTypeRefs must behave the same way as wildcards
		val left2 = if (left instanceof Wildcard) capture(left) else left;
		val right2 = if (right instanceof Wildcard) capture(right) else right;
		if (left2 !== left) {
			assertSubtype("captured", left2, "", right, expectedResult);
		}
		if (right2 !== right) {
			assertSubtype("", left, "captured", right2, expectedResult);
		}
		if (left2 !== left && right2 !== right) {
			assertSubtype("captured", left2, "captured", right2, expectedResult);
		}
		// step #3: captured and then reopened ExistentialTypeRefs must behave the same way as wildcards
		val left3 = if (left instanceof Wildcard) captureAndReopen(left) else left;
		val right3 = if (right instanceof Wildcard) captureAndReopen(right) else right;
		if (left3 !== left) {
			assertSubtype("captured-and-reopened", left3, "", right, expectedResult);
		}
		if (right3 !== right) {
			assertSubtype("", left, "captured-and-reopened", right3, expectedResult);
		}
		if (left3 !== left && right3 !== right) {
			assertSubtype("captured-and-reopened", left3, "captured-and-reopened", right3, expectedResult);
		}
	}

	def private void assertSubtype(String msgPrefixLeft, TypeArgument left, String msgPrefixRight, TypeArgument right, boolean expectedResult) {
		val actualResult = ts.subtypeSucceeded(G, left, right);
		if (actualResult !== expectedResult) {
			val msg = "expected "
				+ msgPrefixLeft + (if(!msgPrefixLeft.empty) " " else "") + left.typeRefAsString
				+ " to " + (if(expectedResult) "be" else "not be")
				+ " a subtype of "
				+ msgPrefixRight + (if(!msgPrefixRight.empty) " " else "") + right.typeRefAsString;
			fail(msg);
		}
	}

	def private TypeRef captureAndReopen(Wildcard wildcard) {
		val captured = capture(wildcard);
		val reopened = ts.reopenExistentialTypes(G, captured);
		return reopened;
	}

	def private TypeRef capture(Wildcard wildcard) {
		val captured = TypeUtils.captureWildcard(List.typeVars.head, wildcard);
		return captured;
	}
}
