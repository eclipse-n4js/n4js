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
package org.eclipse.n4js.typesystem

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.WildcardCaptureTestHelper
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.InferenceVariable
import org.eclipse.n4js.typesystem.constraints.TypeConstraint
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Here we test constraints having bare wildcards on the top level (not wildcards as type argument
 * within a ParameterizedTypeRef, which is tested in {@link InferenceContext_GenericsTest}.
 * <p>
 * PART 1 of 2:<br>
 * wildcards and captured-and-reopened wildcards (i.e. ExistentialTypeRefs with reopened === true) are tested here.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class InferenceContext_WildcardAndExistentialTypeRef1Test extends AbstractInferenceContextTest {

	@Inject
	private WildcardCaptureTestHelper wildcardCaptureTestHelper;

	// --------------------------------------------------------------------------------------------

	@Test
	def void test_rhs_wildcard() {
		script.assertSolutionOfVariations(
			#[
				constraint(alpha,'<:',wildcard) // ⟨ α <: ? ⟩
			],
			alpha -> bottom.ref
		)
		script.assertNoSolutionOfVariations(
			#[
				constraint(alpha,'<:',wildcard), // ⟨ α <: ? ⟩
				constraint(B,'<:',alpha) // ⟨ B <: α ⟩
			],
			alpha
		)
	}

	@Test
	def void test_rhs_wildcard_upperBound() {
		script.assertSolutionOfVariations(
			#[
				constraint(alpha,'<:',wildcardExtends(B)) // ⟨ α <: ? extends B ⟩
			],
			alpha -> bottom.ref
		)
		script.assertNoSolutionOfVariations(
			#[
				constraint(alpha,'<:',wildcardExtends(B)), // ⟨ α <: ? extends B ⟩
				constraint(B,'<:',alpha) // ⟨ B <: α ⟩
			],
			alpha
		)
	}

	@Test
	def void test_rhs_wildcard_lowerBound() {
		script.assertSolutionOfVariations(
			#[
				constraint(alpha,'<:',wildcardSuper(B)) // ⟨ α <: ? super B ⟩
			],
			alpha -> B.ref
		)
	}

	// --------------------------------------------------------------------------------------------

	@Test
	def void test_lhs_wildcard() {
		script.assertSolutionOfVariations(
			#[
				constraint(wildcard,'<:',alpha) // ⟨ ? <: α ⟩
			],
			alpha -> top.ref
		)
		script.assertNoSolutionOfVariations(
			#[
				constraint(wildcard,'<:',alpha), // ⟨ ? <: α ⟩
				constraint(alpha,'<:',B) // ⟨ α <: B ⟩
			],
			alpha
		)
	}

	@Test
	def void test_lhs_wildcard_upperBound() {
		script.assertSolutionOfVariations(
			#[
				constraint(wildcardExtends(B),'<:',alpha) // ⟨ ? extends B <: α ⟩
			],
			alpha -> B.ref
		)
	}

	@Test
	def void test_lhs_wildcard_lowerBound() {
		script.assertSolutionOfVariations(
			#[
				constraint(wildcardSuper(B),'<:',alpha) // ⟨ ? super B <: α ⟩
			],
			alpha -> top.ref
		)
		script.assertNoSolutionOfVariations(
			#[
				constraint(wildcardSuper(B),'<:',alpha), // ⟨ ? super B <: α ⟩
				constraint(alpha,'<:',B) // ⟨ α <: B ⟩
			],
			alpha
		)
	}

	// --------------------------------------------------------------------------------------------

	@Test
	def void test_both_wildcard_lhs_infVar() {
		script.assertSolutionOfVariations(
			#[
				constraint(wildcardExtends(alpha),'<:',wildcardExtends(B)) // ⟨ ? extends α <: ? extends B ⟩
			],
			alpha -> bottom.ref
		)

		script.assertNoSolutionOfVariations(
			#[
				constraint(wildcardSuper(alpha),'<:',wildcardExtends(B)) // ⟨ ? super α <: ? extends B ⟩
			],
			alpha
		)

		script.assertSolutionOfVariations(
			#[
				constraint(wildcardExtends(alpha),'<:',wildcardSuper(B)) // ⟨ ? extends α <: ? super B ⟩
			],
			alpha -> B.ref
		)

		script.assertNoSolutionOfVariations(
			#[
				constraint(wildcardSuper(alpha),'<:',wildcardSuper(B)) // ⟨ ? super α <: ? super B ⟩
			],
			alpha
		)
	}

	@Test
	def void test_both_wildcard_rhs_infVar() {
		script.assertNoSolutionOfVariations(
			#[
				constraint(wildcardExtends(B),'<:',wildcardExtends(alpha)) // ⟨ ? extends B <: ? extends α ⟩
			],
			alpha
		)

		script.assertNoSolutionOfVariations(
			#[
				constraint(wildcardSuper(B),'<:',wildcardExtends(alpha)) // ⟨ ? super B <: ? extends α ⟩
			],
			alpha
		)

		script.assertSolutionOfVariations(
			#[
				constraint(wildcardExtends(B),'<:',wildcardSuper(alpha)) // ⟨ ? extends B <: ? super α ⟩
			],
			alpha -> B.ref
		)

		script.assertSolutionOfVariations(
			#[
				constraint(wildcardSuper(B),'<:',wildcardSuper(alpha)) // ⟨ ? super B <: ? super α ⟩
			],
			alpha -> top.ref
		)
	}

	// --------------------------------------------------------------------------------------------

	/**
	 * Same as {@code #assertSolution()} from super class, but runs several test cases in one go:
	 * <p>
	 * Because in the cases tested here, i.e. wildcards appearing on the top-level of constraints
	 * and *not* as the type argument within a ParameterizedTypeRef, closed and reopened ExistentialTypeRefs
	 * are expected to behave the same way as wildcards. Therefore this methods also asserts these corresponding
	 * cases, to avoid duplicating the test cases above.
	 */
	def protected void assertSolutionOfVariations(Script script, TypeConstraint[] constraints, Pair<InferenceVariable,TypeRef>... expectedInstantiations) {
		val variations = wildcardCaptureTestHelper.createCaptureVariationsForConstraints(_G, constraints, true);
		for (variation : variations) {
			script.assertSolution(variation, expectedInstantiations);
		}
	}

	def protected void assertNoSolutionOfVariations(Script script, TypeConstraint[] constraints, InferenceVariable... inferenceVariables) {
		val variations = wildcardCaptureTestHelper.createCaptureVariationsForConstraints(_G, constraints, true);
		for (variation : variations) {
			script.assertNoSolution(variation, inferenceVariables);
		}
	}
}
