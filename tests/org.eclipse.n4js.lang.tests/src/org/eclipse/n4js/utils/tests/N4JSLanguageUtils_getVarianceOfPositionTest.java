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

import static org.eclipse.n4js.ts.types.util.Variance.CO;
import static org.eclipse.n4js.ts.types.util.Variance.CONTRA;
import static org.eclipse.n4js.ts.types.util.Variance.INV;

import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.junit.Test;

/**
 * Tests for method {@link N4JSLanguageUtils#getVarianceOfPosition(org.eclipse.n4js.ts.typeRefs.TypeRef)}.
 */
public class N4JSLanguageUtils_getVarianceOfPositionTest extends AbstractN4JSLanguageUtilsTest {

	@Test
	public void testBaseCases() {
		assertVarianceOfPosition("class C<T> { field: T; }", INV);
		assertVarianceOfPosition("class C<T> { get g(): T {return null;} }", CO);
		assertVarianceOfPosition("class C<T> { set s(p: T) {} }", CONTRA);
		assertVarianceOfPosition("class C<T> { m(): T {return null;} }", CO);
		assertVarianceOfPosition("class C<T> { m(p: T) {} }", CONTRA);
	}

	@Test
	public void testPrivateVisibility() {
		assertVarianceOfPosition("class C<T> { private field: T; }", null);
		assertVarianceOfPosition("class C<T> { private get g(): T {return null;} }", null);
		assertVarianceOfPosition("class C<T> { private set s(p: T) {} }", null);
		assertVarianceOfPosition("class C<T> { private m(): T {return null;} }", null);
		assertVarianceOfPosition("class C<T> { private m(p: T) {} }", null);
	}

	@Test
	public void testConstructor() {
		assertVarianceOfPosition("class C<T> { constructor(p: T) {} }", null);
	}

	@Test
	public void testFinalField() {
		assertVarianceOfPosition("class C<T> { @Final public field: T = null; }", CO);
	}

	@Test
	public void testPassingOnAsTypeArgument() {
		assertVarianceOfPosition("""
				class G<S1,S2,S3> {}
				class C<T> extends G<string,T,number> {}
				""", INV);
		assertVarianceOfPosition("""
				class G<S1,out S2,S3> {}
				class C<T> extends G<string,T,number> {}
				""", CO);
		assertVarianceOfPosition("""
				class G<S1,in S2,S3> {}
				class C<T> extends G<string,T,number> {}
				""", CONTRA);
	}
}
