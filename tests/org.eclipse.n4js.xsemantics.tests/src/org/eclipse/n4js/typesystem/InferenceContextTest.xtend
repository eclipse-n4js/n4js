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
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class InferenceContextTest extends AbstractInferenceContextTest {

	@Test
	def void testOnlyUpperBounds_01() {
		script.assertSolution(
			#[
				constraint(alpha,'<:',I1),
				constraint(alpha,'<:',I2),
				constraint(alpha,'<:',I3)
			],
			alpha -> intersection(I1,I2,I3)
		)
	}

	@Test
	def void testOnlyUpperBounds_02() {
		script.assertSolution(
			#[
				constraint(I1,':>',alpha),
				constraint(I2,':>',alpha),
				constraint(I3,':>',alpha)
			],
			alpha -> intersection(I1,I2,I3)
		)
	}

	@Test
	def void testOnlyUpperBounds_03() {
		script.assertSolution(
			#[
				constraint(alpha,'<:',X),
				constraint(I1,':>',alpha),
				constraint(alpha,'<:',I2)
			],
			alpha -> intersection(I1,I2,X)
		)
	}

	@Test
	def void testOnlyLowerBounds() {
		script.assertSolution(
			#[
				constraint(X,'<:',alpha),
				constraint(Y,'<:',alpha),
				constraint(Z,'<:',alpha)
			],
			alpha -> union(X,Y,Z)
		)
		script.assertSolution(
			#[
				constraint(alpha,':>',X),
				constraint(alpha,':>',Y),
				constraint(alpha,':>',Z)
			],
			alpha -> union(X,Y,Z)
		)
		script.assertSolution(
			#[
				constraint(X,'<:',alpha),
				constraint(alpha,':>',Y),
				constraint(Z,'<:',alpha)
			],
			alpha -> union(X,Y,Z)
		)
	}

	@Test
	def void testLowerAndUpperBounds_01() {
		script.assertSolution(
			#[
				constraint(C1,'<:',alpha),
				constraint(C2,'<:',alpha),
				constraint(C3,'<:',alpha),
				constraint(alpha,'<:',B)
			],
			alpha -> union(C1,C2,C3)
		)
	}

	@Test
	def void testLowerAndUpperBounds_02() {
		script.assertSolution(
			#[
				constraint(alpha,'<:',I1),
				constraint(alpha,'<:',I2),
				constraint(alpha,'<:',I3),
				constraint(A,'<:',alpha)
			],
			alpha -> A.ref
		)
	}

	@Test
	def void testLowerAndUpperBounds_03() {
		script.assertSolution(
			#[
				constraint(alpha,'<:',I.ref),
				constraint(intersection(IA,IB),'<:',alpha)
			],
			alpha -> intersection(IA,IB)
		)
	}

	@Test
	def void testTwoInferenceVariables_Simple() {
		script.assertSolution(
			#[
				constraint(alpha,'<:',A),
				constraint(alpha,'=',beta),
				constraint(C,'<:',beta)
			],
			alpha -> C.ref,
			beta -> C.ref
		)
		script.assertSolution(
			#[
				constraint(alpha,'<:',A),
				constraint(alpha,':>',beta),
				constraint(C,'<:',beta)
			],
			alpha -> C.ref,
			beta -> C.ref
		)
	}

	@Test
	def void testTwoInferenceVariables_leadingToIntersection_01() {
		script.assertSolution(
			#[
				constraint(alpha,'<:',A),
				constraint(beta,'<:',B),
				constraint(alpha,'=',beta)
			],
			alpha -> intersection(A,B),
			beta -> intersection(A,B)
		)
	}

	@Test
	def void testTwoInferenceVariables_leadingToIntersection_02() {
		script.assertSolution(
			#[
				constraint(alpha,'<:',A),
				constraint(alpha,'<:',beta),
				constraint(C,'<:',beta)
			],
			alpha -> intersection(A,C),
			beta -> C.ref
		)
	}

	@Test
	def void testGenerics() {
		script.assertSolution(
			#[
				constraint(alpha,'=',G.of(A))
			],
			alpha -> G.of(A)
		)
	}

	@Test
	def void test_deferUnboundedInfVar() {
		script.assertSolution(
			#[
				// here alpha depends on beta, so beta would normally be instantiated first (and because it is unbounded, to
				// 'any'); instead, we want beta to be A (see below for real-world example)
				constraint(alpha,'<:',beta),
				constraint(alpha,'<:',A)
			],
			alpha -> A.ref,
			beta -> A.ref
		)
		// Note: this is motivated by the following real-world use case:
		//     function <T> foo({function(T)} fn) : T {return null;}
		//     var A test = foo( function(p) {} );
		// (the function expression should be inferred to {function(A):void}, not {function(any):void})
	}

	@Test
	def void test_deferUnboundedInfVar_counterExample01() {
		script.assertSolution(
			#[
				constraint(alpha,':>',functionType(beta.ref, G.of(A))), // α :> {function(G<A>):β}
				constraint(gamma,':>',G.of(alpha)) // γ :> G<α>
			],
			alpha -> functionType(_G.anyTypeRef, G.of(A)), // {function(G<A>):any}
			beta -> _G.anyTypeRef,
			gamma -> G.of(functionType(_G.anyTypeRef, G.of(A))) // G<{function(G<A>):any}>
		)
	}

	@Test
	def void test_deferUnboundedInfVar_counterExample02() {
		val alphaSolution = union(functionType(_G.anyTypeRef, G.of(A)), functionType(_G.anyTypeRef, G.of(B)));
		script.assertSolution(
			#[
				constraint(alpha,':>',functionType(beta.ref,  G.of(A))), // α :> {function(G<A>):β}
				constraint(alpha,':>',functionType(gamma.ref, G.of(B))), // α :> {function(G<B>):γ}
				constraint(delta,':>',G.of(alpha)) // δ :> G<α>
			],
			alpha -> alphaSolution, // union{{function(G<A>):any},{function(G<B>):any}}
			beta -> _G.anyTypeRef,
			gamma -> _G.anyTypeRef,
			delta -> G.of(alphaSolution) // G<...>
		)
	}

	@Test
	def void test_preferUpperBoundsIfLowerBoundsAreUninteresting01() {
		script.assertSolution(
			#[
				constraint(_G.undefinedType,'<:',alpha), // null <: α
				constraint(alpha,'<:',A) // α <: A
			],
			alpha -> A.ref
		)
	}

	@Test
	def void test_preferUpperBoundsIfLowerBoundsAreUninteresting02() {
		script.assertSolution(
			#[
				constraint(_G.nullType,'<:',alpha), // null <: α
				constraint(alpha,'<:',A) // α <: A
			],
			alpha -> A.ref
		)
	}

	@Test
	def void testRawTypeNotAcceptedAsSolution01() {
		val thisG = TypeRefsFactory.eINSTANCE.createBoundThisTypeRef => [
			actualThisTypeRef = G.rawTypeRef as ParameterizedTypeRef;
		];

		script.assertNoSolution(
			#[
				constraint(alpha,':>',thisG),
				constraint(alpha,'<:',G.of(A))
			],
			alpha
		)
	}

	@Test
	def void testRawTypeNotAcceptedAsSolution02() {
		val thisG = TypeRefsFactory.eINSTANCE.createBoundThisTypeRef => [
			actualThisTypeRef = G.rawTypeRef as ParameterizedTypeRef;
		];

		script.assertNoSolution(
			#[
				constraint(alpha,':>',thisG)
			],
			alpha
		)
	}

	@Test
	def void testTEMP() {
		script.assertSolution(
			#[
				constraint(alpha,'<:',beta),
				constraint(A, '<:',beta),
				constraint(I1,'<:',alpha)
			],
			alpha -> I1.ref,
			beta -> union(A,I1)
		)
	}
}
