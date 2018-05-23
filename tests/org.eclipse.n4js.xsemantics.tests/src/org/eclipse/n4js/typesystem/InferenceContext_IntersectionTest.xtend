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

import org.eclipse.n4js.ts.types.Type
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class InferenceContext_IntersectionTest extends AbstractInferenceContextTest {

	static val code = '''

		class Strng {} // a String-like class
		class Intgr extends Nmbr {} // an Integer-like class
		class Flt extends Nmbr {} // a Float-like class
		class Nmbr {}

		class Lst<T> {} // a covariant-like, List-like class
		class Cnsmr<T> {} // a contravariant-like, Consumer-like class

	''';

	protected var Type Strng;
	protected var Type Intgr;
	protected var Type Flt;
	protected var Type Nmbr;
	protected var Type Lst;
	protected var Type Cnsmr;

	override protected String getCode() {
		DEFAULT_CODE + code;
	}

	@Before
	override void before() {
		super.before();

		Strng = selectType("Strng");
		Intgr = selectType("Intgr");
		Flt = selectType("Flt");
		Nmbr = selectType("Nmbr");
		Lst = selectType("Lst");
		Cnsmr = selectType("Cnsmr");
	}

	@Test
	def void testInvolvingVarsInIntersect_01() { // IDEBUG-540

		// alpha :> intersection(Strng, beta)
		// beta  :> Intgr

		script.assertSolution(
			#[
				constraint(alpha,':>',intersection(Strng, beta)), // the intersection is uninhabited
				constraint(beta,':>',Intgr)
			],
			//
			alpha -> intersection(Strng, Intgr),
			beta -> Intgr.ref
		)

	}
}
