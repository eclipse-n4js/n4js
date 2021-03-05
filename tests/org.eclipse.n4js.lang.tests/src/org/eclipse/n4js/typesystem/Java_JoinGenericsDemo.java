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

class A {/**/
}

class B extends A {/**/
}

class C extends B {/**/
}

@SuppressWarnings("unused")
class G<T> {/**/
}

class H<T> extends G<T> {/**/
}

@SuppressWarnings("unused")
class Col<T> {/**/
}

class List<T> extends Col<T> {/**/
}

class Gen<T extends B> {
	@SuppressWarnings("unused")
	void foo(T t1) {
		A a = new A();
		B b = new B();
		C c = new C();
		// t = a; Type mismatch: cannot convert from A to T
		a = t;
		// t = b; Type mismatch: cannot convert from B to T
		b = t;
		// t = c; Type mismatch: cannot convert from C to T
		// c = t; Type mismatch: cannot convert from B to T
		t = t1;
		t1 = t;
	}

	public T t;
}

class GenDemo {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void f(Gen g1, Gen g2) {
		g1.t = g2.t;
		B b = new B();
		g1.t = b;
		b = g2.t;
	}
}

/**
 * This class demonstrates Java's type inference and computation of "join". It is not a test but used to align N4JS
 * tests.
 */
public class Java_JoinGenericsDemo {

	G<A> ga = null;
	G<B> gb = null;
	G<C> gc = null;

	G<? extends A> gea = null;
	G<? extends B> geb = null;
	G<? extends C> gec = null;

	G<? super A> gsa = null;
	G<? super B> gsb = null;
	G<? super C> gsc = null;

	G<?> gw = null;
	@SuppressWarnings("rawtypes")
	G g = null;

	A a = null;
	B b = null;
	C c = null;

	/***/
	@SuppressWarnings("unused")
	public void lcst() {
		// def void testJoinWithGenericsSameNoWildcard() {
		G<B> join_gb_gb = (true) ? gb : gb;

		// def void testJoinWithGenericsNoWildcard() {
		/* G<? extends A> */G<? extends A> join_ga_gb = (true) ? ga : gb;
		/* G<? extends B> */G<? extends B> join_gc_gb = (true) ? gc : gb;

		// def void testJoinWithUnboundWildcard() {
		/* G<?> */G<? extends Object> join_gw_gb = (true) ? gw : gb;
		/* G<?> */G<? extends Object> join_gw_geb = (true) ? gw : geb;
		/* G<?> */G<? extends Object> join_gw_gsb = (true) ? gw : gsb;

		// def void testJoinWithUpperBoundWildcard() {
		/* G<? extends B> */G<? extends B> join_geb_geb = (true) ? geb : geb;
		/* G<? extends A> */G<? extends A> join_gea_gb = (true) ? gea : gb;
		/* G<? extends B> */G<? extends B> join_geb_gb = (true) ? geb : gb;
		/* G<? extends B> */G<? extends B> join_gec_gb = (true) ? gec : gb;
		/* G<? extends B> */G<? extends B> join_gec_geb = (true) ? gec : geb;
		/* G<? extends A> */G<? extends A> join_gea_geb = (true) ? gea : geb;

		// def void testJoinWithLowerBoundWildcard() {
		/* !!! G<? super B> */G<? extends Object> join_gsa_gb = (true) ? gsa : gb;
		/* !!! G<? super B> */G<? extends Object> join_gsb_gsb = (true) ? gsb : gsb;
		/* !!! G<? super B> */G<? extends Object> join_gsb_gb = (true) ? gsb : gb;
		/* !!! G<? super B> */G<? extends Object> join_gsb_gsb1 = (true) ? gsb : gsb;

		/* !!! G<? super C> */G<? extends Object> join_gsc_gb = (true) ? gsc : gb;

	}

}
