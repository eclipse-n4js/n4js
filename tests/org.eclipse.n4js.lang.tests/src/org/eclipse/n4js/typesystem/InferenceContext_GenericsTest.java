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
import org.eclipse.n4js.typesystem.constraints.TypeConstraint;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Here we test constraints like <code>⟨ G&lt;B> <: G&lt;α> ⟩</code>.
 */
@SuppressWarnings("unchecked")
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class InferenceContext_GenericsTest extends AbstractInferenceContextTest {

	@Test
	public void test_base1() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(of(G, B), "<:", of(G, alpha))
				},
				Pair.of(alpha, ref(B)));
	}

	@Test
	public void test_base2() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(of(G, alpha), "<:", of(G, B))
				},
				Pair.of(alpha, ref(B)));
	}

	@Test
	public void test_rhs_wildcard() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(of(G, alpha), "<:", of(G, wildcard())) // ⟨ G<α> <: G<?> ⟩
				},
				Pair.of(alpha, ref(any())));
	}

	@Test
	public void test_rhs_wildcard_upperBound() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(of(G, alpha), "<:", of(G, wildcardExtends(B))) // ⟨ G<α> <: G<? extends B> ⟩
				},
				Pair.of(alpha, ref(B)));
		assertSolution(script,
				new TypeConstraint[] {
						constraint(of(G, alpha), "<:", of(G, wildcardExtends(B))), // ⟨ G<α> <: G<? extends B> ⟩
						constraint(alpha, "<:", C)
				},
				Pair.of(alpha, ref(C)));
	}

	@Test
	public void test_rhs_wildcard_lowerBound() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(of(G, alpha), "<:", of(G, wildcardSuper(B))) // ⟨ G<α> <: G<? super B> ⟩
				},
				Pair.of(alpha, ref(B)));
		assertSolution(script,
				new TypeConstraint[] {
						constraint(of(G, alpha), "<:", of(G, wildcardSuper(B))), // ⟨ G<α> <: G<? super B> ⟩
						constraint(alpha, ":>", A)
				},
				Pair.of(alpha, ref(A)));
	}

	@Test
	public void test_lhs_wildcard() {
		assertNoSolution(script,
				new TypeConstraint[] {
						constraint(of(G, wildcard()), "<:", of(G, alpha)) // ⟨ G<?> <: G<α> ⟩
				},
				alpha // only possible solution would be α := ?, but so far we assume that wildcards are not valid
						// solutions in general
		);
	}

	@Test
	public void test_lhs_wildcard_upperBound() {
		assertNoSolution(script,
				new TypeConstraint[] {
						constraint(of(G, wildcardExtends(B)), "<:", of(G, alpha)) // ⟨ G<? extends B> <: G<α> ⟩
				},
				alpha // same rationale as in #test_lhs_wildcard()
		);
	}

	@Test
	public void test_lhs_wildcard_lowerBound() {
		assertNoSolution(script,
				new TypeConstraint[] {
						constraint(of(G, wildcardSuper(B)), "<:", of(G, alpha)) // ⟨ G<? super B> <: G<α> ⟩
				},
				alpha // same rationale as in #test_lhs_wildcard()
		);
	}

	@Test
	public void test_both_wildcard_upperBound() {
		// we can fix the case #test_lhs_wildcard_upperBound() by using a wildcard also on rhs:
		assertSolution(script,
				new TypeConstraint[] {
						// ⟨ G<? extends B> <: G<? extends α> ⟩
						constraint(of(G, wildcardExtends(B)), "<:", of(G, wildcardExtends(alpha)))
				},
				Pair.of(alpha, ref(B)));
		assertSolution(script,
				new TypeConstraint[] {
						// ⟨ G<? extends B> <: G<? extends α> ⟩
						constraint(of(G, wildcardExtends(B)), "<:", of(G, wildcardExtends(alpha))),
						constraint(alpha, ":>", A)
				},
				Pair.of(alpha, ref(A)));
	}

	@Test
	public void test_both_wildcard_lowerBound() {
		// we can fix the case #test_lhs_wildcard_lowerBound() by using a wildcard also on rhs:
		assertSolution(script,
				new TypeConstraint[] {
						// ⟨ G<? super B> <: G<? super α> ⟩
						constraint(of(G, wildcardSuper(B)), "<:", of(G, wildcardSuper(alpha)))
				},
				Pair.of(alpha, ref(B)));
		assertSolution(script,
				new TypeConstraint[] {
						// ⟨ G<? super B> <: G<? super α> ⟩
						constraint(of(G, wildcardSuper(B)), "<:", of(G, wildcardSuper(alpha))),
						constraint(alpha, "<:", C)
				},
				Pair.of(alpha, ref(C)));
	}

	@Test
	public void test_inheritanceHierarchy01a() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(of(Gsub, B), "<:", of(G, alpha)) // ⟨ Gsub<B> <: G<α> ⟩
				},
				Pair.of(alpha, ref(B)));
	}

	@Test
	public void test_inheritanceHierarchy01b() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(of(G, alpha), ":>", of(Gsub, B)) // ⟨ G<α> :> Gsub<B> ⟩
				},
				Pair.of(alpha, ref(B)));
	}

	@Test
	public void test_inheritanceHierarchy02a() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(of(Gsub, alpha), "<:", of(G, B)) // ⟨ Gsub<α> <: G<B> ⟩
				},
				Pair.of(alpha, ref(B)));
	}

	@Test
	public void test_inheritanceHierarchy02b() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(of(G, B), ":>", of(Gsub, alpha)) // ⟨ G<B> :> Gsub<α> ⟩
				},
				Pair.of(alpha, ref(B)));
	}
}
