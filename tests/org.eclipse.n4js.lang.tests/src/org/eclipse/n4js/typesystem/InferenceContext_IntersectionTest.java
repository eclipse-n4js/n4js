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
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.typesystem.constraints.TypeConstraint;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@SuppressWarnings("unchecked")
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class InferenceContext_IntersectionTest extends AbstractInferenceContextTest {

	static String code = """

			class Strng {} // a String-like class
			class Intgr extends Nmbr {} // an Integer-like class
			class Flt extends Nmbr {} // a Float-like class
			class Nmbr {}

			class Lst<T> {} // a covariant-like, List-like class
			class Cnsmr<T> {} // a contravariant-like, Consumer-like class

			""";

	protected Type Strng;
	protected Type Intgr;
	protected Type Flt;
	protected Type Nmbr;
	protected Type Lst;
	protected Type Cnsmr;

	@Override
	protected String getCode() {
		return DEFAULT_CODE + code;
	}

	@Override
	@Before
	public void before() {
		super.before();

		Strng = selectType("Strng");
		Intgr = selectType("Intgr");
		Flt = selectType("Flt");
		Nmbr = selectType("Nmbr");
		Lst = selectType("Lst");
		Cnsmr = selectType("Cnsmr");
	}

	@Test
	public void testInvolvingVarsInIntersect_01() { // IDEBUG-540

		// alpha :> intersection(Strng, beta)
		// beta :> Intgr

		assertSolution(script,
				new TypeConstraint[] {
						constraint(alpha, ":>", intersection(Strng, beta)), // the intersection is uninhabited
						constraint(beta, ":>", Intgr)
				},
				//
				Pair.of(alpha, intersection(Strng, Intgr)),
				Pair.of(beta, ref(Intgr)));

	}
}
