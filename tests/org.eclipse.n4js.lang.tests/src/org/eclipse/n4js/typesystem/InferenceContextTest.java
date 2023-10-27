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

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.arrayTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.iterableTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.nullType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.stringTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.undefinedType;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.typesystem.constraints.TypeConstraint;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;

@SuppressWarnings("unchecked")
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class InferenceContextTest extends AbstractInferenceContextTest {

	@Test
	public void testOnlyUpperBounds_01() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "<:", I1),
						constraint(alpha, "<:", I2),
						constraint(alpha, "<:", I3)
				},
				Pair.of(alpha, intersection(I1, I2, I3)));
	}

	@Test
	public void testOnlyUpperBounds_02() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(I1, ":>", alpha),
						constraint(I2, ":>", alpha),
						constraint(I3, ":>", alpha)
				},
				Pair.of(alpha, intersection(I1, I2, I3)));
	}

	@Test
	public void testOnlyUpperBounds_03() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "<:", X),
						constraint(I1, ":>", alpha),
						constraint(alpha, "<:", I2)
				},
				Pair.of(alpha, intersection(I1, I2, X)));
	}

	@Test
	public void testOnlyLowerBounds() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(X, "<:", alpha),
						constraint(Y, "<:", alpha),
						constraint(Z, "<:", alpha)
				},
				Pair.of(alpha, union(X, Y, Z)));
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, ":>", X),
						constraint(alpha, ":>", Y),
						constraint(alpha, ":>", Z)
				},
				Pair.of(alpha, union(X, Y, Z)));
		assertSolution(script,
				new TypeConstraint[] {
						constraint(X, "<:", alpha),
						constraint(alpha, ":>", Y),
						constraint(Z, "<:", alpha)
				},
				Pair.of(alpha, union(X, Y, Z)));
	}

	@Test
	public void testLowerAndUpperBounds_01() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(C1, "<:", alpha),
						constraint(C2, "<:", alpha),
						constraint(C3, "<:", alpha),
						constraint(alpha, "<:", B)
				},
				Pair.of(alpha, union(C1, C2, C3)));
	}

	@Test
	public void testLowerAndUpperBounds_02() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "<:", I1),
						constraint(alpha, "<:", I2),
						constraint(alpha, "<:", I3),
						constraint(A, "<:", alpha)
				},
				Pair.of(alpha, ref(A)));
	}

	@Test
	public void testLowerAndUpperBounds_03() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "<:", ref(I)),
						constraint(intersection(IA, IB), "<:", alpha)
				},
				Pair.of(alpha, intersection(IA, IB)));
	}

	@Test
	public void testTwoInferenceVariables_Simple() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "<:", A),
						constraint(alpha, "=", beta),
						constraint(C, "<:", beta)
				},
				Pair.of(alpha, ref(C)),
				Pair.of(beta, ref(C)));
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "<:", A),
						constraint(alpha, ":>", beta),
						constraint(C, "<:", beta)
				},
				Pair.of(alpha, ref(C)),
				Pair.of(beta, ref(C)));
	}

	@Test
	public void testTwoInferenceVariables_oneInfluencingTheOther() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "<:", beta),
						constraint(A, "<:", beta),
						constraint(I1, "<:", alpha)
				},
				Pair.of(alpha, ref(I1)),
				Pair.of(beta, ref(I1)));
	}

	@Test
	public void testTwoInferenceVariables_leadingToIntersection_01() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "<:", X),
						constraint(beta, "<:", Y),
						constraint(alpha, "=", beta)
				},
				Pair.of(alpha, intersection(X, Y)),
				Pair.of(beta, intersection(X, Y)));
	}

	@Test
	public void testTwoInferenceVariables_leadingToIntersection_02() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "<:", X),
						constraint(alpha, "<:", beta),
						constraint(Y, "<:", beta)
				},
				Pair.of(alpha, intersection(X, Y)),
				Pair.of(beta, ref(Y)));
	}

	@Test
	public void testGenerics() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, "=", of(G, A))
				},
				Pair.of(alpha, of(G, A)));
	}

	@Test
	public void test_deferUnboundedInfVar() {
		assertSolution(script,
				new TypeConstraint[] {
						// here alpha depends on beta, so beta would normally be instantiated first (and because it is
						// unbounded, to "any"); instead, we want beta to be A (see below for real-world example)
						constraint(alpha, "<:", beta),
						constraint(alpha, "<:", A)
				},
				Pair.of(alpha, ref(A)),
				Pair.of(beta, ref(A)));
		// Note: this is motivated by the following real-world use case:
		// function <T> foo({function(T)} fn) : T {return null;}
		// var A test = foo( function(p) {} );
		// (the function expression should be inferred to {function(A):void}, not {function(any):void});
	}

	@Test
	public void test_deferUnboundedInfVar_counterExample01() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, ":>", functionType(ref(beta), of(G, A))), // α :> {function(G<A>):β}
						constraint(gamma, ":>", of(G, alpha)) // γ :> G<α>
				},
				Pair.of(alpha, functionType(anyTypeRef(_G), of(G, A))), // {function(G<A>):any})
				Pair.of(beta, anyTypeRef(_G)),
				Pair.of(gamma, of(G, functionType(anyTypeRef(_G), of(G, A)))) // G<{function(G<A>):any}>)
		);
	}

	@Test
	public void test_deferUnboundedInfVar_counterExample02() {
		UnionTypeExpression alphaSolution = union(functionType(anyTypeRef(_G), of(G, A)),
				functionType(anyTypeRef(_G), of(G, B)));
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, ":>", functionType(ref(beta), of(G, A))), // α :> {function(G<A>):β}
						constraint(alpha, ":>", functionType(ref(gamma), of(G, B))), // α :> {function(G<B>):γ}
						constraint(delta, ":>", of(G, alpha)) // δ :> G<α>
				},
				Pair.of(alpha, alphaSolution), // union{{function(G<A>):any},{function(G<B>):any}})
				Pair.of(beta, anyTypeRef(_G)),
				Pair.of(gamma, anyTypeRef(_G)),
				Pair.of(delta, of(G, alphaSolution)) // G<...>)
		);
	}

	@Test
	public void test_preferUpperBoundsIfLowerBoundsAreUninteresting01() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(undefinedType(_G), "<:", alpha), // null <: α
						constraint(alpha, "<:", A) // α <: A
				},
				Pair.of(alpha, ref(A)));
	}

	@Test
	public void test_preferUpperBoundsIfLowerBoundsAreUninteresting02() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(nullType(_G), "<:", alpha), // null <: α
						constraint(alpha, "<:", A) // α <: A
				},
				Pair.of(alpha, ref(A)));
	}

	@Test
	public void testBoundThisTypeRef01() {
		BoundThisTypeRef thisG = TypeRefsFactory.eINSTANCE.createBoundThisTypeRef();
		thisG.setActualThisTypeRef((ParameterizedTypeRef) rawTypeRef(G));

		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, ":>", thisG),
						constraint(alpha, "<:", of(G, A))
				},
				Pair.of(alpha, thisG));
	}

	@Test
	public void testBoundThisTypeRef02() {
		BoundThisTypeRef thisG = TypeRefsFactory.eINSTANCE.createBoundThisTypeRef();
		thisG.setActualThisTypeRef((ParameterizedTypeRef) rawTypeRef(G));

		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, ":>", thisG)
				},
				Pair.of(alpha, thisG));
	}

	@Test
	public void testRawTypeNotAcceptedAsSolution01() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, ":>", rawTypeRef(G)),
						constraint(alpha, "<:", of(G, A))
				},
				Pair.of(alpha, of(G, A)));
	}

	@Test
	public void testRawTypeNotAcceptedAsSolution02() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, ":>", rawTypeRef(G))
				},
				Pair.of(alpha, of(G, wildcard())));
	}

	// FIXME IDE-1653 more tests like that
	@Test
	public void testArrayVsIterable() {
		assertSolution(script,
				new TypeConstraint[] {
						constraint(A, "<:", alpha),
						constraint(alpha, "<:", A),
						// Array<β> <: Iterable<? extends α>
						constraint(arrayTypeRef(_G, ref(beta)), "<:", iterableTypeRef(_G, wildcardExtends(alpha)))
				},
				Pair.of(alpha, ref(A)),
				Pair.of(beta, ref(A)));
	}

	@Test
	public void testUnionVsUnion01() {
		assertSolution(script,
				new TypeConstraint[] {
						// string|G<A>|H<A> <: string|G<α>|H<α>
						constraint(union(stringTypeRef(_G), of(G, A), of(H, A)), "<:",
								union(stringTypeRef(_G), of(G, alpha), of(H, alpha)))
				},
				Pair.of(alpha, ref(A)));
	}

	@Test
	public void testUnionVsUnion02() {
		assertSolution(script,
				new TypeConstraint[] {
						// string|G<A>|H<A> <: string|G<α>|H<α>
						constraint(union(stringTypeRef(_G), ref(B), of(G, A)), "<:",
								union(stringTypeRef(_G), ref(alpha), of(G, alpha)))
				},
				Pair.of(alpha, ref(A)));
	}
}
