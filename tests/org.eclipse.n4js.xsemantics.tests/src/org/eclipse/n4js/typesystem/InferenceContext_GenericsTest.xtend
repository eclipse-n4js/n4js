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
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Here we test constraints like <code>⟨ G&lt;B> <: G&lt;α> ⟩</code>.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class InferenceContext_GenericsTest extends AbstractInferenceContextTest {


	@Test
	def void test_base1() {
		script.assertSolution(
			#[
				constraint(G.of(B),'<:',G.of(alpha))
			],
			alpha -> B.ref
		)
	}


	@Test
	def void test_base2() {
		script.assertSolution(
			#[
				constraint(G.of(alpha),'<:',G.of(B))
			],
			alpha -> B.ref
		)
	}


	@Test
	def void test_rhs_wildcard() {
		script.assertSolution(
			#[
				constraint(G.of(alpha),'<:',G.of(wildcard)) // ⟨ G<α> <: G<?> ⟩
			],
			alpha -> any.ref
		)
	}
	@Test
	def void test_rhs_wildcard_upperBound() {
		script.assertSolution(
			#[
				constraint(G.of(alpha),'<:',G.of(wildcardExtends(B))) // ⟨ G<α> <: G<? extends B> ⟩
			],
			alpha -> B.ref
		)
		script.assertSolution(
			#[
				constraint(G.of(alpha),'<:',G.of(wildcardExtends(B))), // ⟨ G<α> <: G<? extends B> ⟩
				constraint(alpha,'<:',C)
			],
			alpha -> C.ref
		)
	}
	@Test
	def void test_rhs_wildcard_lowerBound() {
		script.assertSolution(
			#[
				constraint(G.of(alpha),'<:',G.of(wildcardSuper(B))) // ⟨ G<α> <: G<? super B> ⟩
			],
			alpha -> B.ref
		)
		script.assertSolution(
			#[
				constraint(G.of(alpha),'<:',G.of(wildcardSuper(B))), // ⟨ G<α> <: G<? super B> ⟩
				constraint(alpha,':>',A)
			],
			alpha -> A.ref
		)
	}


	@Test
	def void test_lhs_wildcard() {
		script.assertNoSolution(
			#[
				constraint(G.of(wildcard),'<:',G.of(alpha)) // ⟨ G<?> <: G<α> ⟩
			],
			alpha // only possible solution would be α := ?, but so far we assume that wildcards are not valid solutions in general
		)
	}
	@Test
	def void test_lhs_wildcard_upperBound() {
		script.assertNoSolution(
			#[
				constraint(G.of(wildcardExtends(B)),'<:',G.of(alpha)) // ⟨ G<? extends B> <: G<α> ⟩
			],
			alpha // same rationale as in #test_lhs_wildcard()
		)
	}
	@Test
	def void test_lhs_wildcard_lowerBound() {
		script.assertNoSolution(
			#[
				constraint(G.of(wildcardSuper(B)),'<:',G.of(alpha)) // ⟨ G<? super B> <: G<α> ⟩
			],
			alpha // same rationale as in #test_lhs_wildcard()
		)
	}


	@Test
	def void test_both_wildcard_upperBound() {
		// we can fix the case #test_lhs_wildcard_upperBound() by using a wildcard also on rhs:
		script.assertSolution(
			#[
				constraint(G.of(wildcardExtends(B)),'<:',G.of(wildcardExtends(alpha))) // ⟨ G<? extends B> <: G<? extends α> ⟩
			],
			alpha -> B.ref
		)
		script.assertSolution(
			#[
				constraint(G.of(wildcardExtends(B)),'<:',G.of(wildcardExtends(alpha))), // ⟨ G<? extends B> <: G<? extends α> ⟩
				constraint(alpha,':>',A)
			],
			alpha -> A.ref
		)
	}
	@Test
	def void test_both_wildcard_lowerBound() {
		// we can fix the case #test_lhs_wildcard_lowerBound() by using a wildcard also on rhs:
		script.assertSolution(
			#[
				constraint(G.of(wildcardSuper(B)),'<:',G.of(wildcardSuper(alpha))) // ⟨ G<? super B> <: G<? super α> ⟩
			],
			alpha -> B.ref
		)
		script.assertSolution(
			#[
				constraint(G.of(wildcardSuper(B)),'<:',G.of(wildcardSuper(alpha))), // ⟨ G<? super B> <: G<? super α> ⟩
				constraint(alpha,'<:',C)
			],
			alpha -> C.ref
		)
	}


	@Test
	def void test_inheritanceHierarchy01a() {
		script.assertSolution(
			#[
				constraint(Gsub.of(B),'<:',G.of(alpha)) // ⟨ Gsub<B> <: G<α> ⟩
			],
			alpha -> B.ref
		)
	}
	@Test
	def void test_inheritanceHierarchy01b() {
		script.assertSolution(
			#[
				constraint(G.of(alpha),':>',Gsub.of(B)) // ⟨ G<α> :> Gsub<B> ⟩
			],
			alpha -> B.ref
		)
	}
	@Test
	def void test_inheritanceHierarchy02a() {
		script.assertSolution(
			#[
				constraint(Gsub.of(alpha),'<:',G.of(B)) // ⟨ Gsub<α> <: G<B> ⟩
			],
			alpha -> B.ref
		)
	}
	@Test
	def void test_inheritanceHierarchy02b() {
		script.assertSolution(
			#[
				constraint(G.of(B),':>',Gsub.of(alpha)) // ⟨ G<B> :> Gsub<α> ⟩
			],
			alpha -> B.ref
		)
	}
}
