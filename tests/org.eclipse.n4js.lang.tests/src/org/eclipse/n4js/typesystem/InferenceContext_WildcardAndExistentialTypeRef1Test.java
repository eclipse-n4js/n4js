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
package org.eclipse.n4js.typesystem;

import java.util.Arrays;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.WildcardCaptureTestHelper;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.InferenceVariable;
import org.eclipse.n4js.typesystem.constraints.TypeConstraint;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Here we test constraints having bare wildcards on the top level (not wildcards as type argument within a
 * ParameterizedTypeRef, which is tested in {@link InferenceContext_GenericsTest}.
 * <p>
 * PART 1 of 2:<br>
 * wildcards and captured-and-reopened wildcards (i.e. ExistentialTypeRefs with reopened === true) are tested here.
 */
@SuppressWarnings("unchecked")
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class InferenceContext_WildcardAndExistentialTypeRef1Test extends AbstractInferenceContextTest {

	@Inject
	private WildcardCaptureTestHelper wildcardCaptureTestHelper;

	// --------------------------------------------------------------------------------------------

	@Test
	public void test_rhs_wildcard() {
		assertSolutionOfVariations(script,
				new TypeConstraint[] {
						// ⟨ α <: ? ⟩
						constraint(alpha, "<:", wildcard())
				},
				Pair.of(alpha, ref(bottom())));
		assertNoSolutionOfVariations(script,
				new TypeConstraint[] {
						// ⟨ α <: ? ⟩
						constraint(alpha, "<:", wildcard()),
						// ⟨ B <: α ⟩
						constraint(B, "<:", alpha)
				},
				alpha);
	}

	@Test
	public void test_rhs_wildcard_upperBound() {
		assertSolutionOfVariations(script,
				new TypeConstraint[] {
						// ⟨ α <: ? extends B ⟩
						constraint(alpha, "<:", wildcardExtends(B))
				},
				Pair.of(alpha, ref(bottom())));
		assertNoSolutionOfVariations(script,
				new TypeConstraint[] {
						// ⟨ α <: ? extends B ⟩
						constraint(alpha, "<:", wildcardExtends(B)),
						// ⟨ B <: α ⟩
						constraint(B, "<:", alpha)
				},
				alpha);
	}

	@Test
	public void test_rhs_wildcard_lowerBound() {
		assertSolutionOfVariations(script,
				new TypeConstraint[] {
						// ⟨ α <: ? super B ⟩
						constraint(alpha, "<:", wildcardSuper(B))
				},
				Pair.of(alpha, ref(B)));
	}

	// --------------------------------------------------------------------------------------------

	@Test
	public void test_lhs_wildcard() {
		assertSolutionOfVariations(script,
				new TypeConstraint[] {
						// ⟨ ? <: α ⟩
						constraint(wildcard(), "<:", alpha)
				},
				Pair.of(alpha, ref(top())));
		assertNoSolutionOfVariations(script,
				new TypeConstraint[] {
						// ⟨ ? <: α ⟩
						constraint(wildcard(), "<:", alpha),
						// ⟨ α <: B ⟩
						constraint(alpha, "<:", B)
				},
				alpha);
	}

	@Test
	public void test_lhs_wildcard_upperBound() {
		assertSolutionOfVariations(script,
				new TypeConstraint[] {
						// ⟨ ? extends B <: α ⟩
						constraint(wildcardExtends(B), "<:", alpha)
				},
				Pair.of(alpha, ref(B)));
	}

	@Test
	public void test_lhs_wildcard_lowerBound() {
		assertSolutionOfVariations(script,
				new TypeConstraint[] {
						// ⟨ ? super B <: α ⟩
						constraint(wildcardSuper(B), "<:", alpha)
				},
				Pair.of(alpha, ref(top())));
		assertNoSolutionOfVariations(script,
				new TypeConstraint[] {
						// ⟨ ? super B <: α ⟩
						constraint(wildcardSuper(B), "<:", alpha),
						// ⟨ α <: B ⟩
						constraint(alpha, "<:", B)
				},
				alpha);
	}

	// --------------------------------------------------------------------------------------------

	@Test
	public void test_both_wildcard_lhs_infVar() {
		assertSolutionOfVariations(script,
				new TypeConstraint[] {
						// ⟨ ? extends α <: ? extends B ⟩
						constraint(wildcardExtends(alpha), "<:", wildcardExtends(B))
				},
				Pair.of(alpha, ref(bottom())));

		assertNoSolutionOfVariations(script,
				new TypeConstraint[] {
						// ⟨ ? super α <: ? extends B ⟩
						constraint(wildcardSuper(alpha), "<:", wildcardExtends(B))
				},
				alpha);

		assertSolutionOfVariations(script,
				new TypeConstraint[] {
						// ⟨ ? extends α <: ? super B ⟩
						constraint(wildcardExtends(alpha), "<:", wildcardSuper(B))
				},
				Pair.of(alpha, ref(B)));

		assertNoSolutionOfVariations(script,
				new TypeConstraint[] {
						// ⟨ ? super α <: ? super B ⟩
						constraint(wildcardSuper(alpha), "<:", wildcardSuper(B))
				},
				alpha);
	}

	@Test
	public void test_both_wildcard_rhs_infVar() {
		assertNoSolutionOfVariations(script,
				new TypeConstraint[] {
						// ⟨ ? extends B <: ? extends α ⟩
						constraint(wildcardExtends(B), "<:", wildcardExtends(alpha))
				},
				alpha);

		assertNoSolutionOfVariations(script,
				new TypeConstraint[] {
						// ⟨ ? super B <: ? extends α ⟩
						constraint(wildcardSuper(B), "<:", wildcardExtends(alpha))
				},
				alpha);

		assertSolutionOfVariations(script,
				new TypeConstraint[] {
						// ⟨ ? extends B <: ? super α ⟩
						constraint(wildcardExtends(B), "<:", wildcardSuper(alpha))
				},
				Pair.of(alpha, ref(B)));

		assertSolutionOfVariations(script,
				new TypeConstraint[] {
						// ⟨ ? super B <: ? super α ⟩
						constraint(wildcardSuper(B), "<:", wildcardSuper(alpha))
				},
				Pair.of(alpha, ref(top())));
	}

	// --------------------------------------------------------------------------------------------

	/**
	 * Same as {@code #assertSolution()} from super class, but runs several test cases in one go:
	 * <p>
	 * Because in the cases tested here, i.e. wildcards appearing on the top-level of constraints and *not* as the type
	 * argument within a ParameterizedTypeRef, closed and reopened ExistentialTypeRefs are expected to behave the same
	 * way as wildcards. Therefore this methods also asserts these corresponding cases, to avoid duplicating the test
	 * cases above.
	 */
	protected void assertSolutionOfVariations(Script _script, TypeConstraint[] constraints,
			Pair<InferenceVariable, TypeRef>... expectedInstantiations) {

		java.util.List<java.util.List<TypeConstraint>> variations = wildcardCaptureTestHelper
				.createCaptureVariationsForConstraints(Arrays.asList(constraints), true);

		for (java.util.List<TypeConstraint> variation : variations) {
			assertSolution(_script, variation.toArray(new TypeConstraint[0]), expectedInstantiations);
		}
	}

	protected void assertNoSolutionOfVariations(Script _script, TypeConstraint[] constraints,
			InferenceVariable... inferenceVariables) {

		java.util.List<java.util.List<TypeConstraint>> variations = wildcardCaptureTestHelper
				.createCaptureVariationsForConstraints(Arrays.asList(constraints), true);

		for (java.util.List<TypeConstraint> variation : variations) {
			assertNoSolution(_script, variation.toArray(new TypeConstraint[0]), inferenceVariables);
		}
	}
}
