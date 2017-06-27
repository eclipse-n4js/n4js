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

import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.junit.Test

import static org.eclipse.n4js.ts.types.util.Variance.*

/**
 * Tests for method {@link N4JSLanguageUtils#getVarianceOfPosition(ParameterizedTypeRef)}.
 */
class N4JSLanguageUtils_getVarianceOfPositionTest extends AbstractN4JSLanguageUtilsTest {

	@Test
	def public void testBaseCases() {
		"class C<T> { field: T; }".assertVarianceOfPosition(INV);
		"class C<T> { get g(): T {return null;} }".assertVarianceOfPosition(CO);
		"class C<T> { set s(p: T) {} }".assertVarianceOfPosition(CONTRA);
		"class C<T> { m(): T {return null;} }".assertVarianceOfPosition(CO);
		"class C<T> { m(p: T) {} }".assertVarianceOfPosition(CONTRA);
	}

	@Test
	def public void testPrivateVisibility() {
		"class C<T> { private field: T; }".assertVarianceOfPosition(null);
		"class C<T> { private get g(): T {return null;} }".assertVarianceOfPosition(null);
		"class C<T> { private set s(p: T) {} }".assertVarianceOfPosition(null);
		"class C<T> { private m(): T {return null;} }".assertVarianceOfPosition(null);
		"class C<T> { private m(p: T) {} }".assertVarianceOfPosition(null);
	}

	@Test
	def public void testConstructor() {
		"class C<T> { constructor(p: T) {} }".assertVarianceOfPosition(null);
	}

	@Test
	def public void testFinalField() {
		"class C<T> { @Final public field: T = null; }".assertVarianceOfPosition(CO);
	}

	@Test
	def public void testPassingOnAsTypeArgument() {
		'''
			class G<S1,S2,S3> {}
			class C<T> extends G<string,T,number> {}
		'''.assertVarianceOfPosition(INV)
		'''
			class G<S1,out S2,S3> {}
			class C<T> extends G<string,T,number> {}
		'''.assertVarianceOfPosition(CO)
		'''
			class G<S1,in S2,S3> {}
			class C<T> extends G<string,T,number> {}
		'''.assertVarianceOfPosition(CONTRA)
	}
}
