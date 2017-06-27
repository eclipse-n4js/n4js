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
package org.eclipse.n4js.utils.collections

import static extension org.eclipse.n4js.utils.collections.Iterables2.*
import static extension org.junit.Assert.*
import org.junit.Test

/**
 * Tests for {@link Iterables2}.
 */
class Iterables2Test {

	@Test(expected = NullPointerException)
	def void testNullElementsExpectNPE() {
		null.chainOf(new Object);
	}

	@Test(expected = IllegalArgumentException)
	def void testNotContainedRefExpectIAE() {
		#[].chainOf(new Object);
	}

	@Test(expected = IllegalArgumentException)
	def void testNullRefAsNotContainedRefExpectIAE() {
		#[1, 2, 3, 4].chainOf(null);
	}

	@Test
	def void testChainOfWithSingleContainment() {
		#[1, 2, 3, 4, 5].chainOf(3).assertEquals(#[3, 4, 5, 1, 2, 3]);
	}

	@Test
	def void testChainOfWithMultipleContainment() {
		#[1, -1, 3, -1, 4, 5].chainOf(-1).assertEquals(#[-1, 3, -1, 4, 5, 1, -1]);
	}

}
