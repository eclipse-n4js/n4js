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
package org.eclipse.n4js.xsemantics.caching

import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.xsemantics.caching.AbstractTypesystemForPerformanceTest
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test performance involving subtyping
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class PerformanceSubtypeTest extends AbstractTypesystemForPerformanceTest {

	@Test
	def void testSubtyping10() {
		10.repeatedSubtyping
	}

	@Test
	def void testSubtyping100() {
		100.repeatedSubtyping
	}

	@Test
	def void testSubtyping1000() {
		1000.repeatedSubtyping
	}

//	@Test
//	def void testSubtyping10000() {
//		10000.repeatedSubtyping
//	}

	def private repeatedSubtyping(int n) {
		'''
			class A {}
			class B extends A {}
			class C extends B {}

			var a: A = new A();
			var b: B = new B();
			var c: C = new C();

			«FOR i : 1..n»
			a = b
			a = c
			b = c
			«ENDFOR»
		'''.assertValidate(0)
	}

}
