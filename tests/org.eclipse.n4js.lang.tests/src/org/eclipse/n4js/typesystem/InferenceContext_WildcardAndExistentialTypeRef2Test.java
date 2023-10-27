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

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.constraints.TypeConstraint;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Here we test constraints having bare wildcards on the top level (not wildcards as type argument within a
 * ParameterizedTypeRef, which is tested in {@link InferenceContext_GenericsTest}.
 * <p>
 * PART 2 of 2:<br>
 * captured wildcards (i.e. ExistentialTypeRefs with reopened === false) are tested here.
 */
@SuppressWarnings("unchecked")
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class InferenceContext_WildcardAndExistentialTypeRef2Test extends AbstractInferenceContextTest {

	// --------------------------------------------------------------------------------------------

	@Test
	public void test_rhs_capture() {
		TypeRef capture = capture(wildcard());
		assertSolution(script,
				new TypeConstraint[] {
						// ⟨ α <: ? ⟩
						constraint(alpha, "<:", capture)
				},
				Pair.of(alpha, capture));
		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ α <: ? ⟩
						constraint(alpha, "<:", capture),
						// ⟨ B <: α ⟩
						constraint(B, "<:", alpha)
				},
				alpha);
	}

	@Test
	public void test_rhs_capture_upperBound() {
		TypeRef capture = capture(wildcardExtends(B));
		assertSolution(script,
				new TypeConstraint[] {
						// ⟨ α <: ? extends B ⟩
						constraint(alpha, "<:", capture)
				},
				Pair.of(alpha, capture));
		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ α <: ? extends B ⟩
						constraint(alpha, "<:", capture),
						// ⟨ B <: α ⟩
						constraint(B, "<:", alpha)
				},
				alpha);
	}

	@Test
	public void test_rhs_capture_lowerBound() {
		TypeRef capture = capture(wildcardSuper(B));
		assertSolution(script,
				new TypeConstraint[] {
						// ⟨ α <: ? super B ⟩
						constraint(alpha, "<:", capture)
				},
				Pair.of(alpha, capture));
	}

	// --------------------------------------------------------------------------------------------

	@Test
	public void test_lhs_capture() {
		TypeRef capture = capture(wildcard());
		assertSolution(script,
				new TypeConstraint[] {
						// ⟨ ? <: α ⟩
						constraint(capture, "<:", alpha)
				},
				Pair.of(alpha, capture));
		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? <: α ⟩
						constraint(capture, "<:", alpha),
						// ⟨ α <: B ⟩
						constraint(alpha, "<:", B)
				},
				alpha);
	}

	@Test
	public void test_lhs_capture_upperBound() {
		TypeRef capture = capture(wildcardExtends(B));
		assertSolution(script,
				new TypeConstraint[] {
						// ⟨ ? extends B <: α ⟩
						constraint(capture, "<:", alpha)
				},
				Pair.of(alpha, capture));
	}

	@Test
	public void test_lhs_capture_lowerBound() {
		TypeRef capture = capture(wildcardSuper(B));
		assertSolution(script,
				new TypeConstraint[] {
						// ⟨ ? super B <: α ⟩
						constraint(capture, "<:", alpha)
				},
				Pair.of(alpha, capture));
		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? super B <: α ⟩
						constraint(capture, "<:", alpha),
						// ⟨ α <: B ⟩
						constraint(alpha, "<:", B)
				},
				alpha);
	}

	// --------------------------------------------------------------------------------------------

	@Test
	public void test_both_capture_lhs_infVar() {
		TypeRef captureExtendsB = capture(wildcardExtends(B));
		assertSolution(script,
				new TypeConstraint[] {
						// ⟨ ? extends α <: ? extends B ⟩
						constraint(wildcardExtends(alpha), "<:", captureExtendsB)
				},
				Pair.of(alpha, captureExtendsB));
		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? extends α <: ? extends B ⟩
						constraint(capture(wildcardExtends(alpha)), "<:", wildcardExtends(B))
				},
				alpha);
		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? extends α <: ? extends B ⟩
						constraint(capture(wildcardExtends(alpha)), "<:", capture(wildcardExtends(B)))
				},
				alpha);

		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? super α <: ? extends B ⟩
						constraint(wildcardSuper(alpha), "<:", capture(wildcardExtends(B)))
				},
				alpha);
		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? super α <: ? extends B ⟩
						constraint(capture(wildcardSuper(alpha)), "<:", wildcardExtends(B))
				},
				alpha);
		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? super α <: ? extends B ⟩
						constraint(capture(wildcardSuper(alpha)), "<:", capture(wildcardExtends(B)))
				},
				alpha);

		TypeRef captureSuperB = capture(wildcardSuper(B));
		assertSolution(script,
				new TypeConstraint[] {
						// ⟨ ? extends α <: ? super B ⟩
						constraint(wildcardExtends(alpha), "<:", captureSuperB)
				},
				Pair.of(alpha, captureSuperB));
		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? extends α <: ? super B ⟩
						constraint(capture(wildcardExtends(alpha)), "<:", wildcardSuper(B))
				},
				alpha);
		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? extends α <: ? super B ⟩
						constraint(capture(wildcardExtends(alpha)), "<:", capture(wildcardSuper(B)))
				},
				alpha);

		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? super α <: ? super B ⟩
						constraint(wildcardSuper(alpha), "<:", capture(wildcardSuper(B)))
				},
				alpha);
		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? super α <: ? super B ⟩
						constraint(capture(wildcardSuper(alpha)), "<:", wildcardSuper(B))
				},
				alpha);
		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? super α <: ? super B ⟩
						constraint(capture(wildcardSuper(alpha)), "<:", capture(wildcardSuper(B)))
				},
				alpha);
	}

	@Test
	public void test_both_capture_rhs_infVar() {
		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? extends B <: ? extends α ⟩
						constraint(wildcardExtends(B), "<:", capture(wildcardExtends(alpha)))
				},
				alpha);
		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? extends B <: ? extends α ⟩
						constraint(capture(wildcardExtends(B)), "<:", wildcardExtends(alpha))
				},
				alpha);
		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? extends B <: ? extends α ⟩
						constraint(capture(wildcardExtends(B)), "<:", capture(wildcardExtends(alpha)))
				},
				alpha);

		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? super B <: ? extends α ⟩
						constraint(wildcardSuper(B), "<:", capture(wildcardExtends(alpha)))
				},
				alpha);
		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? super B <: ? extends α ⟩
						constraint(capture(wildcardSuper(B)), "<:", wildcardExtends(alpha))
				},
				alpha);
		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? super B <: ? extends α ⟩
						constraint(capture(wildcardSuper(B)), "<:", capture(wildcardExtends(alpha)))
				},
				alpha);

		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? extends B <: ? super α ⟩
						constraint(wildcardExtends(B), "<:", capture(wildcardSuper(alpha)))
				},
				alpha);
		TypeRef captureExtendsB = capture(wildcardExtends(B));
		assertSolution(script,
				new TypeConstraint[] {
						// ⟨ ? extends B <: ? super α ⟩
						constraint(captureExtendsB, "<:", wildcardSuper(alpha))
				},
				Pair.of(alpha, captureExtendsB));
		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? extends B <: ? super α ⟩
						constraint(capture(wildcardExtends(B)), "<:", capture(wildcardSuper(alpha)))
				},
				alpha);

		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? super B <: ? super α ⟩
						constraint(wildcardSuper(B), "<:", capture(wildcardSuper(alpha)))
				},
				alpha);
		TypeRef captureSuperB = capture(wildcardSuper(B));
		assertSolution(script,
				new TypeConstraint[] {
						// ⟨ ? super B <: ? super α ⟩
						constraint(captureSuperB, "<:", wildcardSuper(alpha))
				},
				Pair.of(alpha, captureSuperB));
		assertNoSolution(script,
				new TypeConstraint[] {
						// ⟨ ? super B <: ? super α ⟩
						constraint(capture(wildcardSuper(B)), "<:", capture(wildcardSuper(alpha)))
				},
				alpha);
	}

	// --------------------------------------------------------------------------------------------

	protected TypeRef capture(Wildcard wildcard) {
		TypeRef captured = TypeUtils.captureWildcard(wildcard);
		return captured;
	}
}
