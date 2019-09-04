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

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.Wildcard
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Here we test constraints having bare wildcards on the top level (not wildcards as type argument
 * within a ParameterizedTypeRef, which is tested in {@link InferenceContext_GenericsTest}.
 * <p>
 * PART 2 of 2:<br>
 * captured wildcards (i.e. ExistentialTypeRefs with reopened === false) are tested here.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class InferenceContext_WildcardAndExistentialTypeRef2Test extends AbstractInferenceContextTest {

	// --------------------------------------------------------------------------------------------

	@Test
	def void test_rhs_capture() {
		val capture = capture(wildcard);
		script.assertSolution(
			#[
				constraint(alpha,'<:',capture) // ⟨ α <: ? ⟩
			],
			alpha -> capture
		)
		script.assertNoSolution(
			#[
				constraint(alpha,'<:',capture), // ⟨ α <: ? ⟩
				constraint(B,'<:',alpha) // ⟨ B <: α ⟩
			],
			alpha
		)
	}

	@Test
	def void test_rhs_capture_upperBound() {
		val capture = capture(wildcardExtends(B));
		script.assertSolution(
			#[
				constraint(alpha,'<:',capture) // ⟨ α <: ? extends B ⟩
			],
			alpha -> capture
		)
		script.assertNoSolution(
			#[
				constraint(alpha,'<:',capture), // ⟨ α <: ? extends B ⟩
				constraint(B,'<:',alpha) // ⟨ B <: α ⟩
			],
			alpha
		)
	}

	@Test
	def void test_rhs_capture_lowerBound() {
		val capture = capture(wildcardSuper(B));
		script.assertSolution(
			#[
				constraint(alpha,'<:',capture) // ⟨ α <: ? super B ⟩
			],
			alpha -> capture
		)
	}

	// --------------------------------------------------------------------------------------------

	@Test
	def void test_lhs_capture() {
		val capture = capture(wildcard);
		script.assertSolution(
			#[
				constraint(capture,'<:',alpha) // ⟨ ? <: α ⟩
			],
			alpha -> capture
		)
		script.assertNoSolution(
			#[
				constraint(capture,'<:',alpha), // ⟨ ? <: α ⟩
				constraint(alpha,'<:',B) // ⟨ α <: B ⟩
			],
			alpha
		)
	}

	@Test
	def void test_lhs_capture_upperBound() {
		val capture = capture(wildcardExtends(B));
		script.assertSolution(
			#[
				constraint(capture,'<:',alpha) // ⟨ ? extends B <: α ⟩
			],
			alpha -> capture
		)
	}

	@Test
	def void test_lhs_capture_lowerBound() {
		val capture = capture(wildcardSuper(B));
		script.assertSolution(
			#[
				constraint(capture,'<:',alpha) // ⟨ ? super B <: α ⟩
			],
			alpha -> capture
		)
		script.assertNoSolution(
			#[
				constraint(capture,'<:',alpha), // ⟨ ? super B <: α ⟩
				constraint(alpha,'<:',B) // ⟨ α <: B ⟩
			],
			alpha
		)
	}

	// --------------------------------------------------------------------------------------------

	@Test
	def void test_both_capture_lhs_infVar() {
		val captureExtendsB = capture(wildcardExtends(B));
		script.assertSolution(
			#[
				constraint(wildcardExtends(alpha),'<:',captureExtendsB) // ⟨ ? extends α <: ? extends B ⟩
			],
			alpha -> captureExtendsB
		)
		script.assertNoSolution(
			#[
				constraint(capture(wildcardExtends(alpha)),'<:',wildcardExtends(B)) // ⟨ ? extends α <: ? extends B ⟩
			],
			alpha
		)
		script.assertNoSolution(
			#[
				constraint(capture(wildcardExtends(alpha)),'<:',capture(wildcardExtends(B))) // ⟨ ? extends α <: ? extends B ⟩
			],
			alpha
		)

		script.assertNoSolution(
			#[
				constraint(wildcardSuper(alpha),'<:',capture(wildcardExtends(B))) // ⟨ ? super α <: ? extends B ⟩
			],
			alpha
		)
		script.assertNoSolution(
			#[
				constraint(capture(wildcardSuper(alpha)),'<:',wildcardExtends(B)) // ⟨ ? super α <: ? extends B ⟩
			],
			alpha
		)
		script.assertNoSolution(
			#[
				constraint(capture(wildcardSuper(alpha)),'<:',capture(wildcardExtends(B))) // ⟨ ? super α <: ? extends B ⟩
			],
			alpha
		)

		val captureSuperB = capture(wildcardSuper(B));
		script.assertSolution(
			#[
				constraint(wildcardExtends(alpha),'<:',captureSuperB) // ⟨ ? extends α <: ? super B ⟩
			],
			alpha -> captureSuperB
		)
		script.assertNoSolution(
			#[
				constraint(capture(wildcardExtends(alpha)),'<:',wildcardSuper(B)) // ⟨ ? extends α <: ? super B ⟩
			],
			alpha
		)
		script.assertNoSolution(
			#[
				constraint(capture(wildcardExtends(alpha)),'<:',capture(wildcardSuper(B))) // ⟨ ? extends α <: ? super B ⟩
			],
			alpha
		)

		script.assertNoSolution(
			#[
				constraint(wildcardSuper(alpha),'<:',capture(wildcardSuper(B))) // ⟨ ? super α <: ? super B ⟩
			],
			alpha
		)
		script.assertNoSolution(
			#[
				constraint(capture(wildcardSuper(alpha)),'<:',wildcardSuper(B)) // ⟨ ? super α <: ? super B ⟩
			],
			alpha
		)
		script.assertNoSolution(
			#[
				constraint(capture(wildcardSuper(alpha)),'<:',capture(wildcardSuper(B))) // ⟨ ? super α <: ? super B ⟩
			],
			alpha
		)
	}

	@Test
	def void test_both_capture_rhs_infVar() {
		script.assertNoSolution(
			#[
				constraint(wildcardExtends(B),'<:',capture(wildcardExtends(alpha))) // ⟨ ? extends B <: ? extends α ⟩
			],
			alpha
		)
		script.assertNoSolution(
			#[
				constraint(capture(wildcardExtends(B)),'<:',wildcardExtends(alpha)) // ⟨ ? extends B <: ? extends α ⟩
			],
			alpha
		)
		script.assertNoSolution(
			#[
				constraint(capture(wildcardExtends(B)),'<:',capture(wildcardExtends(alpha))) // ⟨ ? extends B <: ? extends α ⟩
			],
			alpha
		)

		script.assertNoSolution(
			#[
				constraint(wildcardSuper(B),'<:',capture(wildcardExtends(alpha))) // ⟨ ? super B <: ? extends α ⟩
			],
			alpha
		)
		script.assertNoSolution(
			#[
				constraint(capture(wildcardSuper(B)),'<:',wildcardExtends(alpha)) // ⟨ ? super B <: ? extends α ⟩
			],
			alpha
		)
		script.assertNoSolution(
			#[
				constraint(capture(wildcardSuper(B)),'<:',capture(wildcardExtends(alpha))) // ⟨ ? super B <: ? extends α ⟩
			],
			alpha
		)

		script.assertNoSolution(
			#[
				constraint(wildcardExtends(B),'<:',capture(wildcardSuper(alpha))) // ⟨ ? extends B <: ? super α ⟩
			],
			alpha
		)
		val captureExtendsB = capture(wildcardExtends(B));
		script.assertSolution(
			#[
				constraint(captureExtendsB,'<:',wildcardSuper(alpha)) // ⟨ ? extends B <: ? super α ⟩
			],
			alpha -> captureExtendsB
		)
		script.assertNoSolution(
			#[
				constraint(capture(wildcardExtends(B)),'<:',capture(wildcardSuper(alpha))) // ⟨ ? extends B <: ? super α ⟩
			],
			alpha
		)

		script.assertNoSolution(
			#[
				constraint(wildcardSuper(B),'<:',capture(wildcardSuper(alpha))) // ⟨ ? super B <: ? super α ⟩
			],
			alpha
		)
		val captureSuperB = capture(wildcardSuper(B));
		script.assertSolution(
			#[
				constraint(captureSuperB,'<:',wildcardSuper(alpha)) // ⟨ ? super B <: ? super α ⟩
			],
			alpha -> captureSuperB
		)
		script.assertNoSolution(
			#[
				constraint(capture(wildcardSuper(B)),'<:',capture(wildcardSuper(alpha))) // ⟨ ? super B <: ? super α ⟩
			],
			alpha
		)
	}

	// --------------------------------------------------------------------------------------------

	def protected TypeRef capture(Wildcard wildcard) {
		val captured = TypeUtils.captureWildcard(G.typeVars.head, wildcard);
		return captured;
	}
}
