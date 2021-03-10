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

import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class InferenceContext_UnionTest extends AbstractInferenceContextTest {


	// function <alpha, beta, delta> foo(union{alpha,beta} p1, union{beta,G<delta>} p2)
	def baseCaseConstraints() {
		return #[
				constraint(B2,'<:',union(alpha.ref,beta.ref)),
				constraint(B2,'<:',union(beta.ref,G.of(delta)))
			]
	}

	@Test
	def void testConformanceToUnion_BaseCase() {

		// foo(new B2, new B2)
		// best solution: beta->B2, alpha and delta unconstrained

		script.assertSolution(
			baseCaseConstraints,
			// alpha becomes constrained upon "reduceUnionTypeExpression case CO" picking the first elem of the union
			alpha -> B2.ref,
			beta -> B2.ref,
			delta -> null // FIXME don't use null as test expectation!
		)

	}

	@Test
	def void testConformanceToUnion_variant01() {

		// same as base case except: union{beta,alpha} p1

		// function <alpha, beta, delta> foo(union{beta,alpha} p1, union{beta,G<delta>} p2)

		script.assertSolution(
			#[
				constraint(B2,'<:',union(beta.ref,alpha.ref)),
				constraint(B2,'<:',union(beta.ref,G.of(delta)))
			],
			// alpha was second elem of the union this time, beta was the first element.
			alpha -> null, // FIXME don't use null as test expectation!
			beta -> B2.ref,
			delta -> null // FIXME don't use null as test expectation!
		)

	}

	@Test
	def void testConformanceToUnion_variant02() {

		// same as base case except: foo(new B1, new B2)
		// best solution:

		script.assertSolution(
			#[
				constraint(B1,'<:',union(alpha.ref,beta.ref)),
				constraint(B2,'<:',union(beta.ref,G.of(delta)))
			],
			alpha -> B1.ref,
			beta -> B2.ref,
			delta -> null // FIXME don't use null as test expectation!
		)

	}

	@Test
	def void testConformanceToUnion_variant03() {

		// same as variant02 except: union{beta,alpha} p1

		// function <alpha, beta, delta> foo(union{beta,alpha} p1, union{beta,G<delta>} p2)
		// foo(new B1, new B2)

		script.assertSolution(
			#[
				constraint(B1,'<:',union(beta.ref,alpha.ref)),
				constraint(B2,'<:',union(beta.ref,G.of(delta)))
			],
			alpha -> null, // FIXME don't use null as test expectation!
			beta -> union(B1,B2),
			delta -> null // FIXME don't use null as test expectation!
		)

	}

	@Test
	def void testUnionConformance_BaseCase() {

		// foo( cond ? new B1 : new B2 , new A)

		script.assertSolution(
			#[
				constraint(union(B1,B2),'<:',union(alpha.ref,beta.ref)),
				constraint(A,'<:',union(beta.ref,G.of(delta)))
			],
			//
			alpha -> union(B1,B2),
			beta -> A.ref,
			delta -> null // FIXME don't use null as test expectation!
		)

	}

	@Test
	def void testUnionConformance_variant01() {

		// same as testUnionConformance_BaseCase except: union{beta,alpha} p1

		// function <alpha, beta, delta> foo(union{beta,alpha} p1, union{beta,G<delta>} p2)

		// foo( cond ? new B1 : new B2 , new A)

		script.assertSolution(
			#[
				constraint(union(B1,B2),'<:',union(beta.ref,alpha.ref)),
				constraint(A,'<:',union(beta.ref,G.of(delta)))
			],
			//
			alpha -> null, // FIXME don't use null as test expectation!
			beta -> union(A,B1,B2),
			delta -> null // FIXME don't use null as test expectation!
		)

	}

	@Test
	def void testUnionConformance_variant02() {

		// foo( cond ? new A : new B2 , new B1)

		script.assertSolution(
			#[
				constraint(union(A,B2),'<:',union(alpha.ref,beta.ref)),
				constraint(B1,'<:',union(beta.ref,G.of(delta)))
			],
			//
			alpha -> A.ref,
			beta -> B1.ref,
			delta -> null // FIXME don't use null as test expectation!
		)

	}

	@Test
	def void testUnionConformance_variantInterfaces() {

		// foo( cond ? getIA() : getIB() , getI())

		script.assertSolution(
			#[
				constraint(union(IA,IB),'<:',union(alpha.ref,beta.ref)),
				constraint(I,'<:',union(beta.ref,G.of(delta)))
			],
			//
			alpha -> union(IA,IB),
			beta -> I.ref,
			delta -> null // FIXME don't use null as test expectation!
		)

	}

}
