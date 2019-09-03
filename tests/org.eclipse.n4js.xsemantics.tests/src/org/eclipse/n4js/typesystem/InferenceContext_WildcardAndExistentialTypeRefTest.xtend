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
import java.util.List
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.Wildcard
import org.eclipse.n4js.ts.types.InferenceVariable
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.constraints.TypeConstraint
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Here we test constraints having bare wildcards on the top level (not wildcards as type argument
 * within a ParameterizedTypeRef, which is tested in {@link InferenceContext_GenericsTest}.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class InferenceContext_WildcardAndExistentialTypeRefTest extends AbstractInferenceContextTest {

	@Inject
	private N4JSTypeSystem ts;


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


	/**
	 * Same as {@code #assertSolution()} from super class, but runs several test cases in one go:
	 * <p>
	 * Because in the cases tested here, i.e. wildcards appearing on the top-level of constraints
	 * and *not* as the type argument within a ParameterizedTypeRef, closed and reopened ExistentialTypeRefs
	 * are expected to behave the same way as wildcards. Therefore this methods also asserts these corresponding
	 * cases, to avoid duplicating the test cases above.
	 */
	def protected void assertSolutionOfVariations(Script script, TypeConstraint[] constraints, Pair<InferenceVariable,TypeRef>... expectedInstantiations) {
		val variations = createVariations(constraints);
		for (variation : variations) {
			script.assertSolution(variation, expectedInstantiations);
		}
	}

	def protected void assertNoSolutionOfVariations(Script script, TypeConstraint[] constraints, InferenceVariable... inferenceVariables) {
		val variations = createVariations(constraints);
		for (variation : variations) {
			script.assertNoSolution(variation, inferenceVariables);
		}
	}

	def private List<TypeConstraint[]> createVariations(TypeConstraint[] constraints) {
		val result = newArrayList();
		var mask = 0;
		while (true) {
			val variationCapture = createVariation(constraints, mask, false);
			val variationCaptureAndReopen = createVariation(constraints, mask, true);
			if (variationCapture === null || variationCaptureAndReopen === null) {
				return result;
			}
			result += variationCapture;
			result += variationCaptureAndReopen;
			mask++;
		}
	}

	def private TypeConstraint[] createVariation(TypeConstraint[] constraints, long mask, boolean reopen) {
		var m = mask;
		val result = newArrayList;
		for (constraint : constraints) {
			val TypeArgument[] args = #[constraint.left, constraint.right];
			for (var i = 0; i < 2; i++) {
				val arg = args.get(i);
				if (arg instanceof Wildcard) {
					if (m.bitwiseAnd(1) !== 0) {
						args.set(i, if (reopen) captureAndReopen(arg) else capture(arg));
					}
					m = m >> 1;
				}
			}
			result += new TypeConstraint(args.get(0), args.get(1), constraint.variance);
		}
		if (mask !== 0) {
			return null;
		}
		return result;
	}

	def private TypeRef captureAndReopen(Wildcard wildcard) {
		val captured = capture(wildcard);
		val reopened = ts.reopenExistentialTypes(_G, captured);
		return reopened;
	}

	def private TypeRef capture(Wildcard wildcard) {
		val captured = TypeUtils.captureWildcard(null, wildcard);
		return captured;
	}
}
