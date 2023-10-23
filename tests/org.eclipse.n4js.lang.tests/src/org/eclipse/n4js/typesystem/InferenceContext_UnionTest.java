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

@SuppressWarnings("unchecked")
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class InferenceContext_UnionTest extends AbstractInferenceContextTest {

	// function <alpha, beta, delta> foo(union{alpha,beta} p1, union{beta,G<delta>} p2)
	TypeConstraint[] baseCaseConstraints() {
		return new TypeConstraint[] {
				constraint(B2, "<:", union(ref(alpha), ref(beta))),
				constraint(B2, "<:", union(ref(beta), of(G, delta)))
		};
	}

	@Test
	public void testConformanceToUnion_BaseCase() {

		// foo(new B2, new B2)
		// best solution: beta->B2, alpha and delta unconstrained

		assertSolution(script,
				baseCaseConstraints(),
				// alpha becomes constrained upon "reduceUnionTypeExpression case CO" picking the first elem of the
				// union
				Pair.of(alpha, ref(B2)),
				Pair.of(beta, ref(B2)),
				// FIXME don't use null as test expectation!
				Pair.of(delta, null));

	}

	@Test
	public void testConformanceToUnion_variant01() {

		// same as base case except: union{beta,alpha} p1

		// function <alpha, beta, delta> foo(union{beta,alpha} p1, union{beta,G<delta>} p2)

		assertSolution(script,
				new TypeConstraint[] {
						constraint(B2, "<:", union(ref(beta), ref(alpha))),
						constraint(B2, "<:", union(ref(beta), of(G, delta)))
				},
				// alpha was second elem of the union this time, beta was the first element.
				// FIXME don't use null as test expectation!
				Pair.of(alpha, null),
				Pair.of(beta, ref(B2)),
				// FIXME don't use null as test expectation!
				Pair.of(delta, null));

	}

	@Test
	public void testConformanceToUnion_variant02() {

		// same as base case except: foo(new B1, new B2)
		// best solution:

		assertSolution(script,
				new TypeConstraint[] {
						constraint(B1, "<:", union(ref(alpha), ref(beta))),
						constraint(B2, "<:", union(ref(beta), of(G, delta)))
				},
				Pair.of(alpha, ref(B1)),
				Pair.of(beta, ref(B2)),
				// FIXME don't use null as test expectation!
				Pair.of(delta, null));

	}

	@Test
	public void testConformanceToUnion_variant03() {

		// same as variant02 except: union{beta,alpha} p1

		// function <alpha, beta, delta> foo(union{beta,alpha} p1, union{beta,G<delta>} p2)
		// foo(new B1, new B2)

		assertSolution(script,
				new TypeConstraint[] {
						constraint(B1, "<:", union(ref(beta), ref(alpha))),
						constraint(B2, "<:", union(ref(beta), of(G, delta)))
				},
				// FIXME don't use null as test expectation!
				Pair.of(alpha, null),
				Pair.of(beta, union(B1, B2)),
				// FIXME don't use null as test expectation!
				Pair.of(delta, null));

	}

	@Test
	public void testUnionConformance_BaseCase() {

		// foo( cond ? new B1 : new B2 , new A)

		assertSolution(script,
				new TypeConstraint[] {
						constraint(union(B1, B2), "<:", union(ref(alpha), ref(beta))),
						constraint(A, "<:", union(ref(beta), of(G, delta)))
				},
				//
				Pair.of(alpha, union(B1, B2)),
				Pair.of(beta, ref(A)),
				// FIXME don't use null as test expectation!
				Pair.of(delta, null));

	}

	@Test
	public void testUnionConformance_variant01() {

		// same as testUnionConformance_BaseCase except: union{beta,alpha} p1

		// function <alpha, beta, delta> foo(union{beta,alpha} p1, union{beta,G<delta>} p2)

		// foo( cond ? new B1 : new B2 , new A)

		assertSolution(script,
				new TypeConstraint[] {
						constraint(union(B1, B2), "<:", union(ref(beta), ref(alpha))),
						constraint(A, "<:", union(ref(beta), of(G, delta)))
				},
				//
				// FIXME don't use null as test expectation!
				Pair.of(alpha, null),
				Pair.of(beta, union(A, B1, B2)),
				// FIXME don't use null as test expectation!
				Pair.of(delta, null));

	}

	@Test
	public void testUnionConformance_variant02() {

		// foo( cond ? new A : new B2 , new B1)

		assertSolution(script,
				new TypeConstraint[] {
						constraint(union(A, B2), "<:", union(ref(alpha), ref(beta))),
						constraint(B1, "<:", union(ref(beta), of(G, delta)))
				},
				//
				Pair.of(alpha, ref(A)),
				Pair.of(beta, ref(B1)),
				// FIXME don't use null as test expectation!
				Pair.of(delta, null));

	}

	@Test
	public void testUnionConformance_variantInterfaces() {

		// foo( cond ? getIA() : getIB() , getI())

		assertSolution(script,
				new TypeConstraint[] {
						constraint(union(IA, IB), "<:", union(ref(alpha), ref(beta))),
						constraint(I, "<:", union(ref(beta), of(G, delta)))
				},
				//
				Pair.of(alpha, union(IA, IB)),
				Pair.of(beta, ref(I)),
				// FIXME don't use null as test expectation!
				Pair.of(delta, null));

	}

}
