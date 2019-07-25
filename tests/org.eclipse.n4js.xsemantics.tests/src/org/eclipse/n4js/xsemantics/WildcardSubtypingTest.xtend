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
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.types.TClass
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
 * Test for subtype relations where at least one side is a wildcard.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class WildcardSubtypingTest extends AbstractTypesystemTest {

	private RuleEnvironment G;
	private TClass A;
	private TClass B;
	private TClass C;

	@Before
	def void before() {
		val script = createAndValidateScript(JavaScriptVariant.n4js, '''
			class A {}
			class B extends A {}
			class C extends B {}
		''');

		G = script.newRuleEnvironment;

		val types = script.module.topLevelTypes;
		A = types.filter[name=="A"].head as TClass;
		B = types.filter[name=="B"].head as TClass;
		C = types.filter[name=="C"].head as TClass;
		assertNotNull(A);
		assertNotNull(B);
		assertNotNull(C);
	}

	@Test
	def void testWildcards() {

		assertSubtype(G, wildcard(),         wildcard(),         false);
		assertSubtype(G, wildcardExtends(B), wildcard(),         false);
		assertSubtype(G, wildcardSuper(B),   wildcard(),         false);

		assertSubtype(G, wildcard(),         wildcardExtends(B), false);
		assertSubtype(G, wildcardExtends(A), wildcardExtends(B), false);
		assertSubtype(G, wildcardExtends(B), wildcardExtends(B), false);
		assertSubtype(G, wildcardExtends(C), wildcardExtends(B), false);
		assertSubtype(G, wildcardSuper(C),   wildcardExtends(B), false);
		assertSubtype(G, wildcardSuper(B),   wildcardExtends(B), false);
		assertSubtype(G, wildcardSuper(A),   wildcardExtends(B), false);

		assertSubtype(G, wildcard(),         wildcardSuper(B),   false);
		assertSubtype(G, wildcardExtends(A), wildcardSuper(B),   false);
		assertSubtype(G, wildcardExtends(B), wildcardSuper(B),   true);
		assertSubtype(G, wildcardExtends(C), wildcardSuper(B),   true);
		assertSubtype(G, wildcardSuper(C),   wildcardSuper(B),   false);
		assertSubtype(G, wildcardSuper(B),   wildcardSuper(B),   false);
		assertSubtype(G, wildcardSuper(A),   wildcardSuper(B),   false);

		assertSubtype(G, B.ref,              wildcard(),         false);
		assertSubtype(G, A.ref,              wildcardExtends(B), false);
		assertSubtype(G, B.ref,              wildcardExtends(B), false);
		assertSubtype(G, C.ref,              wildcardExtends(B), false);
		assertSubtype(G, A.ref,              wildcardSuper(B),   false);
		assertSubtype(G, B.ref,              wildcardSuper(B),   true);
		assertSubtype(G, C.ref,              wildcardSuper(B),   true);

		assertSubtype(G, wildcard(),         B.ref,              false);
		assertSubtype(G, wildcardExtends(B), A.ref,              true);
		assertSubtype(G, wildcardExtends(B), B.ref,              true);
		assertSubtype(G, wildcardExtends(B), C.ref,              false);
		assertSubtype(G, wildcardSuper(B),   A.ref,              false);
		assertSubtype(G, wildcardSuper(B),   B.ref,              false);
		assertSubtype(G, wildcardSuper(B),   C.ref,              false);
	}

	def private void assertSubtype(RuleEnvironment G, TypeArgument left, TypeArgument right, boolean expectedResult) {
		val actualResult = ts.subtypeSucceeded(G, left, right);
		if (actualResult !== expectedResult) {
			val msg = "expected " + left.typeRefAsString + " to " + (if(expectedResult) "be" else "not be")
				+ " a subtype of " + right.typeRefAsString;
			fail(msg);
		}
	}
}
