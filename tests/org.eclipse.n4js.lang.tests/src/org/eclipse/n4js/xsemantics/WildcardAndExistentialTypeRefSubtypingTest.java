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
package org.eclipse.n4js.xsemantics;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.WildcardCaptureTestHelper;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Test for subtype relations where at least one side is a wildcard or (closed or reopened) ExistentialTypeRef.
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class WildcardAndExistentialTypeRefSubtypingTest extends AbstractTypesystemTest {

	@Inject
	private WildcardCaptureTestHelper wildcardCaptureTestHelper;

	private RuleEnvironment G;
	private Script script;
	private TClass A;
	private TClass B;
	private TClass C;
	private TClass List;

	@Before
	public void before() {
		script = createAndValidateScript(JavaScriptVariant.n4js, """
				class A {}
				class B extends A {}
				class C extends B {}
				class List<T> {}
				""");

		G = newRuleEnvironment(script);

		EList<Type> types = script.getModule().getTypes();
		A = (TClass) findFirst(types, t -> "A".equals(t.getName()));
		B = (TClass) findFirst(types, t -> "B".equals(t.getName()));
		C = (TClass) findFirst(types, t -> "C".equals(t.getName()));
		List = (TClass) findFirst(types, t -> "List".equals(t.getName()));
		assertNotNull(A);
		assertNotNull(B);
		assertNotNull(C);
		assertNotNull(List);
	}

	@Test
	public void testNoBoundOnRHS() {
		// @formatter:off
		assertSubtypeOfVariations(false, wildcard(),         wildcard()         );
		assertSubtypeOfVariations(false, wildcardExtends(B), wildcard()         );
		assertSubtypeOfVariations(false, wildcardSuper(B),   wildcard()         );
		assertSubtypeOfVariations(false, ref(B),             wildcard()         );
		// @formatter:on
	}

	@Test
	public void testUpperBoundOnRHS() {
		// @formatter:off
		assertSubtypeOfVariations(false, wildcard(),         wildcardExtends(B) );
		assertSubtypeOfVariations(false, wildcardExtends(A), wildcardExtends(B) );
		assertSubtypeOfVariations(false, wildcardExtends(B), wildcardExtends(B) );
		assertSubtypeOfVariations(false, wildcardExtends(C), wildcardExtends(B) );
		assertSubtypeOfVariations(false, wildcardSuper(C),   wildcardExtends(B) );
		assertSubtypeOfVariations(false, wildcardSuper(B),   wildcardExtends(B) );
		assertSubtypeOfVariations(false, wildcardSuper(A),   wildcardExtends(B) );
		assertSubtypeOfVariations(false, ref(A),             wildcardExtends(B) );
		assertSubtypeOfVariations(false, ref(B),             wildcardExtends(B) );
		assertSubtypeOfVariations(false, ref(C),             wildcardExtends(B) );
		// @formatter:on
	}

	@Test
	public void testLowerBoundOnRHS() {
		// @formatter:off
		assertSubtypeOfVariations(false, wildcard(),         wildcardSuper(B)   );
		assertSubtypeOfVariations(false, wildcardExtends(A), wildcardSuper(B)   );
		assertSubtypeOfVariations(true,  wildcardExtends(B), wildcardSuper(B)   );
		assertSubtypeOfVariations(true,  wildcardExtends(C), wildcardSuper(B)   );
		assertSubtypeOfVariations(false, wildcardSuper(C),   wildcardSuper(B)   );
		assertSubtypeOfVariations(false, wildcardSuper(B),   wildcardSuper(B)   );
		assertSubtypeOfVariations(false, wildcardSuper(A),   wildcardSuper(B)   );
		assertSubtypeOfVariations(false, ref(A),             wildcardSuper(B)   );
		assertSubtypeOfVariations(true,  ref(B),             wildcardSuper(B)   );
		assertSubtypeOfVariations(true,  ref(C),             wildcardSuper(B)   );
		// @formatter:on
	}

	@Test
	public void testSimpleTypeOnRHS() {
		// @formatter:off
		assertSubtypeOfVariations(false, wildcard(),         ref(B)             );
		assertSubtypeOfVariations(true,  wildcardExtends(B), ref(A)             );
		assertSubtypeOfVariations(true,  wildcardExtends(B), ref(B)             );
		assertSubtypeOfVariations(false, wildcardExtends(B), ref(C)             );
		assertSubtypeOfVariations(false, wildcardSuper(B),   ref(A)             );
		assertSubtypeOfVariations(false, wildcardSuper(B),   ref(B)             );
		assertSubtypeOfVariations(false, wildcardSuper(B),   ref(C)             );
		// @formatter:on
	}

	/**
	 * Asserts the subtype check {@code left <: right} to be successful or not successful (depending on the value of
	 * 'expectedResult').
	 * <p>
	 * Because in the cases tested here, i.e. wildcards appearing on the top-level of a subtype check and *not* as the
	 * type argument within a ParameterizedTypeRef, closed and reopened ExistentialTypeRefs are expected to behave the
	 * same way as wildcards. Therefore this methods also asserts these corresponding cases, to avoid duplicating the
	 * test cases above.
	 */
	private void assertSubtypeOfVariations(boolean expectedResult, TypeArgument left, TypeArgument right) {

		List<List<TypeArgument>> variations = wildcardCaptureTestHelper.createCaptureVariations(
				java.util.List.of(left, right));

		for (List<TypeArgument> variation : variations) {
			TypeArgument currLeft = variation.get(0);
			TypeArgument currRight = variation.get(1);
			assertSubtype(getCaptureInfo(currLeft), currLeft, getCaptureInfo(currRight), currRight, expectedResult);
		}
	}

	private void assertSubtype(String msgPrefixLeft, TypeArgument left, String msgPrefixRight, TypeArgument right,
			boolean expectedResult) {

		boolean actualResult = ts.subtypeSucceeded(G, left, right);
		if (actualResult != expectedResult) {
			String msg = "expected "
					+ msgPrefixLeft + ((!msgPrefixLeft.isEmpty()) ? " " : "") + left.getTypeRefAsString()
					+ " to " + ((expectedResult) ? "be" : "not be")
					+ " a subtype of "
					+ msgPrefixRight + ((!msgPrefixRight.isEmpty()) ? " " : "") + right.getTypeRefAsString();
			fail(msg);
		}
	}

	private String getCaptureInfo(TypeArgument typeArg) {
		if (typeArg instanceof ExistentialTypeRef) {
			return (((ExistentialTypeRef) typeArg).isReopened())
					? "captured-and-reopened"
					: "captured";
		} else {
			return "";
		}
	}
}
