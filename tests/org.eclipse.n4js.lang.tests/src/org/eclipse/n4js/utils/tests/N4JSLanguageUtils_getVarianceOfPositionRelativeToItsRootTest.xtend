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
package org.eclipse.n4js.utils.tests

import org.eclipse.n4js.ts.types.util.Variance
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.junit.Test

/**
 * Tests for method {@link N4JSLanguageUtils#getVarianceOfPositionRelativeToItsRoot(TypeRef)}.
 */
class N4JSLanguageUtils_getVarianceOfPositionRelativeToItsRootTest extends AbstractN4JSLanguageUtilsTest {

	def private CharSequence wrap(CharSequence code) {
		'''
			class A {}
			class B {}
			class C {}
			class G<T> {}
			class G_CO<out T> {}
			class G_CONTRA<in T> {}

			class Test<T> {
				m() {
					var test: «code»;
				}
			}
		'''
	}

	@Test
	def public void testBaseCases() {
		"T".wrap.assertVarianceOfPositionRelativeToItsRoot(Variance.CO);
	}

	@Test
	def public void testNestedAsTypeArgument() {
		"G<T>".wrap.assertVarianceOfPositionRelativeToItsRoot(Variance.INV);
		"G<? extends T>".wrap.assertVarianceOfPositionRelativeToItsRoot(Variance.CO);
		"G<? super T>".wrap.assertVarianceOfPositionRelativeToItsRoot(Variance.CONTRA);
		"G_CO<T>".wrap.assertVarianceOfPositionRelativeToItsRoot(Variance.CO);
		"G_CONTRA<T>".wrap.assertVarianceOfPositionRelativeToItsRoot(Variance.CONTRA);
	}

	@Test
	def public void testNestedInFunctionTypeExpression() {
		"{function():T}".wrap.assertVarianceOfPositionRelativeToItsRoot(Variance.CO);
		"{function(string,T,number)}".wrap.assertVarianceOfPositionRelativeToItsRoot(Variance.CONTRA);
	}

	@Test
	def public void testNestedInComposedTypeRef() {
		"union{string,T,number}".wrap.assertVarianceOfPositionRelativeToItsRoot(Variance.CO);
		"intersection{string,T,number}".wrap.assertVarianceOfPositionRelativeToItsRoot(Variance.CO);
	}

	@Test
	def public void testNestedInClassifierTypeRef() {
		"type{T}".wrap.assertVarianceOfPositionRelativeToItsRoot(Variance.CO);
		"constructor{T}".wrap.assertVarianceOfPositionRelativeToItsRoot(Variance.INV);
	}

	@Test
	def public void testDeeperNesting() {
		"G<? extends G<? extends T>>".wrap.assertVarianceOfPositionRelativeToItsRoot(Variance.CO);
		"G<? extends {function(union{string,G<? super T>})}>".wrap.assertVarianceOfPositionRelativeToItsRoot(Variance.CO);
		"G<? extends {function():union{string,G<? super T>}}>".wrap.assertVarianceOfPositionRelativeToItsRoot(Variance.CONTRA);
		"G<? extends {function(union{string,G<T>})}>".wrap.assertVarianceOfPositionRelativeToItsRoot(Variance.INV);
		"G<{function(union{string,G<? super T>})}>".wrap.assertVarianceOfPositionRelativeToItsRoot(Variance.INV);
	}
}
