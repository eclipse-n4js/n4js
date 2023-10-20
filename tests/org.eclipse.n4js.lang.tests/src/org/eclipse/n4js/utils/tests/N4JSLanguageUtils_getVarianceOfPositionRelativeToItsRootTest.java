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
package org.eclipse.n4js.utils.tests;

import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.junit.Test;

/**
 * Tests for method {@link N4JSLanguageUtils#getVarianceOfPositionRelativeToItsRoot(TypeRef)}.
 */
public class N4JSLanguageUtils_getVarianceOfPositionRelativeToItsRootTest extends AbstractN4JSLanguageUtilsTest {

	private CharSequence wrap(CharSequence code) {
		return """
				class A {}
				class B {}
				class C {}
				class G<T> {}
				class G_CO<out T> {}
				class G_CONTRA<in T> {}

				class Test<T> {
					m() {
						var test: %s;
					}
				}
				""".formatted(code);
	}

	@Test
	public void testBaseCases() {
		assertVarianceOfPositionRelativeToItsRoot(wrap("T"), Variance.CO);
	}

	@Test
	public void testNestedAsTypeArgument() {
		assertVarianceOfPositionRelativeToItsRoot(wrap("G<T>"), Variance.INV);
		assertVarianceOfPositionRelativeToItsRoot(wrap("G<? extends T>"), Variance.CO);
		assertVarianceOfPositionRelativeToItsRoot(wrap("G<? super T>"), Variance.CONTRA);
		assertVarianceOfPositionRelativeToItsRoot(wrap("G_CO<T>"), Variance.CO);
		assertVarianceOfPositionRelativeToItsRoot(wrap("G_CONTRA<T>"), Variance.CONTRA);
	}

	@Test
	public void testNestedInFunctionTypeExpression() {
		assertVarianceOfPositionRelativeToItsRoot(wrap("{function():T}"), Variance.CO);
		assertVarianceOfPositionRelativeToItsRoot(wrap("{function(string,T,number)}"), Variance.CONTRA);
	}

	@Test
	public void testNestedInComposedTypeRef() {
		assertVarianceOfPositionRelativeToItsRoot(wrap("union{string,T,number}"), Variance.CO);
		assertVarianceOfPositionRelativeToItsRoot(wrap("intersection{string,T,number}"), Variance.CO);
	}

	@Test
	public void testNestedInClassifierTypeRef() {
		assertVarianceOfPositionRelativeToItsRoot(wrap("type{T}"), Variance.CO);
		assertVarianceOfPositionRelativeToItsRoot(wrap("constructor{T}"), Variance.INV);
	}

	@Test
	public void testDeeperNesting() {
		assertVarianceOfPositionRelativeToItsRoot(wrap("G<? extends G<? extends T>>"), Variance.CO);
		assertVarianceOfPositionRelativeToItsRoot(wrap("G<? extends {function(union{string,G<? super T>})}>"),
				Variance.CO);
		assertVarianceOfPositionRelativeToItsRoot(wrap("G<? extends {function():union{string,G<? super T>}}>"),
				Variance.CONTRA);
		assertVarianceOfPositionRelativeToItsRoot(wrap("G<? extends {function(union{string,G<T>})}>"), Variance.INV);
		assertVarianceOfPositionRelativeToItsRoot(wrap("G<{function(union{string,G<? super T>})}>"), Variance.INV);
	}
}
