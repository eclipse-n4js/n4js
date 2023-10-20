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
package org.eclipse.n4js.xsemantics.caching;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test performance involving subtyping
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class PerformanceSubtypeTest extends AbstractTypesystemForPerformanceTest {

	@Test
	public void testSubtyping10() {
		repeatedSubtyping(10);
	}

	@Test
	public void testSubtyping100() {
		repeatedSubtyping(100);
	}

	@Test
	public void testSubtyping1000() {
		repeatedSubtyping(1000);
	}

	// @Test
	// public void testSubtyping10000() {
	// repeatedSubtyping(10000);
	// }

	private void repeatedSubtyping(int n) {
		StringBuilder sb = new StringBuilder();
		sb.append("""
				class A {}
				class B extends A {}
				class C extends B {}

				var a: A = new A();
				var b: B = new B();
				var c: C = new C();

				""");
		for (int i = 1; i <= n; i++) {
			sb.append("""
					a = b
					a = c
					b = c
					""");
		}

		assertValidate(sb.toString(), 0);
	}

	@Test(timeout = 1000)
	public void testRecursiveStructuralType() {
		assertValidate("""
				class A {}
				class B {}
				class C {}
				class D {}
				class E {}
				class F {}
				interface ~I<T> {
					public m01(): ~I<A>
					public m02(): ~I<B>
					public m03(): ~I<C>
					public m04(): ~I<D>
					public m05(): ~I<E>
					public m06(): ~I<F>

					public m11(): ~I<A>
					public m12(): ~I<B>
					public m13(): ~I<C>
					public m14(): ~I<D>
					public m15(): ~I<E>
					public m16(): ~I<F>

					public m21(): ~I<A>
					public m22(): ~I<B>
					public m23(): ~I<C>
					public m24(): ~I<D>
					public m25(): ~I<E>
					public m26(): ~I<F>
				}

				let i1: I<string>;
				let i2: I<number>;
				i1 = i2; // <-- took extremely long
				""", 1);
		// 1 errors since string and number are incompatible (despite T never being used, and I being structural)
	}
}
