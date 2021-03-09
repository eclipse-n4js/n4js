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
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class InferenceContext_Union2Test extends AbstractInferenceContextTest {

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

	/*
	 * IDE-1596
	 *
	 * These tests are based on http://ceylon-lang.org/documentation/tour/generics/
	 * Caveat: Ceylon's type system has declaration-site variance.
	 *
	 * Quoting from there:
	 *
	 * First, consider a covariant type like List<Element>. Then for any types X and Y:
	 *   List<X> | List<Y> is a subtype of   List<X | Y> , and
	 *   List<X> & List<Y> is a supertype of List<X & Y>.
	 *
	 * Next, consider a contravariant type like Consumer<Element>. Then for any types X and Y:
	 *   Consumer<X> | Consumer<Y> is a subtype of   Consumer<X & Y> , and
	 *   Consumer<X> & Consumer<Y> is a supertype of Consumer<X | Y>.
	 *
	 */

	@Test @Ignore
	def void testInferenceOfTypeArg() {

		// Given a covariant type List<Element>, the union List<String>|List<Integer> is a subtype of List<String|Integer>.

		// function <alpha> foo(Lst<alpha> p)
		// is invoked with an argument with union type:
		// foo( cond ? listOfString : listOfInteger )

		val listOfString = Lst.of(Strng)
		val listOfInteger = Lst.of(Intgr)
		val listOfAlpha = Lst.of(alpha)

		script.assertSolution(
			#[
				constraint(listOfString,'<:',listOfAlpha),
				constraint(listOfInteger,'<:',listOfAlpha)
			],
			//
			alpha -> union(Strng,Intgr)
		)

		// solving the following constraint set:
		// ⟨ Lst<Strng> <: Lst<α> ⟩
		// ⟨ Lst<Intgr> <: Lst<α> ⟩
		// inference variables: α
		// ****** Reduction
		// reducing: ⟨ Lst<Strng> <: Lst<α> ⟩
		// reducing: ⟨ Strng = α ⟩
		// >>> ADD bound: α = Strng  (ADDED)
		// reducing: ⟨ Lst<Intgr> <: Lst<α> ⟩
		// reducing: ⟨ Intgr = α ⟩
		// >>> ADD bound: α = Intgr  (ADDED)
		// ****** Incorporation
		// --- incorporating:  α = Strng  |  α = Intgr
		// reducing: ⟨ Strng = Intgr ⟩

	}

	@Test
	def void testInvolvingVarsInUnion_01() {

		// alpha <: union(Strng, beta)
		// beta  <: Intgr

		script.assertSolution(
			#[
				constraint(alpha,'<:',union(Strng, beta)),
				constraint(beta,'<:',Intgr)
			],
			//
			alpha -> union(Strng,Intgr),
			beta -> Intgr.ref
		)

	}

	@Test
	def void testInvolvingVarsInUnion_02() {

		// alpha :> union(Strng, beta)
		// beta  :> Intgr

		script.assertSolution(
			#[
				constraint(alpha,':>',union(Strng, beta)), // the intersection is uninhabited
				constraint(beta,':>',Intgr)
			],
			//
			alpha -> union(Intgr, Strng),
			beta -> Intgr.ref
		)

	}

}
